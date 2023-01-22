package shiroroku.elixirs.Elixir;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffect;

import java.util.TreeMap;
import java.util.function.Function;

public interface IElixirIngredient {

	TreeMap<Integer, Tuple<MobEffect, Integer>> getEffect();
}
