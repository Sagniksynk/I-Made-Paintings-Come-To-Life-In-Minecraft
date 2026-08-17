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

public class EighthPaintingEntity extends PathfinderMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(EighthPaintingEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ACTIVATION_TICK = SynchedEntityData.defineId(EighthPaintingEntity.class, EntityDataSerializers.INT);
    
    private static final EntityDataAccessor<Float> TARGET_X = SynchedEntityData.defineId(EighthPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_Y = SynchedEntityData.defineId(EighthPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_Z = SynchedEntityData.defineId(EighthPaintingEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> SPAWN_X = SynchedEntityData.defineId(EighthPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPAWN_Y = SynchedEntityData.defineId(EighthPaintingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPAWN_Z = SynchedEntityData.defineId(EighthPaintingEntity.class, EntityDataSerializers.FLOAT);

    // Break animation is 8s = 160 ticks
    private static final int ANIM_TICKS = 160;
    private static final int TOTAL_LOCKED_TICKS = ANIM_TICKS;

    // Walk animation alternation: 5 seconds = 100 ticks
    private static final int WALK_SWITCH_TICKS = 100;

    private boolean isLocked = false; 
    private boolean hasSetSpawn = false;
    
    public int resetCooldown = 0;

    public EighthPaintingEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        // Default facing: East (positive X) = -90 degrees yaw
        this.setYRot(-90.0F);
        this.setYBodyRot(-90.0F);
        this.setYHeadRot(-90.0F);
        this.setXRot(0.0F);
        this.yRotO = -90.0F;
        this.yBodyRotO = -90.0F;
        this.yHeadRotO = -90.0F;
        
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
                .add(Attributes.FOLLOW_RANGE, 64.0D);
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
            this.moveTo(this.entityData.get(SPAWN_X), this.entityData.get(SPAWN_Y), this.entityData.get(SPAWN_Z), -90.0F, 0.0F);
            this.setYBodyRot(-90.0F);
            this.setYHeadRot(-90.0F);
            this.setDeltaMovement(0, 0, 0);
            
            // Reset frame
            java.util.List<EighthPhotoEntity> frames = this.level().getEntitiesOfClass(EighthPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
            for (EighthPhotoEntity frame : frames) {
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

                java.util.List<EighthPhotoEntity> frames = this.level().getEntitiesOfClass(EighthPhotoEntity.class, this.getBoundingBox().inflate(32.0D));
                EighthPhotoEntity closestFrame = null;
                double minDist = Double.MAX_VALUE;
                for (EighthPhotoEntity frame : frames) {
                    double dist = this.distanceToSqr(frame);
                    if (dist < minDist) {
                        minDist = dist;
                        closestFrame = frame;
                    }
                }
                if (closestFrame != null) {
                    net.minecraft.core.Direction dir = closestFrame.getDirection();
                    // Match the precise crawling delta from the JSON (5.927 blocks forward, 3.521 blocks down)
                    double targetX = closestFrame.getX() + dir.getStepX() * 5.927D;
                    double targetZ = closestFrame.getZ() + dir.getStepZ() * 5.927D;
                    double targetY = this.getY() - 3.521D;

                    // Raycast down to find the floor, so it doesn't teleport underground if placed low!
                    net.minecraft.core.BlockPos.MutableBlockPos pos = new net.minecraft.core.BlockPos.MutableBlockPos(targetX, this.getY(), targetZ);
                    while (pos.getY() > this.level().getMinBuildHeight() && this.level().getBlockState(pos).isAir()) {
                        pos.move(net.minecraft.core.Direction.DOWN);
                    }
                    double floorY = pos.getY() + 1.0D;
                    if (targetY < floorY) {
                        targetY = floorY; // Clamp to floor
                    }

                    this.entityData.set(TARGET_X, (float)targetX);
                    this.entityData.set(TARGET_Y, (float)targetY);
                    this.entityData.set(TARGET_Z, (float)targetZ);
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
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new FollowPlayerGoal());
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return EighthPaintingEntity.this.isActivated() && !EighthPaintingEntity.this.isLocked && super.canUse();
            }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F) {
            @Override
            public boolean canUse() {
                return EighthPaintingEntity.this.isActivated() && !EighthPaintingEntity.this.isLocked && super.canUse();
            }
        });
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return EighthPaintingEntity.this.isActivated() && !EighthPaintingEntity.this.isLocked && super.canUse();
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
                // LOCK: block all movement at the source
                this.isLocked = true;
                this.noPhysics = true;
                this.setNoGravity(true);
                this.setDeltaMovement(0, 0, 0);

                // Determine hold position
                if (ticksSinceActivation >= ANIM_TICKS) {
                    // Buffer phase: teleport root to target
                    this.setPos(tX, tY, tZ);
                }

                super.tick();
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
            this.isLocked = false;
            this.noPhysics = false;
            this.setNoGravity(false);
            
            // Hold at spawn position if not activated
            if (this.hasSetSpawn && !this.level().isClientSide) {
                this.moveTo(this.entityData.get(SPAWN_X), this.entityData.get(SPAWN_Y), this.entityData.get(SPAWN_Z), -90.0F, 0.0F);
                this.setYBodyRot(-90.0F);
                this.setYHeadRot(-90.0F);
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

    private PlayState predicate(AnimationState<EighthPaintingEntity> event) {
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

        // After break animation: alternate walk and walk2 every 5 seconds (100 ticks)
        int ticksSinceUnlocked = ticksSinceActivation - TOTAL_LOCKED_TICKS;
        int cyclePosition = (ticksSinceUnlocked / WALK_SWITCH_TICKS) % 2;

        if (event.isMoving()) {
            if (cyclePosition == 0) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("walk"));
            } else {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("walk2"));
            }
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
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
            if (!EighthPaintingEntity.this.isActivated()) return false;
            if (EighthPaintingEntity.this.isLocked) return false;

            this.targetPlayer = EighthPaintingEntity.this.level().getNearestPlayer(EighthPaintingEntity.this, 64.0D);
            return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.isSpectator();
        }

        @Override
        public boolean canContinueToUse() {
            if (!EighthPaintingEntity.this.isActivated() || EighthPaintingEntity.this.isLocked) return false;
            return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.isSpectator();
        }

        @Override
        public void start() {
            this.delayCounter = 0;
        }

        @Override
        public void tick() {
            if (this.targetPlayer != null) {
                EighthPaintingEntity.this.getLookControl().setLookAt(this.targetPlayer, 30.0F, 30.0F);

                if (--this.delayCounter <= 0) {
                    this.delayCounter = 10;
                    EighthPaintingEntity.this.getNavigation().moveTo(this.targetPlayer, 0.8D);
                }
            }
        }
    }
}
