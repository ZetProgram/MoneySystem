package de.moneysystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.moneysystem.bank.Bank;
import de.moneysystem.bank.UserKontoBank;
import de.moneysystem.user.UserGeldBörse;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!UserKontoBank.bankcfg.contains(p.getUniqueId() + ".Name")) {
			Bank.addKonto(p.getUniqueId(), p);
		}
		if (p.getName() != UserKontoBank.bankcfg.get(p.getUniqueId() + ".Name")) {
			UserKontoBank.bankcfg.set(p.getUniqueId() + ".Name", p.getName());
			UserKontoBank.safekontocfg();
		}
		if (!UserGeldBörse.usergeldcfg.contains(p.getUniqueId() + ".Name")) {
			UserGeldBörse.addUserBörse(p);
		}
		if (p.getName() != UserGeldBörse.usergeldcfg.get(p.getUniqueId() + ".Name")) {
			UserGeldBörse.usergeldcfg.set(p.getUniqueId() + ".Name", p.getName());
			UserGeldBörse.safegeldcfg();
		}
		p.sendMessage("§a[Bank] Wir haben deine Daten geladen!\n");
		p.sendMessage("§a[Bank] Beutel: §6" + UserGeldBörse.getGeldbörse(p) + " §a| Bankkonto: §6" + Bank.getKontoGeld(p));
	}

}
