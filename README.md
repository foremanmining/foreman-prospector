# foreman-prospector

## Prospector ##

Prospector is an open-source Java application that will query a user-provided subnet and find network accessible miners.  This application aims to facilitate the process of adding miners to Foreman for monitoring, and is intended to be used primarily for performing bulk imports.

Prospector currently only supports querying ASIC devices.  It will, however, be expanded upon to include the discovery of rigs.

### Supported Devices ###

The following ASIC miners can be discovered by Prospector:

* Antminer
* Avalon
* Baikal
* Blackminer
* Dayun
* DragonMint
* Whatsminer

### How To Use ###

If running on Windows, execute the `prospector.bat` script located in the `bin/` folder.  
If running on Linux, execute the `prospector.sh` script located in the `bin/` folder.

Select the type of device to discover, enter the subnet to query and API port, and your miners will be discovered.

A `miners.json` file will be created that represents every device that was found after performing a `Save` from the prospector application menu.  That file can be uploaded to a Pickaxe instance on the Foreman dashboard [here](https://dashboard.foreman.mn/dashboard/pickaxe/).

Example of a `miners.json` that could be generated (discovered one Antminer S9 and one Antminer E3):

```
[ {
  "apiPort" : 4028,
  "ipAddress" : "192.168.3.195",
  "minerType" : "Antminer E3",
  "name" : "Antminer E3 - 192.168.3.195",
  "parameters" : { },
  "category" : "ASIC"
}, {
  "apiPort" : 4028,
  "ipAddress" : "192.168.3.196",
  "minerType" : "Antminer S9",
  "name" : "Antminer S9 - 192.168.3.196",
  "parameters" : { },
  "category" : "ASIC"
} ]
```

### Requirements ###

- JDK version 8 (or higher)
- Apache Maven (only if building Prospector from sources)

### Building ###

To build prospector, from the top level of the repository:

```sh
$ mvn clean install
```

Upon a successful build, you should see something similar to the following:

```sh
[INFO] Reactor Summary:
[INFO]
[INFO] prospector: buildmaster ............................ SUCCESS [  1.256 s]
[INFO] prospector: model .................................. SUCCESS [ 11.068 s]
[INFO] prospector: prospect ............................... SUCCESS [  0.616 s]
[INFO] prospector: util ................................... SUCCESS [  2.458 s]
[INFO] prospector: antminer ............................... SUCCESS [ 14.623 s]
[INFO] prospector: avalon ................................. SUCCESS [ 11.202 s]
[INFO] prospector: baikal ................................. SUCCESS [  9.928 s]
[INFO] prospector: blackminer ............................. SUCCESS [ 10.231 s]
[INFO] prospector: dayun .................................. SUCCESS [ 10.000 s]
[INFO] prospector: dragonmint ............................. SUCCESS [ 10.241 s]
[INFO] prospector: menu ................................... SUCCESS [  0.449 s]
[INFO] prospector: scan ................................... SUCCESS [  1.518 s]
[INFO] prospector: whatsminer ............................. SUCCESS [  9.436 s]
[INFO] prospector: app .................................... SUCCESS [ 28.364 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 02:01 min
[INFO] Finished at: 2018-12-29T03:23:42Z
[INFO] Final Memory: 42M/261M
[INFO] ------------------------------------------------------------------------
```

The application distributions can be found in the `target/` folders.  You'll only need one - pick the extension you prefer.

```sh
$ ls -la **/target | grep -E "\.(zip|tar)"
-rwxrwxrwx 1 dan dan 9302829 Dec 28 22:23 prospector-app-1.0.0-SNAPSHOT-bin.tar.bz2
-rwxrwxrwx 1 dan dan 9344727 Dec 28 22:23 prospector-app-1.0.0-SNAPSHOT-bin.tar.gz
-rwxrwxrwx 1 dan dan 9354127 Dec 28 22:23 prospector-app-1.0.0-SNAPSHOT-bin.zip
$

```

## License ##

Copyright © 2018, [OBM LLC](https://obm.mn/).  Released under the [GPL-3.0 License](LICENSE).
