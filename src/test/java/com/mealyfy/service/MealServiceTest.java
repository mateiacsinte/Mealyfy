package com.mealyfy.service;

import com.mealyfy.client.MealClient;
import com.mealyfy.client.MealResponse;
import com.mealyfy.dto.MealDTO;
import com.mealyfy.model.Ingredient;
import com.mealyfy.model.Meal;
import com.mealyfy.repository.IngredientRepository;
import com.mealyfy.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {
    @Mock
    private MealClient mealClient;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    private MealService mealService;

    @BeforeEach
    void setUp() {
        mealService = new MealService(mealClient, mealRepository, ingredientRepository);
    }

    @Test
    void testGetMeals_ShouldReturnMealList() {
        List<Meal> meals = List.of(new Meal());
        when(mealRepository.findAll()).thenReturn(meals);

        List<MealDTO> result = mealService.getMeals();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetRandomMeal_ShouldReturnRandomMeal() {
        List<MealResponse> responses = List.of(new MealResponse());
        when(mealClient.fetchRandomMeal()).thenReturn(responses);

        List<MealDTO> result = mealService.getRandomMeal();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetRandomMeal_WhenEmpty_ShouldReturnEmptyList() {
        when(mealClient.fetchRandomMeal()).thenReturn(Collections.emptyList());

        List<MealDTO> result = mealService.getRandomMeal();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetMealsByFilter_ShouldReturnFilteredMeals() {
        List<MealResponse> responses = List.of(new MealResponse());
        when(mealClient.fetchMealsByArea(anyString())).thenReturn(responses);

        List<MealDTO> result = mealService.getMealsByFilter(Optional.empty(), Optional.empty(), Optional.of("Italian"));

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetMealsByFilter_WithNameFilter_ShouldReturnFilteredMeals() {
        List<MealResponse> responses = List.of(new MealResponse());
        when(mealClient.fetchMealsByName(anyString())).thenReturn(responses);

        List<MealDTO> result = mealService.getMealsByFilter(Optional.of("Pasta"), Optional.empty(), Optional.empty());

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testAddMealById_ShouldSaveAndReturnMeal() {
        MealResponse response = new MealResponse();
        when(mealClient.fetchMealById(anyString())).thenReturn(response);
        when(mealRepository.save(any(Meal.class))).thenReturn(new Meal());

        Meal result = mealService.addMealById("12345");

        assertNotNull(result);
    }

    @Test
    void testAddMealById_ShouldCreateNewIngredientsIfNotFound() {
        MealResponse response = new MealResponse();
        response.setStrIngredient1("Tomato");
        response.setStrIngredient2("Cheese");
        when(mealClient.fetchMealById(anyString())).thenReturn(response);
        when(ingredientRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mealRepository.save(any(Meal.class))).thenReturn(new Meal());

        Meal result = mealService.addMealById("67890");

        assertNotNull(result);
        verify(ingredientRepository, times(2)).save(any(Ingredient.class));
    }
}
