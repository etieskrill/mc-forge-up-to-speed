package net.maxet.forgeuts.common;

import com.mojang.logging.LogUtils;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(Leyline.MODID)
public class Leyline {

    public static final String MODID = "leyline";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<Block> LEY_BLOCK = BLOCKS.register("ley_block", () -> new Block(BlockBehaviour.Properties.of(Material.STRUCTURAL_AIR)));
    public static final RegistryObject<Item> LEY_BLOCK_ITEM = ITEMS.register("ley_block", () -> new BlockItem(LEY_BLOCK.get(), new Item.Properties()));

    public Leyline() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        PARTICLES.register(modEventBus);
        
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }
    
    //TODO this event class might be somewhat importante
//    AdvancementEvent.AdvancementEarnEvent

    //TODO extending this is probably the way to save custom shareable data,
    // such as per-clan data
//    SavedData data = new SavedData() {
//        @Override
//        public CompoundTag save(@NotNull CompoundTag tag) {
//            return null;
//        }
//    };
    
    //TODO similarly, this is what is saved per player using a custom file suffix,
    // perhaps to store advancements prior to joining the clan
//    PlayerEvent.SaveToFile
    
}
