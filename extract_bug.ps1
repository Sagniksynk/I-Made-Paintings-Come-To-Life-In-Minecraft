$json = Get-Content 'src\main\resources\assets\template_mod\animations\sixth_painting.animation.json' -Raw | ConvertFrom-Json
$json.animations.Bug.bones.group | ConvertTo-Json -Depth 10
