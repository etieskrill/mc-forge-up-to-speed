package net.maxet.forgeuts.common;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(LeyLine.MODID)
public class LeyLine {
    
    public static final String MODID = "leyline";
    
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);
    
    public static final RegistryObject<Block> LEY_BLOCK = BLOCKS.register("ley_block", () -> new Block(BlockBehaviour.Properties.of(Material.STRUCTURAL_AIR)));
    public static final RegistryObject<Item> LEY_BLOCK_ITEM = ITEMS.register("ley_block", () -> new BlockItem(LEY_BLOCK.get(), new Item.Properties()));
    
    public LeyLine() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        PARTICLES.register(modEventBus);
    
        MinecraftForge.EVENT_BUS.register(this);
        
        modEventBus.addListener(this::registerCreativeTab);
    }
    
    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
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
