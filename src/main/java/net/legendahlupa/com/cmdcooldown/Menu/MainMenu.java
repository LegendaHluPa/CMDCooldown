package net.legendahlupa.com.cmdcooldown.Menu;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import net.legendahlupa.com.cmdcooldown.Database.DatabaseManager;
import net.legendahlupa.com.cmdcooldown.LoadMainConfig;
import net.legendahlupa.com.cmdcooldown.MenusConfiguration.GetMainMenuConfiguration;
import net.legendahlupa.com.cmdcooldown.Messages.GetMessages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private final DatabaseManager databaseManager;

    public MainMenu(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    public PaginatedGui createAndOpen() {
        PaginatedGui mainMenu = Gui.paginated()
                .title(Component.text("CMD Cooldown"))
                .rows(6)
                .pageSize(28)
                .create();

        fillBorder(mainMenu);
        addCooldownItemsToMenus(mainMenu);

        return mainMenu;
    }

    private void addCooldownItemsToMenus(PaginatedGui mainMenu) {

        FileConfiguration configuration = new LoadMainConfig().load();


        configuration.getConfigurationSection("Cooldowns").getKeys(false).forEach(command -> {


            ItemStack item = new ItemStack(Material.valueOf(new GetMainMenuConfiguration().open().getString("CooldownItem.Material")));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.WHITE + command);

            List<String> lore = new ArrayList<>();
            List<String> configLore = new GetMessages().open().getStringList("Cooldown-Item-in-Menu-lore");

            for (String line : configLore) {
                lore.add(ChatColor.translateAlternateColorCodes('&', line.replace("%cooldown_time%", String.valueOf(configuration.getInt("Cooldowns." + command + ".time")))));
            }



            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);


            GuiItem guiItem = new GuiItem(item, event -> {
                Player player = (Player) event.getWhoClicked();

                if (event.getClick().isRightClick()){
                    new LoadMainConfig().removeCommand(command);
                    event.setCancelled(true);

                    mainMenu.removePageItem(event.getCurrentItem());
                    mainMenu.update();
                    player.updateInventory();

                    return;
                }
                if (event.getClick().isLeftClick()){
                    databaseManager.getWaitInput().put(player, command);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', new GetMessages().open().getString("EnterNumber")));
                    player.closeInventory();

                }
                event.setCancelled(true);
            });
            mainMenu.addItem(guiItem);
        });


    }





    private void fillBorder(PaginatedGui mainMenu) {
        if (new GetMainMenuConfiguration().open() == null) {
            return;
        }

        GetMainMenuConfiguration mainMenuConfiguration = new GetMainMenuConfiguration();

        ItemStack fillBorder = new ItemStack(Material.valueOf(mainMenuConfiguration.open().getString("MainMenuBorder.Material")));
        ItemMeta fillBorderMeta = fillBorder.getItemMeta();
        fillBorderMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', mainMenuConfiguration.open().getString("MainMenuBorder.DisplayName")));
        fillBorder.setItemMeta(fillBorderMeta);


        //Края интерфейса
        mainMenu.getFiller().fillBorder(new GuiItem(fillBorder, event -> {
            event.setCancelled(true);
        }));


        //Предмет следующей страницы
        ItemStack nextIS = new ItemStack(Material.valueOf(mainMenuConfiguration.open().getString("NextItem.Material")));
        ItemMeta nextMeta = nextIS.getItemMeta();
        nextMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', mainMenuConfiguration.open().getString("NextItem.DisplayName")));
        nextIS.setItemMeta(nextMeta);


        GuiItem nextGuiItem = new GuiItem(nextIS, event -> {
            mainMenu.next();
            event.setCancelled(true);
            System.out.println(mainMenu.getCurrentPageNum());

        });


        mainMenu.setItem(6, 6, nextGuiItem);


        //Предмет предидущей страницы
        ItemStack prevIS = new ItemStack(Material.valueOf(mainMenuConfiguration.open().getString("PrevItem.Material")));
        ItemMeta prevMeta = prevIS.getItemMeta();
        prevMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', mainMenuConfiguration.open().getString("PrevItem.DisplayName")));
        prevIS.setItemMeta(prevMeta);

        GuiItem prevGuiItem = new GuiItem(prevIS, event -> {
            mainMenu.previous();
            event.setCancelled(true);
            System.out.println(mainMenu.getCurrentPageNum());

        });


        mainMenu.setItem(6, 4, prevGuiItem);

    }


}
