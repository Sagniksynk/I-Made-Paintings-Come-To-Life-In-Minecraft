$json = Get-Content 'src\main\resources\assets\template_mod\animations\eighth_painting.animation.json' -Raw | ConvertFrom-Json
$json.animations.'break painting'.bones.group.position | ConvertTo-Json -Depth 10
