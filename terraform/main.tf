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

resource "azurerm_resource_group" "res-0" {
  location = var.location
  name     = var.resource_group_name
}

resource "azurerm_storage_account" "res-1" {
  account_replication_type = "LRS"
  location                 = var.location
  name                     = var.storage_account_name
  resource_group_name      = var.resource_group_name
  account_tier             = "Standard"
}

resource "azuread_application_registration" "java_sso" {
  display_name = var.app_display_name
}

resource "azuread_application" "java_sso" {
  display_name     = var.app_display_name
  sign_in_audience = "AzureADMyOrg"

  identifier_uris = [
    var.identifier_uri
  ]

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
    ]

    implicit_grant {
      access_token_issuance_enabled = false
      id_token_issuance_enabled     = false
    }
  }

  single_page_application {
    redirect_uris = [
      "http://localhost:8080/login/oauth2/code/azure/",
    ]
  }
}
