package de.moneysystem.bank;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

import de.moneysystem.Main;
import de.moneysystem.MySQL;

public class Bankexperte implements Listener {

	@EventHandler
	public void onPlayer(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked().getType() == EntityType.VILLAGER) {
			Villager v = (Villager) e.getRightClicked();
			if (v.getCustomName().equals("Bankexperte")) {
				e.setCancelled(true);

				setexpertenInventory(p);
			}
		}
	}

	@EventHandler
	public void onklickAntrag(InventoryClickEvent e) {

		Player p = (Player) e.getWhoClicked();

		if (e.getCurrentItem() != null && e.getClickedInventory() != null) {
			if (e.getClickedInventory().getName().equals("§2Bankexperte")) {
				e.setCancelled(true);
				if (e.getSlot() == 1) {
					// FEHLER
					if (e.isLeftClick()) {

						e.setCancelled(true);
						if (!MySQL.hasKonto(p.getUniqueId())) {
							OpenCreateKonto(p);

						} else {

							p.sendMessage(ChatColor.GREEN + "[Bank] " + ChatColor.RED + "Du besitzt schon ein Konto");

						}
					}
				}

			}

			if (e.getClickedInventory().getName() == "§2Bankkonto-Antrag") {
				e.setCancelled(true);
				if (e.getSlot() == 0) {
					e.setCancelled(true);
					p.sendMessage(ChatColor.GREEN + "[Bank] " + ChatColor.GREEN + "Der Antrag wurde abgebrochen!");

					p.closeInventory();
				}

				if (e.getSlot() == 8) {
					e.setCancelled(true);

					if (Main.getInstance().getConfig().getString("source").equals("file")) {

						Bank.addKonto(p.getUniqueId(), p);
						p.sendMessage(ChatColor.GREEN + "[Bank] Der Antrag wurde angenommen!");

					} else if (Main.getInstance().getConfig().getString("source").equals("sql")) {

						MySQL.createKonto(p.getUniqueId());

					}

					p.closeInventory();

				}

			}
		}
	}

	public void OpenCreateKonto(Player p) {

		Inventory inv = p.getServer().createInventory(null, 9, "§2Bankkonto-Antrag");

		// Infos
		ItemStack Leader = new ItemStack(Material.PAPER);
		ItemMeta leadermeta = Leader.getItemMeta();
		leadermeta.setDisplayName("Infomation");
		List<String> leaderlore = new ArrayList<>();
		leaderlore.add("Mit einem Bankkonto kannst du dein Geld in der Bank sichern");
		leaderlore.add("und jederzeit bei einer Bank etwas Abheben");
		leadermeta.setLore(leaderlore);
		Leader.setItemMeta(leadermeta);

		inv.setItem(4, Leader);

		// Angenommen - Ablehnen

		Dye Beantragendye = new Dye();
		Beantragendye.setColor(DyeColor.LIME);

		ItemStack Beantragen = Beantragendye.toItemStack();
		Beantragen.setAmount(1);
		ItemMeta Beantragenmmeta = Beantragen.getItemMeta();
		Beantragenmmeta.setDisplayName("§b§aBeantragen!");
		Beantragen.setItemMeta(Beantragenmmeta);

		inv.setItem(8, Beantragen);

		Dye ablehndye = new Dye();
		ablehndye.setColor(DyeColor.RED);

		ItemStack Ablehen = ablehndye.toItemStack();
		Ablehen.setAmount(1);
		ItemMeta ablehnmmeta = Ablehen.getItemMeta();
		ablehnmmeta.setDisplayName("§b§cAbbrechen!");
		Ablehen.setItemMeta(ablehnmmeta);

		inv.setItem(0, Ablehen);

		p.openInventory(inv);

	}

	public void setexpertenInventory(Player p) {

		Inventory inv = p.getServer().createInventory(null, 9, "§2Bankexperte");

		ItemStack Chest = new ItemStack(Material.CHEST);

		ItemMeta chestmeta = Chest.getItemMeta();

		chestmeta.setDisplayName(ChatColor.GREEN + "Bank Konto erstellen");

		Chest.setItemMeta(chestmeta);

		ItemStack Chest1 = new ItemStack(Material.CHEST);

		ItemMeta chestmeta1 = Chest1.getItemMeta();

		chestmeta1.setDisplayName(ChatColor.DARK_AQUA + "Kommt Bald...");

		Chest.setItemMeta(chestmeta1);

		ItemStack Chest2 = new ItemStack(Material.CHEST);

		ItemMeta chestmeta2 = Chest2.getItemMeta();

		chestmeta2.setDisplayName(ChatColor.DARK_AQUA + "Kommt Bald...");

		Chest.setItemMeta(chestmeta);
		Chest1.setItemMeta(chestmeta1);
		Chest2.setItemMeta(chestmeta2);

		inv.setItem(1, Chest);
		inv.setItem(4, Chest1);
		inv.setItem(7, Chest2);

		p.openInventory(inv);

	}

}
