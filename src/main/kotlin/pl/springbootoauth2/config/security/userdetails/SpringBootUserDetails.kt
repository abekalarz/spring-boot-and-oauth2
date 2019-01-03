package pl.springbootoauth2.config.security.userdetails

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import pl.springbootoauth2.domain.user.User
import pl.springbootoauth2.domain.user.UserRole

class SpringBootUserDetails(
        private val serialVersionUID: Long = 1L,
        private val authorities: MutableCollection<GrantedAuthority>,
        private val password: String,
        private val username: String
): UserDetails {

    companion object {
        fun fromUser(user: User) = SpringBootUserDetails(
                authorities = convertRoles(user.roles),
                password = user.password,
                username = user.username
        )

        private fun convertRoles(roles: List<UserRole>): MutableCollection<GrantedAuthority> = roles.map { role ->
            val roleName = role.name.name.toUpperCase()

            if (!roleName.startsWith("ROLE_", true))
                SimpleGrantedAuthority("ROLE_$roleName")
            else
                SimpleGrantedAuthority(roleName)
        }
                .toMutableList()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = username

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}