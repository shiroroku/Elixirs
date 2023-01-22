package shiroroku.elixirs;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import shiroroku.elixirs.Elixir.ElixirUtil;
import shiroroku.elixirs.Registry.ItemRegistry;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = Elixirs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SetupClient {

	@SubscribeEvent
	public static void onItemColorHandler(RegisterColorHandlersEvent.Item event) {
		event.register((stack, tintIndex) -> {
			if (tintIndex == 0) {
				//AtomicInteger badEffectCount = new AtomicInteger();
				AtomicReference<Color> mixedColor = new AtomicReference<>();
				ElixirUtil.getFromNBT(stack).forEach((mobEffect, values) -> {
					if (mixedColor.get() == null) {
						mixedColor.set(new Color(mobEffect.getColor()));
					} else {
						mixedColor.set(blendColor(new Color(mobEffect.getColor()), mixedColor.get(), 0.5f));
					}

					//if (!mobEffect.isBeneficial()) {
					//	badEffectCount.getAndIncrement();
					//}
				});

				if (mixedColor.get() != null) {
					return mixedColor.get().brighter().getRGB();
				}
				//return ModUtil.blendColor(new Color(60, 60, 255), new Color(255, 60, 60), badEffectCount.get() <= 0 ? 0 : (float) badEffectCount.get() / 3f).getRGB();
			}
			return 16777215;
		}, ItemRegistry.elixir.get());
	}

	private static Color blendColor(Color c1, Color c2, float ratio) {
		float iRatio = 1.0f - Math.min(1, Math.max(0, ratio));

		int rgb1 = c1.getRGB();
		int a1 = (rgb1 >> 24 & 0xff);
		int r1 = ((rgb1 & 0xff0000) >> 16);
		int g1 = ((rgb1 & 0xff00) >> 8);
		int b1 = (rgb1 & 0xff);

		int rgb2 = c2.getRGB();
		int a2 = (rgb2 >> 24 & 0xff);
		int r2 = ((rgb2 & 0xff0000) >> 16);
		int g2 = ((rgb2 & 0xff00) >> 8);
		int b2 = (rgb2 & 0xff);

		int a = (int) ((a1 * iRatio) + (a2 * ratio));
		int r = (int) ((r1 * iRatio) + (r2 * ratio));
		int g = (int) ((g1 * iRatio) + (g2 * ratio));
		int b = (int) ((b1 * iRatio) + (b2 * ratio));

		return new Color(a << 24 | r << 16 | g << 8 | b);
	}

}
