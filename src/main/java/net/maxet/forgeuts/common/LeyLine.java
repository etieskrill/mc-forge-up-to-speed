package net.maxet.forgeuts.common;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(LeyLine.MODID)
public class LeyLine {

    public static final String MODID = "leyline";

    private static final Minecraft MC = Minecraft.getInstance();
    private static final IEventBus EVENT_BUS = MinecraftForge.EVENT_BUS;

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<Block> LEY_BLOCK = BLOCKS.register("ley_block", () -> new Block(BlockBehaviour.Properties.of(Material.STRUCTURAL_AIR)));
    public static final RegistryObject<Item> LEY_BLOCK_ITEM = ITEMS.register("ley_block", () -> new BlockItem(LEY_BLOCK.get(), new Item.Properties()));

    public static final Lazy<KeyMapping> GUI_MAPPING = Lazy.of(() ->
            new KeyMapping("key." + MODID + "openClanGui", InputConstants.Type.KEYSYM,
                    InputConstants.KEY_C, "key.categories.misc"));

    private static final ResourceLocation BACKGROUND_TEXTURE_FILE =
            new ResourceLocation(MODID, "textures/gui/clan_gui_background_smol.png");

    public LeyLine() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        PARTICLES.register(modEventBus);

        EVENT_BUS.register(this);

        modEventBus.addListener(this::registerCreativeTab);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        while (GUI_MAPPING.get().consumeClick()) {
            MC.setScreen(ClanGuiScreen.getInstance());
        }
    }

    static class ClanGuiScreen extends Screen {
        private static ClanGuiScreen instance;

        public ClanGuiScreen(Component title) {
            super(title);
        }

        @Override
        protected void init() {
            addRenderableWidget(
                    Button.builder(Component.literal("Yeeees"), button1 ->
                            MC.player.sendSystemMessage(Component.literal("Succ ma dicc"))
                    ).build());
            addRenderableWidget(
                    Button.builder(Component.literal("Yeeees2"), button1 ->
                            MC.player.sendSystemMessage(Component.literal("Succ ma dicc 2: Electric Boogaloo"))
                    ).pos(0, 150).build());

            SavedData data = new SavedData() {
                @Override
                public CompoundTag save(CompoundTag p_77763_) {
                    return null;
                }
            };
            //EntityPersistentStorage<?>
        }

        @Override
        public void render(@NotNull PoseStack batch, int x, int y, float partialTick) {
            //renderRect(batch, 0, 0, 100, 100, 0x80000000);
            renderBackground(batch);
            super.render(batch, x, y, partialTick);
        }

        @Override
        public void renderBackground(PoseStack batch) {
            RenderSystem.setShaderColor(0.5f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE_FILE);
            blit(batch, 0, 0, 0, 0, 128, 128, 128, 128);
        }

        //private void renderTexture(int x, int y, float atlasU1, float atlasV1, int atlasWidth, int atlasHeight, atlasU2, atlasV2)

        private void renderRect(PoseStack batch, int x, int y, int width, int height, int colour) {
            GuiComponent.fill(batch, x, y, x + width, y + height, colour);
        }

        public static ClanGuiScreen getInstance() {
            if (instance == null)
                instance = new ClanGuiScreen(Component.literal("Clan GUI")) {
//                    @Override
//                    public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
//                    }
                };
            return instance;
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }
    }

    @SubscribeEvent
    public void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(GUI_MAPPING.get());
    }
    
    @SubscribeEvent
    public void registerCreativeTab(CreativeModeTabEvent.Register event) {
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
