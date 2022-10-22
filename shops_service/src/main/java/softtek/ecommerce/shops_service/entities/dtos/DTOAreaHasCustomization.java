package softtek.ecommerce.shops_service.entities.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import softtek.ecommerce.shops_service.entities.Customization;
import softtek.ecommerce.shops_service.repositories.interfaces.CustomizationsRepo;

import java.util.Optional;

@Getter
@Setter
public class DTOAreaHasCustomization {
    private Customization customization;
    private String idCustomizationArea;
}
