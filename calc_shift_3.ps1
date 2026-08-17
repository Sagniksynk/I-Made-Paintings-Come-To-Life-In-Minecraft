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

# Offset at 2.0s in break painting
$x = 0
$y = -14
$z = -56

$ex = $x * $m00 + $y * $m01 + $z * $m02
$ey = $x * $m10 + $y * $m11 + $z * $m12
$ez = $x * $m20 + $y * $m21 + $z * $m22

Write-Host "Break 2.0s X: $($ex / 16.0)"
Write-Host "Break 2.0s Y: $($ey / 16.0)"
Write-Host "Break 2.0s Z: $($ez / 16.0)"

# Offset at 0.0s in walk
$x = 0
$y = -12
$z = 0

$ex = $x * $m00 + $y * $m01 + $z * $m02
$ey = $x * $m10 + $y * $m11 + $z * $m12
$ez = $x * $m20 + $y * $m21 + $z * $m22

Write-Host "Walk 0.0s X: $($ex / 16.0)"
Write-Host "Walk 0.0s Y: $($ey / 16.0)"
Write-Host "Walk 0.0s Z: $($ez / 16.0)"
