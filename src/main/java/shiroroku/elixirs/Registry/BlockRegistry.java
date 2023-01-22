package shiroroku.elixirs.Registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import shiroroku.elixirs.Elixirs;

import java.util.function.Supplier;

public class BlockRegistry {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Elixirs.MODID);

	/**
	 * Creates and returns a Block while also creating an Item for that block.
	 *
	 * @param id Block id
	 * @param supplier Block factory
	 * @return Registry object of supplied Block
	 */
	private static <I extends Block> RegistryObject<I> regBlockItem(final String id, final Supplier<? extends I> supplier) {
		RegistryObject<I> createdBlock = BLOCKS.register(id, supplier);
		ItemRegistry.ITEMS.register(id, () -> new BlockItem(createdBlock.get(), new Item.Properties().tab(Elixirs.CREATIVETAB)));
		return createdBlock;
	}
}
