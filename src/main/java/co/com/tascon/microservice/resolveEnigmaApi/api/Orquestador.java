package co.com.tascon.microservice.resolveEnigmaApi.api;

import co.com.tascon.microservice.resolveEnigmaApi.model.JsonApiBodyResponseErrors;
import co.com.tascon.microservice.resolveEnigmaApi.model.JsonApiBodyResponseSuccess;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-03-12T18:31:16.899186-05:00[America/Bogota]")
@Api(value = "Orquestador", description = "Orquestador API")
public interface Orquestador {

    @ApiOperation(value = "Resolve Enigma", nickname = "getResolveEnigma", notes = "Resolve Enigma", response = JsonApiBodyResponseSuccess.class, tags={  })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "success response", response = JsonApiBodyResponseSuccess.class),
        @ApiResponse(code = 424, message = "bad input parameter", response = JsonApiBodyResponseErrors.class)
    })
    @RequestMapping(value = "/getResolveEnigma",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<JsonApiBodyResponseSuccess> getResolveEnigma();
}
