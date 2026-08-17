$pitch = -72.5 * [Math]::PI / 180.0
$cos = [Math]::Cos($pitch)
$sin = [Math]::Sin($pitch)

# Start
$y0 = -32.82139
$z0 = 4.33086

# End
$y1 = -140.21542
$z1 = -20.88565

$dy = $y1 - $y0
$dz = $z1 - $z0

$global_dy = $dy * $cos - $dz * $sin
$global_dz = $dy * $sin + $dz * $cos

$block_dy = $global_dy / 16.0
$block_dz = $global_dz / 16.0

Write-Host "Block dY: $block_dy"
Write-Host "Block dZ: $block_dz"
