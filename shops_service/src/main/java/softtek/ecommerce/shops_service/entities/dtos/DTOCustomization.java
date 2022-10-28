package softtek.ecommerce.shops_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOCustomization {
    private String name;
    private String idCustomizationType;
    private float price;
    private String content;
    private String idShop;
}
