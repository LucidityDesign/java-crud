# Configure the Azure provider
terraform {
  required_providers {
    azurerm = {
      source = "hashicorp/azurerm"
    }
    azuread = {
      source  = "hashicorp/azuread"
      version = ">= 2.38.0"
    }
  }

  required_version = ">= 1.1.0"
}

provider "azurerm" {
  features {}

  subscription_id = var.subscription_id
}

provider "azuread" {
  # AzureAD provider is required for identity-related resources
}

resource "azurerm_resource_group" "resource_group" {
  location = var.location
  name     = var.resource_group_name
}

resource "azurerm_storage_account" "storage_account" {
  account_replication_type = "LRS"
  location                 = azurerm_resource_group.resource_group.location
  name                     = var.storage_account_name
  resource_group_name      = azurerm_resource_group.resource_group.name
  account_tier             = "Standard"
}

resource "azurerm_service_plan" "service_plan" {
  name                = "java-service-plan"
  resource_group_name = azurerm_resource_group.resource_group.name
  location            = azurerm_resource_group.resource_group.location
  os_type             = "Linux"
  sku_name            = "B1"
}

resource "azurerm_linux_web_app" "web_app" {
  name                = "java-crud-app-kjrkrgl"
  resource_group_name = azurerm_resource_group.resource_group.name
  location            = azurerm_service_plan.service_plan.location
  service_plan_id     = azurerm_service_plan.service_plan.id

  identity {
    type = "SystemAssigned"
  }

  site_config {
    always_on = true
    application_stack {
      java_version        = "21"
      java_server         = "JAVA"
      java_server_version = "21"
    }
  }

  app_settings = {
    "POSTGRES_USER"              = "@Microsoft.KeyVault(SecretUri=${azurerm_key_vault_secret.POSTGRES_USER.id})"
    "POSTGRES_PASSWORD"          = "@Microsoft.KeyVault(SecretUri=${azurerm_key_vault_secret.POSTGRES_PASSWORD.id})"
    "POSTGRES_DB"                = "@Microsoft.KeyVault(SecretUri=${azurerm_key_vault_secret.POSTGRES_DB.id})"
    "POSTGRES_HOST"              = "@Microsoft.KeyVault(SecretUri=${azurerm_key_vault_secret.POSTGRES_HOST.id})"
    "OAUTH2_CLIENT_SECRET"       = "@Microsoft.KeyVault(SecretUri=${azurerm_key_vault_secret.OAUTH2_CLIENT_SECRET.id})"
    "AZURE_TENANT_ID"            = "@Microsoft.KeyVault(SecretUri=${azurerm_key_vault_secret.AZURE_TENANT_ID.id})"
    "AZURE_CLIENT_ID"            = "@Microsoft.KeyVault(SecretUri=${azurerm_key_vault_secret.AZURE_CLIENT_ID.id})"
    "AZURE_CLIENT_SECRET"        = "@Microsoft.KeyVault(SecretUri=${azurerm_key_vault_secret.AZURE_CLIENT_SECRET.id})"
    "AZURE_STORAGE_KEY"          = "@Microsoft.KeyVault(SecretUri=${azurerm_key_vault_secret.AZURE_STORAGE_KEY.id})"
    "AZURE_STORAGE_ACCOUNT_NAME" = var.storage_account_name
  }
}

resource "azuread_application_password" "java_sso_secret" {
  application_id = azuread_application.java_sso.id
  display_name   = "java-sso-client-secret"
  end_date       = "2027-01-01T00:00:00Z"
}


resource "azurerm_virtual_network" "vnet" {
  name                = "example-vn"
  location            = var.location
  resource_group_name = azurerm_resource_group.resource_group.name
  address_space       = ["10.0.0.0/16"]
}

