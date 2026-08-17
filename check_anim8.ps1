$json = Get-Content 'src\main\resources\assets\template_mod\animations\eighth_painting.animation.json' -Raw | ConvertFrom-Json
$json.animations | Get-Member -MemberType NoteProperty | Select-Object -ExpandProperty Name
