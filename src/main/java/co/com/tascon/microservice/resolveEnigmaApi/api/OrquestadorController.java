package co.com.tascon.microservice.resolveEnigmaApi.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.tascon.microservice.resolveEnigmaApi.model.GetEnigmaRequest;
import co.com.tascon.microservice.resolveEnigmaApi.model.GetEnigmaStepResponse;
import co.com.tascon.microservice.resolveEnigmaApi.model.Header;
import co.com.tascon.microservice.resolveEnigmaApi.model.JsonApiBodyRequest;
import co.com.tascon.microservice.resolveEnigmaApi.model.JsonApiBodyResponseSuccess;
import io.swagger.annotations.ApiParam;

import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class OrquestadorController implements Orquestador {

    @Override
    public ResponseEntity<JsonApiBodyResponseSuccess> getResolveEnigma() {
    	
    	String solution = getAnswer();

        GetEnigmaStepResponse response = new GetEnigmaStepResponse();
        response.setId("12345");
        response.setType("TestGiraffeRefrigerator");
        response.setSolution(solution);

        JsonApiBodyResponseSuccess responseBody = new JsonApiBodyResponseSuccess();
        responseBody.addDataItem(response);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
        
    }
    
    private String getAnswer() {
        // Step1
        String jsonBody = "{\"data\": [{\"header\": {\"id\": \"12345\",\"type\": \"TestGiraffeRefrigerator\"},\"enigma\": \"1\"}]}";
        String answer1 = sendRequestAndGetAnswer(jsonBody, "http://localhost:8081/v1/getOneEnigma/getStep");

        // Step2
        String jsonBody2 = "{\"data\": [{\"header\": {\"id\": \"12345\",\"type\": \"TestGiraffeRefrigerator\"},\"enigma\": \"2\"}]}";
        String answer2 = sendRequestAndGetAnswer(jsonBody2, "http://localhost:8082/v1/getOneEnigma/getStep");

        // Step3
        String jsonBody3 = "{\"data\": [{\"header\": {\"id\": \"12345\",\"type\": \"TestGiraffeRefrigerator\"},\"enigma\": \"2\"}]}";
        String answer3 = sendRequestAndGetAnswer(jsonBody3, "http://localhost:8083/v1/getOneEnigma/getStep");

        String concatenatedAnswer = answer1.concat(" - ").concat(answer2).concat(" - ").concat(answer3);

        return concatenatedAnswer;
    }


    private String sendRequestAndGetAnswer(String jsonBody, String serviceUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(serviceUrl, requestEntity, String.class);
        String responseBody = responseEntity.getBody();
        return extractAnswerFromJson(responseBody);
    }

    private String extractAnswerFromJson(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode answerNode = rootNode.at("/data/0/answer");
            if (!answerNode.isMissingNode()) {
                return answerNode.asText();
            } else {
                return "No se encontró la respuesta";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar la respuesta";
        }
    }

}