resource "azurerm_subnet" "webapp_subnet" {
  name                 = "webapp-sn"
  resource_group_name  = azurerm_resource_group.resource_group.name
  virtual_network_name = azurerm_virtual_network.vnet.name
  address_prefixes     = ["10.0.3.0/24"]

  delegation {
    name = "webapp-delegation"
    service_delegation {
      name = "Microsoft.Web/serverFarms"
      actions = [
        "Microsoft.Network/virtualNetworks/subnets/action",
      ]
    }
  }
}

resource "azurerm_app_service_virtual_network_swift_connection" "webapp_vnet_integration" {
  app_service_id = azurerm_linux_web_app.web_app.id
  subnet_id      = azurerm_subnet.webapp_subnet.id
}

resource "azurerm_subnet" "psql_subnet" {
  name                 = "psql-sn"
  resource_group_name  = azurerm_resource_group.resource_group.name
  virtual_network_name = azurerm_virtual_network.vnet.name
  address_prefixes     = ["10.0.2.0/24"]
  service_endpoints    = ["Microsoft.Storage"]
  delegation {
    name = "fs"
    service_delegation {
      name = "Microsoft.DBforPostgreSQL/flexibleServers"
      actions = [
        "Microsoft.Network/virtualNetworks/subnets/join/action",
      ]
    }
  }
}

resource "azurerm_private_dns_zone" "dns_zone" {
  name                = "example.postgres.database.azure.com"
  resource_group_name = azurerm_resource_group.resource_group.name
}

resource "azurerm_private_dns_zone_virtual_network_link" "vnet_link" {
  name                  = "exampleVnetZone.com"
  private_dns_zone_name = azurerm_private_dns_zone.dns_zone.name
  virtual_network_id    = azurerm_virtual_network.vnet.id
  resource_group_name   = azurerm_resource_group.resource_group.name
  depends_on            = [azurerm_subnet.psql_subnet]
}

resource "azuread_application_registration" "java_sso" {
  display_name = var.app_display_name
}

resource "azuread_application" "java_sso" {
  display_name     = var.app_display_name
  sign_in_audience = "AzureADMyOrg"

  # identifier_uris = [
  #   "api://${azuread_application.java_sso.client_id}"
  # ]

  owners = [var.owner_object_id]

  api {
    oauth2_permission_scope {
      admin_consent_description  = "access_as_user"
      admin_consent_display_name = "access_as_user"
      id                         = "9f8615f6-1768-4dee-9cf8-3bec67132d6b"
      type                       = "Admin"
      value                      = "access_as_user"
      enabled                    = true
    }
  }

  app_role {
    allowed_member_types = ["User"]
    description          = "Regular Users"
    display_name         = "User"
    id                   = "c0b1e526-3aa7-43c6-99a8-4bd557403e90"
    enabled              = true
    value                = "ROLE_USER"
  }

  app_role {
    allowed_member_types = ["User"]
    description          = "Administrators"
    display_name         = "Admin"
    id                   = "a4ace53c-29b8-4b55-8923-823c943bfcbe"
    enabled              = true
    value                = "ROLE_ADMIN"
  }

  required_resource_access {
    resource_app_id = "00000003-0000-0000-c000-000000000000" # Microsoft Graph

    # User.Read, Delegated
    resource_access {
      id   = "e1fe6dd8-ba31-4d61-89e7-88639da4683d"
      type = "Scope"
    }
  }

  web {
    redirect_uris = [
      "http://localhost:8080/login/oauth2/code/azure-dev",
      "https://java-crud-app-kjrkrgl.azurewebsites.net/login/oauth2/code/azure-dev",
    ]

    implicit_grant {
      access_token_issuance_enabled = false
      id_token_issuance_enabled     = false
    }
  }

  single_page_application {
    redirect_uris = [
      "https://java-crud-app-kjrkrgl.azurewebsites.net/login/oauth2/code/azure/",
      "http://localhost:8080/login/oauth2/code/azure/",
    ]
  }

  depends_on = [azuread_application.java_sso]
}

data "azuread_client_config" "current" {}
