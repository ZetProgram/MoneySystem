package de.moneysystem.bank;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.moneysystem.Main;
import de.moneysystem.MySQL;

public class UserKontoBank {

	public static File bankfile = new File("plugins/MoneySystem/bank", "Bankkontos.yml");
	public static YamlConfiguration bankcfg = YamlConfiguration.loadConfiguration(bankfile);

	public static void safekontocfg() {

		try {
			bankcfg.save(bankfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Double getKontoGeld(Player p) {

		double money = 0.0;

		if (Main.getInstance().getConfig().getString("source").equals("file")) {

			money = UserKontoBank.bankcfg.getDouble(p.getUniqueId() + ".Money");

		}
		if (Main.getInstance().getConfig().getString("source").equals("sql")) {

			money = MySQL.getMySQLBankMoney(p);

		}
		return money;

	}

	public static void addGeld(int money, Player p) {

		if (Main.getInstance().getConfig().getString("source").equals("file")) {
			double hatmoney = Main.getBankcfg().getInt(p.getUniqueId() + ".Money");
			double endmoney = hatmoney + money;
			Main.getBankcfg().set(p.getUniqueId() + ".Money", endmoney);
			UserKontoBank.safekontocfg();
		}
		if (Main.getInstance().getConfig().getString("source").equals("sql")) {

			double hatmoney = MySQL.getMySQLBankMoney(p);
			double endmoney = hatmoney + money;

			try {

				MySQL.con.prepareStatement("UPDATE BankKontos SET Kontostand = '" + endmoney + "'").executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public static void removeGeld(int money, Player p) {

		if (Main.getInstance().getConfig().getString("source").equals("file")) {
			double hatmoney = Main.getBankcfg().getInt(p.getUniqueId() + ".Money");
			double endmoney = hatmoney - money;
			Main.getBankcfg().set(p.getUniqueId() + ".Money", endmoney);
			UserKontoBank.safekontocfg();
		}
		if (Main.getInstance().getConfig().getString("source").equals("sql")) {

			double hatmoney = MySQL.getMySQLBankMoney(p);
			double endmoney = hatmoney - money;

			try {

				MySQL.con.prepareStatement("UPDATE BankKontos SET Kontostand = '" + endmoney + "'").executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
