package softtek.ecommerce.shops_service.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.AreaHasCustomization;
import softtek.ecommerce.shops_service.entities.AreaHasCustomizationId;
import softtek.ecommerce.shops_service.entities.Customization;
import softtek.ecommerce.shops_service.entities.dtos.DTOAreaHasCustomization;
import softtek.ecommerce.shops_service.repositories.interfaces.AreasHasCustomizationsRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.CustomizationsRepo;
import softtek.ecommerce.shops_service.services.RestService;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/areas_has_customizations")
public class AreasHasCustmizationsController {
    @Autowired
    AreasHasCustomizationsRepo areasHasCustomizationsRepo;

    @Autowired
    CustomizationsRepo customizationsRepo;

    @Autowired
    RestService restService;

    @GetMapping("")
    Page<AreaHasCustomization> areaHasCustomizations(Pageable page ){
        return areasHasCustomizationsRepo.findAll( page );
    }

    @GetMapping("/byId")
    Optional<AreaHasCustomization> areaHasCustomizations(@RequestParam String idCustomization, @RequestParam String idArea ){
        AreaHasCustomizationId areaHasCustomizationId = new AreaHasCustomizationId( idArea, idCustomization );
        return areasHasCustomizationsRepo.findByAreaHasCustomizationId(areaHasCustomizationId);
    }

    @GetMapping("/byTHA")
    Optional<AreaHasCustomization> getByTHA( @RequestParam String idArea, @RequestParam String idType ){
        return areasHasCustomizationsRepo.findByTHA( idArea, idType );
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createAreaHasCustomization(@RequestBody @Valid DTOAreaHasCustomization dtoAreaHasCustomization ){
        Optional<Customization> customizationOptional = customizationsRepo.findById(dtoAreaHasCustomization.getIdCustomization());

        if ( !customizationOptional.isPresent() || !customizationOptional.get().getActive() ){
            return new ResponseEntity<Object>("The customization does not exist or was deleted", HttpStatus.NOT_FOUND);
        }

        String customizationAreaStr = restService.getBaseProductsServiceObjectPlainJSON("customization_areas/"+dtoAreaHasCustomization.getIdCustomizationArea());

        if ( customizationAreaStr.length() != 0 && !customizationAreaStr.equals("null") ){
            JsonObject customizationArea = new JsonParser().parse( customizationAreaStr ).getAsJsonObject();

            if ( !customizationArea.get("active").getAsBoolean() )
                return new ResponseEntity<Object>("The area " + customizationArea.get("name").getAsString() + " is not active any more", HttpStatus.CONFLICT);
        }
        else{
            return new ResponseEntity<Object>("The area does not exists", HttpStatus.NOT_FOUND);
        }

        AreaHasCustomizationId areaHasCustomizationId = new AreaHasCustomizationId( dtoAreaHasCustomization.getIdCustomizationArea(), dtoAreaHasCustomization.getIdCustomization() );
        AreaHasCustomization areaHasCustomization = new AreaHasCustomization(customizationOptional.get(), areaHasCustomizationId );
        this.areasHasCustomizationsRepo.save( areaHasCustomization );

        return ResponseEntity.ok().build();
    }
}
