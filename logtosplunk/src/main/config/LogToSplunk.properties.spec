
#*******************************************************************************
# Connection Settings
#*******************************************************************************

splunk.craft.connection.host=<host.domain>
# Hostname or IP Address of the Splunk Enterprise instance hosting the HTTP Event Collector input.

splunk.craft.connection.port=8088
# HTTP Event Collector port to send data.


splunk.craft.token=CHANGEME-1234-5678-1234-123456789012
# HTTP Event Collector token

splunk.craft.enable.consolelogging=true
#Whether or not to send events to the Spigot console log in addition to Splunk.
# Warning this could be a lot of data.

splunk.craft.server.name=default
# The name of your Spigot/Minecraft server.
# This can be used to distinguish different Spigot/Minecraft servers on the same host using the same token.

#*******************************************************************************
# Logging Control
#*******************************************************************************

splunk.craft.logging.block.enable=true
# Enables or disables the logging of block (place, break, etc.) events.

splunk.craft.logging.player.enable=true
# Enables or disables the logging of player (login, logout, move, etc.) events.

splunk.craft.logging.creature.enable=true
# Enables or disables the logging of create (spawn, etc.) events.

splunk.craft.logging.death.enable=true
# Enables or disables the logging of death events, both creature and player.

splunk.craft.logging.world.enable=false
# Enables or disables the logging of world events. (Chunk load, Biome / Height map)

splunk.craft.logging.inventory.enable=true
# Enables or disables the logging of inventory (crafting)

splunk.craft.logging.damage.enable=true
# Enables or disables the logging of damage (player, creature, name) events.