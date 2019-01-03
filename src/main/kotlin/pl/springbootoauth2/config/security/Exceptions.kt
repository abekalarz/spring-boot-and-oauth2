package pl.springbootoauth2.config.security

open class OauthClientDetailsNotFoundException(clientId: String?): RuntimeException("OAuth client details for client_id '$clientId' not found.")
