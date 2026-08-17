$path = 'src\main\resources\assets\template_mod\animations\fourth_painting.animation.json'
$data = Get-Content $path -Raw | ConvertFrom-Json

# 1. Fix shrinking: Add scale to Walk animation to match the 3.1x3x3 from break painting
if (-not $data.animations.walk.bones) {
    $data.animations.walk | Add-Member -MemberType NoteProperty -Name "bones" -Value @{}
}
if (-not $data.animations.walk.bones.group) {
    $data.animations.walk.bones | Add-Member -MemberType NoteProperty -Name "group" -Value @{}
}
$data.animations.walk.bones.group | Add-Member -MemberType NoteProperty -Name "scale" -Value @{
    "0.0" = @{ vector = @(3.1, 3, 3) }
} -Force

# 2. Smooth landing: Extend break painting to 4.5s and add landing keyframes
$data.animations.'break painting'.animation_length = 4.5
$group = $data.animations.'break painting'.bones.group

# Add rotation landing to perfectly upright
if (-not $group.rotation) { $group | Add-Member -MemberType NoteProperty -Name "rotation" -Value @{} }
$group.rotation | Add-Member -MemberType NoteProperty -Name "4.5" -Value @{ vector = @(0, 0, 0) } -Force

# Add position landing to the ground directly in front of the painting
if (-not $group.position) { $group | Add-Member -MemberType NoteProperty -Name "position" -Value @{} }
$group.position | Add-Member -MemberType NoteProperty -Name "4.5" -Value @{ vector = @(0, 0, -34.76206) } -Force

$data | ConvertTo-Json -Depth 10 | Set-Content $path
Write-Host "JSON Animation fixed for smooth landing!"
