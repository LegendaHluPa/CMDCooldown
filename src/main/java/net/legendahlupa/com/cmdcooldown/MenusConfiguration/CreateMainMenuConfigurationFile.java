package net.legendahlupa.com.cmdcooldown.MenusConfiguration;

import net.legendahlupa.com.cmdcooldown.CMDCooldown;

import java.io.File;

public class CreateMainMenuConfigurationFile {

    private final CMDCooldown plugin;


    public CreateMainMenuConfigurationFile(CMDCooldown cmdCooldown) {
        this.plugin = cmdCooldown;
    }

    public void TryCreateFile(){
        File MainMenuConfiguration = new File("plugins/CMDCooldown", "MenusConfiguration/MainMenuConfiguration.yml");
        if (!MainMenuConfiguration.exists()) {
            plugin.saveResource("MenusConfiguration/MainMenuConfiguration.yml", false);
        }
        return;
    }

}

