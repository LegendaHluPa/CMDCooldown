package net.legendahlupa.com.cmdcooldown;

import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import net.legendahlupa.com.cmdcooldown.Commands.OpenMainMenu;
import net.legendahlupa.com.cmdcooldown.CooldownService.CooldownExpire;
import net.legendahlupa.com.cmdcooldown.CooldownService.StartCooldown;
import net.legendahlupa.com.cmdcooldown.Database.AutoCacheLoadToDataBase;
import net.legendahlupa.com.cmdcooldown.Database.ConnectionGenerator;
import net.legendahlupa.com.cmdcooldown.Database.DatabaseManager;
import net.legendahlupa.com.cmdcooldown.Events.InputEvent;
import net.legendahlupa.com.cmdcooldown.MenusConfiguration.CreateMainMenuConfigurationFile;
import net.legendahlupa.com.cmdcooldown.Messages.CreateMessageFile;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;


public final class CMDCooldown extends JavaPlugin {
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {

        //Создание конфигурационного файла
        saveDefaultConfig();

        //Инициализация database
        databaseLoader();

        //Создание файла конфигурации
        try {
            new CreateMainMenuConfigurationFile(CMDCooldown.this).TryCreateFile();
            new CreateMessageFile(CMDCooldown.this).TryCreateFile(getConfig().getString("message_language"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        getServer().getPluginManager().registerEvents(new StartCooldown(databaseManager), this);
        getServer().getPluginManager().registerEvents(new InputEvent(databaseManager), this);
        getCommand("CommandCooldown").setExecutor(new OpenMainMenu(databaseManager));

        try {
            new AutoCacheLoadToDataBase(databaseManager, CMDCooldown.this).load(getConfig().getInt("AutoLoad")*20);
            new CooldownExpire(CMDCooldown.this, databaseManager).active();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    private void databaseLoader() {
        try {
            Logger.setGlobalLogLevel(Level.ERROR);
            databaseManager = new DatabaseManager(new ConnectionGenerator(getDataFolder(), getConfig()));
        } catch (SQLException e) {
            getLogger().severe("Error database initialization " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        try {
            databaseManager.loadCacheToDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
