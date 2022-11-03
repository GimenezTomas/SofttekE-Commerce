package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.PaymentMethod;
import softtek.ecommerce.shops_service.entities.Shop;
import softtek.ecommerce.shops_service.entities.dtos.DTOPaymentMethod;
import softtek.ecommerce.shops_service.repositories.interfaces.PaymentMethodsRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.ShopsRepo;
import softtek.ecommerce.shops_service.services.PermissionValidationService;
import softtek.ecommerce.shops_service.services.RestService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/payment_methods")
public class PaymentMethodsController {
    @Autowired
    PaymentMethodsRepo repo;

    @Autowired
    ShopsRepo shopsRepo;

    @Autowired
    RestService restService;

    @Autowired
    PermissionValidationService permissionValidationService;

    @GetMapping("")
    Page<PaymentMethod> paymentMethods(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{idPaymentMethod}")
    Optional<PaymentMethod> paymentMethod( @PathVariable( value = "idPaymentMethod" ) String idPaymentMethod ){
        return repo.findById( idPaymentMethod );
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createPaymentMethod(@RequestBody @Valid DTOPaymentMethod dtoPaymentMethod, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "CREAR_PAYMENTMETHOD";
        if ( !permissionValidationService.validation( idCurrentUser, permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<Shop> shopOptional = shopsRepo.findById( dtoPaymentMethod.getIdShop() );

        if ( !shopOptional.isPresent() || !shopOptional.get().getActive() || !shopOptional.get().getIdUser().equals( idCurrentUser ) )
            return new ResponseEntity<Object>("The shop does not exists, was deleted or you are not the owner", HttpStatus.CONFLICT);

        PaymentMethod paymentMethod = new PaymentMethod( dtoPaymentMethod.getName() );
        paymentMethod.setShop( shopOptional.get() );

        this.repo.save(paymentMethod);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseBody ResponseEntity<Object> deletePaymentMethod( @PathVariable( value = "id") String idPaymentMethod, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "BORRAR_PAYMENTMETHOD";
        if ( !permissionValidationService.validation( idCurrentUser, permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<PaymentMethod> paymentMethodOptional = repo.findById(idPaymentMethod);

        if ( !paymentMethodOptional.isPresent() || !paymentMethodOptional.get().getActive() || !paymentMethodOptional.get().getShop().getIdUser().equals(idCurrentUser) ){
            return new ResponseEntity<>("The payment method does not exists, was deleted or you are not the owner of the shop", HttpStatus.NOT_FOUND);
        }

        String obj = restService.getBuyersServiceObjectPlainJSON("orders/byPaymentMethod"+idPaymentMethod);
        if ( obj.length() != 0 || !obj.equals("null") ){
            return new ResponseEntity<>("The PAYMENTMETHOD has an active order", HttpStatus.NOT_FOUND);
        }

        paymentMethodOptional.get().setActive( false );
        repo.save( paymentMethodOptional.get() );

        return ResponseEntity.ok().build();
    }
}