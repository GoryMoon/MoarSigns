MoarSigns
=========

This is a mod that adds a lot of signs to the game

Installation
---

```sh
git clone [git-repo-url] MoarSigns
```
Download [CodeChickenCore-1.7.2-1.0.0-dev][1] & [NotEnoughItems-1.7.2-1.0.1-dev][1]

Add a folder called `libs`, in the folder put the two downloaded `.jar` files.

If you don't have [Gradle][2] installed on your computer you can use `gradlew` or `gradlew.bat` instead

##### Installing for Intellij IDEA
```sh
gradle setupDecompWorkspace idea
```

##### Installing for Eclipse
```sh
gradle setupDecompWorkspace eclipse
```

##### Test with more signs
To test with more signs go to the [CurseForge][3] page and download the mods that are listed as supported.
The downloaded mods need to place in the `eclipse\mods` folder together with [CodeChickenCore-1.7.2-1.0.0-dev][1].

You can add other mods that you want to have, but it's up to what mod sthat is.

License
----

GNU LGLP v.3


[1]:http://www.chickenbones.craftsaddle.org/Files/New_Versions/links.php
[2]:http://www.gradle.org/
[3]:http://minecraft.curseforge.com/mc-mods/moarsigns/