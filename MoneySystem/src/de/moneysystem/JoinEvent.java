package de.moneysystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.moneysystem.bank.Bank;
import de.moneysystem.bank.UserKontoBank;
import de.moneysystem.user.UserGeldB�rse;

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
		if (!UserGeldB�rse.usergeldcfg.contains(p.getUniqueId() + ".Name")) {
			UserGeldB�rse.addUserB�rse(p);
		}
		if (p.getName() != UserGeldB�rse.usergeldcfg.get(p.getUniqueId() + ".Name")) {
			UserGeldB�rse.usergeldcfg.set(p.getUniqueId() + ".Name", p.getName());
			UserGeldB�rse.safegeldcfg();
		}
		p.sendMessage("�a[Bank] Wir haben deine Daten geladen!\n");
		p.sendMessage("�a[Bank] Beutel: �6" + UserGeldB�rse.getGeldb�rse(p) + " �a| Bankkonto: �6" + Bank.getKontoGeld(p));
	}

}
