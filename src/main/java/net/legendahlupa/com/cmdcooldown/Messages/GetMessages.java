package net.legendahlupa.com.cmdcooldown.Messages;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;


public class GetMessages {
    public FileConfiguration open() {
        // Путь к файлу config.yml
        File configFile = new File("plugins/CMDCooldown", "config.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(configFile);

        switch (Objects.requireNonNull(configuration.getString("message_language"))){
            case "ru":{
                // Путь к файлу messages_ru.yml
                File messagesFile = new File("plugins/CMDCooldown", "Messages/messages_ru.yml");
                if (!messagesFile.exists()){
                    Logger.getLogger("CMDCooldown").severe("Файл messages_ru не обнаружен! Перезагрузите плагин");
                    return null;
                }
                return YamlConfiguration.loadConfiguration(messagesFile);
            }
            case "en":{
                // Путь к файлу messages_ru.yml
                File messagesFile = new File("plugins/CMDCooldown", "Messages/messages_en.yml");
                if (!messagesFile.exists()){
                    Logger.getLogger("CMDCooldown").severe("File messages_ru not found! Restart plugin");
                    return null;
                }
                return YamlConfiguration.loadConfiguration(messagesFile);
            }
        }


        return null;
    }
}
