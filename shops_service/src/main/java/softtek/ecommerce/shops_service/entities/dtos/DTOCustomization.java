package softtek.ecommerce.shops_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOCustomization {
    @NotBlank
    private String name;
    @NotBlank
    private String idCustomizationType;
    @NotNull
    private float price;
    @NotBlank
    private String content;
    @NotBlank
    private String idShop;
}
