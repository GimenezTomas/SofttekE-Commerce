package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.PaymentMethod;
import softtek.ecommerce.shops_service.repositories.interfaces.PaymentMethodsRepo;

import javax.validation.Valid;

@RestController
@RequestMapping("/payment_methods")
public class PaymentMethodsController {
    @Autowired
    PaymentMethodsRepo repo;

    @GetMapping("")
    Page<PaymentMethod> paymentMethods(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{name}")
    PaymentMethod paymentMethod( @PathVariable( value = "name" ) String name ){
        return repo.findByName( name );
    }

    @PostMapping("")
    String createPaymentMethod(@RequestBody @Valid PaymentMethod paymentMethod ){
        //VALIDATIONS
        //TODO
        this.repo.save(paymentMethod);

        return "ok";
    }
}