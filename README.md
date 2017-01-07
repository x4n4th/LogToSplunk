# LogToSplunk
Event logging plugin for Spigot with support for Splunk HTTP Event Collector.

[![GitHub issues](https://img.shields.io/github/issues/x4n4th/LogToSplunk.svg)](https://github.com/x4n4th/LogToSplunk/issues) [![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/x4n4th/LogToSplunk/master/LICENSE) [![GitHub issues](https://api.travis-ci.org/x4n4th/LogToSplunk.svg)](https://travis-ci.org/x4n4th/LogToSplunk)

## Getting Started

### Requirements
* [Spigot](https://www.spigotmc.org/) Minecraft server software version 1.10 or later. 
* [Splunk](http://www.splunk.com) Enterprise 6.3 or later for HTTP Event Collector support. 

### Installing the plugin

1. Download the [latest pre-compiled version of the plugin](https://github.com/PowerSchill/LogToSplunk/releases/latest).
2. Configure the Splunk HTTP Event Collector on your Splunk instance according to the [documentation](http://dev.splunk.com/view/event-collector/SP-CAAAE6M).
3. Place the LogToSplunk.jar file within the plugins directory of your Spigot Minecraft server. 
4. Start the Minecraft server to generate the ./plugins/LogToSplunk directory, configuration file, and items file.
5. Stop the Minecraft server.
6. Edit the LogToSplunk.properties configuration file.

## Known Issues


## Support

Please use the GitHub [issue tracker](https://github.com/x4n4th/LogToSplunk/issues) to submit issues or requests for new features. Screenshots and/or log outputs would be very helpful.

## Credit

> The code in this project was originally developed from the[PowerSchill/minecraft-app](https://github.com/PowerSchill/minecraft-app) project forked from the [Splunk/minecraft-app](https://github.com/splunk/minecraft-app).

> Minecraft Items list (items.json) originally obtained from http://minecraft-ids.grahamedgecombe.com/api.