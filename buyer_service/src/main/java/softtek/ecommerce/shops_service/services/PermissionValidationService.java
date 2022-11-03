package softtek.ecommerce.shops_service.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import softtek.ecommerce.shops_service.exceptions.ValidationException;

import java.util.Optional;

@Service
public class PermissionValidationService {
    @Autowired
    RestService restService;

    public Boolean validation( String idCurrentUser, String permissionName ) throws Exception{
        String userString = restService.getUsersServiceObjectPlainJSON("users/"+idCurrentUser);

        if ( userString.length() != 0 && !userString.equals("null") ){
            JsonObject user = new JsonParser().parse( userString ).getAsJsonObject();

            if ( !user.get("active").getAsBoolean() )
                throw new ValidationException(user.get("id_user").getAsString(), " is not active any more");

            JsonArray permissions = user.get("role").getAsJsonObject().get("permissions").getAsJsonArray();

            for ( JsonElement permission: permissions ) {
                if ( permission.getAsJsonObject().get("name").getAsString().equals(permissionName) ) {
                    return true;
                }
            }

            return false;
        }
        else{
            throw new ValidationException(idCurrentUser, " user does not exists");
        }
    }
}
