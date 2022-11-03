package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.Item;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource( path = "items" )
public interface ItemsRepo extends JpaRepository<Item, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(Item entity);

    List<Item> findByActive(Boolean active );

    List<Item> findByIdPostAndActive( String idPost, Boolean active );
}