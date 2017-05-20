package de.moneysystem.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.potion.PotionEffectType;

import de.moneysystem.Main;
import de.moneysystem.user.UserGeldBörse;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("money")) {

			if (args.length == 0) {

				p.sendMessage(ChatColor.GREEN + "[Beutel] Du hast " + ChatColor.GOLD + UserGeldBörse.getGeldbörse(p) + "$");
				return true;

			}

		}
		if (p.isOp()) {

			if (cmd.getName().equalsIgnoreCase("bank")) {

				if (args.length == 2) {

					if (args[0].equalsIgnoreCase("set")) {

						if (args[1].equalsIgnoreCase("bank")) {

							try {

								Profession profession = Profession.valueOf((String) Main.getInstance().getConfig().get("Bank.VillagerType"));
								Villager v = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
								v.setProfession(profession);

								v.setCustomName("Bank");
								v.setCustomNameVisible(true);

								v.setCanPickupItems(false);

								v.addPotionEffect(PotionEffectType.SLOW.createEffect(Integer.MAX_VALUE, 100));

								p.sendMessage("§a[Bank] Ein Bänker wurde gesetzt!");
								return true;

							} catch (Exception e) {
								p.sendMessage("§a[Bank] §4ERROR: §cBitte überprüfe deine Eingabe in der §6config.yml\n");
								p.sendMessage("§f[INFO] §6Pfad: §cBank/VillagerType");
								return true;
							}
						}

						if (args[1].equalsIgnoreCase("bankexperte")) {
							try {
								Profession profession = Profession.valueOf((String) Main.getInstance().getConfig().get("Bankexperte.VillagerType"));
								Villager v = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
								v.setProfession(profession);

								v.setCustomName("Bankexperte");
								v.setCustomNameVisible(true);

								v.setCanPickupItems(false);

								v.addPotionEffect(PotionEffectType.SLOW.createEffect(Integer.MAX_VALUE, 100));

								p.sendMessage("§a[Bank] Ein Bankexperte wurde gesetzt!");
								return true;

							} catch (Exception e) {
								p.sendMessage("§a[Bank] §4ERROR: §cBitte überprüfe deine Eingabe in der §6config.yml\n");
								p.sendMessage("§f[INFO] §6Pfad: §cBankexperte/VillagerType");
								return true;
							}

						}
					}
				}
			}
		}

		return false;
	}

}