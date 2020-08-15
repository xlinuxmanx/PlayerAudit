package co.linuxman.playeraudit.commands;

import co.linuxman.playeraudit.PlayerAudit;
import co.linuxman.playeraudit.items.AuditWand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerAuditCommands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        AuditWand aw = new AuditWand();
        Player player = ((Player) sender).getPlayer();

        if(sender.hasPermission("playeraudit.admin")){
            if(command.getName().equals("playeraudit") || command.getName().equals("pa")){
                if(args.length == 0 || args[0].equals("help")){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f--------------------"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/playeraudit wand: &fGives you an &6&lAudit Wand"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f--------------------"));
                }else if(args[0].equals("wand")){
                    aw.giveWand(player);
                }else if(args[0].equals("reload")){
                    PlayerAudit.plugin().reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfig reloaded"));
                }
            }
        }
        return false;
    }
}
