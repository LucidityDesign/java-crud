resource "azurerm_key_vault" "key_vault" {
  name                       = "java-crud-vault-iufoerj"
  location                   = azurerm_resource_group.resource_group.location
  resource_group_name        = azurerm_resource_group.resource_group.name
  tenant_id                  = data.azurerm_client_config.current.tenant_id
  sku_name                   = "standard"
  soft_delete_retention_days = 7
  purge_protection_enabled   = true

  access_policy {
    tenant_id = data.azurerm_client_config.current.tenant_id
    object_id = data.azurerm_client_config.current.object_id

    secret_permissions = [
      "Get", "List", "Set", "Delete", "Recover"
    ]
  }
}

resource "azurerm_key_vault_access_policy" "key_vault_access_policy" {
  key_vault_id = azurerm_key_vault.key_vault.id
  tenant_id    = data.azurerm_client_config.current.tenant_id
  object_id    = azurerm_linux_web_app.web_app.identity[0].principal_id

  secret_permissions = [
    "Get", "List"
  ]

  depends_on = [azurerm_linux_web_app.web_app]
}

data "azurerm_client_config" "current" {}

resource "azurerm_role_assignment" "keyvault_reader" {
  scope                = azurerm_key_vault.key_vault.id
  role_definition_name = "Key Vault Secrets User"
  principal_id         = data.azurerm_client_config.current.object_id
}

resource "azurerm_key_vault_secret" "POSTGRES_USER" {
  name         = "postgres-user"
  value        = var.postgres_user
  key_vault_id = azurerm_key_vault.key_vault.id
}

resource "azurerm_key_vault_secret" "POSTGRES_PASSWORD" {
  name         = "postgres-password"
  value        = var.postgres_password
  key_vault_id = azurerm_key_vault.key_vault.id
}

resource "azurerm_key_vault_secret" "POSTGRES_DB" {
  name         = "postgres-db"
  value        = var.postgres_db
  key_vault_id = azurerm_key_vault.key_vault.id
}

resource "azurerm_key_vault_secret" "POSTGRES_HOST" {
  name         = "postgres-host"
  value        = azurerm_postgresql_flexible_server.psql.fqdn
  key_vault_id = azurerm_key_vault.key_vault.id
}

resource "azurerm_key_vault_secret" "OAUTH2_CLIENT_SECRET" {
  name         = "oauth2-client-secret"
  value        = azuread_application_password.java_sso_secret.value
  key_vault_id = azurerm_key_vault.key_vault.id
}

resource "azurerm_key_vault_secret" "AZURE_TENANT_ID" {
  name         = "azure-tenant-id"
  value        = data.azuread_client_config.current.tenant_id
  key_vault_id = azurerm_key_vault.key_vault.id
}

resource "azurerm_key_vault_secret" "AZURE_CLIENT_ID" {
  name         = "azure-client-id"
  value        = azuread_application.java_sso.client_id
  key_vault_id = azurerm_key_vault.key_vault.id
}

resource "azurerm_key_vault_secret" "AZURE_CLIENT_SECRET" {
  name         = "azure-client-secret"
  value        = azuread_application_password.java_sso_secret.value
  key_vault_id = azurerm_key_vault.key_vault.id

  depends_on = [azuread_application_password.java_sso_secret]
}

resource "azurerm_key_vault_secret" "AZURE_STORAGE_KEY" {
  name         = "azure-storage-key"
  value        = azurerm_storage_account.storage_account.primary_access_key
  key_vault_id = azurerm_key_vault.key_vault.id
}
