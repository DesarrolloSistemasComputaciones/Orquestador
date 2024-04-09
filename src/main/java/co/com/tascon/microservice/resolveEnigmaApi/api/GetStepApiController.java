package co.com.tascon.microservice.resolveEnigmaApi.api;

import co.com.tascon.microservice.resolveEnigmaApi.api.GetStepApiController;
import co.com.tascon.microservice.resolveEnigmaApi.model.JsonApiBodyRequest;

import io.swagger.annotations.*;

import org.apache.camel.EndpointInject;
import org.apache.camel.FluentProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-03-05T21:04:17.162782-05:00[America/Bogota]")
@Controller
public class GetStepApiController implements GetStepApi {

	private static final Logger logger = LoggerFactory.getLogger( GetStepApiController.class);
	
	@EndpointInject(uri="direct:resolve-enigma")
	private FluentProducerTemplate producerTemplateEnigma;
	 

    public ResponseEntity<?> getStep(@ApiParam(value ="body", required = true ) @Valid @RequestBody JsonApiBodyRequest body ){
        try {
        	
            Object response = producerTemplateEnigma.withBody(body).request();
            return new ResponseEntity<>( response, HttpStatus.OK);
            
        } catch (Exception e) {
            logger.error("Couldn't Serialize response for content type application/json", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
