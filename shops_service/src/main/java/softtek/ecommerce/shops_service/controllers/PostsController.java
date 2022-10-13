package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.Customization;
import softtek.ecommerce.shops_service.entities.Post;
import softtek.ecommerce.shops_service.repositories.interfaces.PostsRepo;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    PostsRepo repo;

    @GetMapping("")
    Page<Post> posts(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{name}")
    Post post( @PathVariable( value = "name" ) String name ){
        return repo.findByName( name );
    }

    @PostMapping("")
    String createPost(@RequestBody @Valid Post post ){
        //VALIDATIONS
        //TODO
        this.repo.save( post );

        return "ok";
    }
}
