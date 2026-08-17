$geo = Get-Content 'src\main\resources\assets\template_mod\geo\eighth_painting.geo.json' -Raw | ConvertFrom-Json
$geo.'minecraft:geometry'[0].bones | Where-Object { -not $_.parent } | Select-Object -ExpandProperty name
