$json = Get-Content 'src\main\resources\assets\template_mod\animations\seventh_painting.animation.json' -Raw | ConvertFrom-Json
$json.animations.'break painting'.bones.group | ConvertTo-Json -Depth 10
