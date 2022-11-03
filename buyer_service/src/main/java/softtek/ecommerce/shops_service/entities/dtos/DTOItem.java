package softtek.ecommerce.shops_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class DTOItem {
    @NotBlank
    private String idPost;
    @NotBlank
    private String idShoppingCart;
    @Min(1) @NotNull
    private Integer quantity;
}
