package softtek.ecommerce.base_products_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softtek.ecommerce.base_products_service.entities.TypeHasArea;
import softtek.ecommerce.base_products_service.entities.TypeHasAreaKey;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOBaseProduct {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private float price;
    @NotNull
    private int days_fabrication_time;
    private ArrayList<TypeHasAreaKey> typeHasAreaKeys;
}
