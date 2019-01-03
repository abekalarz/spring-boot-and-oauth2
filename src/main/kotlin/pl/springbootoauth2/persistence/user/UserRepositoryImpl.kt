package pl.springbootoauth2.persistence.user

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component
import pl.springbootoauth2.domain.user.User
import pl.springbootoauth2.domain.user.UserNotFoundException
import pl.springbootoauth2.domain.user.UserRepository

@Component
class UserRepositoryImpl(
        private val dbRepository: DbUserRepository
): UserRepository {

    override fun save(user: User) = dbRepository.save(DbUser.fromUser(user))

    // TODO Map 'UserNotFoundException' to correct status (general typical @ControllerAdvice needed!)
    override fun findByUsername(username: String) =
            dbRepository.findByUsername(username)?.toUser() ?: throw UserNotFoundException(username)
}

interface DbUserRepository: MongoRepository<DbUser, String> {
    fun save(user: DbUser)
    fun findByUsername(username: String): DbUser?
}
