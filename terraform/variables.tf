variable "subscription_id" {
  description = "Azure Subscription ID"
  type        = string
}

variable "location" {
  description = "Azure Region"
  type        = string
  default     = "germanywestcentral"
}

variable "resource_group_name" {
  description = "Name of the Resource Group"
  type        = string
}

variable "storage_account_name" {
  description = "Storage Account Name"
  type        = string
}

variable "app_display_name" {
  description = "Display name for the AzureAD application"
  type        = string
}

variable "identifier_uri" {
  description = "Identifier URI for the AzureAD application"
  type        = string
}

variable "owner_object_id" {
  description = "Azure AD Object ID of the application owner"
  type        = string
}
