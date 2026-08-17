package com.shiraken.template_mod.network.packet;

import com.shiraken.template_mod.entity.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class ActivatePaintingPacket {

    public ActivatePaintingPacket() {
    }

    public ActivatePaintingPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    private boolean isLookingAt(ServerPlayer player, net.minecraft.world.entity.Entity entity) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F).normalize();
        
        if (entity instanceof FifthDoorEntity) {
            // Use precise bounding box ray tracing for the large door
            java.util.Optional<Vec3> clip = entity.getBoundingBox().clip(eyePos, eyePos.add(lookVec.scale(10.0D)));
            if (clip.isPresent()) return true;
        }
        
        Vec3 targetPos = entity.position();
        if (entity instanceof SeventhPaintingEntity) {
            targetPos = targetPos.add(0, 1.0, 0); // Center of the wither
        } else {
            targetPos = entity.getBoundingBox().getCenter();
        }
        
        Vec3 toEntity = targetPos.subtract(eyePos).normalize();
        return lookVec.dot(toEntity) > 0.85; // Use a more forgiving dot product threshold
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ServerLevel level = player.serverLevel();
                AABB boundingBox = player.getBoundingBox().inflate(64.0D);
                
                net.minecraft.world.phys.Vec3 eyePos = player.getEyePosition();
                net.minecraft.world.phys.Vec3 lookVec = player.getViewVector(1.0F).normalize();
                
                FirstPaintingEntity bestTarget1 = null;
                double bestDot1 = 0.85; 
                List<FirstPaintingEntity> paintings1 = level.getEntitiesOfClass(FirstPaintingEntity.class, boundingBox);
                for (FirstPaintingEntity painting : paintings1) {
                    if (painting.isActivated()) continue; 
                    net.minecraft.world.phys.Vec3 toEntity = painting.position().subtract(eyePos).normalize();
                    double dot = lookVec.dot(toEntity);
                    if (dot > bestDot1) { bestDot1 = dot; bestTarget1 = painting; }
                }

                com.shiraken.template_mod.entity.SecondPaintingEntity bestTarget2 = null;
                double bestDot2 = 0.85; 
                List<com.shiraken.template_mod.entity.SecondPaintingEntity> paintings2 = level.getEntitiesOfClass(com.shiraken.template_mod.entity.SecondPaintingEntity.class, boundingBox);
                for (com.shiraken.template_mod.entity.SecondPaintingEntity painting : paintings2) {
                    if (painting.isActivated()) continue; 
                    net.minecraft.world.phys.Vec3 toEntity = painting.position().subtract(eyePos).normalize();
                    double dot = lookVec.dot(toEntity);
                    if (dot > bestDot2) { bestDot2 = dot; bestTarget2 = painting; }
                }
                
                com.shiraken.template_mod.entity.ThirdPaintingEntity bestTarget3 = null;
                double bestDot3 = 0.85; 
                List<com.shiraken.template_mod.entity.ThirdPaintingEntity> paintings3 = level.getEntitiesOfClass(com.shiraken.template_mod.entity.ThirdPaintingEntity.class, boundingBox);
                for (com.shiraken.template_mod.entity.ThirdPaintingEntity painting : paintings3) {
                    if (painting.isActivated()) continue; 
                    net.minecraft.world.phys.Vec3 toEntity = painting.position().subtract(eyePos).normalize();
                    double dot = lookVec.dot(toEntity);
                    if (dot > bestDot3) { bestDot3 = dot; bestTarget3 = painting; }
                }

                com.shiraken.template_mod.entity.FourthPaintingEntity bestTarget4 = null;
                double bestDot4 = 0.85; 
                List<com.shiraken.template_mod.entity.FourthPaintingEntity> paintings4 = level.getEntitiesOfClass(com.shiraken.template_mod.entity.FourthPaintingEntity.class, boundingBox);
                for (com.shiraken.template_mod.entity.FourthPaintingEntity painting : paintings4) {
                    if (painting.isActivated()) continue; 
                    net.minecraft.world.phys.Vec3 toEntity = painting.position().subtract(eyePos).normalize();
                    double dot = lookVec.dot(toEntity);
                    if (dot > bestDot4) { bestDot4 = dot; bestTarget4 = painting; }
                }

                FifthDoorEntity bestTarget5 = null;
                SeventhPaintingEntity bestTarget6 = null;
                EighthPaintingEntity bestTarget8 = null;
                NinthPaintingEntity bestTarget9 = null;
                com.shiraken.template_mod.entity.TenthPaintingEntity bestTarget10 = null;
                com.shiraken.template_mod.entity.SixthPaintingEntity bestTarget6_sixth = null;
                double minDistance = 64.0D;
                
                for (FifthDoorEntity entity : level.getEntitiesOfClass(FifthDoorEntity.class, boundingBox)) {
                    if (isLookingAt(player, entity)) {
                        double dist = player.distanceToSqr(entity);
                        if (dist < minDistance) { minDistance = dist; bestTarget5 = entity; bestTarget6 = null; bestTarget6_sixth = null; bestTarget8 = null; bestTarget9 = null; bestTarget10 = null; }
                    }
                }
                
                for (SeventhPaintingEntity entity : level.getEntitiesOfClass(SeventhPaintingEntity.class, boundingBox)) {
                    if (isLookingAt(player, entity)) {
                        double dist = player.distanceToSqr(entity);
                        if (dist < minDistance) { minDistance = dist; bestTarget5 = null; bestTarget6 = entity; bestTarget6_sixth = null; bestTarget8 = null; bestTarget9 = null; bestTarget10 = null; }
                    }
                }

                for (EighthPaintingEntity entity : level.getEntitiesOfClass(EighthPaintingEntity.class, boundingBox)) {
                    if (isLookingAt(player, entity)) {
                        double dist = player.distanceToSqr(entity);
                        if (dist < minDistance) { minDistance = dist; bestTarget5 = null; bestTarget6 = null; bestTarget6_sixth = null; bestTarget8 = entity; bestTarget9 = null; bestTarget10 = null; }
                    }
                }

                for (NinthPaintingEntity entity : level.getEntitiesOfClass(NinthPaintingEntity.class, boundingBox)) {
                    if (isLookingAt(player, entity)) {
                        double dist = player.distanceToSqr(entity);
                        if (dist < minDistance) { minDistance = dist; bestTarget5 = null; bestTarget6 = null; bestTarget6_sixth = null; bestTarget8 = null; bestTarget9 = entity; bestTarget10 = null; }
                    }
                }

                for (com.shiraken.template_mod.entity.TenthPaintingEntity entity : level.getEntitiesOfClass(com.shiraken.template_mod.entity.TenthPaintingEntity.class, boundingBox)) {
                    if (isLookingAt(player, entity)) {
                        double dist = player.distanceToSqr(entity);
                        if (dist < minDistance) { minDistance = dist; bestTarget5 = null; bestTarget6 = null; bestTarget6_sixth = null; bestTarget8 = null; bestTarget9 = null; bestTarget10 = entity; }
                    }
                }

                for (com.shiraken.template_mod.entity.SixthPaintingEntity entity : level.getEntitiesOfClass(com.shiraken.template_mod.entity.SixthPaintingEntity.class, boundingBox)) {
                    if (isLookingAt(player, entity)) {
                        double dist = player.distanceToSqr(entity);
                        if (dist < minDistance) { minDistance = dist; bestTarget5 = null; bestTarget6 = null; bestTarget8 = null; bestTarget9 = null; bestTarget10 = null; bestTarget6_sixth = entity; }
                    }
                }

                if (bestTarget5 != null) {
                    bestTarget5.setOpen(!bestTarget5.isOpen());
                } else if (bestTarget6 != null) {
                    if (!bestTarget6.isActivated() && !bestTarget6.isOnCooldown()) {
                        bestTarget6.setActivated(true);
                        List<SeventhPhotoEntity> frames = level.getEntitiesOfClass(SeventhPhotoEntity.class, bestTarget6.getBoundingBox().inflate(32.0D));
                        SeventhPhotoEntity closestFrame = null;
                        double closestDist = Double.MAX_VALUE;
                        for (SeventhPhotoEntity frame : frames) {
                            double dist = bestTarget6.distanceToSqr(frame);
                            if (dist < closestDist) { closestDist = dist; closestFrame = frame; }
                        }
                        if (closestFrame != null) closestFrame.setActivated(true);
                    }
                } else if (bestTarget8 != null) {
                    if (!bestTarget8.isActivated() && !bestTarget8.isOnCooldown()) {
                        bestTarget8.setActivated(true);
                        List<EighthPhotoEntity> frames = level.getEntitiesOfClass(EighthPhotoEntity.class, bestTarget8.getBoundingBox().inflate(32.0D));
                        EighthPhotoEntity closestFrame = null;
                        double closestDist = Double.MAX_VALUE;
                        for (EighthPhotoEntity frame : frames) {
                            double dist = bestTarget8.distanceToSqr(frame);
                            if (dist < closestDist) { closestDist = dist; closestFrame = frame; }
                        }
                        if (closestFrame != null) closestFrame.setActivated(true);
                    }
                } else if (bestTarget9 != null) {
                    if (!bestTarget9.isActivated() && !bestTarget9.isOnCooldown()) {
                        bestTarget9.setActivated(true);
                        List<NinthPhotoEntity> frames = level.getEntitiesOfClass(NinthPhotoEntity.class, bestTarget9.getBoundingBox().inflate(32.0D));
                        NinthPhotoEntity closestFrame = null;
                        double closestDist = Double.MAX_VALUE;
                        for (NinthPhotoEntity frame : frames) {
                            double dist = bestTarget9.distanceToSqr(frame);
                            if (dist < closestDist) { closestDist = dist; closestFrame = frame; }
                        }
                        if (closestFrame != null) closestFrame.setActivated(true);
                    }
                } else if (bestTarget10 != null) {
                    if (!bestTarget10.isActivated() && !bestTarget10.isOnCooldown()) {
                        bestTarget10.setActivated(true);
                        java.util.List<com.shiraken.template_mod.entity.TenthPhotoEntity> frames = level.getEntitiesOfClass(com.shiraken.template_mod.entity.TenthPhotoEntity.class, bestTarget10.getBoundingBox().inflate(32.0D));
                        com.shiraken.template_mod.entity.TenthPhotoEntity closestFrame = null;
                        double closestDist = Double.MAX_VALUE;
                        for (com.shiraken.template_mod.entity.TenthPhotoEntity frame : frames) {
                            double dist = bestTarget10.distanceToSqr(frame);
                            if (dist < closestDist) { closestDist = dist; closestFrame = frame; }
                        }
                        if (closestFrame != null) closestFrame.setActivated(true);
                    }
                } else if (bestTarget6_sixth != null) {
                    if (!bestTarget6_sixth.isActivated() && !bestTarget6_sixth.isActivatedAlt() && !bestTarget6_sixth.isOnCooldown()) {
                        bestTarget6_sixth.setActivatedState(true, false);
                        java.util.List<com.shiraken.template_mod.entity.SixthPhotoEntity> frames = level.getEntitiesOfClass(com.shiraken.template_mod.entity.SixthPhotoEntity.class, bestTarget6_sixth.getBoundingBox().inflate(32.0D));
                        com.shiraken.template_mod.entity.SixthPhotoEntity closestFrame = null;
                        double closestDist = Double.MAX_VALUE;
                        for (com.shiraken.template_mod.entity.SixthPhotoEntity frame : frames) {
                            double dist = bestTarget6_sixth.distanceToSqr(frame);
                            if (dist < closestDist) { closestDist = dist; closestFrame = frame; }
                        }
                        if (closestFrame != null) closestFrame.setActivated(true);
                    }
                } else if (bestTarget1 != null && bestDot1 >= bestDot2 && bestDot1 >= bestDot3 && bestDot1 >= bestDot4) {
                    if (!bestTarget1.isOnCooldown()) {
                        bestTarget1.setActivated(true);
                        List<CustomPhotoEntity> frames = level.getEntitiesOfClass(CustomPhotoEntity.class, bestTarget1.getBoundingBox().inflate(16.0D));
                        CustomPhotoEntity bestFrame = null;
                        double minFrameDist = Double.MAX_VALUE;
                        for (CustomPhotoEntity frame : frames) {
                            double dist = bestTarget1.distanceToSqr(frame);
                            if (dist < minFrameDist) { minFrameDist = dist; bestFrame = frame; }
                        }
                        if (bestFrame != null) bestFrame.setActivated(true);
                    }
                } else if (bestTarget2 != null && bestDot2 >= bestDot1 && bestDot2 >= bestDot3 && bestDot2 >= bestDot4) {
                    if (!bestTarget2.isOnCooldown()) {
                        bestTarget2.setActivated(true);
                        List<SecondPhotoEntity> frames = level.getEntitiesOfClass(SecondPhotoEntity.class, bestTarget2.getBoundingBox().inflate(16.0D));
                        SecondPhotoEntity bestFrame = null;
                        double minFrameDist = Double.MAX_VALUE;
                        for (SecondPhotoEntity frame : frames) {
                            double dist = bestTarget2.distanceToSqr(frame);
                            if (dist < minFrameDist) { minFrameDist = dist; bestFrame = frame; }
                        }
                        if (bestFrame != null) bestFrame.setActivated(true);
                    }
                } else if (bestTarget3 != null && bestDot3 >= bestDot1 && bestDot3 >= bestDot2 && bestDot3 >= bestDot4) {
                    if (!bestTarget3.isOnCooldown()) {
                        bestTarget3.setActivated(true);
                        List<ThirdPhotoEntity> frames = level.getEntitiesOfClass(ThirdPhotoEntity.class, bestTarget3.getBoundingBox().inflate(16.0D));
                        ThirdPhotoEntity bestFrame = null;
                        double minFrameDist = Double.MAX_VALUE;
                        for (ThirdPhotoEntity frame : frames) {
                            double dist = bestTarget3.distanceToSqr(frame);
                            if (dist < minFrameDist) { minFrameDist = dist; bestFrame = frame; }
                        }
                        if (bestFrame != null) bestFrame.setActivated(true);
                    }
                } else if (bestTarget4 != null && bestDot4 >= bestDot1 && bestDot4 >= bestDot2 && bestDot4 >= bestDot3) {
                    if (!bestTarget4.isOnCooldown()) {
                        bestTarget4.setActivated(true);
                        List<FourthPhotoEntity> frames = level.getEntitiesOfClass(FourthPhotoEntity.class, bestTarget4.getBoundingBox().inflate(16.0D));
                        FourthPhotoEntity bestFrame = null;
                        double minFrameDist = Double.MAX_VALUE;
                        for (FourthPhotoEntity frame : frames) {
                            double dist = bestTarget4.distanceToSqr(frame);
                            if (dist < minFrameDist) { minFrameDist = dist; bestFrame = frame; }
                        }
                        if (bestFrame != null) bestFrame.setActivated(true);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}

