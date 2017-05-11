package de.moneysystem;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.plugin.java.JavaPlugin;

import de.moneysystem.bank.Bank;
import de.moneysystem.bank.UserKontoBank;
import de.moneysystem.cmd.Commands;
import de.moneysystem.user.UserGeldBörse;


public class Main extends JavaPlugin{
	
	private static Main main;

	@Override
	public void onEnable() {
		
		main = this;
		
		registerEvents();
		registerCommands();
		
		loadFiles();
		
		System.out.println("[MoneySystem] Erfolgereich gestartet!");
		
	}
	
	@Override
	public void onDisable() {
		
		System.out.println("[MoneySystem] Datenbank wird gestoppt!");
		System.out.println("[MoneySystem] Datenbank wurde gestoppt!");
		
	}
	
	public void loadFiles() {
		
		createConfig();
		
		if (UserKontoBank.bankfile.exists() && UserGeldBörse.usergeldfile.exists()) {
			
			System.out.println("[MoneySystem] User-Daten wurde geladen!");
			
		} else {
			
			UserKontoBank.safekontocfg();
			
			System.out.println("[MoneySystem] Geld-Dateien wurden erstellt!");
			
		}
		
	}
	
	public void registerEvents() {
		
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new Bank(), this);
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
		main.getConfig().options().copyDefaults(true);
		main.saveConfig();
		
	}
	
}
