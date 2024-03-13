package co.com.tascon.microservice.resolveEnigmaApi.api;

import co.com.tascon.microservice.resolveEnigmaApi.model.GetEnigmaRequest;
import co.com.tascon.microservice.resolveEnigmaApi.model.GetEnigmaStepResponse;
import co.com.tascon.microservice.resolveEnigmaApi.model.Header;
import co.com.tascon.microservice.resolveEnigmaApi.model.JsonApiBodyRequest;
import co.com.tascon.microservice.resolveEnigmaApi.model.JsonApiBodyResponseErrors;
import co.com.tascon.microservice.resolveEnigmaApi.model.JsonApiBodyResponseSuccess;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-03-05T21:04:17.162782-05:00[America/Bogota]")
@Controller
public class GetStepApiController implements GetStepApi {

	@org.springframework.beans.factory.annotation.Autowired
	public GetStepApiController() {
		
	}
	
	
    public ResponseEntity<JsonApiBodyResponseSuccess> getStep(@ApiParam(value = "request body get enigma Step", required = true) @Valid @RequestBody JsonApiBodyRequest body) {
    	
    	GetEnigmaRequest enigmaRequest = body.getData().get(0);
        Header header = enigmaRequest.getHeader();
        String id = header.getId();
        String type = header.getType();
        String enigma = enigmaRequest.getEnigma();

        String solution = answerEnigma(enigma);

        GetEnigmaStepResponse response = new GetEnigmaStepResponse();
        response.setId(id);
        response.setType(type);
        response.setSolution(solution);

        JsonApiBodyResponseSuccess responseBody = new JsonApiBodyResponseSuccess();
        responseBody.addDataItem(response);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    	
    }

    private String answerEnigma(String enigmaQuestion) {
        return "Step1: Open the regrigerator ";
    }

}
