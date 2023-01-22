package shiroroku.elixirs.Registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import shiroroku.elixirs.Elixir.ElixirIngredient;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class IngredientRegistry {

	public static Map<ResourceLocation, ElixirIngredient> INGREDIENTS = new LinkedHashMap<>();

	public static Optional<ElixirIngredient> getIngredient(Item stack) {
		for (Map.Entry<ResourceLocation, ElixirIngredient> entry : INGREDIENTS.entrySet()) {
			ElixirIngredient value = entry.getValue();
			if (value.item().is(stack)) {
				return Optional.of(value);
			}
		}
		return Optional.empty();
	}

}
