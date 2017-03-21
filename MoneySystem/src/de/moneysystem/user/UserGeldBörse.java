package de.moneysystem.user;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
		
		UserGeldBörse.usergeldcfg.set(p.getUniqueId() + ".Name", p.getName());
		UserGeldBörse.usergeldcfg.set(p.getUniqueId() + ".Money", 500.00);
		UserGeldBörse.usergeldcfg.options().copyDefaults(true);
		UserGeldBörse.safegeldcfg();

	}
	
	public static Integer getGeldbörse(Player p) {
		
		return usergeldcfg.getInt(p.getUniqueId() + ".Money" );
		
	}
	
	public static void addGeld(int money, Player p) {
		
		int hatmoney = usergeldcfg.getInt(p.getUniqueId() + ".Money");
		int endmoney = hatmoney + money;
		usergeldcfg.set(p.getUniqueId() + ".Money", endmoney);
		safegeldcfg();
		
	}
	
	public static void removeGeld(int money, Player p) {
		
		int hatmoney = usergeldcfg.getInt(p.getUniqueId() + ".Money");
		int endmoney = hatmoney - money;
		usergeldcfg.set(p.getUniqueId() + ".Money", endmoney);
		safegeldcfg();
	}
	
}
