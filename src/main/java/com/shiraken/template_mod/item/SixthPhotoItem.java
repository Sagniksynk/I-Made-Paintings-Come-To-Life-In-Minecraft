package com.shiraken.template_mod.item;

import com.shiraken.template_mod.entity.SixthPhotoEntity;
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

public class SixthPhotoItem extends Item {
    public SixthPhotoItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();

        if (player != null && !this.mayPlace(player, direction, itemstack, blockpos1)) {
            return InteractionResult.FAIL;
        } else {
            Level level = context.getLevel();
            
            int width = 2;
            int height = 2;
            CompoundTag tag = itemstack.getTag();
            if (tag != null) {
                if (tag.contains("PhotoWidth")) width = tag.getInt("PhotoWidth");
                if (tag.contains("PhotoHeight")) height = tag.getInt("PhotoHeight");
            }
            
            SixthPhotoEntity frame = new SixthPhotoEntity(level, blockpos1, direction, width, height);

            if (frame.survives()) {
                if (!level.isClientSide) {
                    frame.playPlacementSound();
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, frame.position());
                    level.addFreshEntity(frame);
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

    protected boolean mayPlace(Player player, Direction direction, ItemStack itemStack, BlockPos pos) {
        return !direction.getAxis().isVertical() && player.mayUseItemAt(pos, direction, itemStack);
    }
}
