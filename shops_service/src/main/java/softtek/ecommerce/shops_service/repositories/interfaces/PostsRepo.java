package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.Post;

import java.util.Optional;

@RepositoryRestResource( path = "posts" )
public interface PostsRepo extends JpaRepository<Post, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(Post entity);

    @Query(value = "select * from posts p where p.id_customizated_product = :id and active = 1 limit 1", nativeQuery = true)
    Optional<Post> findByCustomizatedProductById( @Param("id") String idCustomizatedProduct );
    //Optional<Post> findOneByCustomizatedProductId_Customizated_Product(String idCustomizatedProduct );
}
