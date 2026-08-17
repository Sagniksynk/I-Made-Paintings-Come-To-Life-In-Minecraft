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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraft.network.FriendlyByteBuf;

public class EighthPhotoEntity extends HangingEntity implements IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(EighthPhotoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ACTIVATION_TICK = SynchedEntityData.defineId(EighthPhotoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WIDTH = SynchedEntityData.defineId(EighthPhotoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HEIGHT = SynchedEntityData.defineId(EighthPhotoEntity.class, EntityDataSerializers.INT);

    public EighthPhotoEntity(EntityType<? extends EighthPhotoEntity> type, Level level) {
        super(type, level);
    }

    public EighthPhotoEntity(Level level, BlockPos pos, Direction direction, int width, int height) {
        super(TemplateMod.EIGHTH_PHOTO.get(), level, pos);
        this.entityData.set(WIDTH, width);
        this.entityData.set(HEIGHT, height);
        this.setDirection(direction);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ACTIVATED, false);
        this.entityData.define(ACTIVATION_TICK, 0);
        this.entityData.define(WIDTH, 2);
        this.entityData.define(HEIGHT, 2);
    }

    public boolean isActivated() {
        return this.entityData.get(IS_ACTIVATED);
    }

    public void setActivated(boolean activated) {
        this.entityData.set(IS_ACTIVATED, activated);
        if (activated) {
            this.entityData.set(ACTIVATION_TICK, this.tickCount);
        }
    }

    public int getActivationTick() {
        return this.entityData.get(ACTIVATION_TICK);
    }

    public int getPhotoWidth() {
        return this.entityData.get(WIDTH);
    }

    public int getPhotoHeight() {
        return this.entityData.get(HEIGHT);
    }

    @Override
    public int getWidth() {
        return this.getPhotoWidth() * 16;
    }

    @Override
    public int getHeight() {
        return this.getPhotoHeight() * 16;
    }

    @Override
    public void dropItem(@Nullable Entity entity) {
        this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
        // Do not drop item for custom paintings to act like map decoration
    }

    @Override
    public void playPlacementSound() {
        this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (WIDTH.equals(key) || HEIGHT.equals(key)) {
            this.recalculateBoundingBox();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("PhotoWidth", this.getPhotoWidth());
        tag.putInt("PhotoHeight", this.getPhotoHeight());
        tag.putByte("Facing", (byte)this.direction.get3DDataValue());
        tag.putBoolean("IsActivated", this.isActivated());
        tag.putInt("ActivationTick", this.getActivationTick());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(WIDTH, tag.getInt("PhotoWidth"));
        this.entityData.set(HEIGHT, tag.getInt("PhotoHeight"));
        this.direction = Direction.from3DDataValue(tag.getByte("Facing"));
        this.setDirection(this.direction);
        this.entityData.set(IS_ACTIVATED, tag.getBoolean("IsActivated"));
        this.entityData.set(ACTIVATION_TICK, tag.getInt("ActivationTick"));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean survives() {
        return true;
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
