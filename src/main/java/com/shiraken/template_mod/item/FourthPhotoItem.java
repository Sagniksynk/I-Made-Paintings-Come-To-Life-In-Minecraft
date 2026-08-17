package com.shiraken.template_mod.item;

import com.shiraken.template_mod.entity.FourthPhotoEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class FourthPhotoItem extends Item {
    public FourthPhotoItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();

        if (direction != Direction.DOWN && direction != Direction.UP && player != null && this.mayPlace(player, direction, itemstack, blockpos1)) {
            Level level = context.getLevel();

            int width = 1;
            int height = 1;

            if (itemstack.hasTag()) {
                CompoundTag tag = itemstack.getTag();
                if (tag.contains("PhotoWidth")) width = tag.getInt("PhotoWidth");
                if (tag.contains("PhotoHeight")) height = tag.getInt("PhotoHeight");
            }

            FourthPhotoEntity customPhotoEntity = new FourthPhotoEntity(level, blockpos1, direction, width, height);

            if (customPhotoEntity.survives()) {
                if (!level.isClientSide) {
                    customPhotoEntity.playPlacementSound();
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, customPhotoEntity.position());
                    level.addFreshEntity(customPhotoEntity);
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.CONSUME;
            }
        } else {
            return InteractionResult.FAIL;
        }
    }

    protected boolean mayPlace(Player player, Direction direction, ItemStack stack, BlockPos pos) {
        return !direction.getAxis().isVertical() && player.mayUseItemAt(pos, direction, stack);
    }
}
