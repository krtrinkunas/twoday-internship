package lt.krtrinkunas.twodayinternship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity was not found.")
public class EntityNotFoundException extends RuntimeException {
}
