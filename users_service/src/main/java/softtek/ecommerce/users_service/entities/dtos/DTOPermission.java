package softtek.ecommerce.users_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DTOPermission {
    private String idPermission;
    private String idCurrentUser;
}