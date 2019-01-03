package pl.springbootoauth2.domain.user

data class User(
        val username: String,
        val password: String,
        val roles: List<UserRole> = listOf()
)

data class UserRole(val name: UserRoleType)
