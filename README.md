# I Made Paintings Come To Life In Minecraft

A Minecraft Forge 1.20.1 mod that turns paintings into living GeckoLib entities. Place a painting frame, then activate it so the figure steps out of the canvas, plays custom animations, and can later reset back into the frame.

Built from the Forge MDK with [GeckoLib](https://github.com/bernie-g/geckolib) for models and animation.

## Requirements

| | |
| --- | --- |
| Minecraft | 1.20.1 |
| Forge | 47.3.0 or newer |
| Java | 17 |
| GeckoLib | 4.7.4 for Forge 1.20.1 |

Players need GeckoLib installed as a separate mod alongside this one.

## Features

- Living painting mobs with GeckoLib models and animations (skeletons, creeper, spider, wither, and more)
- Placeable painting frames for each painting
- Haunted room and haunted door entities
- Ghost Quartz (no collision) and Orange Carpet blocks
- Creative tab **Paintings Tab** with spawn eggs, frames, and related items

## Controls

Keybinds are under **I Made Paintings Come to Life** in Minecraft's Controls menu.

| Action | Default |
| --- | --- |
| Activate Paintings | Alt + 1 |
| Activate Painting Alternative | Alt + 2 |
| Activate Room | `.` (period) |

**Activate Paintings** wakes nearby painting entities so they come out of their frames. **Activate Painting Alternative** is used by the sixth painting. **Activate Room** plays the haunted room / hand animation.

## Commands

These require operator permission level 2. Reset commands start a 1-minute cooldown on the matching painting mobs.

- `/deletefirstpaintingmob`
- `/deletesecondpaintingmob`
- `/deletethirdpaintingmob`
- `/deletefourthpaintingmob`
- `/deletesixthpaintingmob`
- `/deleteseventhpaintingmob`
- `/deleteeighthpaintingmob`
- `/deleteninthpaintingmob`
- `/deletetenthpaintingmob`
- `/bypasscooldown` — clears the reset cooldown on all painting mobs

## Building from source

You need [JDK 17](https://adoptium.net/) installed.

```bash
./gradlew build
```

On Windows:

```bat
gradlew.bat build
```

The compiled jar is written to `build/libs/`. Copy it into your Minecraft `mods` folder with Forge 1.20.1 and GeckoLib 4.7.4.

To generate IntelliJ run configurations:

```bash
./gradlew genIntellijRuns
```

## Project layout

```
src/main/java/com/shiraken/template_mod/   Java source
src/main/resources/                        Models, animations, textures, lang
models/                                    Extra GeckoLib / Blockbench assets
```

Mod ID: `template_mod`

## License

This mod is released under the [MIT License](LICENSE). See [NOTICE](NOTICE) for third-party credits. The Forge MDK license is in `licenses/MinecraftForge-MDK.txt`.
