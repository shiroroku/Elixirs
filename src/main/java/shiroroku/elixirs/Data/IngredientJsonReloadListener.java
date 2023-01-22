package shiroroku.elixirs.Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import shiroroku.elixirs.Elixir.ElixirIngredient;
import shiroroku.elixirs.Elixirs;
import shiroroku.elixirs.Registry.IngredientRegistry;

import java.util.Map;

public class IngredientJsonReloadListener extends SimpleJsonResourceReloadListener {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public IngredientJsonReloadListener() {
		super(GSON, "ingredients");
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
		for (var entry : pObject.entrySet()) {
			ElixirIngredient.CODEC.parse(JsonOps.INSTANCE, entry.getValue())
				.resultOrPartial(err -> Elixirs.LOGGER.error("Failed to load ingredient json: {}", err))
				.ifPresent((ingredient) -> IngredientRegistry.INGREDIENTS.put(entry.getKey(), ingredient));
		}
	}
}
