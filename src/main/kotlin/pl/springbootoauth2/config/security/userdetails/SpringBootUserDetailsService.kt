package pl.springbootoauth2.config.security.userdetails

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import pl.springbootoauth2.domain.user.UserRepository

@Service
class SpringBootUserDetailsService(
        private val userRepository: UserRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
            SpringBootUserDetails
                    .fromUser(userRepository.findByUsername(username))


}