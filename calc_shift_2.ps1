# Hand bone rotation
$yaw = 10 * [Math]::PI / 180.0
$pitch = 90 * [Math]::PI / 180.0
$roll = -110 * [Math]::PI / 180.0

$cy = [Math]::Cos($yaw)
$sy = [Math]::Sin($yaw)
$cp = [Math]::Cos($pitch)
$sp = [Math]::Sin($pitch)
$cr = [Math]::Cos($roll)
$sr = [Math]::Sin($roll)

$m00 = $cy * $cr + $sy * $sp * $sr
$m01 = -$cy * $sr + $sy * $sp * $cr
$m02 = $sy * $cp

$m10 = $cp * $sr
$m11 = $cp * $cr
$m12 = -$sp

$m20 = -$sy * $cr + $cy * $sp * $sr
$m21 = $sy * $sr + $cy * $sp * $cr
$m22 = $cy * $cp

# Shift from start of walk [0, -12, 0] to end of break painting [0, -14, -123]
$x = 0
$y = -14 - (-12)
$z = -123 - 0

# Local to Entity shift
$ex = $x * $m00 + $y * $m01 + $z * $m02
$ey = $x * $m10 + $y * $m11 + $z * $m12
$ez = $x * $m20 + $y * $m21 + $z * $m22

Write-Host "Entity Shift X: $($ex / 16.0)"
Write-Host "Entity Shift Y: $($ey / 16.0)"
Write-Host "Entity Shift Z: $($ez / 16.0)"
