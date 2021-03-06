package de.moneysystem.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.moneysystem.MySQL;
import de.moneysystem.user.UserGeldB�rse;

public class Bank implements Listener {

	int money;



	public static void addKonto(UUID uuid, Player p) {

		UserKontoBank.bankcfg.set(p.getUniqueId() + ".Name", p.getName());
		UserKontoBank.bankcfg.options().copyDefaults(true);
		UserKontoBank.safekontocfg();

	}

	@EventHandler
	public void onPlayer(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked().getType() == EntityType.VILLAGER) {
			Villager v = (Villager) e.getRightClicked();

			if (v.getCustomName().equals("Bank")) {
				e.setCancelled(true);
				if (MySQL.hasKonto(p.getUniqueId())) {

					setInventory(p);

				} else {

					e.setCancelled(true);
					p.closeInventory();
					p.sendMessage(ChatColor.RED + "Du besitzt noch kein Bank Konto, erstelle dir eins bei dem Bankexperten!");

				}
			}

		}

	}

	@EventHandler
	public void onVillagerKill(EntityDamageByEntityEvent e) {
		Player p = (Player) e.getDamager();
		if (e.getEntityType() == EntityType.VILLAGER) {
			if (p.isOp()) {

				if (e.getEntity().getName().equals("Bank")) {
					e.setCancelled(false);
				}
			} else {

				if (e.getEntity().getName().equals("Bank")) {
					e.setCancelled(true);
				}
			}
		} else {
			e.setCancelled(false);
		}
	}

	@EventHandler
	public void onKlick(InventoryClickEvent e) {

		Player p = (Player) e.getWhoClicked();

		if (e.getCurrentItem() != null && e.getClickedInventory() != null) {

			if (e.getClickedInventory().getName().equals("�2Bank")) {
				e.setCancelled(true);
				double userbeutel = UserGeldB�rse.getGeldb�rse(p);

				double userbank = UserKontoBank.getKontoGeld(p);

				if (e.isLeftClick()) {

					if (e.getSlot() == 2) {

						if (userbeutel >= 100) {

							UserGeldB�rse.removeGeld(100, p);
							UserKontoBank.addGeld(100, p);

							setInventory(p);

							p.sendMessage("�a[Bank] Du hast erfolgreich dein Geld �berwiesen!");

						} else {

							p.sendMessage("�a[Bank]�c Du hast nicht genug Geld in deinem Beutel!");

						}

					}

					if (e.getSlot() == 4) {

						if (userbeutel >= 200) {

							UserGeldB�rse.removeGeld(200, p);
							UserKontoBank.addGeld(200, p);

							setInventory(p);

							p.sendMessage("�a[Bank] Du hast erfolgreich dein Geld �berwiesen!");

						} else {

							p.sendMessage("�a[Bank]�c Du hast nicht genug Geld in deinem Beutel!");
						}

					}

					if (e.getSlot() == 6) {

						if (userbeutel >= 500) {

							UserGeldB�rse.removeGeld(500, p);
							UserKontoBank.addGeld(500, p);

							setInventory(p);

							p.sendMessage("�a[Bank] Du hast erfolgreich dein Geld �berwiesen!");

						} else {

							p.sendMessage("�a[Bank]�c Du hast nicht genug Geld in deinem Beutel!");

						}

					}
				}
				if (e.isRightClick()) {

					if (e.getSlot() == 2) {

						if (userbank >= 100) {

							UserKontoBank.removeGeld(100, p);
							UserGeldB�rse.addGeld(100, p);

							setInventory(p);

							p.sendMessage("�a[Bank] Du hast erfolgreich dein Geld �berwiesen!");

						} else {

							p.sendMessage("�a[Bank]�c Du hast nicht genug Geld in deinem Beutel!");

						}

					}

					if (e.getSlot() == 4) {

						if (userbank >= 200) {

							UserKontoBank.removeGeld(200, p);
							UserGeldB�rse.addGeld(200, p);

							setInventory(p);

							p.sendMessage("�a[Bank] Du hast erfolgreich dein Geld �berwiesen!");

						} else {

							p.sendMessage("�a[Bank]�c Du hast nicht genug Geld in deinem Beutel!");

						}

					}

					if (e.getSlot() == 6) {

						if (userbank >= 500) {

							UserKontoBank.removeGeld(500, p);
							UserGeldB�rse.addGeld(500, p);

							setInventory(p);

							p.sendMessage("�a[Bank] Du hast erfolgreich dein Geld �berwiesen!");

						} else {

							p.sendMessage("�a[Bank]�c Du hast nicht genug Geld in deinem Beutel!");

						}

					}
				}
			}

		}

	}

	private void setInventory(Player p) {

		Inventory inv = p.getServer().createInventory(null, 9, "�2Bank");

		List<String> golddescription1 = new ArrayList<>();
		golddescription1.add("�d100$ �a von deinem Geldbeutel �berweisen �6Linkklicken!");
		golddescription1.add("�d100$ �a von deinem Konto �berweisen �6Rechtsklicken!");

		List<String> golddescription2 = new ArrayList<>();
		golddescription2.add("�d200$ �a von deinem Geldbeutel �berweisen �6Linkklicken!");
		golddescription2.add("�d200$ �a von deinem Konto �berweisen �6Rechtsklicken!");

		List<String> golddescription3 = new ArrayList<>();
		golddescription3.add("�d500$ �a von deinem Geldbeutel �berweisen �6Linkklicken!");
		golddescription3.add("�d500$ �a von deinem Konto �berweisen �6Rechtsklicken!");

		ItemStack Gold1 = new ItemStack(Material.GOLD_INGOT);

		ItemMeta goldmeta1 = Gold1.getItemMeta();
		goldmeta1.setDisplayName("�a100$");

		goldmeta1.setLore(golddescription1);

		Gold1.setItemMeta(goldmeta1);

		ItemStack Gold2 = new ItemStack(Material.GOLD_INGOT);

		ItemMeta goldmeta2 = Gold2.getItemMeta();
		goldmeta2.setDisplayName("�a200$");

		goldmeta2.setLore(golddescription2);

		Gold2.setItemMeta(goldmeta2);

		ItemStack Gold3 = new ItemStack(Material.GOLD_INGOT);

		ItemMeta goldmeta3 = Gold3.getItemMeta();
		goldmeta3.setDisplayName("�a500$");

		goldmeta3.setLore(golddescription3);

		Gold3.setItemMeta(goldmeta3);

		ItemStack beutel = new ItemStack(Material.CHEST);

		ItemMeta beutelmeta = beutel.getItemMeta();

		beutelmeta.setDisplayName("�aBeutel: �c" + UserGeldB�rse.getGeldb�rse(p) + "$");

		beutel.setItemMeta(beutelmeta);

		ItemStack bank = new ItemStack(Material.CHEST);

		ItemMeta bankmeta = bank.getItemMeta();

		bankmeta.setDisplayName("�aBank: �c" + UserKontoBank.getKontoGeld(p) + "$");

		bank.setItemMeta(bankmeta);

		inv.setItem(0, beutel);
		inv.setItem(2, Gold1);
		inv.setItem(4, Gold2);
		inv.setItem(6, Gold3);
		inv.setItem(8, bank);

		p.openInventory(inv);

	}

}
