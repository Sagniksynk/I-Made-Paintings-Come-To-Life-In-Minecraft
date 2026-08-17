$oldPath = 'src\main\resources\assets\template_mod\animations\seventh_painting.animation.json'

$oldJson = Get-Content $oldPath -Raw | ConvertFrom-Json

# Redefine the group bone position for seventh painting to instantly snap to [0, 0, 0] at 5.5s
# This zeroes out the visual offset exactly when the Java hitbox teleports to the visual location!
$groupPos = [ordered]@{
    "3.0" = @{ vector = @(0, 0, -1) }
    "3.5" = @{ vector = @(0, 2.04, -16.06) }
    "4.0" = @{ vector = @(0, 13.08, -25.44) }
    "4.9583" = @{ vector = @(0, 8, -34) }
    "5.49" = @{ vector = @(0, 8, -34); lerp_mode = "step" }
    "5.5" = @{ vector = @(0, 0, 0) }
}

$oldJson.animations.'break painting'.bones.group.position = $groupPos

$oldJson | ConvertTo-Json -Depth 10 | Set-Content $oldPath
Write-Host "Seventh painting JSON snapped properly!"
