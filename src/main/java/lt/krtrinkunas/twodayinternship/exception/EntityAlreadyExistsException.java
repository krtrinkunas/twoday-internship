package lt.krtrinkunas.twodayinternship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Entity already exists.")
public class EntityAlreadyExistsException extends RuntimeException {
}
