package com.shiraken.template_mod.item;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FifthRoomEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class FifthRoomItem extends Item {
    public FifthRoomItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();

        if (player != null && player.mayUseItemAt(blockpos1, direction, itemstack)) {
            Level level = context.getLevel();

            FifthRoomEntity customEntity = new FifthRoomEntity(TemplateMod.FIFTH_ROOM.get(), level);
            customEntity.setPos(blockpos1.getX() + 0.5D, blockpos1.getY(), blockpos1.getZ() + 0.5D);

            if (!level.isClientSide) {
                level.gameEvent(player, GameEvent.ENTITY_PLACE, customEntity.position());
                level.addFreshEntity(customEntity);
            }

            itemstack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.FAIL;
        }
    }
}
