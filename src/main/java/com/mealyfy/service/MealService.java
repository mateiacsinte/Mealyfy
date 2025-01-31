package com.mealyfy.service;

import com.mealyfy.client.MealResponse;
import com.mealyfy.dto.MealDTO;
import com.mealyfy.client.MealClient;
import com.mealyfy.model.Ingredient;
import com.mealyfy.model.Meal;
import com.mealyfy.repository.IngredientRepository;
import com.mealyfy.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MealService {
    private final MealClient mealClient;
    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;

    public MealService(MealClient mealClient, MealRepository mealRepository, IngredientRepository ingredientRepository) {
        this.mealClient = mealClient;
        this.mealRepository = mealRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<MealDTO> getMeals(){
        return mealRepository.findAll().stream().map(MealDTO::new).toList();
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

    public Meal addMealById(String mealId){
        MealDTO mealDTO = new MealDTO(this.mealClient.fetchMealById(mealId));
        Set<Ingredient> ingredients = new HashSet<>();


        for(String ingredient: mealDTO.getIngredients()){
            Optional<Ingredient> ingredientEntity = ingredientRepository.findByName(ingredient);
            if(ingredientEntity.isPresent()){
                ingredients.add(ingredientEntity.get());
            }else{
                Ingredient newIngredient = new Ingredient();
                newIngredient.setName(ingredient);
                newIngredient = ingredientRepository.save(newIngredient);
                ingredients.add(newIngredient);
            }
        }

        Meal meal = new Meal();
        meal.setName(mealDTO.getName());
        meal.setMealThumb(mealDTO.getMealThumb());
        meal.setPublicId(mealDTO.getId());
        meal.setArea(mealDTO.getArea());
        meal.setIngredients(ingredients);

        return this.mealRepository.save(meal);
    }
}

