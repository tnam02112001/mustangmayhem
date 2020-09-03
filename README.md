# Mustang Mayhem
Mustang Mayhem is a 2D endless-arcade game developed by me and my teammate Brett Gowling in March 2020. With the main goal of honoring the mascot of Cal Poly, we decided to choose Musty the Mustang as the main character of this game.

![image](https://github.com/tnam02112001/mustangmayhem/blob/master/Mustang%20Mayhem.jpg)

<iframe width="560" height="315" src="https://www.youtube.com/embed/NyHtgaiAwWM" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

## Gameplay
The goal of our game is simple: Don't die!

You are a Mustang in a world of vehicles traveling at deadly speeds, and your job is to survive. Using the arrow keys, you must maneuver around benches, rocks, and cars coming from both your left and right. The stage is constantly moving, and if your character falls behind, it is game over. 

Additionally, the longer the game runs, which also translates to the farther you travel, the faster the cars will drive, making it more difficult to progress. There is also an additional factor that you must consider during your journey: The Hunter. The Hunter is always chasing you, and if he catches you, game over.

Unfortunately, dying is inevitable, but it is your job to survive for as long as possible. Your score is determined by how far you travel vertically. Enjoy!

## System Requirements
| Requirement | Description |
| --- | --- |
| Operating System | Microsoft Windows, Mac OS, Linux |
| Third Party Software | [Java SE](https://www.oracle.com/java/technologies/javase-downloads.html) version 13 or later |

## Downloading and Playing Mustang Mayhem
1. Download the [latest stable realease](https://github.com/tnam02112001/mustangmayhem/releases/latest/download/MustangMayhem.zip)
2. Unzip the package
3. Run `MustangMayhem.jar`

>⚠ Note: If you run into any issues when opening the game:
> - Be sure that you have already installed [Java SE](https://www.oracle.com/java/technologies/javase-downloads.html) 13 or later on your computer.
> - Check if any antivirus software prevents the game from running. Some antivirus software might warn users about opening executable files downloaded from the Internet.
***
# Project Overview
Please take a few minutes to review the overview below:

## Development Process
Our development process took us about one week to finish. Our process went through two stages: the designing stage and the programming stage. As I was proficient in using Adobe Creative Suite, I was in charge of the designing stage. I used Adobe Photoshop to create the environment, UI, and animations of this game.

After the designing stage, my teammate and I worked on the programming stage together. We chose Java and PApplet as our main programming environments. For this project, we had to solve two major challenges: Generating the world randomly, and increasing the difficulty as the game progresses. While my teammate primarily focused on parsing the world, I worked on speeding up certain entities to increase the challenge of the game. 

## Core Logics
The game generates the world randomly. Each row in the world has a 50-50 chance of becoming either a road or a grass patch. In the case of a grass patch, the program randomly generates up to 5 obstacles, benches, and rocks. In the case of a road, cars will be spawning either from the left or from the right. 

The Mustang is the main character. The player uses arrow keys to control it. The Mustang visually flips its directions based on its last x-directional movement.  The mechanics behind this entity are simple, as the directional cues are all manual. The ghost is simply an animated entity that marks where the Mustang died.

Every car has a buffer value, which represents the spaces between each car. This buffer randomly ranges from two to eight. The Mustang can touch the car from the top, bottom, or back. However, if he runs into the car from the front, he gets run over. Pathing for the cars is simple, as they move either to the left or to the right.  A car has a 50-50 chance of being either a car moving towards the left or towards the right.

Using the Dijkstra pathfinding algorithm, The Hunter tries to get to the Mustang. The longer the game runs, the faster the Hunter is. As a result, the player needs to control the Mustang faster to survive from hunting.

