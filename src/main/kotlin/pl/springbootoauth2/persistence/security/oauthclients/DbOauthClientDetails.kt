package pl.springbootoauth2.persistence.security.oauthclients

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document
import pl.springbootoauth2.config.security.oauthclients.OauthClientDetails

// TODO Find out way to be safer! -? I mean, implement and verify 'resourceIds' validation !
// Example of class with use of this: https://github.com/spring-projects/spring-security-oauth/blob/master/tests/annotation/multi/src/main/java/demo/Application.java

@Document(collection = "oauth_client_details")
data class DbOauthClientDetails @PersistenceConstructor constructor(
        @Id val id: String,
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
) {
    companion object {
        fun fromOauthClientDetails(oauthClient: OauthClientDetails) = DbOauthClientDetails(
                oauthClient.clientId,
                oauthClient.resourceIds,
                oauthClient.clientSecret,
                oauthClient.scope,
                oauthClient.authorizedGrantTypes,
                oauthClient.webServerRedirectUri,
                oauthClient.authorities,
                oauthClient.accessTokenValidity,
                oauthClient.refreshTokenValidity,
                oauthClient.additionalInformation,
                oauthClient.autoApprove
        )
    }
}
