package top.byteeeee.craftable_carved_pumpkin;

import net.fabricmc.api.ModInitializer;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import top.byteeeee.craftable_carved_pumpkin.specialCraftingRecipe.PumpkinScissorsRecipe;

public class CraftableCarvedPumpkin implements ModInitializer {

    public static final RecipeSerializer<PumpkinScissorsRecipe> PUMPKIN_SCISSORS_RECIPE_SERIALIZER = RecipeSerializer.register("ams_crafting_carved_pumpkin", new SpecialRecipeSerializer<>(PumpkinScissorsRecipe::new));

    public static final String modName = "Craftable-Carved-Pumpkin";
    public static final Logger LOGGER = LogManager.getLogger(modName);

    @Override
    public void onInitialize() {
        LOGGER.info(modName + " " + "loaded!");
    }
}
