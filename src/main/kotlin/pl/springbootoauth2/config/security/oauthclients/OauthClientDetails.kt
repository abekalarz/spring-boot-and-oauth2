package pl.springbootoauth2.config.security.oauthclients

data class OauthClientDetails(
        val clientId: String,
        val resourceIds: Array<String>?,
        val clientSecret: String?,
        val scope: Array<String>,
        val authorizedGrantTypes: Array<String>,
        val webServerRedirectUri: Array<String>,
        val authorities: Array<String>?,
        val accessTokenValidity: Int,
        val refreshTokenValidity: Int?,
        val additionalInformation: Array<String>?,
        val autoApprove: Boolean
)