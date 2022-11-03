package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.Buyer;
import softtek.ecommerce.shops_service.repositories.interfaces.BuyersRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.OrdersRepo;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/buyers")
public class BuyerController {
    @Autowired
    BuyersRepo repo;

    @Autowired
    OrdersRepo ordersRepo;

    @GetMapping("")
    Page<Buyer> buyers(
            @RequestParam( value = "active", required = false ) Boolean active,
            Pageable page
    ){
        if ( active != null ){
            return new PageImpl<>(repo.findByActive(active));
        }

        return repo.findAll( page );
    }

    @GetMapping("/{idBuyer}")
    Optional<Buyer> buyer (@PathVariable( value = "idBuyer" ) String idBuyer ){
        return repo.findById( idBuyer );
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> buyer(@RequestBody @Valid Buyer buyer ){
        if ( repo.findByActiveAndIdentificationNumber( true, buyer.getIdentificationNumber()).isPresent() )
            return new ResponseEntity<>("The buyer already exists", HttpStatus.CONFLICT);
        repo.save( buyer );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idBuyer}")
    @ResponseBody ResponseEntity<Object> deleteBuyer(@PathVariable( value = "idBuyer" ) String idBuyer ){
        Optional<Buyer> buyerOptional = repo.findByIdBuyerAndActive( idBuyer, true );

        if ( !buyerOptional.isPresent() )
            return new ResponseEntity<>("The buyer does not exists or was deleted", HttpStatus.NOT_FOUND);

        if ( ordersRepo.findByActiveAndIdBuyer( true, idBuyer ).isPresent() )
            return new ResponseEntity<>("The buyer has active orders", HttpStatus.CONFLICT);

        buyerOptional.get().setActive( false );
        repo.save( buyerOptional.get() );
        return ResponseEntity.ok().build();
    }
}
