package softtek.ecommerce.shops_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOPost {
    @NotBlank
    private String description;
    @NotBlank
    private String idShop;
    @NotBlank
    private String idCustomizatedProduct;
}
