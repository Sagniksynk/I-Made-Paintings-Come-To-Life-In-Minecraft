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
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
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

public class SeventhPaintingEntity extends PathfinderMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(SeventhPaintingEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ACTIVATION_TICK = SynchedEntityData.defineId(SeventhPaintingEntity.class, EntityDataSerializers.INT);
    
    private static final EntityDataAccessor<Float> TARGET_X = SynchedEntityData.defineId(SeventhPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_Y = SynchedEntityData.defineId(SeventhPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_Z = SynchedEntityData.defineId(SeventhPaintingEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> SPAWN_X = SynchedEntityData.defineId(SeventhPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPAWN_Y = SynchedEntityData.defineId(SeventhPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPAWN_Z = SynchedEntityData.defineId(SeventhPaintingEntity.class, EntityDataSerializers.FLOAT);

    // Break animation is 5.5s = 110 ticks
    private static final int ANIM_TICKS = 110;
    private static final int TOTAL_LOCKED_TICKS = ANIM_TICKS;

    private boolean isLocked = false; 
    private boolean hasSetSpawn = false;
    
    public int resetCooldown = 0;

    public SeventhPaintingEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        this.setYRot(90.0F); // West
        this.setYBodyRot(90.0F);
        this.setYHeadRot(90.0F);
        this.setXRot(0.0F);
        this.yRotO = 90.0F;
        this.yBodyRotO = 90.0F;
        this.yHeadRotO = 90.0F;
        
        if (!this.level().isClientSide) {
            this.entityData.set(SPAWN_X, (float)this.getX());
            this.entityData.set(SPAWN_Y, (float)this.getY());
            this.entityData.set(SPAWN_Z, (float)this.getZ());
            this.hasSetSpawn = true;
        }

        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ACTIVATED, false);
        this.entityData.define(ACTIVATION_TICK, 0);
        this.entityData.define(TARGET_X, 0.0f);
        this.entityData.define(TARGET_Y, 0.0f);
        this.entityData.define(TARGET_Z, 0.0f);
        this.entityData.define(SPAWN_X, 0.0f);
        this.entityData.define(SPAWN_Y, 0.0f);
        this.entityData.define(SPAWN_Z, 0.0f);
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
            this.moveTo(this.entityData.get(SPAWN_X), this.entityData.get(SPAWN_Y), this.entityData.get(SPAWN_Z), 90.0F, 0.0F);
            this.setYBodyRot(90.0F);
            this.setYHeadRot(90.0F);
            this.setDeltaMovement(0, 0, 0);
            
            // Reset frame
            java.util.List<SeventhPhotoEntity> frames = this.level().getEntitiesOfClass(SeventhPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
            for (SeventhPhotoEntity frame : frames) {
                frame.setActivated(false);
            }
        }
    }

    public void setActivated(boolean activated) {
        if (activated && this.isActivated()) return; // Prevent re-activation
        this.entityData.set(IS_ACTIVATED, activated);
        if (activated) {
            this.entityData.set(ACTIVATION_TICK, this.tickCount);
            
            if (!this.level().isClientSide) {
                if (!this.hasSetSpawn) {
                    this.entityData.set(SPAWN_X, (float)this.getX());
                    this.entityData.set(SPAWN_Y, (float)this.getY());
                    this.entityData.set(SPAWN_Z, (float)this.getZ());
                    this.hasSetSpawn = true;
                }

                java.util.List<SeventhPhotoEntity> frames = this.level().getEntitiesOfClass(SeventhPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
                SeventhPhotoEntity closestFrame = null;
                double minDist = Double.MAX_VALUE;
                for (SeventhPhotoEntity frame : frames) {
                    double dist = this.distanceToSqr(frame);
                    if (dist < minDist) {
                        minDist = dist;
                        closestFrame = frame;
                    }
                }
                if (closestFrame != null) {
                    net.minecraft.core.Direction dir = closestFrame.getDirection();
                    // Match the JSON animation offset perfectly at 5.5s: 2.125 blocks forward (-34 units) and 0.5 blocks up (8 units)
                    this.entityData.set(TARGET_X, (float)(closestFrame.getX() + dir.getStepX() * 2.125D));
                    this.entityData.set(TARGET_Y, (float)(this.getY() + 0.5D));
                    this.entityData.set(TARGET_Z, (float)(closestFrame.getZ() + dir.getStepZ() * 2.125D));
                } else {
                    this.entityData.set(TARGET_X, (float)this.getX());
                    this.entityData.set(TARGET_Y, (float)this.getY());
                    this.entityData.set(TARGET_Z, (float)this.getZ());
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
        compound.putFloat("SpawnX", this.entityData.get(SPAWN_X));
        compound.putFloat("SpawnY", this.entityData.get(SPAWN_Y));
        compound.putFloat("SpawnZ", this.entityData.get(SPAWN_Z));
        compound.putBoolean("HasSetSpawn", this.hasSetSpawn);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(IS_ACTIVATED, compound.getBoolean("IsActivated"));
        this.entityData.set(ACTIVATION_TICK, compound.getInt("ActivationTick"));
        this.resetCooldown = compound.getInt("ResetCooldown");
        if (compound.contains("SpawnX")) {
            this.entityData.set(SPAWN_X, compound.getFloat("SpawnX"));
            this.entityData.set(SPAWN_Y, compound.getFloat("SpawnY"));
            this.entityData.set(SPAWN_Z, compound.getFloat("SpawnZ"));
            this.hasSetSpawn = compound.getBoolean("HasSetSpawn");
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isLocked) return;
        super.travel(travelVector);
    }

    @Override
    public void move(MoverType type, Vec3 pos) {
        if (this.isLocked) return;
        super.move(type, pos);
    }

    @Override
    public void push(double x, double y, double z) {
        if (this.isLocked) return;
        super.push(x, y, z);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FloatingChaseGoal());
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F) {
            @Override
            public boolean canUse() {
                return SeventhPaintingEntity.this.isActivated() && !SeventhPaintingEntity.this.isLocked && super.canUse();
            }
        });
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && this.resetCooldown > 0) {
            this.resetCooldown--;
        }

        if (this.isActivated()) {
            int ticksSinceActivation = this.tickCount - this.getActivationTick();
            
            double tX = this.entityData.get(TARGET_X);
            double tY = this.entityData.get(TARGET_Y);
            double tZ = this.entityData.get(TARGET_Z);

            if (ticksSinceActivation < TOTAL_LOCKED_TICKS) {
                // LOCKED
                this.isLocked = true;
                this.noPhysics = true;
                this.setNoGravity(true);
                this.setDeltaMovement(0, 0, 0);

                super.tick();
                this.setDeltaMovement(0, 0, 0);

            } else {
                // UNLOCKED: floating mode
                this.isLocked = false;
                this.noPhysics = false;
                this.setNoGravity(true); // Always float

                if (ticksSinceActivation == TOTAL_LOCKED_TICKS) {
                    this.setPos(tX, tY, tZ);
                }

                super.tick();
            }
        } else {
            // NOT ACTIVATED: Stay still at spawn
            this.isLocked = false;
            this.noPhysics = false;
            this.setNoGravity(true);
            
            if (this.hasSetSpawn && !this.level().isClientSide) {
                this.moveTo(this.entityData.get(SPAWN_X), this.entityData.get(SPAWN_Y), this.entityData.get(SPAWN_Z), 90.0F, 0.0F);
                this.setYBodyRot(90.0F);
                this.setYHeadRot(90.0F);
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

    private PlayState predicate(AnimationState<SeventhPaintingEntity> event) {
        if (!this.isActivated()) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }

        int ticksSinceActivation = this.tickCount - this.getActivationTick();

        if (ticksSinceActivation < TOTAL_LOCKED_TICKS) {
            event.getController().setAnimationSpeed(1.0D);
            event.getController().setAnimation(RawAnimation.begin().thenPlay("break painting"));
            return PlayState.CONTINUE;
        }

        // Hold the last frame of the break animation while chasing
        event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("break painting"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    class FloatingChaseGoal extends Goal {
        private Player targetPlayer;

        public FloatingChaseGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (!SeventhPaintingEntity.this.isActivated()) return false;
            if (SeventhPaintingEntity.this.isLocked) return false;

            this.targetPlayer = SeventhPaintingEntity.this.level().getNearestPlayer(SeventhPaintingEntity.this, 64.0D);
            return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.isSpectator();
        }

        @Override
        public boolean canContinueToUse() {
            if (!SeventhPaintingEntity.this.isActivated() || SeventhPaintingEntity.this.isLocked) return false;
            return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.isSpectator();
        }

        @Override
        public void tick() {
            if (this.targetPlayer != null) {
                SeventhPaintingEntity.this.getLookControl().setLookAt(this.targetPlayer, 30.0F, 30.0F);

                double dx = this.targetPlayer.getX() - SeventhPaintingEntity.this.getX();
                double dy = (this.targetPlayer.getY() + this.targetPlayer.getEyeHeight() / 2.0) - SeventhPaintingEntity.this.getY();
                double dz = this.targetPlayer.getZ() - SeventhPaintingEntity.this.getZ();
                double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
                
                if (dist > 1.5) { // don't push completely inside the player
                    Vec3 dir = new Vec3(dx, dy, dz).normalize();
                    SeventhPaintingEntity.this.setDeltaMovement(dir.x * 0.15, dir.y * 0.15, dir.z * 0.15);
                    
                    // Face the player body-wise too
                    float targetYRot = (float) (Math.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
                    SeventhPaintingEntity.this.setYRot(targetYRot);
                    SeventhPaintingEntity.this.setYBodyRot(targetYRot);
                    SeventhPaintingEntity.this.setYHeadRot(targetYRot);
                } else {
                    SeventhPaintingEntity.this.setDeltaMovement(0, 0, 0);
                }
            }
        }
    }
}
