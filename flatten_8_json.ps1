$oldPath = 'src\main\resources\assets\template_mod\animations\eighth_painting.animation.json'

$oldJson = Get-Content $oldPath -Raw | ConvertFrom-Json

# Redefine the group18 position to add the counter-offset to flatten the overall jump
# Original group18 base position is [-3, 11, 40] (from 0 to 6s)
# At 7s it goes to [-31, 11, 40]
# We add the Torso's negative delta to perfectly cancel the massive movement!
$group18Pos = [ordered]@{
    "0.0" = @{ vector = @(-3, 11, 40) }
    "2.5" = @{ vector = @(-3, 32.58, 44.70) }
    "3.0" = @{ vector = @(-3, 73.08, 45.94) }
    "3.5" = @{ vector = @(2, 125.88, 24.85) }
    "6.0" = @{ vector = @(-9, 118.39, 65.21) }
    "7.0" = @{ vector = @(-37, 118.39, 65.21) }
    "8.0" = @{ vector = @(-9, 118.39, 65.21) }
}

$oldJson.animations.'break painting'.bones.group18.position = $group18Pos

$oldJson | ConvertTo-Json -Depth 10 | Set-Content $oldPath
Write-Host "Eighth painting JSON flattened successfully!"
