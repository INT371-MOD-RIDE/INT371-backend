package sit.int371.sixpack_overflow_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "never gonnagive you up")
public class ValidationEnum extends RuntimeException{

}