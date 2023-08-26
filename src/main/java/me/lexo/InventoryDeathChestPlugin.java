package me.lexo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryDeathChestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Inventory playerInventory = player.getInventory();

        // Obtener la ubicación del jugador en el momento de su muerte
        Location deathLocation = player.getLocation();

        // Crear el cofre en la ubicación de la muerte
        Block deathBlock = deathLocation.getBlock();
        deathBlock.setType(Material.CHEST);

        if (deathBlock.getState() instanceof Chest) {
            Chest deathChest = (Chest) deathBlock.getState();
            Inventory chestInventory = deathChest.getBlockInventory();

            // Transferir elementos del inventario del jugador al cofre
            for (ItemStack item : playerInventory.getContents()) {
                if (item != null && item.getType() != Material.AIR) {
                    chestInventory.addItem(item);
                }
            }

            // Limpiar el inventario del jugador
            playerInventory.clear();
        }
    }
}

