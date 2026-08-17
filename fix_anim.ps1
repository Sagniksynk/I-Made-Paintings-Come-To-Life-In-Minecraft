$newPath = 'models\New Animations\first painting.animation.json'
$oldPath = 'src\main\resources\assets\template_mod\animations\1st_skull.animation.json'

$newData = Get-Content $newPath -Raw | ConvertFrom-Json
$oldData = Get-Content $oldPath -Raw | ConvertFrom-Json

$newWalk = $newData.animations.Walk

foreach ($key in $newWalk.bones.group18.position.PSObject.Properties.Name) {
    $newWalk.bones.group18.position.$key.vector[2] = 0
}

$oldData.animations.Walk = $newWalk

foreach ($key in $oldData.animations.animation.bones.group18.position.PSObject.Properties.Name) {
    $oldData.animations.animation.bones.group18.position.$key.vector[1] = 0
}

$oldData | ConvertTo-Json -Depth 10 | Set-Content $oldPath

Write-Host "Done!"
