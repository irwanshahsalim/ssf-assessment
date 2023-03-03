package ibf.ssfassessment.service;

import java.util.LinkedList;
import java.util.List;
import java.io.StringReader;

import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import ibf.ssfassessment.model.Cart;
import ibf.ssfassessment.model.Item;
import ibf.ssfassessment.model.Quotation;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


@Service
public class QuotationService {

    public String url = "https://quotation.chuklee.com/quotation"; 

    public Quotation getQuotations(List<String> items) {

        Quotation quote = new Quotation(); 
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (int i = 0; i < items.size(); i++) {
            arrBuilder.add(items.get(i)); 
        }
        JsonArray arr = arrBuilder.build(); 
        System.out.println("JsonArray: " + arr);

        RequestEntity<String> req = RequestEntity.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Accept", MediaType.APPLICATION_JSON_VALUE)
            .body(arr.toString(), String.class); 

        RestTemplate template = new RestTemplate(); 
        ResponseEntity<String> resp = null;
        String payload = ""; 
        Integer statusCode = 0; 

        try {
            resp = template.exchange(req, String.class); 
            payload = resp.getBody(); 
            statusCode = resp.getStatusCode().value(); 
        } catch (HttpClientErrorException ex) {
            
            payload = ex.getResponseBodyAsString();
            statusCode= ex.getStatusCode().value();
            quote.addQuotation("error", (float) statusCode);
            return quote;  

        } finally {
            System.out.printf(">>> Status Code: %d\n", statusCode);
            System.out.printf(">>> Payload: %s\n", payload);
        }

        JsonReader reader = Json.createReader(new StringReader(payload)); 
        JsonObject json = reader.readObject(); 


        quote.setQuoteId(json.getString("quoteId"));
        JsonArray json2 = json.getJsonArray("quotations"); 


        for (int i = 0; i < json2.size(); i++) {
            JsonObject json3 = json2.getJsonObject(i); 
            quote.addQuotation(json3.getString("item"), (float) json3.getJsonNumber("unitPrice").doubleValue());
        }

        return quote; 
    }

    public List<String> getList(Cart cart) {

        List<Item> contents = cart.getContents(); 
        List<String> items = new LinkedList<>(); 

        for (int i = 0; i < contents.size(); i++) {
            items.add(contents.get(i).getItemName()); 
        }
        return items; 
    }

    public Float calculateCost(Quotation quote, Cart cart) {

        List<Item> contents = cart.getContents(); 
        Float cost = 0.0f; 

        for (int i = 0; i < contents.size(); i++) {

            Float unitPxOfItem = quote.getQuotations().get(contents.get(i).getItemName());
            Integer quantity = contents.get(i).getQuantity(); 
            cost += (unitPxOfItem * quantity); 
        }
        return cost; 
    }
    
}
