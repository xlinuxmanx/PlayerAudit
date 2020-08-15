package co.linuxman.playeraudit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class PlayerAuditTabCompleter implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> autoComplete = new ArrayList<String>();
        autoComplete.add("help");
        autoComplete.add("reload");
        autoComplete.add("wand");

        if(args.length == 1){
            return autoComplete;
        }
        return null;
    }
}
