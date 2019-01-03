package pl.springbootoauth2.api.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.springbootoauth2.domain.user.SignupService
import pl.springbootoauth2.domain.user.User
import pl.springbootoauth2.domain.user.UserRole
import pl.springbootoauth2.domain.user.UserRoleType.USER

import pl.springbootoauth2.logger

//import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/signup")
class SignupEndpoint(
        private val signupService: SignupService
) {

    // TODO Add some mechanism to react when user want to role = ADMIN (some validation, restrictions or checking for additional approvals) !!
    @PostMapping
    @CrossOrigin(origins = ["http://localhost:8080"])
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(@RequestBody user: User) {
        logger.info("Saving user credentials...")

        signupService.addUser(
                user.copy(
                        roles = listOf(UserRole(USER))
                                .plus(user.roles)
                                .distinct())
        )
    }

    // Example of '/signup' endpoint with redirect to specific url.
/*
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
            @RequestBody user: User,
            response: HttpServletResponse) {
        logger.info("Saving user credentials...")

        signupService.addUser(
                user.copy(
                        roles = listOf(UserRole(USER))
                                .plus(user.roles)
                                .distinct())
        )

        return response.sendRedirect("/")
    }
*/

    companion object {
        private val logger by logger()
    }
}
