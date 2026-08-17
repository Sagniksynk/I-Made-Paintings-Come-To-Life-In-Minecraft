# Define the rotation matrix for X-axis pitch of -72.5 degrees
$pitch = -72.5 * [Math]::PI / 180.0
$cos = [Math]::Cos($pitch)
$sin = [Math]::Sin($pitch)

# Torso position at 6.0s in local coordinates
$x = 6.0
$y = -140.21
$z = -20.88

# group18 base position
$gx = -3.0
$gy = 11.0
$gz = 40.0

# Apply rotation to Torso position
$global_x = $x
$global_y = $y * $cos - $z * $sin
$global_y = $global_y + $gy

$global_z = $y * $sin + $z * $cos
$global_z = $global_z + $gz

# Convert units to blocks (1 block = 16 units)
$block_x = $global_x / 16.0
$block_y = $global_y / 16.0
$block_z = $global_z / 16.0

Write-Host "Global X: $block_x"
Write-Host "Global Y: $block_y"
Write-Host "Global Z: $block_z"
