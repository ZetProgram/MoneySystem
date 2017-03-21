package de.moneysystem.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.moneysystem.Main;
import de.moneysystem.user.UserGeldBörse;
import net.minecraft.server.v1_11_R1.EntityEvoker.e;

public class Bank implements Listener {

	int money;

	public static void addMoney(int money, Player p) {

		int hatmoney = Main.getBankcfg().getInt(p.getUniqueId() + ".Money");
		int endmoney = hatmoney + money;
		Main.getBankcfg().set(p.getUniqueId() + ".Money", endmoney);
		UserKontoBank.safekontocfg();

	}

	public static void addKonto(UUID uuid, Player p) {

		UserKontoBank.bankcfg.set(p.getUniqueId() + ".Name", p.getName());
		UserKontoBank.bankcfg.set(p.getUniqueId() + ".Money", 500.00);
		UserKontoBank.bankcfg.options().copyDefaults(true);
		UserKontoBank.safekontocfg();

	}

	public static Integer getKontoGeld(Player p) {

		return UserKontoBank.bankcfg.getInt(p.getUniqueId() + ".Money");

	}

	@EventHandler
	public void onPlayer(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked().getType() == EntityType.VILLAGER) {
			Villager v = (Villager) e.getRightClicked();

			if (v.getCustomName().equals("Bank")) {
				e.setCancelled(true);

				setInventory(p);

			}
		}

	}

	@EventHandler
	public void onVillagerKill(EntityDamageEvent e) {

		if (e.getEntityType() == EntityType.VILLAGER) {

			if (e.getEntity().getName() == "Bank") {
				e.setCancelled(true);
			}

		}
	}

	@EventHandler
	public void onKlick(InventoryClickEvent e) {

		Player p = (Player) e.getWhoClicked();

		if (e.getCurrentItem() != null && e.getClickedInventory() != null) {
			if (e.getClickedInventory().getName().equals("§2Bank")) {

				e.setCancelled(true);

				int userbeutel = UserGeldBörse.getGeldbörse(p);

				int userbank = UserKontoBank.getBankGeld(p);

				if (e.isLeftClick()) {

					if (e.getSlot() == 2) {

						if (userbeutel >= 100) {

							UserGeldBörse.removeGeld(100, p);
							UserKontoBank.addGeld(100, p);

							setInventory(p);

							p.sendMessage("§a[Bank] Du hast erfolgreich dein Geld überwiesen!");

						} else {

							p.sendMessage("§a[Bank]§c Du hast nicht genug Geld in deinem Beutel!");

						}

					}

					if (e.getSlot() == 4) {

						if (userbeutel >= 200) {

							UserGeldBörse.removeGeld(200, p);
							UserKontoBank.addGeld(200, p);

							setInventory(p);

							p.sendMessage("§a[Bank] Du hast erfolgreich dein Geld überwiesen!");

						} else {

							p.sendMessage("§a[Bank]§c Du hast nicht genug Geld in deinem Beutel!");
						}

					}

					if (e.getSlot() == 6) {

						if (userbeutel >= 500) {

							UserGeldBörse.removeGeld(500, p);
							UserKontoBank.addGeld(500, p);

							setInventory(p);

							p.sendMessage("§a[Bank] Du hast erfolgreich dein Geld überwiesen!");

						} else {

							p.sendMessage("§a[Bank]§c Du hast nicht genug Geld in deinem Beutel!");

						}

					}
				}
				if (e.isRightClick()) {

					if (e.getSlot() == 2) {

						if (userbank >= 100) {

							UserKontoBank.removeGeld(100, p);
							UserGeldBörse.addGeld(100, p);

							setInventory(p);

							p.sendMessage("§a[Bank] Du hast erfolgreich dein Geld überwiesen!");

						} else {

							p.sendMessage("§a[Bank]§c Du hast nicht genug Geld in deinem Beutel!");

						}

					}

					if (e.getSlot() == 4) {

						if (userbank >= 200) {

							UserKontoBank.removeGeld(200, p);
							UserGeldBörse.addGeld(200, p);

							setInventory(p);

							p.sendMessage("§a[Bank] Du hast erfolgreich dein Geld überwiesen!");

						} else {

							p.sendMessage("§a[Bank]§c Du hast nicht genug Geld in deinem Beutel!");

						}

					}

					if (e.getSlot() == 6) {

						if (userbank >= 500) {

							UserKontoBank.removeGeld(500, p);
							UserGeldBörse.addGeld(500, p);

							setInventory(p);

							p.sendMessage("§a[Bank] Du hast erfolgreich dein Geld überwiesen!");

						} else {

							p.sendMessage("§a[Bank]§c Du hast nicht genug Geld in deinem Beutel!");

						}

					}
				}

			}
		}
	}

	public void setInventory(Player p) {

		Inventory inv = p.getServer().createInventory(null, 9, "§2Bank");

		List<String> golddescription1 = new ArrayList<>();
		golddescription1.add("§d100$ §a von deinem Geldbeutel überweisen §6Linkklicken!");
		golddescription1.add("§d100$ §a von deinem Konto überweisen §6Rechtsklicken!");

		List<String> golddescription2 = new ArrayList<>();
		golddescription2.add("§d200$ §a von deinem Geldbeutel überweisen §6Linkklicken!");
		golddescription2.add("§d200$ §a von deinem Konto überweisen §6Rechtsklicken!");

		List<String> golddescription3 = new ArrayList<>();
		golddescription3.add("§d500$ §a von deinem Geldbeutel überweisen §6Linkklicken!");
		golddescription3.add("§d500$ §a von deinem Konto überweisen §6Rechtsklicken!");

		ItemStack Gold1 = new ItemStack(Material.GOLD_INGOT);

		ItemMeta goldmeta1 = Gold1.getItemMeta();
		goldmeta1.setDisplayName("§a100$");

		goldmeta1.setLore(golddescription1);

		Gold1.setItemMeta(goldmeta1);

		ItemStack Gold2 = new ItemStack(Material.GOLD_INGOT);

		ItemMeta goldmeta2 = Gold2.getItemMeta();
		goldmeta2.setDisplayName("§a200$");

		goldmeta2.setLore(golddescription2);

		Gold2.setItemMeta(goldmeta2);

		ItemStack Gold3 = new ItemStack(Material.GOLD_INGOT);

		ItemMeta goldmeta3 = Gold3.getItemMeta();
		goldmeta3.setDisplayName("§a500$");

		goldmeta3.setLore(golddescription3);

		Gold3.setItemMeta(goldmeta3);

		ItemStack beutel = new ItemStack(Material.CHEST);

		ItemMeta beutelmeta = beutel.getItemMeta();

		beutelmeta.setDisplayName("§aBeutel: §c" + UserGeldBörse.getGeldbörse(p) + "$");

		beutel.setItemMeta(beutelmeta);

		ItemStack bank = new ItemStack(Material.CHEST);

		ItemMeta bankmeta = bank.getItemMeta();

		bankmeta.setDisplayName("§aBank: §c" + UserKontoBank.getBankGeld(p) + "$");

		bank.setItemMeta(bankmeta);

		inv.setItem(0, beutel);
		inv.setItem(2, Gold1);
		inv.setItem(4, Gold2);
		inv.setItem(6, Gold3);
		inv.setItem(8, bank);

		p.openInventory(inv);

	}

}
