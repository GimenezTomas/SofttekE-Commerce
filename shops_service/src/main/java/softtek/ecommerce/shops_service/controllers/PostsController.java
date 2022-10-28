package softtek.ecommerce.shops_service.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.CustomizatedProduct;
import softtek.ecommerce.shops_service.entities.Customization;
import softtek.ecommerce.shops_service.entities.Post;
import softtek.ecommerce.shops_service.entities.Shop;
import softtek.ecommerce.shops_service.entities.dtos.DTOPost;
import softtek.ecommerce.shops_service.repositories.interfaces.CustomizatedProductsRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.PostsRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.ShopsRepo;
import softtek.ecommerce.shops_service.services.PermissionValidationService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    PostsRepo repo;

    @Autowired
    CustomizatedProductsRepo customizatedProductRepo;

    @Autowired
    ShopsRepo shopsRepo;

    @Autowired
    PermissionValidationService permissionValidationService;

    @GetMapping("")
    Page<Post> posts(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{idPost}")
    Optional<Post> post(@PathVariable( value = "idPost" ) String idPost ){
        return repo.findById(idPost);
    }

    @GetMapping("/byIdCustomizatedProduct/{idCustomizatedProduct}")
    Optional<Post> postByIdCustomizatedProduct(@PathVariable( value = "idCustomizatedProduct" ) String idCustomizatedProduct ){
        return repo.findByCustomizatedProductById(idCustomizatedProduct);
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createPost(@RequestBody DTOPost dtoPost, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "CREAR_POST";
        if ( !permissionValidationService.validation( idCurrentUser, permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<CustomizatedProduct> customizatedProductOptional = customizatedProductRepo.findById( dtoPost.getIdCustomizatedProduct() );

        if ( !customizatedProductOptional.isPresent() || !customizatedProductOptional.get().getActive() || !customizatedProductOptional.get().getShop().getIdUser().equals(idCurrentUser) ){
            return new ResponseEntity<Object>("The customizatedProduct does not exists, was deleted or you are not the owner", HttpStatus.CONFLICT);
        }

        Post post = new Post( dtoPost.getDescription() );
        post.setShop( customizatedProductOptional.get().getShop() );
        post.setCustomizatedProduct( customizatedProductOptional.get() );

        this.repo.save( post );

        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/{idPost}")
    @ResponseBody ResponseEntity<Object> deletePost(@PathVariable( value = "idPost" ) String idPost, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "BORRAR_POST";
        if ( permissionValidationService.validation( idCurrentUser, permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<Post> postOptional = repo.findById(idPost);

        if ( !postOptional.isPresent() || !postOptional.get().getActive() || !postOptional.get().getShop().getIdUser().equals(idCurrentUser) )
            return new ResponseEntity<Object>("The post does not exists, was deleted or you are not the owner", HttpStatus.NOT_FOUND);

        postOptional.get().setActive(false);
        repo.save(postOptional.get());
    //TODO
        return ResponseEntity.ok().build();
    }
}
