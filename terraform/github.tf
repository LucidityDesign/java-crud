provider "github" {
  token = var.github_token # GitHub personal access token (with repo/admin access)
  owner = var.github_owner # GitHub username or organization
}

data "github_actions_public_key" "public_key" {
  repository = "java-crud"
}

resource "github_actions_secret" "AZURE_TENANT_ID" {
  repository      = "java-crud"
  secret_name     = "AZURE_TENANT_ID"
  plaintext_value = data.azuread_client_config.current.tenant_id
}

resource "github_actions_secret" "AZURE_CLIENT_ID" {
  repository      = "java-crud"
  secret_name     = "AZURE_CLIENT_ID"
  plaintext_value = azuread_application.java_sso.client_id

  depends_on = [azuread_application.java_sso]
}

resource "github_actions_secret" "AZURE_SUBSCRIPTION_ID" {
  repository      = "java-crud"
  secret_name     = "AZURE_SUBSCRIPTION_ID"
  plaintext_value = var.subscription_id
}

resource "github_actions_secret" "KEYVAULT_NAME" {
  repository      = "java-crud"
  secret_name     = "KEYVAULT_NAME"
  plaintext_value = azurerm_key_vault.key_vault.name

  depends_on = [azurerm_key_vault.key_vault]
}
