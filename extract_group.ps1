$json = Get-Content 'src\main\resources\assets\template_mod\animations\sixth_painting.animation.json' -Raw | ConvertFrom-Json
$json.animations.Fighting2.bones.group | ConvertTo-Json -Depth 10
