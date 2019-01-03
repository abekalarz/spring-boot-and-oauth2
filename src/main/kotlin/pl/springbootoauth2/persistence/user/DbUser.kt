package pl.springbootoauth2.persistence.user

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import pl.springbootoauth2.domain.user.User
import pl.springbootoauth2.domain.user.UserRole
import pl.springbootoauth2.domain.user.UserRoleType

@Document(collection = "users")
data class DbUser @PersistenceConstructor constructor(
        @Id var id: String?,
        @Indexed(background = true, unique = true) val username: String,
        val password: String,
        val roles: List<String>
) {
    fun toUser() = User(
            username,
            password,
            roles.map { UserRole(UserRoleType.valueOf(it)) }
    )

    companion object {
        fun fromUser(user: User) = DbUser(
                null,
                user.username,
                user.password,
                user.roles.map { it.name.name }
        )
    }
}