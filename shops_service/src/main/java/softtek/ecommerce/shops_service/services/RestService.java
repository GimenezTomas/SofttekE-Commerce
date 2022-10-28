package softtek.ecommerce.shops_service.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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


}
