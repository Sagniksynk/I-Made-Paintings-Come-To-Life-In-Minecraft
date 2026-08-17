$animPath = 'src\main\resources\assets\template_mod\animations\second_painting.animation.json'
$animData = Get-Content $animPath -Raw | ConvertFrom-Json

$group18Pos = $animData.animations.'break painting'.bones.group18.position
foreach ($key in $group18Pos.PSObject.Properties.Name) {
    if ($group18Pos.$key.vector[1] -lt 0) {
        $group18Pos.$key.vector[1] = 0
    }
}

$animData | ConvertTo-Json -Depth 10 | Set-Content $animPath
Write-Host "Done!"
