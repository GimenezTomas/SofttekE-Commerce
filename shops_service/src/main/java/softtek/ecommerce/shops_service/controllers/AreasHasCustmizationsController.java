package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.AreaHasCustomization;
import softtek.ecommerce.shops_service.entities.AreaHasCustomizationId;
import softtek.ecommerce.shops_service.entities.Customization;
import softtek.ecommerce.shops_service.entities.dtos.DTOAreaHasCustomization;
import softtek.ecommerce.shops_service.repositories.interfaces.AreasHasCustomizationsRepo;

import javax.validation.Valid;

@RestController
@RequestMapping("/areas_has_customizations")
public class AreasHasCustmizationsController {
    @Autowired
    AreasHasCustomizationsRepo repo;

    @GetMapping("")
    Page<AreaHasCustomization> areaHasCustomizations(Pageable page ){
        return repo.findAll( page );
    }

    /*@PostMapping("")
    String createAreaHasCustomization(@RequestBody @Valid AreaHasCustomization areaHasCustomization ){
        //VALIDATIONS
        //TODO
        this.repo.save( areaHasCustomization );

        return "ok";
    }*/

    @PostMapping("")
    String createAreaHasCustomization(@RequestBody DTOAreaHasCustomization dtoAreaHasCustomization ){
        //VALIDATIONS
        //TODO
        AreaHasCustomizationId areaHasCustomizationId = new AreaHasCustomizationId( dtoAreaHasCustomization.getIdCustomizationArea(), dtoAreaHasCustomization.getCustomization().getIdCustomization() );
        AreaHasCustomization areaHasCustomization = new AreaHasCustomization(dtoAreaHasCustomization.getCustomization(), areaHasCustomizationId );
        this.repo.save( areaHasCustomization );

        return "ok";
    }
}
