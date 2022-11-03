package softtek.ecommerce.base_products_service.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softtek.ecommerce.base_products_service.entities.TypeHasAreaKey;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOTHA {
    private ArrayList<TypeHasAreaKey> typeHasAreaKeys;
}
