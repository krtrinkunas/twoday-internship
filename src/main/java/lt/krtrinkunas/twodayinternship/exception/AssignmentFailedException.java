package lt.krtrinkunas.twodayinternship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Assignment to enclosures failed.")
public class AssignmentFailedException extends RuntimeException {
}
