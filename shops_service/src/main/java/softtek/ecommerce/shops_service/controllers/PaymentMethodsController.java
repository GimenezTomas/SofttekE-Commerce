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
    @ResponseBody ResponseEntity<Object> createPaymentMethod(@RequestBody DTOPaymentMethod dtoPaymentMethod, @RequestParam String idCurrentUser ) throws Exception {
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
}