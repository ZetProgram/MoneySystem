package de.moneysystem;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.plugin.java.JavaPlugin;

import de.moneysystem.bank.Bank;
import de.moneysystem.bank.Bankexperte;
import de.moneysystem.bank.UserKontoBank;
import de.moneysystem.cmd.Commands;
import de.moneysystem.user.UserGeldBörse;

public class Main extends JavaPlugin {

	private static Main main;

	@Override
	public void onEnable() {

		main = this;

		registerEvents();
		registerCommands();
		
		createConfig();
		
		// MySQL
		CheckMySQL();
		if (getConfig().getString("source").equals("sql")) {
			MySQL.connect();
		} else if (getConfig().getString("source").equals("file")) {
			loadFiles();
		}

		System.out.println("[MoneySystem] Erfolgereich gestartet!");

	}

	@Override
	public void onDisable() {
		
		if (MySQL.isConnected()) {
			MySQL.close();
		} else {
			return;
		}
		
		System.out.println("[MoneySystem] Datenbank wurde gestoppt!");

	}

	public void loadFiles() {



		if (UserKontoBank.bankfile.exists() && UserGeldBörse.usergeldfile.exists()) {

			System.out.println("[MoneySystem] User-Daten wurde geladen!");

		} else {

			UserKontoBank.safekontocfg();
			UserGeldBörse.safegeldcfg();

			System.out.println("[MoneySystem] Geld-Dateien wurden erstellt!");

		}

	}

	public void registerEvents() {

		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new Bank(), this);
		getServer().getPluginManager().registerEvents(new Bankexperte(), this);
	}

	public void registerCommands() {

		getCommand("money").setExecutor(new Commands());
		getCommand("bank").setExecutor(new Commands());

	}

	public static Main getInstance() {

		return Main.main;

	}

	public static File getBankFile() {

		return UserKontoBank.bankfile;

	}

	public static YamlConfiguration getBankcfg() {

		return UserKontoBank.bankcfg;

	}

	public static File getUserGeldFile() {

		return UserGeldBörse.usergeldfile;

	}

	public static YamlConfiguration getUserGeldcfg() {

		return UserGeldBörse.usergeldcfg;

	}

	public static void createConfig() {

		main.reloadConfig();
		main.getConfig().options().header("Config des Money-System | Version: " + Main.getInstance().getDescription().getVersion());
		main.getConfig().addDefault("Bank.VillagerType", Profession.PRIEST.toString());
		main.getConfig().addDefault("Bankexperte.VillagerType", Profession.PRIEST.toString());
		main.getConfig().addDefault("source", "file");
		main.getConfig().options().copyDefaults(true);
		main.saveConfig();

	}

	public static void CheckMySQL() {
		File file = new File("plugins/MoneySystem", "config.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (cfg.getString("source").equals("sql")) {

			cfg.addDefault("MySQL.username", "root");
			cfg.addDefault("MySQL.password", "password");
			cfg.addDefault("MySQL.database", "localhost");
			cfg.addDefault("MySQL.host", "localhost");
			cfg.addDefault("MySQL.port", "3306");
			cfg.options().copyDefaults(true);

			try {
				cfg.save(file);

			} catch (IOException e) {
				e.printStackTrace();
			}
			readMySQL();

		} else return;
	}

	public static void readMySQL() {
		FileConfiguration cfg = main.getConfig();
		MySQL.username = cfg.getString("MySQL.username");
		MySQL.password = cfg.getString("MySQL.password");
		MySQL.database = cfg.getString("MySQL.database");
		MySQL.host = cfg.getString("MySQL.host");
		MySQL.port = cfg.getString("MySQL.port");
	}

}
