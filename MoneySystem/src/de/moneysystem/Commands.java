package de.moneysystem;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.potion.PotionEffectType;

import de.moneysystem.user.UserGeldBörse;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("money")) {

			if (args.length == 0) {
				p.sendMessage("§a[Guthaben] Du hast §6" + UserGeldBörse.getGeldbörse(p) + "$");
				return true;
			}

			// if (args.length == 3) {
			// if (args[0].equalsIgnoreCase("add")) {
			// if (PermissionsEx.getUser(p).getPermissions("money.add") != null)
			// {
			// Player target = Bukkit.getPlayerExact(args[1]);
			//
			// int money = Integer.parseInt(args[2]);
			//
			// UserGeldBörse.addGeld(money, target);
			// UserGeldBörse.safegeldcfg();
			//
			// p.sendMessage("§a[Bank]"+ "§6" + money + "$" + "§a wurden
			// überwiesen");
			// return true;
			// }
			// }
			// }
		}

		if (cmd.getName().equalsIgnoreCase("bank")) {

			if (args.length == 1) {

				if (args[0].equalsIgnoreCase("set")) {

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

			}

		}

		return false;
	}

}