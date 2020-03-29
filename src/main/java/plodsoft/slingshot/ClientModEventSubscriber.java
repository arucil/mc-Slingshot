package plodsoft.slingshot;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Slingshot.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {
    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ENDER_BALL.get(),
                m -> new net.minecraft.client.renderer.entity.SpriteRenderer<>(m,
                        Minecraft.getInstance().getItemRenderer()));

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.METAL_BALL.get(),
                m -> new net.minecraft.client.renderer.entity.SpriteRenderer<>(m,
                        Minecraft.getInstance().getItemRenderer()));

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.STONE_BALL.get(),
                m -> new net.minecraft.client.renderer.entity.SpriteRenderer<>(m,
                Minecraft.getInstance().getItemRenderer()));

    }
}
