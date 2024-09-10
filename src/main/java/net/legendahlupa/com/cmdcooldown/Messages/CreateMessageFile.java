package net.legendahlupa.com.cmdcooldown.Messages;

import net.legendahlupa.com.cmdcooldown.CMDCooldown;


import java.io.File;
import java.io.IOException;

public class CreateMessageFile {

    private final CMDCooldown plugin;


    public CreateMessageFile(CMDCooldown cmdCooldown){
        this.plugin = cmdCooldown;
    }

    public void TryCreateFile(String language) throws IOException {
        switch (language){
            case "ru":{
                File messagesFile = new File("plugins/CMDCooldown", "Messages/messages_ru.yml");
                if (!messagesFile.exists()) {
                    plugin.saveResource("Messages/messages_ru.yml", false);
                }
                return;
            }
            case "en":{
                File messagesFile = new File("plugins/CMDCooldown", "Messages/messages_en.yml");
                if (!messagesFile.exists()) {
                    plugin.saveResource("Messages/messages_en.yml", false);
                }
                return;
            }
        }

    }
}
