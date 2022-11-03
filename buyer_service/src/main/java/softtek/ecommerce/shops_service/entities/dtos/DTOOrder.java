package softtek.ecommerce.shops_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTOOrder {
    @NotBlank
    private String idPaymentMethod;
    @NotBlank
    private String idShoppingCart;
    @NotBlank
    private String idBuyer;
}
