package com.mealyfy.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Component
public class MealClient {
    private final RestTemplate restTemplate;
    private final Gson gson;

    private final String BASE_URL = "https://www.themealdb.com/api/json/v1/1";

    // ✅ Constructor Injection for Easier Testing
    public MealClient(RestTemplate restTemplate, Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    public List<MealResponse> fetchRandomMeal() {
        JsonArray mealsArray = this.doGetByRoute("/random.php");

        return parseMealArray(mealsArray);
    }

    public List<MealResponse> fetchMealsByName(String name) {
        JsonArray mealsArray = this.doGetByRoute("/search.php?s=" + name);

        return parseMealArray(mealsArray);
    }

    public List<MealResponse> fetchMealsByIngredient(String ingredient) {
        JsonArray mealsArray = this.doGetByRoute( "/filter.php?i=" + ingredient);

        return parseMealArray(mealsArray);
    }

    public List<MealResponse> fetchMealsByArea(String area) {
        JsonArray mealsArray = this.doGetByRoute( "/filter.php?a=" + area);

        return parseMealArray(mealsArray);
    }

    private JsonArray doGetByRoute(String route){
        String responseJson = restTemplate.getForObject(BASE_URL + route, String.class);
        JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();
        if(jsonObject.get("meals").isJsonNull()) return new JsonArray();
        return jsonObject.getAsJsonArray("meals");
    }

    private List<MealResponse> parseMealArray(JsonArray mealsArray) {
        List<MealResponse> mealResponses = new LinkedList<>();

        for(int i = 0; i < mealsArray.size(); i++){
            JsonObject mealJson = mealsArray.get(i).getAsJsonObject();
            mealResponses.add(gson.fromJson(mealJson, MealResponse.class));
        }
        return mealResponses;
    }
}