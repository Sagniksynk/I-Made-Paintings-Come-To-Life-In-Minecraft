$path = 'src\main\resources\assets\template_mod\animations\fourth_painting.animation.json'
$data = Get-Content $path -Raw | ConvertFrom-Json

# Revert scale in Walk animation
if ($data.animations.walk.bones.group) {
    if ($data.animations.walk.bones.group.scale) {
        $data.animations.walk.bones.group.PSObject.Properties.Remove('scale')
    }
}

# Revert break painting animation length
$data.animations.'break painting'.animation_length = 4

# Revert break painting 4.5 keyframes
$group = $data.animations.'break painting'.bones.group
if ($group.rotation) {
    if ($group.rotation.'4.5') {
        $group.rotation.PSObject.Properties.Remove('4.5')
    }
}
if ($group.position) {
    if ($group.position.'4.5') {
        $group.position.PSObject.Properties.Remove('4.5')
    }
}

$data | ConvertTo-Json -Depth 10 | Set-Content $path
Write-Host "JSON Animation reverted!"
