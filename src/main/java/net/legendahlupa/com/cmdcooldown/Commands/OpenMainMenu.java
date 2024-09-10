package net.legendahlupa.com.cmdcooldown.Commands;

import net.legendahlupa.com.cmdcooldown.Database.DatabaseManager;
import net.legendahlupa.com.cmdcooldown.Menu.MainMenu;
import net.legendahlupa.com.cmdcooldown.Messages.GetMessages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

public class OpenMainMenu implements CommandExecutor {

    private final DatabaseManager databaseManager;
    public OpenMainMenu(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) {
            if (new GetMessages().open() == null) {
                return true;
            }
            Logger.getLogger("CMDCooldown").severe(new GetMessages().open().getString("Only-For-Players"));
            return true;
        }
        Player player = (Player) commandSender;

        if(!player.hasPermission("commandcooldown.open")){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(new GetMessages().open().getString("No-Permission"))));
            return true;
        }

        new MainMenu(databaseManager).createAndOpen().open(player);

        return true;
    }
}
