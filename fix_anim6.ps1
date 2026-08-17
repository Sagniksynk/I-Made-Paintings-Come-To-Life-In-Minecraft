$oldPath = 'src\main\resources\assets\template_mod\animations\sixth_painting.animation.json'
$newPath = 'models\New Animations\6th_painting.animation.json'

$oldJson = Get-Content $oldPath -Raw | ConvertFrom-Json
$newJson = Get-Content $newPath -Raw | ConvertFrom-Json

# 1. Update walk animation
$oldJson.animations.walk = $newJson.animations.walk

# 2. Fix Fighting2 landing
$f2Group = $oldJson.animations.Fighting2.bones.group
if (-not $f2Group.position) { $f2Group | Add-Member -MemberType NoteProperty -Name "position" -Value @{} }
$f2Group.position | Add-Member -MemberType NoteProperty -Name "2.99" -Value @{ vector = @(0, 0, -31) } -Force
$f2Group.position | Add-Member -MemberType NoteProperty -Name "3.0" -Value @{ vector = @(0, 0, 0) } -Force
if ($f2Group.position.'9.0') {
    $f2Group.position.'9.0'.vector = @(-14, 0, 0)
}

# 3. Fix Bug landing
$bugGroup = $oldJson.animations.Bug.bones.group
if (-not $bugGroup.position) { $bugGroup | Add-Member -MemberType NoteProperty -Name "position" -Value @{} }
$bugGroup.position | Add-Member -MemberType NoteProperty -Name "2.99" -Value @{ vector = @(0, 0, -31) } -Force
$bugGroup.position | Add-Member -MemberType NoteProperty -Name "3.0" -Value @{ vector = @(0, 0, 0) } -Force

$oldJson | ConvertTo-Json -Depth 10 | Set-Content $oldPath
Write-Host "Sixth painting animations fixed!"
