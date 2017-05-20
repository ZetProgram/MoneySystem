package de.moneysystem.user;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.moneysystem.Main;
import de.moneysystem.MySQL;

public class UserGeldBörse {

	public static File usergeldfile = new File("plugins/MoneySystem/user", "userGeldDaten.yml");
	public static YamlConfiguration usergeldcfg = YamlConfiguration.loadConfiguration(usergeldfile);

	public static void safegeldcfg() {

		try {
			usergeldcfg.save(usergeldfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void addUserBörse(Player p) {
		if (Main.getInstance().getConfig().getString("source").equals("file")) {
			UserGeldBörse.usergeldcfg.set(p.getUniqueId() + ".Name", p.getName());
			UserGeldBörse.usergeldcfg.set(p.getUniqueId() + ".Money", 500.00);
			UserGeldBörse.usergeldcfg.options().copyDefaults(true);
			UserGeldBörse.safegeldcfg();
		}
		if (Main.getInstance().getConfig().getString("source").equals("sql")) {
			double kontostand = 500;
			try {
				MySQL.con.prepareStatement("INSERT INTO usermoney (PlayerName, UUID, Money) VALUES ('" + p.getName() + "','" + p.getUniqueId() + "','" + kontostand + "')").executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public static Double getGeldbörse(Player p) {
		double getGeld = -1;
		if (Main.getInstance().getConfig().getString("source").equals("file")) {
			getGeld = usergeldcfg.getDouble(p.getUniqueId() + ".Money");
		}
		if (Main.getInstance().getConfig().getString("source").equals("sql")) {
			try {

				PreparedStatement ps = MySQL.con.prepareStatement("SELECT Money FROM usermoney WHERE PlayerName = ?");
				ps.setString(1, p.getName());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					getGeld = rs.getDouble("Money");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return getGeld;
	}

	public static void addGeld(double money, Player p) {
		if (Main.getInstance().getConfig().getString("source").equals("file")) {
			double hatmoney = Main.getUserGeldcfg().getInt(p.getUniqueId() + ".Money");
			double endmoney = hatmoney + money;
			Main.getUserGeldcfg().set(p.getUniqueId() + ".Money", endmoney);
			safegeldcfg();
		}
		if (Main.getInstance().getConfig().getString("source").equals("sql")) {

			double hatmoney = MySQL.getMySQLUserMoney(p);
			double endmoney = hatmoney + money;

			try {
				MySQL.con.prepareStatement("UPDATE usermoney SET Money = '" + endmoney + "'").executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public static void removeGeld(int money, Player p) {

		if (Main.getInstance().getConfig().getString("source").equals("file")) {
			double hatmoney = Main.getUserGeldcfg().getInt(p.getUniqueId() + ".Money");
			double endmoney = hatmoney - money;
			Main.getUserGeldcfg().set(p.getUniqueId() + ".Money", endmoney);
			safegeldcfg();
		}
		if (Main.getInstance().getConfig().getString("source").equals("sql")) {

			double hatmoney = MySQL.getMySQLUserMoney(p);
			double endmoney = hatmoney - money;

			try {

				MySQL.con.prepareStatement("UPDATE usermoney SET Money = '" + endmoney + "'").executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
