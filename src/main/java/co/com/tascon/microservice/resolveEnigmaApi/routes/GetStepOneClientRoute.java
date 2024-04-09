package co.com.tascon.microservice.resolveEnigmaApi.routes;

import co.com.tascon.microservice.resolveEnigmaApi.model.client.ClientJsonApiBodyResponseSuccess;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;



@Component
public class GetStepOneClientRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:get-step-one")
        		.routeId("getStepOne")
            	.setHeader(Exchange.HTTP_METHOD, constant("POST"))
            	.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .to("freemarker:templates/GetStepOneClientTemplate.ftl")
//            	.log("Respuest del microservicio Step1 ${body}")
            .hystrix()
            .hystrixConfiguration().executionTimeoutInMilliseconds(5000).end()
            .to("http4://localhost:8081/v1/getOneEnigma/getStep")
            	.convertBodyTo(String.class)
            	.unmarshal().json( JsonLibrary.Jackson, ClientJsonApiBodyResponseSuccess[].class )
            .process( new Processor() {
            		@Override
            		public void process( Exchange exchange ) throws Exception {
            			
            			ClientJsonApiBodyResponseSuccess stepOneResponse = ( ClientJsonApiBodyResponseSuccess ) exchange.getIn().getBody(ClientJsonApiBodyResponseSuccess.class);

            			if( stepOneResponse.getData().get(0).getStep().equalsIgnoreCase("1")) {
            				
            				exchange.setProperty("Step1","Step1: " + stepOneResponse.getData().get(0).getStepDescription() );
            			}
            			else {
            				exchange.setProperty("Error", "0001");
            				exchange.setProperty("descError", "Error consulting the step One");
            			}
            		}
            })
            .endHystrix()
            .onFallback()
            .process( new Processor() {
            	@Override
            	public void process(Exchange exchange) throws Exception {
            		exchange.setProperty("Error", "0002");
            		exchange.setProperty("descError", "Error consulting step one");
            	}
            })
            .end()
            .log("Response Code ${exchangeProperty[Error]}")
            .log("Response Description ${exchangeProperty[descError]}");
    }

    
}
