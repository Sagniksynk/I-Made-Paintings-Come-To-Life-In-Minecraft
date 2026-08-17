package com.shiraken.template_mod.item;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FifthDoorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class FifthDoorItem extends Item {
    public FifthDoorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);

        FifthDoorEntity entity = TemplateMod.FIFTH_DOOR.get().create(level);
        if (entity != null) {
            entity.setPos(blockpos1.getX() + 0.5D, blockpos1.getY(), blockpos1.getZ() + 0.5D);
            level.addFreshEntity(entity);
            level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);

            ItemStack itemstack = context.getItemInHand();
            itemstack.shrink(1);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }
}
