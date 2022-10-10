package softtek.ecommerce.base_products_service.entities.dtos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import softtek.ecommerce.base_products_service.entities.TypeHasArea;

@Projection( name = "projtha1", types = TypeHasArea.class )
public interface DTOTypeHasArea {
    @Value("#{target.c}")
    String getIdCustomizationType();
}
