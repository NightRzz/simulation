# World Simulation
## Object Oriented Programming project in Java
![simulation](https://i.imgur.com/Qoscblv.png)  

The goal of the project is to write an object-oriented simulation application in Java. For visualization the graphical library Swing is used.

The application implements 2 main classes of organisms:
* Animals 
* Plants

These classes are expanded into subspecies, which have their own behavioral and characteristic features.  
| Plant | Strength | action() | collision() |
| :--------: | :--------: | :--------: | :--------: |
| Grass | 0 | - | - |
| Sonchus | 0 | Makes three attempts to spread in one turn. | - |
| Guaranna | 0 | - | Increases the strength of the animal that ate this plant by 3. |
| Belladonna | 99 | - | The animal that ate the plant dies. | 
| Heracleum sosnowskyi | 10 | Kills all animals in its vicinity. | The animal that ate the plant dies. |  
 
| Animal | Strength | Initiative | action() | collision() |
| :--------: | :--------: | :--------: | :--------: | :--------: |
| Wolf | 9 | 5 | - | - |
| Sheep | 4 | 4 | - | - |
| Fox | 3 | 7 | Good sense of smell: a fox will never move into a field occupied by an organism stronger than it. | - |
| Turtle | 2 | 1 | In 75% of cases, it does not changes its position. | Repels animal attacks with a strength of less than 5. The attacker must return to his previous field. |
| Antilope | 4 | 4 | The range of movement is 2 fields. | 50% chance of escaping before the fight. Then adjacent field. |

The player can control the movement of the human. In addition, the human has a special ability that strengthens him by 5 points, and his strength will decrease with each round to the value before the skill. After reaching pre-ability value, next 5 rounds skill can't be activated.  
| Strength | Initiative | action() | collision() |
| :--------: | :--------: | :--------: | :--------: |
| 5 | 4 | Human moves in the same way as animals, but the direction of his movement is not random, and corresponds to the arrow pressed by the player on the keyboard. I.e., if the player presses the arrow to the left, then (when it is the player's turn) the character will move one field to the left. | Human has a special skill that can be activated with a separate button on the keyboard. Once activated, the skill affects the behavior of the collision() method for five consecutive turns. After that, the skill is deactivated and cannot be activated again for the next five turns. **Skill**: invincibility - Human cannot be killed. If confronted with a stronger opponent, Human moves to a randomly selected adjacent field. |  
 
It is also possible to add an organism to a specific position by simply clicking on the square where you want to add an organism, and in the menu that appears, select the creature you want to add. 

![](https://i.imgur.com/v35OftD.png)  
There are 2 ways to control the simulation:
* With buttons in the GUI
* With keys on the keyboard

The application also allows you to save and load the current state of the simulation. On startup, the simulation world is filled with creatures in random order




