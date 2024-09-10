package net.legendahlupa.com.cmdcooldown.MenusConfiguration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public class GetMainMenuConfiguration {
    public FileConfiguration open() {

        // Путь к файлу config.yml
        File configFile = new File("plugins/CMDCooldown", "config.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(configFile);


        File MainMenuConfiguration = new File("plugins/CMDCooldown", "MenusConfiguration/MainMenuConfiguration.yml");


        switch (Objects.requireNonNull(configuration.getString("message_language"))){

            case "ru":{
                // Путь к файлу messages_ru.yml

                if (!MainMenuConfiguration.exists()){
                    Logger.getLogger("CMDCooldown").severe("Файл MainMenuConfiguration.yml не обнаружен! Перезагрузите плагин");
                    return null;
                }
                return YamlConfiguration.loadConfiguration(MainMenuConfiguration);
            }
            case "en":{
                // Путь к файлу messages_ru.yml
                if (!MainMenuConfiguration.exists()){
                    Logger.getLogger("CMDCooldown").severe("File MainMenuConfiguration.yml not found! Restart plugin");
                    return null;
                }
                return YamlConfiguration.loadConfiguration(MainMenuConfiguration);
            }
        }

        return YamlConfiguration.loadConfiguration(configFile);
    }
}
