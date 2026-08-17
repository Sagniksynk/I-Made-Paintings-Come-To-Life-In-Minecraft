package com.shiraken.template_mod.item;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.SeventhPhotoEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class SeventhPhotoItem extends Item {
    public SeventhPhotoItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();

        if (direction.getAxis().isHorizontal() && player != null && player.mayUseItemAt(blockpos1, direction, itemstack)) {
            Level level = context.getLevel();
            // The texture is 66x99, which is approx 4x6 blocks, but let's make it slightly smaller or exact.
            // Actually the original photo entity might just use 3x4 blocks for 16 pixel units. Let's make it 3 wide, 4 high or similar.
            
            // Default sizes if no NBT exists
            int width = 3;
            int height = 4;

            if (itemstack.hasTag()) {
                net.minecraft.nbt.CompoundTag tag = itemstack.getTag();
                if (tag.contains("PhotoWidth")) width = tag.getInt("PhotoWidth");
                if (tag.contains("PhotoHeight")) height = tag.getInt("PhotoHeight");
            }

            SeventhPhotoEntity customphotoentity = new SeventhPhotoEntity(level, blockpos1, direction, width, height);

            if (customphotoentity.survives()) {
                if (!level.isClientSide) {
                    customphotoentity.playPlacementSound();
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, customphotoentity.position());
                    level.addFreshEntity(customphotoentity);
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.CONSUME;
            }
        } else {
            return InteractionResult.PASS;
        }
    }
}
