package pl.springbootoauth2.config.security.oauthclients

import pl.springbootoauth2.persistence.security.oauthclients.DbOauthClientDetails

interface OauthClientDetailsRepository {
    fun save(clientDetails: OauthClientDetails)
    fun findAll(): List<DbOauthClientDetails>
}