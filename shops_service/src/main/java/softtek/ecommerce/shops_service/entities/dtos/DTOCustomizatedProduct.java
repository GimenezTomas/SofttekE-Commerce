package softtek.ecommerce.shops_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softtek.ecommerce.shops_service.entities.AreaHasCustomizationId;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOCustomizatedProduct {
    @NotBlank
    private String name;
    @NotBlank
    private String idBaseProduct;
    @NotBlank
    private String idShop;

    private ArrayList<AreaHasCustomizationId> areaHasCustomizationIds;
}
