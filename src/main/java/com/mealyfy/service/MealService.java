package com.mealyfy.service;

import com.mealyfy.client.MealResponse;
import com.mealyfy.dto.MealDTO;
import com.mealyfy.client.MealClient;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealService {
    private final MealClient mealClient;

    public MealService(MealClient mealClient) {
        this.mealClient = mealClient;
    }

    public List<MealDTO> getRandomMeal() {
        List<MealResponse> mealResponses =  mealClient.fetchRandomMeal();

        if(mealResponses.isEmpty()) return new LinkedList<>();

        return mealResponses.stream().map(MealDTO::new).collect(Collectors.toList());
    }

    //this method was created because api does not allow multiple filtering
    public List<MealDTO> getMealsByFilter(Optional<String> name, Optional<String> ingredient, Optional<String> area) {
        List<MealDTO> meals;

        if(area.isPresent()){
            meals = mealClient.fetchMealsByArea(area.get()).stream().map(MealDTO::new).toList();
            if(ingredient.isPresent()) meals = meals.stream()
                    .filter(m -> m.getIngredients().contains(ingredient.get())).toList();
            if(name.isPresent()) meals =  meals.stream()
                    .filter(m-> m.getName().toLowerCase().contains(name.get().toLowerCase())).toList();
            return meals;
        }
        if(ingredient.isPresent()){
            meals = mealClient.fetchMealsByIngredient(ingredient.get()).stream().map(MealDTO::new).toList();
            if(name.isPresent()) meals = meals.stream()
                    .filter(m -> m.getName().toLowerCase().contains(name.get().toLowerCase())).toList();
            return meals;
        }
        return name.map(s -> mealClient.fetchMealsByName(s).stream().map(MealDTO::new).toList()).orElseGet(LinkedList::new);

    }
}

