package com.org.recipe.bo.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.Test;

import com.org.recipe.Application;
import com.org.recipe.controller.AbstractTest;
import com.org.recipe.entity.Category;
import com.org.recipe.entity.Recipe;
import com.org.recipe.exception.RecordAlreadyExistsException;
import com.org.recipe.exception.RecordNotFoundException;

@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class RecipeBoServiceTest extends AbstractTest{

	private static final String EGG = "EGG";
	private static final String BREADCRUMB = "Breadcrumb";
	private static final String MEAT_SCHNITZEL = "Meat Schnitzel";
	private static final String MEAT_POTATO_WEDGE = "Meat Potato Wedge";
	private static final String OVEN = "oven";
	private static final String POTATO = "Potato";
	private static final String RECIPE_NOT_CREATED_ALREADY_RECEIPE_EXISTS_FOR_MEAT_POTATO_WEDGE = "Recipe not created already receipe exists for Meat Potato Wedge";
	private static final String SALMON_65 = "Salmon 65";
	private static final String SALMON = "Salmon";
	private static final String CHILLY = "Chilly";
	private static final String DRUMSTICK = "DRUMSTICK";
	private static final String TOMATO = "Tomato";
	private static final String MEAT = "Meat";
	private static final String PEPPER = "Pepper";
	private static final String SALMON_GRAVY = "Salmon Gravy";
	private static final String SAMBAR = "Sambar";


	@Mock
	private WebApplicationContext webApplicationContext;

	@Autowired
	private RecipeBoService recipeBoService;
	
	
	@Test
	@DisplayName("create")
	public void testCreate() {
		Set<String> ingredients = new HashSet<>();
		ingredients.add(PEPPER);
		ingredients.add(POTATO);
		ingredients.add(MEAT);
		String instruction = "Marinate meat with Indian spices and fry with oil";

		Category category = Category.NON_VEG;
		int NO_OF_SERVINGS = 2;
		var restaurant = new Recipe(MEAT_POTATO_WEDGE, NO_OF_SERVINGS, ingredients, instruction);
		restaurant.setCategory(category);
		Recipe savedEntity = recipeBoService.create(restaurant);

		assertAll("Receice is created", () -> assertEquals(MEAT_POTATO_WEDGE, savedEntity.getName()),
				() -> assertEquals(instruction, savedEntity.getInstructions()),
				() -> assertEquals(NO_OF_SERVINGS, savedEntity.getNoOfServings()),
				() -> assertEquals(ingredients, savedEntity.getIngredients()),
				() -> assertEquals(category, savedEntity.getCategory()));

		RecordAlreadyExistsException recordAlreadyExists = assertThrows(RecordAlreadyExistsException.class, () -> {
			recipeBoService.create(restaurant);
		});

		assertEquals(RECIPE_NOT_CREATED_ALREADY_RECEIPE_EXISTS_FOR_MEAT_POTATO_WEDGE,
				recordAlreadyExists.getMessage());

		recipeBoService.delete(MEAT_POTATO_WEDGE);
	}

	@Test
	@DisplayName("update")
	public void testUpdate() {
		int NO_OF_SERVINGS = 2;
		Set<String> ingredients = new HashSet<>();
		ingredients.add(DRUMSTICK);
		ingredients.add(TOMATO);
		ingredients.add(CHILLY);
		Category category = Category.VEG;

		String modifiedInstruction = "Boil dal with drumstick , tomato , chilly and Indian spices and serve hot";

		var sambarRecipe = new Recipe(SAMBAR, NO_OF_SERVINGS, ingredients, modifiedInstruction);
		sambarRecipe.setCategory(category);
		recipeBoService.create(sambarRecipe);
		
		var modifiedSambar = new Recipe(SAMBAR, NO_OF_SERVINGS, ingredients, modifiedInstruction);
		modifiedSambar.setCategory(category);
		Recipe savedEntity = recipeBoService.update(SAMBAR, modifiedSambar);

		assertAll("Receice is update", () -> assertEquals(SAMBAR, savedEntity.getName()),
				() -> assertEquals(modifiedInstruction, savedEntity.getInstructions()),
				() -> assertEquals(NO_OF_SERVINGS, savedEntity.getNoOfServings()),
				() -> assertEquals(ingredients, savedEntity.getIngredients()),
				() -> assertEquals(category, savedEntity.getCategory()));

		recipeBoService.delete(SAMBAR);

		RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> {
			recipeBoService.update(SAMBAR, modifiedSambar);
		});

		assertEquals("Recipe not updated since no receipe found for Sambar", recordNotFoundException.getMessage());

	}

	@Test
	@DisplayName("Delete")
	public void testDelete() {

		Set<String> ingredients = new HashSet<>();
		ingredients.add(EGG);
		ingredients.add(MEAT);
		ingredients.add(BREADCRUMB);
		String instruction = "Cut slice of meat, coat it with egg and breadcrumb then fry until golden brown";

		int NO_OF_SERVINGS = 2;
		Recipe meatSchnitzel = new Recipe(MEAT_SCHNITZEL, NO_OF_SERVINGS, ingredients, instruction);
		meatSchnitzel.setCategory(Category.NON_VEG);
		
		recipeBoService.create(meatSchnitzel);

		recipeBoService.delete(MEAT_SCHNITZEL);

		
		RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> {
			recipeBoService.update(MEAT_SCHNITZEL, meatSchnitzel);
		});

		assertEquals("Recipe not updated since no receipe found for Meat Schnitzel",
				recordNotFoundException.getMessage());

	}

	@Test
	@DisplayName("allRecipes")
	public void testAllrecipes() {
		String instruction = "Marinate Salmon fish with olive oil , garlic and ginger. After 30 min of marination cook in oven";
		int NO_OF_SERVINGS = 2;
		Set<String> ingredients = new HashSet<>();
		ingredients.add(SALMON);
		ingredients.add(TOMATO);
		ingredients.add(CHILLY);
		Category category = Category.NON_VEG;

		var salmonRecipe = new Recipe(SALMON_65, NO_OF_SERVINGS, ingredients, instruction);
		salmonRecipe.setCategory(category);
		recipeBoService.create(salmonRecipe);
		
		Set<String> muttonIngredients = new HashSet<>();
		muttonIngredients.add(PEPPER);
		muttonIngredients.add(MEAT);
		String muttonInstruction = "Marinate meat with Indian spices and bake with oven";

		String MEAT_PEPPER_MASALA = "Meat Pepper Masala";
		int NO_OF_SERVINGS_MUTTON = 4;
		Recipe muttonRecipe = new Recipe(MEAT_PEPPER_MASALA, NO_OF_SERVINGS_MUTTON, muttonIngredients, muttonInstruction);
		muttonRecipe.setCategory(Category.NON_VEG);
		
		recipeBoService.create(muttonRecipe);

		List<Recipe> allRecipes = recipeBoService.findAllRecipes();
		assertTrue(allRecipes.size() > 0);
		int count = allRecipes.size();

		recipeBoService.delete(SALMON_65);
		allRecipes = recipeBoService.findAllRecipes();
		assertTrue(allRecipes.size() > 0);
		assertEquals(--count, allRecipes.size());

	}

	@Test
	@DisplayName("filterRecipes")
	public void testFilterRecipes() {
		String instruction = "Marinate Salmon fish with olive oil , garlic and ginger. After 30 min of marination cook in oven for 10 min";
		
		final int NO_OF_SERVINGS_4 = 4;
		Set<String> ingredients = new HashSet<>();
		ingredients.add(SALMON);
		ingredients.add(TOMATO);
		ingredients.add(CHILLY);
		Category category = Category.NON_VEG;

		var salmonRecipe = new Recipe(SALMON_GRAVY, NO_OF_SERVINGS_4, ingredients, instruction);
		salmonRecipe.setCategory(category);
		
		recipeBoService.create(salmonRecipe);

		instruction = "Boil dal with Indian spices and fry with oil and reheat with oven before serving";
		String SAMBAR = "Sambar";
		
		ingredients = new HashSet<>();
		ingredients.add(DRUMSTICK);
		ingredients.add(TOMATO);
		ingredients.add(CHILLY);
		ingredients.add(POTATO);
		Category vegCategory = Category.VEG;

		var sambarRecipe = new Recipe(SAMBAR, NO_OF_SERVINGS_4, ingredients, instruction);
		sambarRecipe.setCategory(vegCategory);
		recipeBoService.create(sambarRecipe);

		List<String> emptyExcludeIngredients=new ArrayList<>();

		List<String> emptyIncludeIngredients=new ArrayList<>();
		
		List<Recipe> nonVegRecipes = recipeBoService.filterRecipes(vegCategory, emptyIncludeIngredients, emptyExcludeIngredients, null, null);
		assertTrue(nonVegRecipes.size() > 0);

		nonVegRecipes.forEach((e) -> {
			assertAll("All are Veg Recipes", () -> assertEquals(Category.VEG, e.getCategory()));
		});

		List<String> potatoIngredients = new ArrayList<>();
		potatoIngredients.add(POTATO);
		
		
		List<Recipe> withPotatoIngredientsAndServingsWith4 = recipeBoService.filterRecipes(null, potatoIngredients, emptyExcludeIngredients, NO_OF_SERVINGS_4, null);

		withPotatoIngredientsAndServingsWith4.forEach((e) -> {
			assertAll("All are potato ingredients and no of servings 4", 
					() -> assertTrue(e.getIngredients().stream().anyMatch(p->p.equals(POTATO))),
					() -> assertEquals(NO_OF_SERVINGS_4,e.getNoOfServings())
					);
		});
		
		List<String> excludeSalmonIngredient = new ArrayList<>();
		excludeSalmonIngredient.add(SALMON);
		
		List<Recipe> withoutSalmonAndInstructionHasOven = recipeBoService.filterRecipes(null, emptyIncludeIngredients, excludeSalmonIngredient, null, OVEN);

		withoutSalmonAndInstructionHasOven.forEach((e) -> {
			assertAll("All are potato ingredients and no of servings 4", 
					() -> assertTrue(e.getIngredients().stream().noneMatch(p->p.equals(SALMON))),
					() -> assertTrue(e.getInstructions().contains(OVEN))
					);
		});
		
		recipeBoService.delete(SALMON_GRAVY);
		recipeBoService.delete(SAMBAR);

		
	}

}
