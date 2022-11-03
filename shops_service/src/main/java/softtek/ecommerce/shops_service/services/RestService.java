package softtek.ecommerce.shops_service.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import softtek.ecommerce.shops_service.entities.dtos.DTOTHA;
import softtek.ecommerce.shops_service.exceptions.AreaHasCustomizationException;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class RestService {
    private final RestTemplate restTemplate;


    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getUsersServiceObjectPlainJSON( String url2 ){
        String url1 = "http://localhost:8082/"+url2;
        return this.restTemplate.getForObject( url1, String.class );
    }

    public String getBaseProductsServiceObjectPlainJSON( String url2 ){
        String url1 = "http://localhost:8080/"+url2;
        return this.restTemplate.getForObject( url1, String.class );
    }

    public String getBuyersServiceObjectPlainJSON( String url2 ){
        String url1 = "http://localhost:8084/"+url2;
        return this.restTemplate.getForObject( url1, String.class );
    }

    public boolean postBaseProductContainsTHA(JsonObject dtothas, String url2 ) throws AreaHasCustomizationException {
        String url1 = "http://localhost:8080/"+url2;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(dtothas.toString(), headers);

        try{
            this.restTemplate.postForObject(url1, request, String.class);
        }catch ( HttpStatusCodeException exception ){
            throw exception;
        }
        return true;
    }
}
