package com.shiraken.template_mod.entity;

import com.shiraken.template_mod.TemplateMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraft.network.FriendlyByteBuf;

public class FourthPhotoEntity extends HangingEntity implements IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(FourthPhotoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ACTIVATION_TICK = SynchedEntityData.defineId(FourthPhotoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WIDTH = SynchedEntityData.defineId(FourthPhotoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HEIGHT = SynchedEntityData.defineId(FourthPhotoEntity.class, EntityDataSerializers.INT);


    public FourthPhotoEntity(EntityType<? extends FourthPhotoEntity> type, Level level) {
        super(type, level);
    }

    public FourthPhotoEntity(Level level, BlockPos pos, Direction direction, int width, int height) {
        super(TemplateMod.FOURTH_PHOTO.get(), level, pos);
        this.entityData.set(WIDTH, width);
        this.entityData.set(HEIGHT, height);
        this.setDirection(direction);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(IS_ACTIVATED, false);
        this.entityData.define(ACTIVATION_TICK, 0);
        this.entityData.define(WIDTH, 1);
        this.entityData.define(HEIGHT, 1);
    }

    public boolean isActivated() {
        return this.entityData.get(IS_ACTIVATED);
    }

    public void setActivated(boolean activated) {
        if (activated && !this.isActivated()) {
            this.entityData.set(ACTIVATION_TICK, this.tickCount);
        }
        this.entityData.set(IS_ACTIVATED, activated);
    }

    public int getActivationTick() {
        return this.entityData.get(ACTIVATION_TICK);
    }

    @Override
    public int getWidth() {
        return this.getPhotoWidth() * 16;
    }

    @Override
    public int getHeight() {
        return this.getPhotoHeight() * 16;
    }

    public int getPhotoWidth() {
        return this.entityData.get(WIDTH);
    }

    public int getPhotoHeight() {
        return this.entityData.get(HEIGHT);
    }

    @Override
    public boolean survives() {
        return true;
    }

    @Override
    public void dropItem(@Nullable net.minecraft.world.entity.Entity entity) {
        if (this.level().getGameRules().getBoolean(net.minecraft.world.level.GameRules.RULE_DOENTITYDROPS)) {
            this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
            this.spawnAtLocation(TemplateMod.FOURTH_PHOTO_ITEM.get());
        }
    }

    @Override
    public void playPlacementSound() {
        this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte("Facing", (byte)this.direction.get3DDataValue());
        tag.putInt("PhotoWidth", this.getPhotoWidth());
        tag.putInt("PhotoHeight", this.getPhotoHeight());
        tag.putBoolean("IsActivated", this.isActivated());
        tag.putInt("ActivationTick", this.getActivationTick());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.direction = Direction.from3DDataValue(tag.getByte("Facing"));
        if (tag.contains("PhotoWidth")) this.entityData.set(WIDTH, tag.getInt("PhotoWidth"));
        if (tag.contains("PhotoHeight")) this.entityData.set(HEIGHT, tag.getInt("PhotoHeight"));
        if (tag.contains("IsActivated")) this.setActivated(tag.getBoolean("IsActivated"));
        if (tag.contains("ActivationTick")) this.entityData.set(ACTIVATION_TICK, tag.getInt("ActivationTick"));
        this.setDirection(this.direction);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(this.getPhotoWidth());
        buffer.writeInt(this.getPhotoHeight());
        buffer.writeEnum(this.direction != null ? this.direction : Direction.SOUTH);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        int w = additionalData.readInt();
        int h = additionalData.readInt();
        this.entityData.set(WIDTH, w);
        this.entityData.set(HEIGHT, h);
        this.direction = additionalData.readEnum(Direction.class);
        this.setDirection(this.direction);
    }
}
