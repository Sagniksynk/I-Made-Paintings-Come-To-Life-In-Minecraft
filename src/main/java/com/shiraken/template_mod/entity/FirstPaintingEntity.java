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

public class FirstPaintingEntity extends PathfinderMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(FirstPaintingEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ACTIVATION_TICK = SynchedEntityData.defineId(FirstPaintingEntity.class, EntityDataSerializers.INT);
    
    // Add SynchedEntityData for the target coordinates so server dictates where the entity goes
    private static final EntityDataAccessor<Float> TARGET_X = SynchedEntityData.defineId(FirstPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_Y = SynchedEntityData.defineId(FirstPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_Z = SynchedEntityData.defineId(FirstPaintingEntity.class, EntityDataSerializers.FLOAT);

    // Save initial spawn position to reset properly
    private static final EntityDataAccessor<Float> SPAWN_X = SynchedEntityData.defineId(FirstPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPAWN_Y = SynchedEntityData.defineId(FirstPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPAWN_Z = SynchedEntityData.defineId(FirstPaintingEntity.class, EntityDataSerializers.FLOAT);

    private static final int ANIM_TICKS = 60;
    private static final int BUFFER_TICKS = 0;
    private static final int TOTAL_LOCKED_TICKS = ANIM_TICKS + BUFFER_TICKS;

    private boolean isLocked = false; // flag to block travel/move/push
    private int resetCooldown = 0;
    private boolean hasSetSpawn = false;
    
    public int clientTicksSinceActivation = 0;

    public FirstPaintingEntity(EntityType<? extends PathfinderMob> type, Level level) {
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
                .add(Attributes.MAX_HEALTH, 20.0D)
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
            java.util.List<CustomPhotoEntity> frames = this.level().getEntitiesOfClass(CustomPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
            for (CustomPhotoEntity frame : frames) {
                frame.setActivated(false);
            }
        }
    }

    public void setActivated(boolean activated) {
        this.entityData.set(IS_ACTIVATED, activated);
        if (activated) {
            this.entityData.set(ACTIVATION_TICK, this.tickCount);
            
            // SERVER ONLY: compute the target destination when activated
            if (!this.level().isClientSide) {
                if (!this.hasSetSpawn) {
                    this.entityData.set(SPAWN_X, (float)this.getX());
                    this.entityData.set(SPAWN_Y, (float)this.getY());
                    this.entityData.set(SPAWN_Z, (float)this.getZ());
                    this.hasSetSpawn = true;
                }

                java.util.List<CustomPhotoEntity> frames = this.level().getEntitiesOfClass(CustomPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
                CustomPhotoEntity closestFrame = null;
                double minDist = Double.MAX_VALUE;
                for (CustomPhotoEntity frame : frames) {
                    double dist = this.distanceToSqr(frame);
                    if (dist < minDist) {
                        minDist = dist;
                        closestFrame = frame;
                    }
                }
                if (closestFrame != null) {
                    net.minecraft.core.Direction dir = closestFrame.getDirection();
                    // Direction vector points outwards. Target should be 5.255 blocks relative to SPAWN
                    // to perfectly match where the Blockbench break animation ends.
                    this.entityData.set(TARGET_X, (float)(this.entityData.get(SPAWN_X) + dir.getStepX() * 5.255D));
                    this.entityData.set(TARGET_Y, (float)this.entityData.get(SPAWN_Y));
                    this.entityData.set(TARGET_Z, (float)(this.entityData.get(SPAWN_Z) + dir.getStepZ() * 5.255D));
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
                return FirstPaintingEntity.this.isActivated() && !FirstPaintingEntity.this.isLocked && super.canUse();
            }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F) {
            @Override
            public boolean canUse() {
                return FirstPaintingEntity.this.isActivated() && !FirstPaintingEntity.this.isLocked && super.canUse();
            }
        });
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return FirstPaintingEntity.this.isActivated() && !FirstPaintingEntity.this.isLocked && super.canUse();
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
            int ticksSinceActivation = this.level().isClientSide ? this.clientTicksSinceActivation : (this.tickCount - this.getActivationTick());
            
            double tX = this.entityData.get(TARGET_X);
            double tY = this.entityData.get(TARGET_Y);
            double tZ = this.entityData.get(TARGET_Z);

            if (ticksSinceActivation < TOTAL_LOCKED_TICKS) {
                // LOCK: block all movement at the source
                this.isLocked = true;
                this.noPhysics = true;
                this.setNoGravity(true);
                this.setDeltaMovement(0, 0, 0);

                // Determine hold position
                if (ticksSinceActivation >= ANIM_TICKS) {
                    // Buffer phase: root stays inside the painting so the visual offset doesn't double up!
                    // We only teleport the root right as the Walk animation begins.
                }
                // During animation phase: root stays wherever it already is (the animation bones handle the visual crossing)

                // Let super.tick() run — but travel/move/push are all blocked so position won't change
                super.tick();

                // Zero out any delta movement that AI goals might have set
                this.setDeltaMovement(0, 0, 0);

            } else {
                // UNLOCK: normal walking
                this.isLocked = false;
                this.noPhysics = false;
                this.setNoGravity(false);

                // Make sure entity is at target before enabling physics
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
            
            // Hold at spawn position and match frame rotation if not activated
            if (this.hasSetSpawn && !this.level().isClientSide) {
                float rot = 90.0F;
                java.util.List<CustomPhotoEntity> frames = this.level().getEntitiesOfClass(CustomPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
                CustomPhotoEntity closestFrame = null;
                double minDist = Double.MAX_VALUE;
                for (CustomPhotoEntity frame : frames) {
                    double dist = this.distanceToSqr(frame);
                    if (dist < minDist) {
                        minDist = dist;
                        closestFrame = frame;
                    }
                }
                if (closestFrame != null) {
                    rot = closestFrame.getDirection().toYRot();
                }
                this.moveTo(this.entityData.get(SPAWN_X), this.entityData.get(SPAWN_Y), this.entityData.get(SPAWN_Z), rot, 0.0F);
                this.setYBodyRot(rot);
                this.setYHeadRot(rot);
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

    private PlayState predicate(AnimationState<FirstPaintingEntity> event) {
        if (!this.isActivated()) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }

        int ticksSinceActivation = this.level().isClientSide ? this.clientTicksSinceActivation : (this.tickCount - this.getActivationTick());

        if (ticksSinceActivation < TOTAL_LOCKED_TICKS) {
            event.getController().setAnimationSpeed(0.666D);
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation"));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimationSpeed(0.5D);
            event.getController().setAnimation(RawAnimation.begin().thenLoop("Walk"));
            return PlayState.CONTINUE;
        } else {
            // Force it to stay in the Walk pose with 0 speed instead of holding the 'break painting' frame
            event.getController().setAnimationSpeed(0.0D);
            event.getController().setAnimation(RawAnimation.begin().thenLoop("Walk"));
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
            if (!FirstPaintingEntity.this.isActivated()) return false;
            if (FirstPaintingEntity.this.isLocked) return false;

            this.targetPlayer = FirstPaintingEntity.this.level().getNearestPlayer(FirstPaintingEntity.this, 64.0D);
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
                FirstPaintingEntity.this.getLookControl().setLookAt(this.targetPlayer, 30.0F, 30.0F);

                if (--this.delayCounter <= 0) {
                    this.delayCounter = 10;
                    FirstPaintingEntity.this.getNavigation().moveTo(this.targetPlayer, 0.8D);
                }
            }
        }
    }
}
