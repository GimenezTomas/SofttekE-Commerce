package softtek.ecommerce.users_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import softtek.ecommerce.users_service.entities.Permission;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler( PermissionInvalidException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.CONFLICT )
    String invalidPermission( PermissionInvalidException ex ){
        return "The permission " + ex.getName() + " has an error: " + ex.getErrorType();
    }

    @ExceptionHandler( RoleInvalidException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.CONFLICT )
    String invalidRole( RoleInvalidException ex ){
        return "The role " + ex.getName() + " has an error: " + ex.getErrorType();
    }

    @ExceptionHandler( UserInvalidException.class )
    @ResponseBody
    @ResponseStatus( HttpStatus.CONFLICT )
    String invalidUser( UserInvalidException ex ){
        return "The user " + ex.getName() + " has an error: " + ex.getErrorType();
    }
}
