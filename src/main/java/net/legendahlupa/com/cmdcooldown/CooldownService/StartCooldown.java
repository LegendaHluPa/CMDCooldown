package net.legendahlupa.com.cmdcooldown.CooldownService;


import net.legendahlupa.com.cmdcooldown.Database.CooldownModel;
import net.legendahlupa.com.cmdcooldown.Database.DatabaseManager;
import net.legendahlupa.com.cmdcooldown.LoadMainConfig;
import net.legendahlupa.com.cmdcooldown.Messages.GetMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StartCooldown implements Listener {
    private DatabaseManager databaseManager;

    public StartCooldown(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        FileConfiguration configuration = new LoadMainConfig().load();
        String inputCommand = event.getMessage();
        Player player = event.getPlayer();

        configuration.getConfigurationSection("Cooldowns").getKeys(false).forEach(command -> {


            if (inputCommand.contains("/" + command) ||
                    inputCommand.matches(".+:" + command) || isAlias(configuration,command, inputCommand)) {

                if (databaseManager.getCache().containsKey(player.getUniqueId())) {
                    if (!databaseManager.isCooldownExpired(player.getUniqueId())) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new GetMessages().open().getString("CooldownActive").replaceAll("%command%", command)));
                        return;
                    }
                }

                int cooldownTime = configuration.getInt("Cooldowns." + command + ".time");
                databaseManager.putToCache(player.getUniqueId(), new CooldownModel(player.getUniqueId(), command, cooldownTime, System.currentTimeMillis()));

            }


        });

    }
    private boolean isAlias(FileConfiguration configuration, String command, String inputCommand){
        List<String> aliases = configuration.getStringList("Cooldowns." + command + ".aliases");
        for (String alias : aliases){
            if (inputCommand.contains("/" + alias) || inputCommand.matches(".+:" + alias)){
                return true;
            }
        }

        return false;
    }
}
