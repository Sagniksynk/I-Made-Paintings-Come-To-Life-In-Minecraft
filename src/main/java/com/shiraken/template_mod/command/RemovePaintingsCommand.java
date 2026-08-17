package com.shiraken.template_mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.shiraken.template_mod.entity.FirstPaintingEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class RemovePaintingsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("deletefirstpaintingmob")
                .requires(source -> source.hasPermission(2))
                .executes(context -> deleteFirstPaintingMob(context.getSource())));

        dispatcher.register(Commands.literal("deletesecondpaintingmob")
                .requires(source -> source.hasPermission(2))
                .executes(context -> deleteSecondPaintingMob(context.getSource())));
        dispatcher.register(Commands.literal("deletethirdpaintingmob")
                .requires(source -> source.hasPermission(2))
                .executes(context -> deleteThirdPaintingMob(context.getSource())));
        dispatcher.register(Commands.literal("deletefourthpaintingmob")
                .requires(source -> source.hasPermission(2))
                .executes(context -> deleteFourthPaintingMob(context.getSource())));

        dispatcher.register(Commands.literal("deleteseventhpaintingmob")
                .requires(source -> source.hasPermission(2))
                .executes(context -> deleteSeventhPaintingMob(context.getSource())));
        dispatcher.register(Commands.literal("deleteeighthpaintingmob")
                .requires(source -> source.hasPermission(2))
                .executes(context -> deleteEighthPaintingMob(context.getSource())));
        dispatcher.register(Commands.literal("deleteninthpaintingmob")
                .requires(source -> source.hasPermission(2))
                .executes(context -> deleteNinthPaintingMob(context.getSource())));
        dispatcher.register(Commands.literal("deletetenthpaintingmob")
                .requires(source -> source.hasPermission(2))
                .executes(context -> deleteTenthPaintingMob(context.getSource())));
        dispatcher.register(Commands.literal("deletesixthpaintingmob")
                .requires(source -> source.hasPermission(2))
                .executes(context -> deleteSixthPaintingMob(context.getSource())));
        dispatcher.register(Commands.literal("bypasscooldown")
                .requires(source -> source.hasPermission(2))
                .executes(context -> bypassCooldown(context.getSource())));
    }

    

    

    
    public static int deleteSixthFrame(CommandSourceStack source) {
        int resetCount = 0;
        for (ServerLevel serverLevel : source.getServer().getAllLevels()) {
            for (Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof com.shiraken.template_mod.entity.SixthPhotoEntity) {
                    entity.discard();
                    resetCount++;
                }
            }
        }
        final int finalCount = resetCount;
        source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Deleted " + finalCount + " sixth frames across all dimensions."), true);
        return resetCount;
    }

    public static int permaDeleteSixthPainting(CommandSourceStack source) {
        int resetCount = 0;
        for (ServerLevel serverLevel : source.getServer().getAllLevels()) {
            for (Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof com.shiraken.template_mod.entity.SixthPaintingEntity) {
                    entity.discard();
                    resetCount++;
                }
            }
        }
        final int finalCount = resetCount;
        source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Permanently deleted " + finalCount + " sixth painting mobs across all dimensions."), true);
        return resetCount;
    }

    public static int deleteSixthPaintingMob(CommandSourceStack source) {
        int resetCount = 0;
        for (ServerLevel serverLevel : source.getServer().getAllLevels()) {
            for (Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof com.shiraken.template_mod.entity.SixthPaintingEntity) {
                    ((com.shiraken.template_mod.entity.SixthPaintingEntity)entity).triggerReset();
                    resetCount++;
                }
            }
        }
        final int finalCount = resetCount;
        source.sendSuccess(() -> net.minecraft.network.chat.Component.literal("Reset " + finalCount + " sixth painting mobs across all dimensions."), true);
        return resetCount;
    }
    private static int deleteFirstPaintingMob(CommandSourceStack source) {
        int resetCount = 0;
        
        // Loop through all server dimensions
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof FirstPaintingEntity) {
                    ((FirstPaintingEntity) entity).triggerReset();
                    resetCount++;
                }
            }
        }

        final int finalCount = resetCount;
        source.sendSuccess(() -> Component.literal("Reset " + finalCount + " First Painting mobs (1 min cooldown started)."), true);
        return finalCount;
    }

    private static int deleteSecondPaintingMob(CommandSourceStack source) {
        int resetCount = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof com.shiraken.template_mod.entity.SecondPaintingEntity) {
                    ((com.shiraken.template_mod.entity.SecondPaintingEntity) entity).triggerReset();
                    resetCount++;
                }
            }
        }
        final int finalCount = resetCount;
        source.sendSuccess(() -> Component.literal("Reset " + finalCount + " Second Painting mobs (1 min cooldown started)."), true);
        return finalCount;
    }

        private static int deleteThirdPaintingMob(CommandSourceStack source) {
        int resetCount = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof com.shiraken.template_mod.entity.ThirdPaintingEntity) {
                    ((com.shiraken.template_mod.entity.ThirdPaintingEntity) entity).triggerReset();
                    resetCount++;
                }
            }
        }
        final int finalCount = resetCount;
        source.sendSuccess(() -> Component.literal("Reset " + finalCount + " Third Painting mobs (1 min cooldown started)."), true);
        return finalCount;
    }

    private static int deleteFourthPaintingMob(CommandSourceStack source) {
        int resetCount = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof com.shiraken.template_mod.entity.FourthPaintingEntity) {
                    ((com.shiraken.template_mod.entity.FourthPaintingEntity) entity).triggerReset();
                    resetCount++;
                }
            }
        }
        final int finalCount = resetCount;
        source.sendSuccess(() -> Component.literal("Reset " + finalCount + " Fourth Painting mobs (1 min cooldown started)."), true);
        return finalCount;
    }

    private static int deleteSeventhPaintingMob(CommandSourceStack source) {
        int resetCount = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof com.shiraken.template_mod.entity.SeventhPaintingEntity) {
                    ((com.shiraken.template_mod.entity.SeventhPaintingEntity) entity).triggerReset();
                    resetCount++;
                }
            }
        }
        final int finalCount = resetCount;
        source.sendSuccess(() -> Component.literal("Reset " + finalCount + " Seventh Painting mobs (1 min cooldown started)."), true);
        return finalCount;
    }

    private static int deleteEighthPaintingMob(CommandSourceStack source) {
        int resetCount = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof com.shiraken.template_mod.entity.EighthPaintingEntity) {
                    ((com.shiraken.template_mod.entity.EighthPaintingEntity) entity).triggerReset();
                    resetCount++;
                }
            }
        }
        final int finalCount = resetCount;
        source.sendSuccess(() -> Component.literal("Reset " + finalCount + " Eighth Painting mobs (1 min cooldown started)."), true);
        return finalCount;
    }

    private static int deleteNinthPaintingMob(CommandSourceStack source) {
        int resetCount = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof com.shiraken.template_mod.entity.NinthPaintingEntity) {
                    ((com.shiraken.template_mod.entity.NinthPaintingEntity) entity).triggerReset();
                    resetCount++;
                }
            }
        }
        final int finalCount = resetCount;
        source.sendSuccess(() -> Component.literal("Reset " + finalCount + " Ninth Painting mobs (1 min cooldown started)."), true);
        return finalCount;
    }

    private static int deleteTenthPaintingMob(CommandSourceStack source) {
        int resetCount = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof com.shiraken.template_mod.entity.TenthPaintingEntity) {
                    ((com.shiraken.template_mod.entity.TenthPaintingEntity) entity).triggerReset();
                    resetCount++;
                }
            }
        }
        final int finalCount = resetCount;
        source.sendSuccess(() -> Component.literal("Reset " + finalCount + " Tenth Painting mobs (1 min cooldown started)."), true);
        return finalCount;
    }

    private static int bypassCooldown(CommandSourceStack source) {
        int clearedCount = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof FirstPaintingEntity) {
                    ((FirstPaintingEntity) entity).clearCooldown();
                    clearedCount++;
                } else if (entity instanceof com.shiraken.template_mod.entity.SecondPaintingEntity) {
                    ((com.shiraken.template_mod.entity.SecondPaintingEntity) entity).clearCooldown();
                    clearedCount++;
                } else if (entity instanceof com.shiraken.template_mod.entity.ThirdPaintingEntity) {
                    ((com.shiraken.template_mod.entity.ThirdPaintingEntity) entity).clearCooldown();
                    clearedCount++;
                } else if (entity instanceof com.shiraken.template_mod.entity.FourthPaintingEntity) {
                    ((com.shiraken.template_mod.entity.FourthPaintingEntity) entity).clearCooldown();
                    clearedCount++;
                } else if (entity instanceof com.shiraken.template_mod.entity.SeventhPaintingEntity) {
                    ((com.shiraken.template_mod.entity.SeventhPaintingEntity) entity).clearCooldown();
                    clearedCount++;
                } else if (entity instanceof com.shiraken.template_mod.entity.EighthPaintingEntity) {
                    ((com.shiraken.template_mod.entity.EighthPaintingEntity) entity).clearCooldown();
                    clearedCount++;
                } else if (entity instanceof com.shiraken.template_mod.entity.NinthPaintingEntity) {
                    ((com.shiraken.template_mod.entity.NinthPaintingEntity) entity).clearCooldown();
                    clearedCount++;
                } else if (entity instanceof com.shiraken.template_mod.entity.TenthPaintingEntity) {
                    ((com.shiraken.template_mod.entity.TenthPaintingEntity) entity).clearCooldown();
                    clearedCount++;
                } else if (entity instanceof com.shiraken.template_mod.entity.SixthPaintingEntity) {
                    ((com.shiraken.template_mod.entity.SixthPaintingEntity) entity).clearCooldown();
                    clearedCount++;
                }
            }
        }
        final int finalCount = clearedCount;
        source.sendSuccess(() -> Component.literal("Bypassed cooldown for " + finalCount + " painting mobs."), true);
        return finalCount;
    }
}




