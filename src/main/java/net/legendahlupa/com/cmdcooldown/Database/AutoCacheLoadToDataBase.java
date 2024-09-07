package net.legendahlupa.com.cmdcooldown.Database;

import net.legendahlupa.com.cmdcooldown.CMDCooldown;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class AutoCacheLoadToDataBase {

    private final DatabaseManager databaseManager;
    private final CMDCooldown cmdCooldown;
    public AutoCacheLoadToDataBase(DatabaseManager databaseManager, CMDCooldown cmdCooldown){
        this.databaseManager = databaseManager;
        this.cmdCooldown = cmdCooldown;
    }


    public void load(int interval) throws SQLException {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    databaseManager.loadCacheToDataBase();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskTimer(cmdCooldown, 0, interval);

    }

}
