# PlayerAudit

Track the griefing without manual surveilence!
Basically what this plugin does is it logs various player actions that can be used for griefing. It also comes with a special Audit Wand used to check who is the owner of a block without having to scavange through the logs!

Each player on join will be given a profile folder where every event listed below will be logged into text files by date. There is a global log file that records everything into one log if you want to see everything a player did in order. Lava and Water placement logs will be in an individual log file at the root of their profile folder.

[![Features](https://i.imgur.com/b9idVS7.png "Features")](https://i.imgur.com/b9idVS7.png "Features")
- **Log Block Place!**
- **Log Block Break!**
- **Log Chat Messages!**
- **Log Commands!**
- **Log Sign Placed!**
- **Log Teleporting!**
- **Log Events to Console! (WARNING: CAN CAUSE SPAM ON LARGER SERVERS)**
- **Audit Wand!**

[![](https://i.imgur.com/TUaV2yP.gif)](https://i.imgur.com/TUaV2yP.gif)

[![Commands](https://i.imgur.com/tYnWsGS.png "Commands")](https://i.imgur.com/tYnWsGS.png "Commands")
- **/playeraudit help** - Displays command help
- **/playeraudit reload** - Reloads PlayerAudit config
- **/playeraudit wand** - Gives player an Audit Wand

[![Permissions](https://i.imgur.com/jeW7WNG.png "Permissions")](https://i.imgur.com/jeW7WNG.png "Permissions")

*This plugin is intended for staff usage*
- **playeraudit.admin** - Gives permission to all PlayerAudit commands

*Hint: Players who do not have permission cannot pickup an Audit Wand that was mistakenly dropped ;)*

[![Configuration](https://i.imgur.com/JmWZDD1.png "Configuration")](https://i.imgur.com/JmWZDD1.png "Configuration")

Configuration is quite simple and self explanitory.

    # What to log
    log-lava-bucket: true
    log-water-bucket: true
    log-block-place: true
    log-block-break: true
    log-chat-messages: true
    log-player-commands: true
    log-sign-messages: true
    log-player-teleport: true
    
    # Show log messages in console as well
    # WARNING: THIS CAN SPAM YOUR CONSOLE ON LARGER SERVERS, DISABLING BY DEFAULT
    log-to-console: false
