$oldPath = 'src\main\resources\assets\template_mod\animations\sixth_painting.animation.json'
$newPath = 'models\New Animations\6th_painting.animation.json'

$oldJson = Get-Content $oldPath -Raw | ConvertFrom-Json
$newJson = Get-Content $newPath -Raw | ConvertFrom-Json

# 1. Update walk animation (handling the space in the key)
$walkAnim = $newJson.animations.'walk '
if (-not $walkAnim) {
    $walkAnim = $newJson.animations.walk
}

$oldJson.animations | Add-Member -MemberType NoteProperty -Name "walk" -Value $walkAnim -Force
$oldJson.animations.PSObject.Properties.Remove('walk ')

$oldJson | ConvertTo-Json -Depth 10 | Set-Content $oldPath
Write-Host "Sixth painting animations fixed!"
