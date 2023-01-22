package shiroroku.elixirs.Registry;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import shiroroku.elixirs.Elixir.ElixirItem;
import shiroroku.elixirs.Elixirs;

import java.util.function.Supplier;

public class ItemRegistry {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Elixirs.MODID);

	public static final RegistryObject<Item> elixir = ITEMS.register("elixir", () -> new ElixirItem(new Item.Properties().tab(Elixirs.CREATIVETAB)));
	public static final RegistryObject<Block> wise_hazel = regElixirIngredient("wise_hazel", () -> new FlowerBlock(MobEffects.REGENERATION, 10, BlockBehaviour.Properties.of(Material.PLANT)
		.noCollission()
		.instabreak()
		.sound(SoundType.GRASS)));
	public static final RegistryObject<Block> blazel = regElixirIngredient("blazel", () -> new FlowerBlock(MobEffects.WITHER, 10, BlockBehaviour.Properties.of(Material.PLANT)
		.noCollission()
		.instabreak()
		.sound(SoundType.GRASS)));
	public static final RegistryObject<Block> blightshade = regElixirIngredient("blightshade", () -> new FlowerBlock(MobEffects.BLINDNESS, 10, BlockBehaviour.Properties.of(Material.PLANT)
		.noCollission()
		.instabreak()
		.sound(SoundType.GRASS)));

	private static <I extends Item> RegistryObject<Item> regElixirIngredient(final String id) {
		return ITEMS.register(id, () -> new Item(new Item.Properties().tab(Elixirs.CREATIVETAB)));
	}

	private static <I extends Block> RegistryObject<I> regElixirIngredient(final String id, final Supplier<? extends I> supplier) {
		RegistryObject<I> createdBlock = BlockRegistry.BLOCKS.register(id, supplier);
		ITEMS.register(id, () -> new BlockItem(createdBlock.get(), new Item.Properties().tab(Elixirs.CREATIVETAB)));
		return createdBlock;
	}
}
