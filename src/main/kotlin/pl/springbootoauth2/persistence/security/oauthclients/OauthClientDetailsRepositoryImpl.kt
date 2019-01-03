package pl.springbootoauth2.persistence.security.oauthclients

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component
import pl.springbootoauth2.config.security.OauthClientDetailsNotFoundException
import pl.springbootoauth2.config.security.oauthclients.OauthClientDetails
import pl.springbootoauth2.config.security.oauthclients.OauthClientDetailsRepository

@Component
class OauthClientDetailsRepositoryImpl(
        private val dbRepository: DbOauthClientDetailsRepository
): OauthClientDetailsRepository {
    override fun save(clientDetails: OauthClientDetails) =
            dbRepository.save(DbOauthClientDetails.fromOauthClientDetails(clientDetails))

    override fun findAll(): List<DbOauthClientDetails> {
        val values = dbRepository.findAll()
        if (values.size > 0) {
            return values
        } else {
            throw OauthClientDetailsNotFoundException("set of client ids")
        }
    }
}

interface DbOauthClientDetailsRepository: MongoRepository<DbOauthClientDetails, String> {
    fun save(clientDetails: DbOauthClientDetails)
}
