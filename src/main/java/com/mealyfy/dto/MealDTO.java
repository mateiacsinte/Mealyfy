package com.mealyfy.dto;

import com.mealyfy.client.MealResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class MealDTO {

    public MealDTO(MealResponse mealResponse){
        this.id = mealResponse.getIdMeal();
        this.name = mealResponse.getStrMeal();
        this.category = mealResponse.getStrCategory();
        this.area = mealResponse.getStrArea();
        this.instructions = mealResponse.getStrInstructions();
        this.mealThumb = mealResponse.getStrMealThumb();
        this.ingredients = new LinkedList<>();
        this.ingredients = Stream.of(mealResponse.getStrIngredient1(),mealResponse.getStrIngredient2(),
                        mealResponse.getStrIngredient4(),mealResponse.getStrIngredient5(),mealResponse.getStrIngredient6(),
                        mealResponse.getStrIngredient8(), mealResponse.getStrIngredient9(), mealResponse.getStrIngredient10(),
                        mealResponse.getStrIngredient11(), mealResponse.getStrIngredient12(),
                        mealResponse.getStrIngredient13(), mealResponse.getStrIngredient14(),
                        mealResponse.getStrIngredient15())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toList());
    }

    private String id;
    private String name;
    private String category;
    private String area;
    private String instructions;
    private String mealThumb;
    private List<String> ingredients;
}

