package de.moneysystem.bank;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
	
	public static Integer getBankGeld(Player p) {
		
		return bankcfg.getInt(p.getUniqueId() + ".Money" );
		
	}
	
	public static void addGeld(int money, Player p) {
		
		int hatmoney = bankcfg.getInt(p.getUniqueId() + ".Money");
		int endmoney = hatmoney + money;
		bankcfg.set(p.getUniqueId() + ".Money", endmoney);
		safekontocfg();
		
	}
	
	public static void removeGeld(int money, Player p) {
		
		int hatmoney = bankcfg.getInt(p.getUniqueId() + ".Money");
		int endmoney = hatmoney - money;
		bankcfg.set(p.getUniqueId() + ".Money", endmoney);
		safekontocfg();
	}

}
