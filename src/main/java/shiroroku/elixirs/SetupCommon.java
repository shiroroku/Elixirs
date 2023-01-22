package shiroroku.elixirs;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Elixirs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupCommon {

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(ModEvents.class);
		//event.enqueueWork(PlantGeneration::registerPlantGeneration);
	}

}