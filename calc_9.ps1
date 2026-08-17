$yaw = 10 * [Math]::PI / 180.0
$pitch = 90 * [Math]::PI / 180.0
$roll = -110 * [Math]::PI / 180.0

$cy = [Math]::Cos($yaw)
$sy = [Math]::Sin($yaw)
$cp = [Math]::Cos($pitch)
$sp = [Math]::Sin($pitch)
$cr = [Math]::Cos($roll)
$sr = [Math]::Sin($roll)

# Rotation Matrix from Yaw, Pitch, Roll (ZYX order usually, or YXZ)
# Let's use standard Blockbench/GeckoLib rotation order: X, Y, Z
# Actually GeckoLib order is often X, Y, Z (Pitch, Yaw, Roll)
# Matrix: Rx * Ry * Rz
$m00 = $cy * $cr + $sy * $sp * $sr
$m01 = -$cy * $sr + $sy * $sp * $cr
$m02 = $sy * $cp

$m10 = $cp * $sr
$m11 = $cp * $cr
$m12 = -$sp

$m20 = -$sy * $cr + $cy * $sp * $sr
$m21 = $sy * $sr + $cy * $sp * $cr
$m22 = $cy * $cp

# Local offsets
$x = 0
$y = -14
$z = -123

# Global offsets
$gx = $x * $m00 + $y * $m01 + $z * $m02
$gy = $x * $m10 + $y * $m11 + $z * $m12
$gz = $x * $m20 + $y * $m21 + $z * $m22

$block_x = $gx / 16.0
$block_y = $gy / 16.0
$block_z = $gz / 16.0

Write-Host "Block dX: $block_x"
Write-Host "Block dY: $block_y"
Write-Host "Block dZ: $block_z"
