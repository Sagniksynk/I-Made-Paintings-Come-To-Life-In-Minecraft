package com.shiraken.template_mod.entity;

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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.Vec3;
import javax.annotation.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

public class SecondPaintingEntity extends PathfinderMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(SecondPaintingEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ACTIVATION_TICK = SynchedEntityData.defineId(SecondPaintingEntity.class, EntityDataSerializers.INT);

    // Sync start and target coordinates so client and server lerp perfectly
    public static final EntityDataAccessor<Float> START_X = SynchedEntityData.defineId(SecondPaintingEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> START_Y = SynchedEntityData.defineId(SecondPaintingEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> START_Z = SynchedEntityData.defineId(SecondPaintingEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> TARGET_X = SynchedEntityData.defineId(SecondPaintingEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> TARGET_Y = SynchedEntityData.defineId(SecondPaintingEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> TARGET_Z = SynchedEntityData.defineId(SecondPaintingEntity.class, EntityDataSerializers.FLOAT);

    private static final int ANIM_TICKS = 100;
    private static final int BUFFER_TICKS = 0;
    private static final int TOTAL_LOCKED_TICKS = ANIM_TICKS + BUFFER_TICKS;

    private boolean isLocked = false;
    private int resetCooldown = 0;
    private boolean hasSetSpawn = false;
    
    public int clientTicksSinceActivation = 0;

    public SecondPaintingEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        this.setYRot(270.0F);
        this.setYBodyRot(270.0F);
        this.setYHeadRot(270.0F);
        this.setXRot(0.0F);
        this.yRotO = 270.0F;
        this.yBodyRotO = 270.0F;
        this.yHeadRotO = 270.0F;
        
        if (!this.level().isClientSide) {
            this.entityData.set(START_X, (float)this.getX());
            this.entityData.set(START_Y, (float)this.getY());
            this.entityData.set(START_Z, (float)this.getZ());
            this.hasSetSpawn = true;
        }

        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ACTIVATED, false);
        this.entityData.define(ACTIVATION_TICK, 0);
        this.entityData.define(START_X, 0.0f);
        this.entityData.define(START_Y, 0.0f);
        this.entityData.define(START_Z, 0.0f);
        this.entityData.define(TARGET_X, 0.0f);
        this.entityData.define(TARGET_Y, 0.0f);
        this.entityData.define(TARGET_Z, 0.0f);
    }

    public boolean isActivated() {
        return this.entityData.get(IS_ACTIVATED);
    }

    public boolean isOnCooldown() {
        return this.resetCooldown > 0;
    }

    public void clearCooldown() {
        this.resetCooldown = 0;
    }

    public void triggerReset() {
        if (!this.level().isClientSide) {
            this.setActivated(false);
            this.resetCooldown = 1200; // 1 minute cooldown
            
            // Teleport back to spawn
            this.moveTo(this.entityData.get(START_X), this.entityData.get(START_Y), this.entityData.get(START_Z), 270.0F, 0.0F);
            this.setYBodyRot(270.0F);
            this.setYHeadRot(270.0F);
            this.setDeltaMovement(0, 0, 0);
            
            // Reset frame
            java.util.List<SecondPhotoEntity> frames = this.level().getEntitiesOfClass(SecondPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
            for (SecondPhotoEntity frame : frames) {
                frame.setActivated(false);
            }
        }
    }

    public void setActivated(boolean activated) {
        this.entityData.set(IS_ACTIVATED, activated);
        if (activated) {
            this.entityData.set(ACTIVATION_TICK, this.tickCount);
            
            if (!this.level().isClientSide) {
                if (!this.hasSetSpawn) {
                    this.entityData.set(START_X, (float)this.getX());
                    this.entityData.set(START_Y, (float)this.getY());
                    this.entityData.set(START_Z, (float)this.getZ());
                    this.hasSetSpawn = true;
                }
                
                java.util.List<SecondPhotoEntity> frames = this.level().getEntitiesOfClass(SecondPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
                SecondPhotoEntity closestFrame = null;
                double minDist = Double.MAX_VALUE;
                for (SecondPhotoEntity frame : frames) {
                    double dist = this.distanceToSqr(frame);
                    if (dist < minDist) {
                        minDist = dist;
                        closestFrame = frame;
                    }
                }
                if (closestFrame != null) {
                    net.minecraft.core.Direction dir = closestFrame.getDirection();
                    // Match Blockbench distance of 8.0625 blocks exactly, relative to START_X/Z.
                    double targetX = this.entityData.get(START_X) + dir.getStepX() * 8.0625D;
                    double targetZ = this.entityData.get(START_Z) + dir.getStepZ() * 8.0625D;
                    double targetY = this.entityData.get(START_Y);

                    // Raycast down to find the floor, so it lands perfectly on the ground!
                    net.minecraft.core.BlockPos.MutableBlockPos pos = new net.minecraft.core.BlockPos.MutableBlockPos(targetX, targetY, targetZ);
                    while (pos.getY() > this.level().getMinBuildHeight() && this.level().getBlockState(pos).isAir()) {
                        pos.move(net.minecraft.core.Direction.DOWN);
                    }
                    targetY = pos.getY() + 1.0D;

                    this.entityData.set(TARGET_X, (float)targetX);
                    this.entityData.set(TARGET_Y, (float)targetY);
                    this.entityData.set(TARGET_Z, (float)targetZ);
                } else {
                    this.entityData.set(TARGET_X, (float)this.entityData.get(START_X));
                    this.entityData.set(TARGET_Y, (float)this.entityData.get(START_Y));
                    this.entityData.set(TARGET_Z, (float)this.entityData.get(START_Z));
                }
            }
        }
    }

    public int getActivationTick() {
        return this.entityData.get(ACTIVATION_TICK);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsActivated", this.isActivated());
        compound.putInt("ActivationTick", this.getActivationTick());
        compound.putInt("ResetCooldown", this.resetCooldown);
        compound.putFloat("StartX", this.entityData.get(START_X));
        compound.putFloat("StartY", this.entityData.get(START_Y));
        compound.putFloat("StartZ", this.entityData.get(START_Z));
        compound.putBoolean("HasSetSpawn", this.hasSetSpawn);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(IS_ACTIVATED, compound.getBoolean("IsActivated"));
        this.entityData.set(ACTIVATION_TICK, compound.getInt("ActivationTick"));
        this.resetCooldown = compound.getInt("ResetCooldown");
        if (compound.contains("StartX")) {
            this.entityData.set(START_X, compound.getFloat("StartX"));
            this.entityData.set(START_Y, compound.getFloat("StartY"));
            this.entityData.set(START_Z, compound.getFloat("StartZ"));
            this.hasSetSpawn = compound.getBoolean("HasSetSpawn");
        }
    }

    // ========== BLOCK ALL MOVEMENT DURING LOCKED PHASE ==========

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isLocked) {
            return;
        }
        super.travel(travelVector);
    }

    @Override
    public void move(MoverType type, Vec3 pos) {
        if (this.isLocked) {
            return;
        }
        super.move(type, pos);
    }

    @Override
    public void push(double x, double y, double z) {
        if (this.isLocked) {
            return;
        }
        super.push(x, y, z);
    }

    // ================================================================

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new FollowPlayerGoal());
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return SecondPaintingEntity.this.isActivated() && !SecondPaintingEntity.this.isLocked && super.canUse();
            }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F) {
            @Override
            public boolean canUse() {
                return SecondPaintingEntity.this.isActivated() && !SecondPaintingEntity.this.isLocked && super.canUse();
            }
        });
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return SecondPaintingEntity.this.isActivated() && !SecondPaintingEntity.this.isLocked && super.canUse();
            }
        });
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && this.resetCooldown > 0) {
            this.resetCooldown--;
        }

        if (this.isActivated()) {
            if (this.level().isClientSide) {
                this.clientTicksSinceActivation++;
            }
            int ticksSinceActivation = this.tickCount - this.getActivationTick();
            
            double sX = this.entityData.get(START_X);
            double sY = this.entityData.get(START_Y);
            double sZ = this.entityData.get(START_Z);
            double tX = this.entityData.get(TARGET_X);
            double tY = this.entityData.get(TARGET_Y);
            double tZ = this.entityData.get(TARGET_Z);

            if (ticksSinceActivation < TOTAL_LOCKED_TICKS) {
                // LOCK movement
                this.isLocked = true;
                this.noPhysics = true;
                this.setNoGravity(true);
                this.setDeltaMovement(0, 0, 0);

                // No lerp! The visual movement is handled 100% by Blockbench!
                // We just sit inside the painting until the animation is finished.

                // Let super.tick() run — travel/move/push are blocked so position won't change
                super.tick();

                // Zero out any delta movement
                this.setDeltaMovement(0, 0, 0);

            } else {
                // UNLOCK
                this.isLocked = false;
                this.noPhysics = false;
                this.setNoGravity(false);

                if (ticksSinceActivation == TOTAL_LOCKED_TICKS) {
                    this.setPos(tX, tY, tZ);
                }

                super.tick();
            }
        } else {
            this.clientTicksSinceActivation = 0;
            this.isLocked = false;
            this.noPhysics = false;
            this.setNoGravity(false);

            if (this.hasSetSpawn && !this.level().isClientSide) {
                this.moveTo(this.entityData.get(START_X), this.entityData.get(START_Y), this.entityData.get(START_Z), 270.0F, 0.0F);
                this.setYBodyRot(270.0F);
                this.setYHeadRot(270.0F);
                this.setDeltaMovement(0, 0, 0);
            }

            super.tick();
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
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

    private PlayState predicate(AnimationState<SecondPaintingEntity> event) {
        if (!this.isActivated()) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }

        int ticksSinceActivation = this.level().isClientSide ? this.clientTicksSinceActivation : (this.tickCount - this.getActivationTick());

        if (ticksSinceActivation < TOTAL_LOCKED_TICKS) {
            event.getController().setAnimationSpeed(1.0D);
            event.getController().setAnimation(RawAnimation.begin().thenPlay("break painting"));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimationSpeed(1.0D);
            event.getController().setAnimation(RawAnimation.begin().thenLoop("walk"));
            return PlayState.CONTINUE;
        } else {
            // Idle state to prevent holding the ghost frame of break painting
            event.getController().setAnimationSpeed(0.0D);
            event.getController().setAnimation(RawAnimation.begin().thenLoop("walk"));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    class FollowPlayerGoal extends Goal {
        private int delayCounter = 0;
        private Player targetPlayer;

        public FollowPlayerGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (!SecondPaintingEntity.this.isActivated()) return false;
            if (SecondPaintingEntity.this.isLocked) return false;

            this.targetPlayer = SecondPaintingEntity.this.level().getNearestPlayer(SecondPaintingEntity.this, 64.0D);
            return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.isSpectator();
        }

        @Override
        public boolean canContinueToUse() {
            return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.isSpectator();
        }

        @Override
        public void start() {
            this.delayCounter = 0;
        }

        @Override
        public void tick() {
            if (this.targetPlayer != null) {
                SecondPaintingEntity.this.getLookControl().setLookAt(this.targetPlayer, 30.0F, 30.0F);

                if (--this.delayCounter <= 0) {
                    this.delayCounter = 10;
                    SecondPaintingEntity.this.getNavigation().moveTo(this.targetPlayer, 0.8D);
                }
            }
        }
    }
}
