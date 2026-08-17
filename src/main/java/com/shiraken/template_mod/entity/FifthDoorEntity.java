package com.shiraken.template_mod.entity;

import com.shiraken.template_mod.TemplateMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FifthDoorEntity extends PathfinderMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Boolean> IS_OPEN = SynchedEntityData.defineId(FifthDoorEntity.class, EntityDataSerializers.BOOLEAN);

    // Client-side tracking to ensure animations transition properly when state changes
    private boolean clientPrevOpen = false;
    private boolean hasInitialized = false;

    public FifthDoorEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_OPEN, false);
    }

    public boolean isOpen() {
        return this.entityData.get(IS_OPEN);
    }

    public void setOpen(boolean open) {
        this.entityData.set(IS_OPEN, open);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsOpen", this.isOpen());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("IsOpen")) {
            this.setOpen(compound.getBoolean("IsOpen"));
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(net.minecraft.world.entity.Entity entity) {
        // Do not push other entities
    }

    @Override
    public void push(double x, double y, double z) {
        // Do not get pushed
    }

    @Override
    public void travel(Vec3 travelVector) {
        // No movement
    }

    @Override
    public void move(MoverType type, Vec3 pos) {
        // No movement
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(0, 0, 0);
        this.setYRot(-90.0f); // East
        this.setYBodyRot(-90.0f);
        this.setYHeadRot(-90.0f);
        this.setXRot(0.0f);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!this.level().isClientSide && source.getEntity() instanceof Player player) {
            if (player.isCrouching()) {
                this.spawnAtLocation(TemplateMod.FIFTH_DOOR_ITEM.get());
                this.discard();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<FifthDoorEntity> event) {
        boolean currentOpen = this.isOpen();

        if (!this.hasInitialized) {
            this.clientPrevOpen = currentOpen;
            this.hasInitialized = true;
            
            if (currentOpen) {
                event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("open"));
                return PlayState.CONTINUE;
            } else {
                // By default the model is natively closed (0,0,0), so we just stop if it spawns closed
                return PlayState.STOP;
            }
        }

        // Only trigger an animation when the state actually changes
        if (currentOpen != this.clientPrevOpen) {
            this.clientPrevOpen = currentOpen;
            if (currentOpen) {
                event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("open"));
            } else {
                event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("close"));
            }
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
