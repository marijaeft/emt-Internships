package mk.ukim.finki.wp.internships.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidUsernameException extends AuthenticationException {
    public InvalidUsernameException(String message) {
        super(message);
    }
}
