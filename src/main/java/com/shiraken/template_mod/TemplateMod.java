package com.shiraken.template_mod;

import com.mojang.logging.LogUtils;
import com.shiraken.template_mod.entity.FirstPaintingEntity;
import com.shiraken.template_mod.entity.CustomPhotoEntity;
import com.shiraken.template_mod.entity.SecondPaintingEntity;
import com.shiraken.template_mod.entity.SecondPhotoEntity;
import com.shiraken.template_mod.item.CustomPhotoItem;
import com.shiraken.template_mod.item.SecondPhotoItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TemplateMod.MODID)
public class TemplateMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "template_mod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> ORANGE_CARPET_BLOCK = BLOCKS.register("orange_carpet_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.ORANGE_CARPET)));
    public static final RegistryObject<Item> ORANGE_CARPET_BLOCK_ITEM = ITEMS.register("orange_carpet_block", () -> new BlockItem(ORANGE_CARPET_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> GHOST_QUARTZ_BLOCK = BLOCKS.register("ghost_quartz_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).noCollission()));
    public static final RegistryObject<Item> GHOST_QUARTZ_BLOCK_ITEM = ITEMS.register("ghost_quartz_block", () -> new com.shiraken.template_mod.item.GhostQuartzBlockItem(GHOST_QUARTZ_BLOCK.get(), new Item.Properties()));

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())));

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    public static final RegistryObject<EntityType<FirstPaintingEntity>> FIRST_PAINTING =
            ENTITY_TYPES.register("first_painting",
                    () -> EntityType.Builder.of(FirstPaintingEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 1.8f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "first_painting").toString()));

    public static final RegistryObject<EntityType<CustomPhotoEntity>> CUSTOM_PHOTO =
            ENTITY_TYPES.register("custom_photo",
                    () -> EntityType.Builder.<CustomPhotoEntity>of(CustomPhotoEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(10)
                            .updateInterval(Integer.MAX_VALUE)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "custom_photo").toString()));

    public static final RegistryObject<EntityType<SecondPaintingEntity>> SECOND_PAINTING =
            ENTITY_TYPES.register("second_painting",
                    () -> EntityType.Builder.of(SecondPaintingEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 1.8f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "second_painting").toString()));

    public static final RegistryObject<EntityType<SecondPhotoEntity>> SECOND_PHOTO =
            ENTITY_TYPES.register("second_photo",
                    () -> EntityType.Builder.<SecondPhotoEntity>of(SecondPhotoEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(10)
                            .updateInterval(Integer.MAX_VALUE)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "second_photo").toString()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.ThirdPaintingEntity>> THIRD_PAINTING =
            ENTITY_TYPES.register("third_painting",
                    () -> EntityType.Builder.of(com.shiraken.template_mod.entity.ThirdPaintingEntity::new, MobCategory.CREATURE)
                            .sized(1.0f, 2.25f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "third_painting").toString()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.ThirdPhotoEntity>> THIRD_PHOTO =
            ENTITY_TYPES.register("third_photo",
                    () -> EntityType.Builder.<com.shiraken.template_mod.entity.ThirdPhotoEntity>of(com.shiraken.template_mod.entity.ThirdPhotoEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(10)
                            .updateInterval(Integer.MAX_VALUE)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "third_photo").toString()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.FourthPaintingEntity>> FOURTH_PAINTING =
            ENTITY_TYPES.register("fourth_painting",
                    () -> EntityType.Builder.of(com.shiraken.template_mod.entity.FourthPaintingEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.8f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "fourth_painting").toString()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.FourthPhotoEntity>> FOURTH_PHOTO =
            ENTITY_TYPES.register("fourth_photo",
                    () -> EntityType.Builder.<com.shiraken.template_mod.entity.FourthPhotoEntity>of(com.shiraken.template_mod.entity.FourthPhotoEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(10)
                            .updateInterval(Integer.MAX_VALUE)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "fourth_photo").toString()));

    public static final RegistryObject<Item> FIRST_PAINTING_SPAWN_EGG = ITEMS.register("first_painting_spawn_egg",
            () -> new ForgeSpawnEggItem(FIRST_PAINTING, 0x948e8d, 0x3b3635, new Item.Properties()));

    public static final RegistryObject<Item> SECOND_PAINTING_SPAWN_EGG = ITEMS.register("second_painting_spawn_egg",
            () -> new ForgeSpawnEggItem(SECOND_PAINTING, 0x948e8d, 0x3b3635, new Item.Properties()));

    public static final RegistryObject<Item> THIRD_PAINTING_SPAWN_EGG = ITEMS.register("third_painting_spawn_egg",
            () -> new ForgeSpawnEggItem(THIRD_PAINTING, 0x51A03E, 0x000000, new Item.Properties()));

    public static final RegistryObject<Item> CUSTOM_PHOTO_ITEM = ITEMS.register("custom_photo_item",
            () -> new CustomPhotoItem(new Item.Properties()));

    public static final RegistryObject<Item> SECOND_PHOTO_ITEM = ITEMS.register("second_photo_item",
            () -> new SecondPhotoItem(new Item.Properties()));

    public static final RegistryObject<Item> THIRD_PHOTO_ITEM = ITEMS.register("third_photo_item",
            () -> new com.shiraken.template_mod.item.ThirdPhotoItem(new Item.Properties()));

    public static final RegistryObject<Item> FOURTH_PAINTING_SPAWN_EGG = ITEMS.register("fourth_painting_spawn_egg",
            () -> new ForgeSpawnEggItem(FOURTH_PAINTING, 0x4B0000, 0x000000, new Item.Properties()));

    public static final RegistryObject<Item> FOURTH_PHOTO_ITEM = ITEMS.register("fourth_photo_item",
            () -> new com.shiraken.template_mod.item.FourthPhotoItem(new Item.Properties()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.FifthRoomEntity>> FIFTH_ROOM =
            ENTITY_TYPES.register("fifth_room",
                    () -> EntityType.Builder.of(com.shiraken.template_mod.entity.FifthRoomEntity::new, MobCategory.CREATURE)
                            .sized(2.0f, 2.0f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "fifth_room").toString()));

    public static final RegistryObject<Item> FIFTH_ROOM_ITEM = ITEMS.register("fifth_room_item",
            () -> new com.shiraken.template_mod.item.FifthRoomItem(new Item.Properties()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.FifthDoorEntity>> FIFTH_DOOR =
            ENTITY_TYPES.register("fifth_door",
                    () -> EntityType.Builder.of(com.shiraken.template_mod.entity.FifthDoorEntity::new, MobCategory.CREATURE)
                            .sized(2.0f, 2.0f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "fifth_door").toString()));

    public static final RegistryObject<Item> FIFTH_DOOR_ITEM = ITEMS.register("fifth_door_item",
            () -> new com.shiraken.template_mod.item.FifthDoorItem(new Item.Properties()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.SeventhPaintingEntity>> SEVENTH_PAINTING =
            ENTITY_TYPES.register("seventh_painting",
                    () -> EntityType.Builder.of(com.shiraken.template_mod.entity.SeventhPaintingEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 1.8f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "seventh_painting").toString()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.SeventhPhotoEntity>> SEVENTH_PHOTO =
            ENTITY_TYPES.register("seventh_photo",
                    () -> EntityType.Builder.<com.shiraken.template_mod.entity.SeventhPhotoEntity>of(com.shiraken.template_mod.entity.SeventhPhotoEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(10)
                            .updateInterval(Integer.MAX_VALUE)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "seventh_photo").toString()));

    public static final RegistryObject<Item> SEVENTH_PAINTING_SPAWN_EGG = ITEMS.register("seventh_painting_spawn_egg",
            () -> new net.minecraftforge.common.ForgeSpawnEggItem(SEVENTH_PAINTING, 0x111111, 0x444444, new Item.Properties()));

    public static final RegistryObject<Item> SEVENTH_PHOTO_ITEM = ITEMS.register("seventh_photo_item",
            () -> new com.shiraken.template_mod.item.SeventhPhotoItem(new Item.Properties()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.EighthPaintingEntity>> EIGHTH_PAINTING =
            ENTITY_TYPES.register("eighth_painting",
                    () -> EntityType.Builder.of(com.shiraken.template_mod.entity.EighthPaintingEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 1.8f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "eighth_painting").toString()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.EighthPhotoEntity>> EIGHTH_PHOTO =
            ENTITY_TYPES.register("eighth_photo",
                    () -> EntityType.Builder.<com.shiraken.template_mod.entity.EighthPhotoEntity>of(com.shiraken.template_mod.entity.EighthPhotoEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(10)
                            .updateInterval(Integer.MAX_VALUE)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "eighth_photo").toString()));

    public static final RegistryObject<Item> EIGHTH_PAINTING_SPAWN_EGG = ITEMS.register("eighth_painting_spawn_egg",
            () -> new net.minecraftforge.common.ForgeSpawnEggItem(EIGHTH_PAINTING, 0x4A3728, 0x2B1D12, new Item.Properties()));

    public static final RegistryObject<Item> EIGHTH_PHOTO_ITEM = ITEMS.register("eighth_photo_item",
            () -> new com.shiraken.template_mod.item.EighthPhotoItem(new Item.Properties()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.NinthPaintingEntity>> NINTH_PAINTING =
            ENTITY_TYPES.register("ninth_painting",
                    () -> EntityType.Builder.of(com.shiraken.template_mod.entity.NinthPaintingEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 1.8f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "ninth_painting").toString()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.NinthPhotoEntity>> NINTH_PHOTO =
            ENTITY_TYPES.register("ninth_photo",
                    () -> EntityType.Builder.<com.shiraken.template_mod.entity.NinthPhotoEntity>of(com.shiraken.template_mod.entity.NinthPhotoEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(10)
                            .updateInterval(Integer.MAX_VALUE)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "ninth_photo").toString()));

    public static final RegistryObject<Item> NINTH_PAINTING_SPAWN_EGG = ITEMS.register("ninth_painting_spawn_egg",
            () -> new net.minecraftforge.common.ForgeSpawnEggItem(NINTH_PAINTING, 0x8C7A6B, 0x3E2723, new Item.Properties()));

    public static final RegistryObject<Item> NINTH_PHOTO_ITEM = ITEMS.register("ninth_photo_item",
            () -> new com.shiraken.template_mod.item.NinthPhotoItem(new Item.Properties()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.TenthPaintingEntity>> TENTH_PAINTING =
            ENTITY_TYPES.register("tenth_painting",
                    () -> EntityType.Builder.of(com.shiraken.template_mod.entity.TenthPaintingEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 1.8f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "tenth_painting").toString()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.TenthPhotoEntity>> TENTH_PHOTO =
            ENTITY_TYPES.register("tenth_photo",
                    () -> EntityType.Builder.<com.shiraken.template_mod.entity.TenthPhotoEntity>of(com.shiraken.template_mod.entity.TenthPhotoEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(10)
                            .updateInterval(Integer.MAX_VALUE)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "tenth_photo").toString()));

    public static final RegistryObject<Item> TENTH_PAINTING_SPAWN_EGG = ITEMS.register("tenth_painting_spawn_egg",
            () -> new net.minecraftforge.common.ForgeSpawnEggItem(TENTH_PAINTING, 0xDDDDDD, 0x444444, new Item.Properties()));

    public static final RegistryObject<Item> TENTH_PHOTO_ITEM = ITEMS.register("tenth_photo_item",
            () -> new com.shiraken.template_mod.item.TenthPhotoItem(new Item.Properties()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.SixthPaintingEntity>> SIXTH_PAINTING =
            ENTITY_TYPES.register("sixth_painting",
                    () -> EntityType.Builder.of(com.shiraken.template_mod.entity.SixthPaintingEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 1.8f)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "sixth_painting").toString()));

    public static final RegistryObject<EntityType<com.shiraken.template_mod.entity.SixthPhotoEntity>> SIXTH_PHOTO =
            ENTITY_TYPES.register("sixth_photo",
                    () -> EntityType.Builder.<com.shiraken.template_mod.entity.SixthPhotoEntity>of(com.shiraken.template_mod.entity.SixthPhotoEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(10)
                            .updateInterval(Integer.MAX_VALUE)
                            .build(new net.minecraft.resources.ResourceLocation(MODID, "sixth_photo").toString()));

    public static final RegistryObject<Item> SIXTH_PAINTING_SPAWN_EGG = ITEMS.register("sixth_painting_spawn_egg",
            () -> new net.minecraftforge.common.ForgeSpawnEggItem(SIXTH_PAINTING, 0x555555, 0x888888, new Item.Properties()));

    public static final RegistryObject<Item> SIXTH_PHOTO_ITEM = ITEMS.register("sixth_photo_item",
            () -> new com.shiraken.template_mod.item.SixthPhotoItem(new Item.Properties()));
    public static final RegistryObject<CreativeModeTab> PAINTINGS_TAB = CREATIVE_MODE_TABS.register("paintings_tab", () -> CreativeModeTab.builder()
            .icon(() -> net.minecraft.world.item.Items.PAINTING.getDefaultInstance())
            .title(Component.translatable("creativetab.paintings_tab"))
            .displayItems((parameters, output) -> {
                output.accept(FIRST_PAINTING_SPAWN_EGG.get());
                output.accept(SECOND_PAINTING_SPAWN_EGG.get());
                output.accept(THIRD_PAINTING_SPAWN_EGG.get());
                output.accept(FOURTH_PAINTING_SPAWN_EGG.get());
                output.accept(SIXTH_PAINTING_SPAWN_EGG.get());
                output.accept(SEVENTH_PAINTING_SPAWN_EGG.get());
                output.accept(CUSTOM_PHOTO_ITEM.get());
                output.accept(SECOND_PHOTO_ITEM.get());
                output.accept(THIRD_PHOTO_ITEM.get());
                output.accept(FOURTH_PHOTO_ITEM.get());
                output.accept(SIXTH_PHOTO_ITEM.get());
                output.accept(SEVENTH_PHOTO_ITEM.get());
                output.accept(EIGHTH_PAINTING_SPAWN_EGG.get());
                output.accept(EIGHTH_PHOTO_ITEM.get());
                output.accept(NINTH_PAINTING_SPAWN_EGG.get());
                output.accept(NINTH_PHOTO_ITEM.get());
                output.accept(TENTH_PAINTING_SPAWN_EGG.get());
                output.accept(TENTH_PHOTO_ITEM.get());
                output.accept(FIFTH_ROOM_ITEM.get());
                output.accept(FIFTH_DOOR_ITEM.get());
                output.accept(ORANGE_CARPET_BLOCK_ITEM.get());
                output.accept(GHOST_QUARTZ_BLOCK_ITEM.get());
            }).build());

    public TemplateMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            com.shiraken.template_mod.network.NetworkHandler.register();
        });

        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}

