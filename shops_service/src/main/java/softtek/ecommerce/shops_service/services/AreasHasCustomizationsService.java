package softtek.ecommerce.shops_service.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import softtek.ecommerce.shops_service.entities.AreaHasCustomization;
import softtek.ecommerce.shops_service.entities.AreaHasCustomizationId;
import softtek.ecommerce.shops_service.entities.dtos.DTOCustomizatedProduct;
import softtek.ecommerce.shops_service.exceptions.AreaHasCustomizationException;
import softtek.ecommerce.shops_service.repositories.interfaces.AreasHasCustomizationsRepo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AreasHasCustomizationsService {

    @Autowired
    AreasHasCustomizationsRepo areasHasCustomizationsRepo;

    @Autowired
    RestService restService;

    public Set<AreaHasCustomization> dataValidation( DTOCustomizatedProduct dtoCustomizatedProduct, String idCurrentUser ) throws Exception {
        //INICIALIZO EL ARRAY DONDE VOY A GUARDAR LOS TYPEHASAREAKEY
        JsonArray typeHasAreaKeys = new JsonArray();
        Set< AreaHasCustomization > areaHasCustomizations = new HashSet<>();

        //RECORRO TODOS LOS AREAHASCUSTOMIZATIONS ENVIADOS POR POST
        for ( AreaHasCustomizationId areaHasCustomizationId : dtoCustomizatedProduct.getAreaHasCustomizationIds() ) {
            Optional<AreaHasCustomization> areaHasCustomizationOptional = areasHasCustomizationsRepo.findByAreaHasCustomizationId( new AreaHasCustomizationId( areaHasCustomizationId.getIdCustomizationArea(), areaHasCustomizationId.getIdCustomization() ));

            //VERIFICO QUE EXISTA, ESTE ACTIVO, ESTAS CUSTOMIZACIONES LE PERTENEZCAN A LA TIENDA EN CUESTION Y QUE EL USUARIO SEA EL DUEÑO DE LA TIENDA
            if ( !areaHasCustomizationOptional.isPresent() || !areaHasCustomizationOptional.get().getCustomization().getActive() || !areaHasCustomizationOptional.get().getCustomization().getShop().getIdShop().equals(dtoCustomizatedProduct.getIdShop()) || !areaHasCustomizationOptional.get().getCustomization().getShop().getIdUser().equals(idCurrentUser) ){
                throw new AreaHasCustomizationException("The areaHasCustomization does not exists ","");
            }

            //CREO EL OBJETO JSON CON EL CUSTOMIZATIONTYPE Y CUSTOMIZATIONAREA
            JsonObject typeHasAreaKey = new JsonObject();
            typeHasAreaKey.addProperty("idCustomizationType", areaHasCustomizationOptional.get().getCustomization().getIdCustomizationType() );
            typeHasAreaKey.addProperty("idCustomizationArea", areaHasCustomizationOptional.get().getAreaHasCustomizationId().getIdCustomizationArea() );

            //LO AGREGO AL ARRAY
            typeHasAreaKeys.add( typeHasAreaKey );

            areaHasCustomizations.add( new AreaHasCustomization( areaHasCustomizationOptional.get().getCustomization(), areaHasCustomizationOptional.get().getAreaHasCustomizationId()));
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("typeHasAreaKeys", typeHasAreaKeys);

        //LE PEGO A ESA URL, LA MISMA VERIFICA QUE EXISTA TAL BASEPRODUCT, ESTE ACTIVO Y QUE LOS TYPEHASAREA ESTEN PERMITIDOS PARA ESTE BASEPRODUCT
        try{
            restService.postBaseProductContainsTHA(jsonObject, "base_products/"+dtoCustomizatedProduct.getIdBaseProduct()+"/typeHasAreas");
            return areaHasCustomizations;
        }catch ( HttpStatusCodeException exception ){
            throw exception;
        }
    }

}
