package pl.springbootoauth2.domain.user

interface UserRepository {
    fun save(user: User)
    fun findByUsername(username: String): User
}