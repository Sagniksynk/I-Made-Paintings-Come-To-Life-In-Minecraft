package com.shiraken.template_mod.event;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.shiraken.template_mod.TemplateMod;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = TemplateMod.MODID)
public class CommandEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("firstframe")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("width", IntegerArgumentType.integer(1, 10))
                                .then(Commands.argument("height", IntegerArgumentType.integer(1, 10))
                                        .executes(context -> {
                                            int width = IntegerArgumentType.getInteger(context, "width");
                                            int height = IntegerArgumentType.getInteger(context, "height");
                                            
                                            ItemStack stack = new ItemStack(TemplateMod.CUSTOM_PHOTO_ITEM.get());
                                            CompoundTag tag = new CompoundTag();
                                            tag.putInt("PhotoWidth", width);
                                            tag.putInt("PhotoHeight", height);
                                            stack.setTag(tag);
                                            
                                            context.getSource().getPlayerOrException().getInventory().add(stack);
                                            context.getSource().sendSuccess(() -> Component.literal("Granted custom photo frame sized " + width + "x" + height + "!"), false);
                                            return 1;
                                        })))
        );

        event.getDispatcher().register(
                Commands.literal("secondframe")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("width", IntegerArgumentType.integer(1, 10))
                                .then(Commands.argument("height", IntegerArgumentType.integer(1, 10))
                                        .executes(context -> {
                                            int width = IntegerArgumentType.getInteger(context, "width");
                                            int height = IntegerArgumentType.getInteger(context, "height");
                                            
                                            ItemStack stack = new ItemStack(TemplateMod.SECOND_PHOTO_ITEM.get());
                                            CompoundTag tag = new CompoundTag();
                                            tag.putInt("PhotoWidth", width);
                                            tag.putInt("PhotoHeight", height);
                                            stack.setTag(tag);
                                            
                                            context.getSource().getPlayerOrException().getInventory().add(stack);
                                            context.getSource().sendSuccess(() -> Component.literal("Granted second photo frame sized " + width + "x" + height + "!"), false);
                                            return 1;
                                        })))
        );

        event.getDispatcher().register(
                Commands.literal("thirdframe")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("width", IntegerArgumentType.integer(1, 10))
                                .then(Commands.argument("height", IntegerArgumentType.integer(1, 10))
                                        .executes(context -> {
                                            int width = IntegerArgumentType.getInteger(context, "width");
                                            int height = IntegerArgumentType.getInteger(context, "height");
                                            
                                            ItemStack stack = new ItemStack(TemplateMod.THIRD_PHOTO_ITEM.get());
                                            CompoundTag tag = new CompoundTag();
                                            tag.putInt("PhotoWidth", width);
                                            tag.putInt("PhotoHeight", height);
                                            stack.setTag(tag);
                                            
                                            context.getSource().getPlayerOrException().getInventory().add(stack);
                                            context.getSource().sendSuccess(() -> Component.literal("Granted third photo frame sized " + width + "x" + height + "!"), false);
                                            return 1;
                                        })))
        );

        event.getDispatcher().register(
                Commands.literal("fourthframe")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("width", IntegerArgumentType.integer(1, 10))
                                .then(Commands.argument("height", IntegerArgumentType.integer(1, 10))
                                        .executes(context -> {
                                            int width = IntegerArgumentType.getInteger(context, "width");
                                            int height = IntegerArgumentType.getInteger(context, "height");
                                            
                                            ItemStack stack = new ItemStack(TemplateMod.FOURTH_PHOTO_ITEM.get());
                                            CompoundTag tag = new CompoundTag();
                                            tag.putInt("PhotoWidth", width);
                                            tag.putInt("PhotoHeight", height);
                                            stack.setTag(tag);
                                            
                                            context.getSource().getPlayerOrException().getInventory().add(stack);
                                            context.getSource().sendSuccess(() -> Component.literal("Granted fourth photo frame sized " + width + "x" + height + "!"), false);
                                            return 1;
                                        })))
        );

        event.getDispatcher().register(
                Commands.literal("eighthframe")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("width", IntegerArgumentType.integer(1, 10))
                                .then(Commands.argument("height", IntegerArgumentType.integer(1, 10))
                                        .executes(context -> {
                                            int width = IntegerArgumentType.getInteger(context, "width");
                                            int height = IntegerArgumentType.getInteger(context, "height");
                                            
                                            ItemStack stack = new ItemStack(TemplateMod.EIGHTH_PHOTO_ITEM.get());
                                            CompoundTag tag = new CompoundTag();
                                            tag.putInt("PhotoWidth", width);
                                            tag.putInt("PhotoHeight", height);
                                            stack.setTag(tag);
                                            
                                            context.getSource().getPlayerOrException().getInventory().add(stack);
                                            context.getSource().sendSuccess(() -> Component.literal("Granted eighth photo frame sized " + width + "x" + height + "!"), false);
                                            return 1;
                                        })))
        );

        event.getDispatcher().register(
                Commands.literal("ninthframe")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("width", IntegerArgumentType.integer(1, 10))
                                .then(Commands.argument("height", IntegerArgumentType.integer(1, 10))
                                        .executes(context -> {
                                            int width = IntegerArgumentType.getInteger(context, "width");
                                            int height = IntegerArgumentType.getInteger(context, "height");
                                            
                                            ItemStack stack = new ItemStack(TemplateMod.NINTH_PHOTO_ITEM.get());
                                            CompoundTag tag = new CompoundTag();
                                            tag.putInt("PhotoWidth", width);
                                            tag.putInt("PhotoHeight", height);
                                            stack.setTag(tag);
                                            
                                            context.getSource().getPlayerOrException().getInventory().add(stack);
                                            context.getSource().sendSuccess(() -> Component.literal("Granted ninth photo frame sized " + width + "x" + height + "!"), false);
                                            return 1;
                                        })))
        );

        event.getDispatcher().register(
                Commands.literal("tenthframe")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("width", IntegerArgumentType.integer(1, 10))
                                .then(Commands.argument("height", IntegerArgumentType.integer(1, 10))
                                        .executes(context -> {
                                            int width = IntegerArgumentType.getInteger(context, "width");
                                            int height = IntegerArgumentType.getInteger(context, "height");
                                            
                                            ItemStack stack = new ItemStack(TemplateMod.TENTH_PHOTO_ITEM.get());
                                            CompoundTag tag = new CompoundTag();
                                            tag.putInt("PhotoWidth", width);
                                            tag.putInt("PhotoHeight", height);
                                            stack.setTag(tag);
                                            
                                            context.getSource().getPlayerOrException().getInventory().add(stack);
                                            context.getSource().sendSuccess(() -> Component.literal("Granted tenth photo frame sized " + width + "x" + height + "!"), false);
                                            return 1;
                                        })))
        );

        event.getDispatcher().register(
                Commands.literal("deletefirstframe")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteFirstFrame(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deletesecondframe")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteSecondFrame(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deletethirdframe")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteThirdFrame(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deletefourthframe")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteFourthFrame(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("permadeletefirstpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteFirstPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("permadeletesecondpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteSecondPainting(context.getSource()))
        );
        
        event.getDispatcher().register(
                Commands.literal("permadeletethirdpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteThirdPainting(context.getSource()))
        );
        
        event.getDispatcher().register(
                Commands.literal("permadeletefourthpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteFourthPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deletefifthroom")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteFifthRoom(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deletefifthdoor")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteFifthDoor(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deleteseventhframe")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteSeventhFrame(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deleteseventhpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteSeventhPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("permadeleteseventhpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteSeventhPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deleteeighthframe")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteEighthFrame(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deleteeighthpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteEighthPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("permadeleteeighthpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteEighthPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deleteninthframe")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteNinthFrame(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deleteninthpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteNinthPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("permadeleteninthpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteNinthPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deletetenthframe")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteTenthFrame(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deletetenthpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteTenthPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("permadeletetenthpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteTenthPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("sixthframe")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("width", IntegerArgumentType.integer(1, 10))
                                .then(Commands.argument("height", IntegerArgumentType.integer(1, 10))
                                        .executes(context -> {
                                            int width = IntegerArgumentType.getInteger(context, "width");
                                            int height = IntegerArgumentType.getInteger(context, "height");
                                            
                                            ItemStack stack = new ItemStack(TemplateMod.SIXTH_PHOTO_ITEM.get());
                                            CompoundTag tag = new CompoundTag();
                                            tag.putInt("PhotoWidth", width);
                                            tag.putInt("PhotoHeight", height);
                                            stack.setTag(tag);
                                            
                                            context.getSource().getPlayerOrException().getInventory().add(stack);
                                            context.getSource().sendSuccess(() -> Component.literal("Granted sixth photo frame sized " + width + "x" + height + "!"), false);
                                            return 1;
                                        })))
        );

        event.getDispatcher().register(
                Commands.literal("deletesixthframe")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> deleteSixthFrame(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("deletesixthpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteSixthPainting(context.getSource()))
        );

        event.getDispatcher().register(
                Commands.literal("permadeletesixthpainting")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> permadeleteSixthPainting(context.getSource()))
        );

    }

    private static int deleteFirstFrame(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.CustomPhotoEntity> frames = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.CustomPhotoEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.CustomPhotoEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.CustomPhotoEntity frame : frames) {
                net.minecraft.world.phys.AABB aabb = frame.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted == null) {
                // Fallback: closest frame within 6 blocks
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.CustomPhotoEntity frame : frames) {
                    double dist = player.distanceToSqr(frame);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Successfully deleted targeted custom photo frame."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No custom photo frame found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete custom photo frame: " + e.getMessage()));
            return 0;
        }
    }

    private static int deleteSecondFrame(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.SecondPhotoEntity> frames = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.SecondPhotoEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.SecondPhotoEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.SecondPhotoEntity frame : frames) {
                net.minecraft.world.phys.AABB aabb = frame.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.SecondPhotoEntity frame : frames) {
                    double dist = player.distanceToSqr(frame);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Successfully deleted targeted second photo frame."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No second photo frame found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete second photo frame: " + e.getMessage()));
            return 0;
        }
    }

    private static int permadeleteFirstPainting(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.FirstPaintingEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.FirstPaintingEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.FirstPaintingEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.FirstPaintingEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.FirstPaintingEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Permanently deleted targeted First Painting Skeleton."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No First Painting Skeleton found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete First Painting Skeleton: " + e.getMessage()));
            return 0;
        }
    }

    private static int permadeleteSecondPainting(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.SecondPaintingEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.SecondPaintingEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.SecondPaintingEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.SecondPaintingEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.SecondPaintingEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Permanently deleted targeted Second Painting Skeleton."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No Second Painting Skeleton found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete Second Painting Skeleton: " + e.getMessage()));
            return 0;
        }
    }
    private static int deleteThirdFrame(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.ThirdPhotoEntity> frames = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.ThirdPhotoEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.ThirdPhotoEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.ThirdPhotoEntity frame : frames) {
                net.minecraft.world.phys.AABB aabb = frame.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.ThirdPhotoEntity frame : frames) {
                    double dist = player.distanceToSqr(frame);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Successfully deleted targeted third photo frame."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No third photo frame found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete third photo frame: " + e.getMessage()));
            return 0;
        }
    }

    private static int permadeleteThirdPainting(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.ThirdPaintingEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.ThirdPaintingEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.ThirdPaintingEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.ThirdPaintingEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.ThirdPaintingEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Permanently deleted targeted Third Painting Creeper."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No Third Painting Creeper found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete Third Painting Creeper: " + e.getMessage()));
            return 0;
        }
    }

    private static int deleteFourthFrame(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.FourthPhotoEntity> frames = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.FourthPhotoEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.FourthPhotoEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.FourthPhotoEntity frame : frames) {
                net.minecraft.world.phys.AABB aabb = frame.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.FourthPhotoEntity frame : frames) {
                    double dist = player.distanceToSqr(frame);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Successfully deleted targeted fourth photo frame."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No fourth photo frame found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete fourth photo frame: " + e.getMessage()));
            return 0;
        }
    }

    private static int permadeleteFourthPainting(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.FourthPaintingEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.FourthPaintingEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.FourthPaintingEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.FourthPaintingEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.FourthPaintingEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Permanently deleted targeted Fourth Painting Spider."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No Fourth Painting Spider found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete Fourth Painting Spider: " + e.getMessage()));
            return 0;
        }
    }

    private static int deleteFifthRoom(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 64.0D, lookVec.y * 64.0D, lookVec.z * 64.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(64.0D);
            
            java.util.List<com.shiraken.template_mod.entity.FifthRoomEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.FifthRoomEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.FifthRoomEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.FifthRoomEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(20.0D); // Inflate the AABB because the room is large
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.FifthRoomEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 64.0D * 64.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Successfully deleted targeted Haunted Room."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No Haunted Room found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete Haunted Room: " + e.getMessage()));
            return 0;
        }
    }

    private static int deleteFifthDoor(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 64.0D, lookVec.y * 64.0D, lookVec.z * 64.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(64.0D);
            
            java.util.List<com.shiraken.template_mod.entity.FifthDoorEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.FifthDoorEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.FifthDoorEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.FifthDoorEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(20.0D); // Inflate the AABB because the door is large
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.FifthDoorEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 64.0D * 64.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Successfully deleted targeted Haunted Door."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No Haunted Door found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete Haunted Door: " + e.getMessage()));
            return 0;
        }
    }


    private static int deleteSeventhFrame(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.SeventhPhotoEntity> frames = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.SeventhPhotoEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.SeventhPhotoEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.SeventhPhotoEntity frame : frames) {
                net.minecraft.world.phys.AABB aabb = frame.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.SeventhPhotoEntity frame : frames) {
                    double dist = player.distanceToSqr(frame);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Deleted the seventh frame you were looking at!"), false);
                return 1;
            } else {
                source.sendFailure(net.minecraft.network.chat.Component.literal("No seventh frame found in sight."));
                return 0;
            }
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException e) {
            return 0;
        }
    }

    private static int permadeleteSeventhPainting(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 64.0D, lookVec.y * 64.0D, lookVec.z * 64.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(64.0D);
            
            java.util.List<com.shiraken.template_mod.entity.SeventhPaintingEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.SeventhPaintingEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.SeventhPaintingEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.SeventhPaintingEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(1.0D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.SeventhPaintingEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 64.0D * 64.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Permanently deleted the seventh painting!"), false);
                return 1;
            } else {
                source.sendFailure(net.minecraft.network.chat.Component.literal("No seventh painting found in sight or range."));
                return 0;
            }
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException e) {
            return 0;
        }
    }

    private static int deleteEighthFrame(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.EighthPhotoEntity> frames = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.EighthPhotoEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.EighthPhotoEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.EighthPhotoEntity frame : frames) {
                net.minecraft.world.phys.AABB aabb = frame.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.EighthPhotoEntity frame : frames) {
                    double dist = player.distanceToSqr(frame);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Deleted the eighth frame you were looking at!"), false);
                return 1;
            } else {
                source.sendFailure(net.minecraft.network.chat.Component.literal("No eighth frame found in sight."));
                return 0;
            }
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException e) {
            return 0;
        }
    }

    private static int permadeleteEighthPainting(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 64.0D, lookVec.y * 64.0D, lookVec.z * 64.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(64.0D);
            
            java.util.List<com.shiraken.template_mod.entity.EighthPaintingEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.EighthPaintingEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.EighthPaintingEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.EighthPaintingEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(1.0D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.EighthPaintingEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 64.0D * 64.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Permanently deleted the eighth painting!"), false);
                return 1;
            } else {
                source.sendFailure(net.minecraft.network.chat.Component.literal("No eighth painting found in sight or range."));
                return 0;
            }
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException e) {
            return 0;
        }
    }

    private static int deleteNinthFrame(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.NinthPhotoEntity> frames = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.NinthPhotoEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.NinthPhotoEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.NinthPhotoEntity frame : frames) {
                net.minecraft.world.phys.AABB aabb = frame.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.NinthPhotoEntity frame : frames) {
                    double dist = player.distanceToSqr(frame);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Deleted the ninth frame you were looking at!"), false);
                return 1;
            } else {
                source.sendFailure(net.minecraft.network.chat.Component.literal("No ninth frame found in sight."));
                return 0;
            }
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException e) {
            return 0;
        }
    }

    private static int permadeleteNinthPainting(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 64.0D, lookVec.y * 64.0D, lookVec.z * 64.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(64.0D);
            
            java.util.List<com.shiraken.template_mod.entity.NinthPaintingEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.NinthPaintingEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.NinthPaintingEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.NinthPaintingEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(1.0D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.NinthPaintingEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 64.0D * 64.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Permanently deleted the ninth painting!"), false);
                return 1;
            } else {
                source.sendFailure(net.minecraft.network.chat.Component.literal("No ninth painting found in sight or range."));
                return 0;
            }
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException e) {
            return 0;
        }
    }

    private static int deleteTenthFrame(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.TenthPhotoEntity> frames = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.TenthPhotoEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.TenthPhotoEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.TenthPhotoEntity frame : frames) {
                net.minecraft.world.phys.AABB aabb = frame.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.TenthPhotoEntity frame : frames) {
                    double dist = player.distanceToSqr(frame);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Deleted the tenth frame you were looking at!"), false);
                return 1;
            } else {
                source.sendFailure(net.minecraft.network.chat.Component.literal("No tenth frame found in sight."));
                return 0;
            }
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException e) {
            return 0;
        }
    }

    private static int permadeleteTenthPainting(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 64.0D, lookVec.y * 64.0D, lookVec.z * 64.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(64.0D);
            
            java.util.List<com.shiraken.template_mod.entity.TenthPaintingEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.TenthPaintingEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.TenthPaintingEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.TenthPaintingEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(1.0D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.TenthPaintingEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 64.0D * 64.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Permanently deleted the tenth painting!"), false);
                return 1;
            } else {
                source.sendFailure(net.minecraft.network.chat.Component.literal("No tenth painting found in sight or range."));
                return 0;
            }
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException e) {
            return 0;
        }
    }

    private static int deleteSixthFrame(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.SixthPhotoEntity> frames = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.SixthPhotoEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.SixthPhotoEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.SixthPhotoEntity frame : frames) {
                net.minecraft.world.phys.AABB aabb = frame.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.SixthPhotoEntity frame : frames) {
                    double dist = player.distanceToSqr(frame);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = frame;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Successfully deleted targeted sixth photo frame."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No sixth photo frame found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete sixth photo frame: " + e.getMessage()));
            return 0;
        }
    }

    private static int permadeleteSixthPainting(net.minecraft.commands.CommandSourceStack source) {
        try {
            net.minecraft.server.level.ServerPlayer player = source.getPlayerOrException();
            net.minecraft.world.phys.Vec3 eyePosition = player.getEyePosition(1.0F);
            net.minecraft.world.phys.Vec3 lookVec = player.getLookAngle();
            net.minecraft.world.phys.Vec3 reachVec = eyePosition.add(lookVec.x * 10.0D, lookVec.y * 10.0D, lookVec.z * 10.0D);
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(10.0D);
            
            java.util.List<com.shiraken.template_mod.entity.SixthPaintingEntity> entities = 
                player.level().getEntitiesOfClass(com.shiraken.template_mod.entity.SixthPaintingEntity.class, searchBox, entity -> true);
            
            com.shiraken.template_mod.entity.SixthPaintingEntity targeted = null;
            double minDistance = Double.MAX_VALUE;
            for (com.shiraken.template_mod.entity.SixthPaintingEntity entity : entities) {
                net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(0.5D);
                java.util.Optional<net.minecraft.world.phys.Vec3> clip = aabb.clip(eyePosition, reachVec);
                if (clip.isPresent()) {
                    double dist = eyePosition.distanceToSqr(clip.get());
                    if (dist < minDistance) {
                        minDistance = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted == null) {
                double closestDist = Double.MAX_VALUE;
                for (com.shiraken.template_mod.entity.SixthPaintingEntity entity : entities) {
                    double dist = player.distanceToSqr(entity);
                    if (dist < 6.0D * 6.0D && dist < closestDist) {
                        closestDist = dist;
                        targeted = entity;
                    }
                }
            }
            
            if (targeted != null) {
                targeted.discard();
                source.sendSuccess(() -> Component.literal("Permanently deleted targeted Sixth Painting Fighters."), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("No Sixth Painting Fighters found in your line of sight or nearby."));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Failed to delete Sixth Painting Fighters: " + e.getMessage()));
            return 0;
        }
    }
}

