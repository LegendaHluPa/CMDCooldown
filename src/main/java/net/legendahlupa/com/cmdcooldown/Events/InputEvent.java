package net.legendahlupa.com.cmdcooldown.Events;

import net.legendahlupa.com.cmdcooldown.Database.DatabaseManager;
import net.legendahlupa.com.cmdcooldown.LoadMainConfig;
import net.legendahlupa.com.cmdcooldown.Menu.MainMenu;
import net.legendahlupa.com.cmdcooldown.Messages.GetMessages;
import org.apache.logging.log4j.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.HashMap;

public class InputEvent implements Listener {

    private final DatabaseManager databaseManager;

    public InputEvent(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    @EventHandler
    public void input(PlayerChatEvent event){
        String message = event.getMessage();
        Player player = event.getPlayer();
        HashMap waitInput = databaseManager.getWaitInput();
        if (waitInput.containsKey(player)){
            try {
                String value = (String) waitInput.get(player);
                new LoadMainConfig().changeTime(value, Integer.parseInt(message));
                waitInput.remove(player);
                event.setCancelled(true);
                new MainMenu(databaseManager).createAndOpen().open(player);
                return;
            } catch (NumberFormatException e){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', new GetMessages().open().getString("NotNumber")));
                event.setCancelled(true);
                return;
            }

        }
    }

}
