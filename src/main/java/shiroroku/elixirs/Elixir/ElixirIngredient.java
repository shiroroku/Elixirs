package shiroroku.elixirs.Elixir;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public record ElixirIngredient(ItemStack item, CompoundTag effect1, CompoundTag effect2, CompoundTag effect3) {

	public static final Codec<ElixirIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ItemStack.CODEC.fieldOf("item").forGetter(ElixirIngredient::item),
			CompoundTag.CODEC.fieldOf("effect_1").forGetter(ElixirIngredient::effect1),
			CompoundTag.CODEC.fieldOf("effect_2").forGetter(ElixirIngredient::effect2),
			CompoundTag.CODEC.fieldOf("effect_3").forGetter(ElixirIngredient::effect3))
		.apply(instance, ElixirIngredient::new));

}
