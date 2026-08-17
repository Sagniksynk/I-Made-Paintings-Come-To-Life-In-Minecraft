$path = 'src\main\resources\assets\template_mod\animations\fourth_painting.animation.json'
$data = Get-Content $path -Raw | ConvertFrom-Json

# 1. Revert scale in Walk animation (user wants it smaller like before)
if ($data.animations.walk.bones.group.scale) {
    $data.animations.walk.bones.group.PSObject.Properties.Remove('scale')
}

# 2. Smooth landing between 4.0s and 4.5s
$data.animations.'break painting'.animation_length = 4.5
$group = $data.animations.'break painting'.bones.group

# Fix rotation so it holds [85, 0, 150] until 4.0s before smoothly landing
if (-not $group.rotation) { $group | Add-Member -MemberType NoteProperty -Name "rotation" -Value @{} }
$group.rotation | Add-Member -MemberType NoteProperty -Name "4.0" -Value @{ vector = @(85, 0, 150) } -Force
$group.rotation | Add-Member -MemberType NoteProperty -Name "4.5" -Value @{ vector = @(0, 0, 0) } -Force

# Position landing
if (-not $group.position) { $group | Add-Member -MemberType NoteProperty -Name "position" -Value @{} }
$group.position | Add-Member -MemberType NoteProperty -Name "4.5" -Value @{ vector = @(0, 0, -34.76206) } -Force

# Smoothly shrink from 3.1x back to 1x between 4.0s and 4.5s
if (-not $group.scale) { $group | Add-Member -MemberType NoteProperty -Name "scale" -Value @{} }
$group.scale | Add-Member -MemberType NoteProperty -Name "4.0" -Value @{ vector = @(3.1, 3, 3) } -Force
$group.scale | Add-Member -MemberType NoteProperty -Name "4.5" -Value @{ vector = @(1, 1, 1) } -Force

$data | ConvertTo-Json -Depth 10 | Set-Content $path
Write-Host "JSON Animation fixed for true smooth landing and shrinking!"
