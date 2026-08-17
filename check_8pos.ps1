$json = Get-Content 'src\main\resources\assets\template_mod\animations\eighth_painting.animation.json' -Raw | ConvertFrom-Json
Write-Host "Break painting:"
$json.animations.'break painting'.bones.group.position | ConvertTo-Json -Depth 10
Write-Host "Walk:"
$json.animations.walk.bones.group.position | ConvertTo-Json -Depth 10
