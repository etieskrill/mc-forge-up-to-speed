package net.maxet.forgeuts.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.maxet.forgeuts.common.Leyline.LEY_BLOCK_ITEM;
import static net.maxet.forgeuts.common.Leyline.MODID;

public class ClientEvents {
    
    private static final Minecraft MC = Minecraft.getInstance();
    
    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void onRegisterBindings(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.OPEN_GUI);
        }
    
        @SubscribeEvent
        public static void onRegisterCreativeTab(CreativeModeTabEvent.Register event) {
            event.registerCreativeModeTab(new ResourceLocation(MODID, "all"), builder ->
                    builder
                            .title(Component.translatable("itemGroup." + MODID + ".all"))
                            .icon(() -> new ItemStack(LEY_BLOCK_ITEM.get()))
                            .displayItems((params, output) -> {
                                output.accept(LEY_BLOCK_ITEM.get());
                            })
            );
        }
    }
    
    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ForgeBusEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            while (KeyBinding.OPEN_GUI.consumeClick()) {
                MC.setScreen(ClanScreen.get());
            }
        }
    }
    
}


