package net.legendahlupa.com.cmdcooldown.CooldownService;

import net.legendahlupa.com.cmdcooldown.CMDCooldown;
import net.legendahlupa.com.cmdcooldown.Database.CooldownModel;
import net.legendahlupa.com.cmdcooldown.Database.DatabaseManager;
import net.legendahlupa.com.cmdcooldown.LoadMainConfig;
import net.legendahlupa.com.cmdcooldown.Messages.GetMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.UUID;

public class CooldownExpire {

    private final CMDCooldown cmdCooldown;
    private final DatabaseManager databaseManager;

    public CooldownExpire(CMDCooldown cmdCooldown, DatabaseManager databaseManager){
        this.cmdCooldown = cmdCooldown;
        this.databaseManager = databaseManager;
    }


    public void active(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for (CooldownModel model : databaseManager.getCache().values()){
                    if (databaseManager.isCooldownExpired(model.getUuid())){
                        Player player = Bukkit.getPlayer(model.getUuid());
                        if (player != null){
                            if (new LoadMainConfig().load().getBoolean("NotifyExpired")){
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', new GetMessages().open().getString("CooldownExpired").replaceAll("%command%", model.getCommand())));
                            }
                        }
                        databaseManager.removeFromCache(player.getUniqueId());
                        try {
                            databaseManager.removeFromDataBase(model);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }.runTaskTimer(cmdCooldown, 0,20);
    }

}
