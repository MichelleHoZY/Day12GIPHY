package vttp2022.paf.PAFDay115.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class GiphyService {

    // GIPHY_API_KEY
    @Value("${giphy.api.key}")
    private String giphykey;

    // private final static String url = "https://api.giphy.com/v1/gifs/search?api_key=hwfs89tOHgI0uOpOquAZzO6dqE0ZAIN6&q=pokemon&limit=25&offset=0&rating=g&lang=en";

    public static final String GIPHY_SEARCH = "https://api.giphy.com/v1/gifs/search?";

    public List<String> getGifs(String Phrase) {
        return getGifs(Phrase, "G", 10);
    }

    public List<String> getGifs(String Phrase, Integer Limit) {
        return getGifs(Phrase, "G", Limit);
    }

    public List<String> getGifs(String Phrase, String rating) {
        return getGifs(Phrase, rating, 10);
    }

    public List<String> getGifs(String Phrase, String Rating, Integer Limit) {
        List<String> result = new LinkedList<>();

        String url = UriComponentsBuilder.fromUriString(GIPHY_SEARCH)
        .queryParam("api_key", giphykey)
        .queryParam("q", Phrase)
        .queryParam("limit", Limit)
        .queryParam("rating", Rating)
        .queryParam("lang", "en")
        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        try(InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader jReader = Json.createReader(is);
            JsonObject jObject = jReader.readObject();

            JsonArray data = jObject.getJsonArray("data");
            for (int i=0; i < data.size(); i++) {
                JsonObject eachObject = data.getJsonObject(i);
                JsonObject images = eachObject.getJsonObject("images");
                JsonObject fixed_height = images.getJsonObject("fixed_height");
                String image_url = fixed_height.getString("url");

                result.add(image_url);
            }
            // JsonObject firstObject = data.getJsonObject(0);
            // JsonObject images = firstObject.getJsonObject("images");
            // JsonObject fixed_height = images.getJsonObject("fixed_height");
            // String image_url = fixed_height.getString("url");

            // result.add(image_url);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }
    
}
