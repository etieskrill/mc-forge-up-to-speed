# mc-forge-up-to-speed

A simple repository in which me and a friend of mine teach ourselves the basics of creating Minecraft mods using the 
Forge framework, mostly via learning by doing.


# Quickstart modding

Following are some of the attributes which can be changed in the `build.gradle` file:
- `group`: Java base package structure
- `version`: the mod's version
- `dependencies/minecraft`: the minecraft release version
- `jar/manifest/attributes/*`: JAR bundle meta information

Changing these will in most cases require reloading the gradle project.

One way to deploy the mod is by packing it using the gradle build task, which then outputs the packed JAR file into the 
[build/libs](build/libs) folder. This can then be put in the minecraft mods folder in a local installation.

The other way is to run the included gradlew wrapper script, which offers two options for running the configuration:
1. Use `gradlew runClient`, which runs a custom full instance of Minecraft directly, or
2. use `gradlew runServer`, which starts up a Minecraft server instance that can be connected to using whatever local
minecraft client on `localhost` (the packed mod JAR has to be present in the local installation however)

__Note__: the dedicated server will unrun itself on startup, until the [EULA](run/eula.txt) was accepted.

Both options generate a pseudo `.minecraft` folder structure in the [run](run) folder, where one can install other mods,
find logs and game saves as well as adapt game options manually.

Similarly, the _gradlew_ wrapper offers the standard housekeeping functionality:
- `gradlew --refresh-dependencies` to renew the local cache
- `gradlew clean` to remove all locally generated files

Everything from here on can be found in the official [1.19.X Forge documentation](https://docs.minecraftforge.net/en/1.19.x/).
Reading documentations is a rather dry way of learning about such an interactive topic as minecraft modding, therefore
we suggest instead that one should follow the [Minecraft By Example](https://github.com/TheGreyGhost/MinecraftByExample)
repository, which presents the facets of the Forge API in an incremental task-style approach, allowing for a hands-on
experience to... brace yourself... GET UP TO SPEED HAHAHA GOTTEM. Its funny because its the name of this repository you see.
