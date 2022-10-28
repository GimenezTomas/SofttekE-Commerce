package softtek.ecommerce.shops_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOCustomizatedProduct {
    private String name;
    private String idBaseProduct;
    private String idCustomization;
    private String idShop;
}
