package softtek.ecommerce.shops_service.services;

import com.google.gson.JsonObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    private final RestTemplate restTemplate;


    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getShopsServiceObjectPlainJSON( String url2 ){
        String url1 = "http://localhost:8081/"+url2;
        return this.restTemplate.getForObject( url1, String.class );
    }

    public String getUsersServiceObjectPlainJSON( String url2 ){
        String url1 = "http://localhost:8082/"+url2;
        return this.restTemplate.getForObject( url1, String.class );
    }

    public String getBaseProductsServiceObjectPlainJSON( String url2 ){
        String url1 = "http://localhost:8080/"+url2;
        return this.restTemplate.getForObject( url1, String.class );
    }

    public String postBaseProductContainsTHA(JsonObject dtothas, String url2 ){
        try{
            String url1 = "http://localhost:8080/"+url2;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(dtothas.toString(), headers);

            String httpStatus = this.restTemplate.postForObject(url1, request, String.class);

            return httpStatus;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
