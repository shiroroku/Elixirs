package shiroroku.elixirs;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shiroroku.elixirs.Command.IngredientKnowledgeCommand;
import shiroroku.elixirs.Data.IngredientJsonReloadListener;
import shiroroku.elixirs.Registry.CapabilityRegistry;
import shiroroku.elixirs.Registry.IngredientRegistry;

import java.util.concurrent.atomic.AtomicInteger;

public class ModEvents {

	@SubscribeEvent
	public static void onRegisterCommand(RegisterCommandsEvent event) {
		IngredientKnowledgeCommand.register(event.getDispatcher());
	}

	@SubscribeEvent
	public static void onRegisterReloadListeners(AddReloadListenerEvent event) {
		IngredientRegistry.INGREDIENTS.clear();
		event.addListener(new IngredientJsonReloadListener());
	}

	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		IngredientRegistry.getIngredient(event.getItemStack().getItem()).ifPresent((ingredient -> {
			event.getToolTip().add(Component.translatable("item.elixirs.ingredient").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
			CapabilityRegistry.getIngredientKnowledge(Minecraft.getInstance().player).ifPresent(cap ->

				AtomicInteger slot = new AtomicInteger(1);
				effectMap.values().forEach(mobeffect -> {
					if (cap.knows(stack.getItem(), slot.get())) {
						int seconds = mobeffect.getB();
						String formatted = String.format("%d:%02d", (seconds % 3600) / 60, seconds = seconds % 60);
						String duration = mobeffect.getA().isInstantenous() ? "" : " (" + formatted + ")";
						tooltip.add(Component.literal("- ")
							.append(Component.translatable(mobeffect.getA().getDescriptionId()))
							.append(duration)
							.withStyle(mobeffect.getA().isBeneficial() ? ChatFormatting.BLUE : ChatFormatting.RED));
					} else {
						tooltip.add(Component.literal("- ").append("???").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
					}
					slot.getAndIncrement();
				});
			});

		}));
	}

}
