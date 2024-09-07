package net.legendahlupa.com.cmdcooldown;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LoadMainConfig {

    private FileConfiguration config;
    private File configFile;

    public LoadMainConfig() {
        configFile = new File("plugins/CMDCooldown", "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration load() {
        if (configFile.exists()) {
            return config;
        } else {
            return null;
        }
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeCommand(String command){
        config.set("Cooldowns."+ command, null);
        saveConfig();
    }

    public void changeTime(String command, int time){
        config.set("Cooldowns."+ command+".time", time);
        saveConfig();
    }

}
