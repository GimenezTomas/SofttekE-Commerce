package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.Bill;
import softtek.ecommerce.shops_service.entities.BillGeneratorAdapter;
import softtek.ecommerce.shops_service.entities.Order;
import softtek.ecommerce.shops_service.entities.State;
import softtek.ecommerce.shops_service.entities.dtos.DTOBillData;
import softtek.ecommerce.shops_service.repositories.interfaces.BillsRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.OrdersRepo;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/bills")
public class BillsController {
    @Autowired
    BillsRepo repo;

    @Autowired
    OrdersRepo ordersRepo;

    @GetMapping("")
    Page<Bill> orders(
            @RequestParam( value = "active", required = false ) Boolean active,
            Pageable page
    ){
        if ( active != null ){
            return new PageImpl<>(repo.findByActive(active));
        }

        return repo.findAll( page );
    }

    @GetMapping("/{idBill}")
    Optional<Bill> bill (@PathVariable( value = "idBill" ) String idBill ){
        return repo.findById( idBill );
    }

    @Transactional
    @PostMapping("")
    @ResponseBody ResponseEntity<Object> bill(@RequestBody DTOBillData dtoBillData ){
        Bill bill = new Bill();

        Optional<Order> optionalOrder = ordersRepo.findById( dtoBillData.getIdOrder() );
        if ( !optionalOrder.isPresent() || !optionalOrder.get().getActive() || optionalOrder.get().getState() != State.GENERATED )
            return new ResponseEntity<>("The order does not exists, was deleted or it's no longer in GENERATED status", HttpStatus.CONFLICT);


        optionalOrder.get().billRecived(bill.generate(dtoBillData, new BillGeneratorAdapter()));
        optionalOrder.get().setBill(bill); //TODO

        repo.save( bill );
        ordersRepo.save( optionalOrder.get() );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idBill}")
    @ResponseBody ResponseEntity<Object> deleteBill( @PathVariable( value = "idBill" ) String idBill ){
        Optional<Bill> billOptional = repo.findById( idBill );

        if ( !billOptional.isPresent() || !billOptional.get().getActive() )
            return new ResponseEntity<>("The bill does not exists or was deleted", HttpStatus.NOT_FOUND);

        Optional<Order> orderOptional = ordersRepo.findByActiveAndIdBill( true, idBill );
        if ( orderOptional.isPresent() )
            return new ResponseEntity<>("The bill has an active order", HttpStatus.CONFLICT);

        billOptional.get().setActive( false );
        repo.save( billOptional.get() );
        return ResponseEntity.ok().build();
    }

}
