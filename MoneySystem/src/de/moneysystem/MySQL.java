package de.moneysystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MySQL {

	public static String host;
	public static String port;
	public static String database;
	public static String username;
	public static String password;

	public static Connection con;

	public static void connect() {
		if (!isConnected()) {
			try {

				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MySQL Verbindung aufgebaut!");

			} catch (SQLException e) {

				e.printStackTrace();
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[MySQL] Ein Fehler ist aufgetreten, die MySQL verbindung wurde abgebrochen!");
			}
		}
	}

	public static void close() {
		if (isConnected()) {
			try {

				con.close();
				Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "MySQL Verbindung geschlossen!");

			} catch (SQLException e) {

				e.printStackTrace();

			}
		}
	}

	public static boolean isConnected() {
		return con != null;
	}

	// Nur für hasKonto();
	public static ResultSet KontoQuery(String sql) {

		try {
			return con.createStatement().executeQuery(sql);
		} catch (Exception e) {

		}
		return null;
	}

	public static Double getMySQLBankMoney(Player p) {
		double money = 0;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT Kontostand FROM BankKontos WHERE PlayerName = ?");
			ps.setString(1, p.getName());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				money = rs.getDouble("Kontostand");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return money;

	}

	public static Double getMySQLUserMoney(Player p) {
		double money = 0;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT Money FROM usermoney WHERE PlayerName = ?");
			ps.setString(1, p.getName());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				money = rs.getDouble("Money");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return money;

	}

	public static void createKonto(UUID id) {

		/*
		 * 
		 * Syntax: PlayerName, KontoGeld
		 * 
		 */
		Player p = Bukkit.getPlayer(id);
		if (isConnected()) {
			try {
				double kontostandnull = 0;
				con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS BankKontos (PlayerName VARCHAR(100), UUID VARCHAR(100), Kontostand VARCHAR(100))");

				con.prepareStatement("INSERT INTO BankKontos (PlayerName, UUID, Kontostand) VALUES ('" + p.getName() + "','" + p.getUniqueId() + "','" + kontostandnull + "')").executeUpdate();

				p.sendMessage(ChatColor.GREEN + "[Bank] Dein Antrag wurde angenommen!");

			} catch (SQLException e) {
				e.printStackTrace();

				p.sendMessage(ChatColor.RED + "Ein Fehler ist aufgetreten, Benachrichtige bitte ein Administrator!!");
			}
		}
	}
	
	public static boolean hasKonto(UUID uniqueId) {
		Boolean haskonto = false;
		Player p = Bukkit.getPlayer(uniqueId);
		if (Main.getInstance().getConfig().getString("source").equals("file")) {

			haskonto = true;

		}

		if (Main.getInstance().getConfig().getString("source").equals("sql")) {
			if (isConnected()) {

				try {
					con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS BankKontos (PlayerName VARCHAR(100), UUID VARCHAR(100), Kontostand VARCHAR(100))");

					ResultSet s = con.createStatement().executeQuery("SELECT * FROM BankKontos");
					if (s.next()) {
						haskonto = true;
					} else {
						haskonto = false;
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return haskonto;
	}
	
	public static boolean hasBeutel(UUID uniqueId) {
		Boolean haskonto = false;
		Player p = Bukkit.getPlayer(uniqueId);
		if (Main.getInstance().getConfig().getString("source").equals("file")) {

			haskonto = true;

		}

		if (Main.getInstance().getConfig().getString("source").equals("sql")) {
			if (isConnected()) {

				try {
					con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS usermoney (PlayerName VARCHAR(100), UUID VARCHAR(100), Money VARCHAR(100))");

					ResultSet s = con.createStatement().executeQuery("SELECT * FROM usermoney");
					if (s.next()) {
						haskonto = false;
					} else {
						haskonto = true;
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return haskonto;
	}
}
