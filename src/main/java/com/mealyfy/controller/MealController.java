package com.mealyfy.controller;

import com.mealyfy.model.Meal;
import com.mealyfy.service.MealService;
import com.mealyfy.dto.MealDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/meals")
public class MealController {
    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("")
    public List<MealDTO> getMeals(){
        return mealService.getMeals();
    }

    @GetMapping("external/random")
    public List<MealDTO> getRandomMeal() {
        return mealService.getRandomMeal();
    }

    //eg: http://localhost:8080/meals/filter?area=Italian&name=Spaghetti
    @GetMapping("external/filter")
    public List<MealDTO> getMealsByFilter(@RequestParam Optional<String> name,
                                              @RequestParam Optional<String> ingredient,
                                              @RequestParam Optional<String> area) {
        return mealService.getMealsByFilter(name, ingredient, area);
    }

    @PostMapping("/add/{mealId}")
    public Meal addMealById(@PathVariable String mealId) {
        return mealService.addMealById(mealId);
    }

}

