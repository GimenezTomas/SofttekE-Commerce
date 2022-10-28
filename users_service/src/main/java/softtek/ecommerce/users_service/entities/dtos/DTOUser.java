package softtek.ecommerce.users_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DTOUser {
    private String email;
    private String password;
    private String idCurrentUser;
    private String idRole;
}
