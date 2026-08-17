$oldPath = 'src\main\resources\assets\template_mod\animations\sixth_painting.animation.json'

$oldJson = Get-Content $oldPath -Raw | ConvertFrom-Json

# Redefine Fighting2 group position using [ordered] to keep keyframes in order for GeckoLib
$f2Pos = [ordered]@{
    "0.0" = @{ vector = @(0, 25, 44) }
    "1.5" = @{ vector = @(0, 0, 0); lerp_mode = "catmullrom" }
    "3.0" = @{ vector = @(0, 0, 0) }
    "9.0" = @{ vector = @(-14, 0, 0) }
}
$oldJson.animations.Fighting2.bones.group.position = $f2Pos

# Redefine Bug group position using [ordered]
$bugPos = [ordered]@{
    "0.0" = @{ vector = @(0, 25, 44) }
    "1.5" = @{ vector = @(0, 0, 0); lerp_mode = "catmullrom" }
    "3.0" = @{ vector = @(0, 0, 0) }
}
$oldJson.animations.Bug.bones.group.position = $bugPos

$oldJson | ConvertTo-Json -Depth 10 | Set-Content $oldPath
Write-Host "JSON smoothed successfully and ORDERED!"
