package softtek.ecommerce.users_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionInvalidException extends Exception {
    private String errorType;
    private String name;

}
