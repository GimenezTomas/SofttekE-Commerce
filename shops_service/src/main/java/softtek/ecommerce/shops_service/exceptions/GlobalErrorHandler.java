package softtek.ecommerce.shops_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler( ValidationException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.CONFLICT )
    String invalidValidation( ValidationException ex ){
        return "The validation failed: " + ex.getName() + " has an error: " + ex.getErrorType();
    }
}
