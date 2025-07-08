# resource "azurerm_redis_cache" "redis_cache" {
#   name                = "java-job-portal-redis"
#   location            = azurerm_resource_group.resource_group.location
#   resource_group_name = azurerm_resource_group.resource_group.name
#   capacity            = 0
#   family              = "C"
#   sku_name            = "Standard"
#   minimum_tls_version = "1.2"

#   redis_configuration {
#   }
# }

resource "azurerm_postgresql_flexible_server" "psql" {
  name                          = "psql-oefknkefnmjs"
  resource_group_name           = azurerm_resource_group.resource_group.name
  location                      = azurerm_virtual_network.vnet.location
  version                       = "16"
  delegated_subnet_id           = azurerm_subnet.psql_subnet.id
  private_dns_zone_id           = azurerm_private_dns_zone.dns_zone.id
  public_network_access_enabled = false
  administrator_login           = var.postgres_user
  administrator_password        = var.postgres_password
  zone                          = "3"

  storage_mb = 32768

  sku_name   = "B_Standard_B1ms"
  depends_on = [azurerm_private_dns_zone_virtual_network_link.vnet_link, azurerm_subnet.psql_subnet]

}
