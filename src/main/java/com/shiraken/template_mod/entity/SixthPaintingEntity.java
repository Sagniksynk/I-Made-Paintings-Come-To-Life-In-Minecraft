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
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import javax.annotation.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SixthPaintingEntity extends PathfinderMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(SixthPaintingEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ACTIVATED_ALT = SynchedEntityData.defineId(SixthPaintingEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ACTIVATION_TICK = SynchedEntityData.defineId(SixthPaintingEntity.class, EntityDataSerializers.INT);
    
    private static final EntityDataAccessor<Float> TARGET_X = SynchedEntityData.defineId(SixthPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_Y = SynchedEntityData.defineId(SixthPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_Z = SynchedEntityData.defineId(SixthPaintingEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> SPAWN_X = SynchedEntityData.defineId(SixthPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPAWN_Y = SynchedEntityData.defineId(SixthPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPAWN_Z = SynchedEntityData.defineId(SixthPaintingEntity.class, EntityDataSerializers.FLOAT);

    private static final int ANIM_TICKS_NORMAL = 200; // 10s
    private static final int ANIM_TICKS_ALT = 180; // 9s
    private static final int FRAME_SWAP_TICK = 7; // 0.33s

    private boolean isLocked = false; 
    private boolean hasSetSpawn = false;
    
    public int resetCooldown = 0;

    public SixthPaintingEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        this.setYRot(90.0F);
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
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ACTIVATED, false);
        this.entityData.define(IS_ACTIVATED_ALT, false);
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
    
    public boolean isActivatedAlt() {
        return this.entityData.get(IS_ACTIVATED_ALT);
    }

    public boolean isOnCooldown() {
        return this.resetCooldown > 0;
    }

    public void clearCooldown() {
        this.resetCooldown = 0;
    }

    public void triggerReset() {
        if (!this.level().isClientSide) {
            this.setActivatedState(false, false);
            this.resetCooldown = 1200;
            
            this.moveTo(this.entityData.get(SPAWN_X), this.entityData.get(SPAWN_Y), this.entityData.get(SPAWN_Z), 90.0F, 0.0F);
            this.setYBodyRot(90.0F);
            this.setYHeadRot(90.0F);
            this.setDeltaMovement(0, 0, 0);
            
            java.util.List<SixthPhotoEntity> frames = this.level().getEntitiesOfClass(SixthPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
            for (SixthPhotoEntity frame : frames) {
                frame.setActivated(false);
            }
        }
    }

    public void setActivatedState(boolean normal, boolean alt) {
        if ((normal && this.isActivated()) || (alt && this.isActivatedAlt())) return;
        this.entityData.set(IS_ACTIVATED, normal);
        this.entityData.set(IS_ACTIVATED_ALT, alt);
        
        if (normal || alt) {
            this.entityData.set(ACTIVATION_TICK, this.tickCount);
            
            if (!this.level().isClientSide) {
                if (!this.hasSetSpawn) {
                    this.entityData.set(SPAWN_X, (float)this.getX());
                    this.entityData.set(SPAWN_Y, (float)this.getY());
                    this.entityData.set(SPAWN_Z, (float)this.getZ());
                    this.hasSetSpawn = true;
                }

                java.util.List<SixthPhotoEntity> frames = this.level().getEntitiesOfClass(SixthPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
                SixthPhotoEntity closestFrame = null;
                double minDist = Double.MAX_VALUE;
                for (SixthPhotoEntity frame : frames) {
                    double dist = this.distanceToSqr(frame);
                    if (dist < minDist) {
                        minDist = dist;
                        closestFrame = frame;
                    }
                }
                if (closestFrame != null) {
                    net.minecraft.core.Direction dir = closestFrame.getDirection();
                    double targetX = closestFrame.getX() + dir.getStepX() * 1.9375D;
                    double targetZ = closestFrame.getZ() + dir.getStepZ() * 1.9375D;
                    this.entityData.set(TARGET_X, (float)targetX);
                    this.entityData.set(TARGET_Z, (float)targetZ);
                    
                    // Raycast down to find ground level
                    net.minecraft.core.BlockPos pos = net.minecraft.core.BlockPos.containing(targetX, this.getY(), targetZ);
                    while (pos.getY() > this.level().getMinBuildHeight() && 
                           (this.level().isEmptyBlock(pos.below()) || !this.level().getBlockState(pos.below()).blocksMotion())) {
                        pos = pos.below();
                    }
                    this.entityData.set(TARGET_Y, (float)pos.getY());
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
        compound.putBoolean("IsActivatedAlt", this.isActivatedAlt());
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
        this.entityData.set(IS_ACTIVATED_ALT, compound.getBoolean("IsActivatedAlt"));
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
        if (this.isLocked && this.noPhysics) return;
        super.travel(travelVector);
    }

    @Override
    public void move(MoverType type, Vec3 pos) {
        if (this.isLocked && this.noPhysics) return;
        super.move(type, pos);
    }

    @Override
    public void push(double x, double y, double z) {
        if (this.isLocked && this.noPhysics) return;
        super.push(x, y, z);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new net.minecraft.world.entity.ai.goal.FloatGoal(this));
        this.goalSelector.addGoal(1, new ChasePlayerGoal());
        this.goalSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.MeleeAttackGoal(this, 1.2D, false) {
            @Override
            public boolean canUse() {
                return SixthPaintingEntity.this.isChasing() && super.canUse();
            }
            @Override
            public boolean canContinueToUse() {
                return SixthPaintingEntity.this.isChasing() && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(3, new net.minecraft.world.entity.ai.goal.LookAtPlayerGoal(this, Player.class, 8.0F) {
            @Override
            public boolean canUse() {
                return SixthPaintingEntity.this.isChasing() && !SixthPaintingEntity.this.isLocked && super.canUse();
            }
        });
        this.targetSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return SixthPaintingEntity.this.isChasing();
            }
        });
    }

    // Returns true when the mob has finished its animation and is in chase mode
    private boolean isChasing() {
        if (!this.isActivated() && !this.isActivatedAlt()) return false;
        int ticksSinceActivation = this.tickCount - this.getActivationTick();
        int requiredLocks = this.isActivatedAlt() ? ANIM_TICKS_ALT : ANIM_TICKS_NORMAL;
        return ticksSinceActivation >= requiredLocks;
    }

    class ChasePlayerGoal extends net.minecraft.world.entity.ai.goal.Goal {
        private Player targetPlayer;

        public ChasePlayerGoal() {
            this.setFlags(java.util.EnumSet.of(net.minecraft.world.entity.ai.goal.Goal.Flag.MOVE, net.minecraft.world.entity.ai.goal.Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (!SixthPaintingEntity.this.isChasing()) return false;
            this.targetPlayer = SixthPaintingEntity.this.level().getNearestPlayer(SixthPaintingEntity.this, 64.0D);
            return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.isSpectator();
        }

        @Override
        public boolean canContinueToUse() {
            if (!SixthPaintingEntity.this.isChasing()) return false;
            return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.isSpectator();
        }

        @Override
        public void tick() {
            if (this.targetPlayer == null) return;
            // Make entity look at and walk toward player
            SixthPaintingEntity.this.getLookControl().setLookAt(this.targetPlayer, 30.0F, 30.0F);
            double dx = this.targetPlayer.getX() - SixthPaintingEntity.this.getX();
            double dz = this.targetPlayer.getZ() - SixthPaintingEntity.this.getZ();
            double dist = Math.sqrt(dx * dx + dz * dz);
            // Force body/head rotation toward the player every tick
            float targetYRot = (float)(Math.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
            SixthPaintingEntity.this.setYRot(targetYRot);
            SixthPaintingEntity.this.setYBodyRot(targetYRot);
            SixthPaintingEntity.this.setYHeadRot(targetYRot);
            if (dist > 1.5) {
                SixthPaintingEntity.this.getNavigation().moveTo(this.targetPlayer, 1.2D);
            } else {
                SixthPaintingEntity.this.getNavigation().stop();
            }
        }
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && this.resetCooldown > 0) {
            this.resetCooldown--;
        }

        if (this.isActivated() || this.isActivatedAlt()) {
            int ticksSinceActivation = this.tickCount - this.getActivationTick();
            
            double tX = this.entityData.get(TARGET_X);
            double tY = this.entityData.get(TARGET_Y);
            double tZ = this.entityData.get(TARGET_Z);
            
            int requiredLocks = this.isActivatedAlt() ? ANIM_TICKS_ALT : ANIM_TICKS_NORMAL;

            if (ticksSinceActivation < requiredLocks) {
                this.isLocked = true;

                if (ticksSinceActivation < 60) {
                    // Frozen in place horizontally
                    this.noPhysics = true;
                    this.setNoGravity(true);
                    this.setDeltaMovement(0, 0, 0);
                    
                    // Smoothly lerp X, Y, Z coordinates to ground between 1.5s (30 ticks) and 3.0s (60 ticks)
                    if (ticksSinceActivation >= 30) {
                        double startX = this.entityData.get(SPAWN_X);
                        double startY = this.entityData.get(SPAWN_Y);
                        double startZ = this.entityData.get(SPAWN_Z);
                        
                        double endX = tX;
                        double endY = this.entityData.get(TARGET_Y);
                        double endZ = tZ;
                        
                        double progress = (ticksSinceActivation - 30) / 30.0;
                        
                        // Add a beautiful 0.5-block high jump arc using a sine wave
                        double jumpArc = Math.sin(progress * Math.PI) * 0.5;
                        
                        double currentX = startX + (endX - startX) * progress;
                        double currentY = startY + (endY - startY) * progress + jumpArc;
                        double currentZ = startZ + (endZ - startZ) * progress;
                        
                        this.setPos(currentX, currentY, currentZ);
                    }
                } else {
                    // After 3.0s: fighting on the ground!
                    this.noPhysics = false;
                    this.setNoGravity(false);
                }

                if (ticksSinceActivation == 60) {
                    // Exactly at 3.0s (landing), teleport the hitbox forward out of the wall!
                    this.setPos(tX, this.entityData.get(TARGET_Y), tZ);
                }

                if (ticksSinceActivation == FRAME_SWAP_TICK) {
                    if (!this.level().isClientSide) {
                        java.util.List<SixthPhotoEntity> frames = this.level().getEntitiesOfClass(SixthPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
                        SixthPhotoEntity closestFrame = null;
                        double minDist = Double.MAX_VALUE;
                        for (SixthPhotoEntity frame : frames) {
                            double dist = this.distanceToSqr(frame);
                            if (dist < minDist) {
                                minDist = dist;
                                closestFrame = frame;
                            }
                        }
                        if (closestFrame != null && !closestFrame.isActivated()) {
                            closestFrame.setActivated(true);
                        }
                    }
                }

                super.tick();
                if (ticksSinceActivation < 60) {
                    this.setDeltaMovement(0, 0, 0);
                }

            } else {
                // Chase mode
                this.isLocked = false;
                this.noPhysics = false;
                this.setNoGravity(false);

                super.tick();

                // Force rotation toward nearest player AFTER super.tick() so navigation can't override it
                if (!this.level().isClientSide) {
                    net.minecraft.world.entity.player.Player nearestPlayer = this.level().getNearestPlayer(this, 64.0D);
                    if (nearestPlayer != null) {
                        double dx = nearestPlayer.getX() - this.getX();
                        double dz = nearestPlayer.getZ() - this.getZ();
                        float yaw = (float)(Math.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                        this.setYRot(yaw);
                        this.setYBodyRot(yaw);
                        this.setYHeadRot(yaw);
                        this.yRotO = yaw;
                    }
                }
            }
        } else {
            this.isLocked = false;
            this.noPhysics = false;
            this.setNoGravity(false);
            
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
        if (this.isLocked || (!this.isActivated() && !this.isActivatedAlt())) return false;
        return super.hurt(source, amount);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 5, this::predicate));
    }

    private PlayState predicate(AnimationState<SixthPaintingEntity> event) {
        if (!this.isActivated() && !this.isActivatedAlt()) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }

        int ticksSinceActivation = this.tickCount - this.getActivationTick();

        if (this.isActivatedAlt()) {
            if (ticksSinceActivation < ANIM_TICKS_ALT) {
                event.getController().setAnimation(RawAnimation.begin().thenPlay("Bug"));
                return PlayState.CONTINUE;
            }
        } else {
            if (ticksSinceActivation < ANIM_TICKS_NORMAL) {
                event.getController().setAnimation(RawAnimation.begin().thenPlay("Fighting2"));
                return PlayState.CONTINUE;
            }
        }
        
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("walk"));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
