package me.benrobson.zanderhub.gui;

import me.benrobson.zanderhub.ZanderHubMain;
import me.benrobson.zanderhub.events.PluginMessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HubCompass implements Listener {
    ZanderHubMain plugin;
    public void navigationgui(ZanderHubMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void navigationgui(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() ==  Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                CompassNavGUI(player);
            }
        }
    }

    Inventory compnav = Bukkit.createInventory(null, 9, "Server Selector");

    public void CompassNavGUI(Player player) {
        if (player == null) {
            return;
        }

        ItemStack survival = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta survivalMeta = survival.getItemMeta();
        survivalMeta.setDisplayName(ChatColor.WHITE + "Survival");
        survivalMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me to join the Survival server."));
        survival.setItemMeta(survivalMeta);
        compnav.setItem(1, survival);

        ItemStack mixed = new ItemStack(Material.IRON_SWORD);
        ItemMeta mixedMeta = mixed.getItemMeta();
        mixedMeta.setDisplayName(ChatColor.WHITE + "Mixed");
        mixedMeta.setLore(Arrays.asList(ChatColor.WHITE + "Play work together with Campers in Minigames."));
        mixed.setItemMeta(mixedMeta);
        compnav.setItem(5, mixed);

        ItemStack xprace = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta xpraceMeta = xprace.getItemMeta();
        xpraceMeta.setDisplayName(ChatColor.WHITE + "XP Race");
        xpraceMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me to gather XP!"));
        xprace.setItemMeta(xpraceMeta);
        compnav.setItem(7, xprace);

        ItemStack creative = new ItemStack(Material.BEACON);
        ItemMeta creativeMeta = creative.getItemMeta();
        creativeMeta.setDisplayName(ChatColor.WHITE + "Creative");
        creativeMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me to enter Creative!"));
        creative.setItemMeta(creativeMeta);
        compnav.setItem(3, creative);

        player.openInventory(compnav);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Server Selector")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getCurrentItem().hasItemMeta()) {
            player.closeInventory();
            return;
        }

        switch (event.getCurrentItem().getType()) {
            // Survival
            case IRON_PICKAXE:
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Sending you to Survival..");
                if (player.hasPermission("bungeecord.server.survival")) {
                    PluginMessageChannel.connect(player, "survival");
                } else  {
                    player.sendMessage(ChatColor.RED + "You do not have access to this server.");
                }
                break;

            // Mixed
            case IRON_SWORD:
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Sending you to Mixed..");
                if (player.hasPermission("bungeecord.server.mixed")) {
                    PluginMessageChannel.connect(player, "mixed");
                } else  {
                    player.sendMessage(ChatColor.RED + "You do not have access to this server.");
                }
                break;

            // XP Race
            case EXPERIENCE_BOTTLE:
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Sending you to XP Race..");
                if (player.hasPermission("bungeecord.server.xprace")) {
                    PluginMessageChannel.connect(player, "xprace");
                } else  {
                    player.sendMessage(ChatColor.RED + "You do not have access to this server.");
                }
                break;

            default:
                break;
        }
    }
}
