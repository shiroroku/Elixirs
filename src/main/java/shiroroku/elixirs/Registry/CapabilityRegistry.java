package shiroroku.elixirs.Registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.network.PacketDistributor;
import shiroroku.elixirs.Capability.IIngredientKnowledge;
import shiroroku.elixirs.Capability.IngredientKnowledgeCapability;
import shiroroku.elixirs.Elixirs;
import shiroroku.elixirs.Network.IngredientKnowledgePacket;
import shiroroku.elixirs.SetupNetwork;

@Mod.EventBusSubscriber(modid = Elixirs.MODID)
public class CapabilityRegistry {

	public static final Capability<IIngredientKnowledge> ingredient_knowledge = CapabilityManager.get(new CapabilityToken<>() {});

	@SubscribeEvent
	public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Player) {
			event.addCapability(new ResourceLocation(Elixirs.MODID, "ingredient_knowledge"), new IngredientKnowledgeCapability.Provider());
		}
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(IIngredientKnowledge.class);
	}

	public static LazyOptional<IIngredientKnowledge> getIngredientKnowledge(LivingEntity entity) {
		return entity.getCapability(ingredient_knowledge);
	}

	@SubscribeEvent
	public static void playerClone(PlayerEvent.Clone event) {
		Player oldPlayer = event.getOriginal();
		oldPlayer.revive();
		getIngredientKnowledge(oldPlayer).ifPresent(oldKnowledge -> getIngredientKnowledge(event.getEntity()).ifPresent(knowledge -> knowledge.setKnowledge(oldKnowledge.getKnowledge())));
		event.getOriginal().invalidateCaps();
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		sendIngredientKnowledgePacket(event.getEntity());
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		sendIngredientKnowledgePacket(event.getEntity());
	}

	@SubscribeEvent
	public static void onPlayerStartTracking(PlayerEvent.StartTracking event) {
		sendIngredientKnowledgePacket(event.getEntity());
	}

	@SubscribeEvent
	public static void onPlayerDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
		sendIngredientKnowledgePacket(event.getEntity());
	}

	public static void sendIngredientKnowledgePacket(Player player) {
		if (EffectiveSide.get() == LogicalSide.SERVER && player instanceof ServerPlayer serverPlayer) {
			SetupNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new IngredientKnowledgePacket(CapabilityRegistry.getIngredientKnowledge(player)
				.orElse(new IngredientKnowledgeCapability()), player.getUUID()));
		}
	}
}