$oldPath = 'src\main\resources\assets\template_mod\animations\eighth_painting.animation.json'

$oldJson = Get-Content $oldPath -Raw | ConvertFrom-Json

# Restore the original group18 position!
$group18Pos = [ordered]@{
    "0.0" = @{ vector = @(-3, 11, 40) }
    "4.5" = @{ vector = @(-3, 11, 40) }
    "6.0" = @{ vector = @(-3, 11, 40) }
    "7.0" = @{ vector = @(-31, 11, 40) }
    "8.0" = @{ vector = @(-3, 11, 40) }
}

$oldJson.animations.'break painting'.bones.group18.position = $group18Pos

$oldJson | ConvertTo-Json -Depth 10 | Set-Content $oldPath
Write-Host "Eighth painting JSON restored successfully!"
