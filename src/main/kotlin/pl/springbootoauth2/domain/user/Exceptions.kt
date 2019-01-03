package pl.springbootoauth2.domain.user

open class UserNotFoundException(username: String?): RuntimeException("User details for username '$username' not found,")
