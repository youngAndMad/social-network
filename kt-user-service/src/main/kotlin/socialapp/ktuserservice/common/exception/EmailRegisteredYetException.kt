package socialapp.ktuserservice.common.exception

class EmailRegisteredYetException(email: String) : RuntimeException("Email $email is registered yet") {
}