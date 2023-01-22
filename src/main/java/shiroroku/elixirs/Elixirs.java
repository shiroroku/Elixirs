package shiroroku.elixirs;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import shiroroku.elixirs.Registry.BlockRegistry;
import shiroroku.elixirs.Registry.ItemRegistry;

@Mod(Elixirs.MODID)
public class Elixirs {

	public static final String MODID = "elixirs";
	public static final Logger LOGGER = LogUtils.getLogger();

	public Elixirs() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemRegistry.ITEMS.register(eventBus);
		BlockRegistry.BLOCKS.register(eventBus);
	}

	public static final CreativeModeTab CREATIVETAB = new CreativeModeTab(MODID) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ItemRegistry.elixir.get());
		}
	};
}
