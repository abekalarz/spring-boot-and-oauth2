package pl.springbootoauth2.domain.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

import pl.springbootoauth2.logger

@Service
class SignupService(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {
    fun addUser(user: User) {
        userRepository.save(
                user.copy(password = passwordEncoder.encode(user.password)))
    }

    fun findUser(username: String) = userRepository.findByUsername(username)

    companion object {
        private val logger by logger()
    }
}