package de.moneysystem;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.moneysystem.bank.UserKontoBank;
import de.moneysystem.user.UserGeldB�rse;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (Main.getInstance().getConfig().getString("source").equals("file")) {
			if (!UserGeldB�rse.usergeldcfg.contains(p.getUniqueId() + ".Name")) {
				UserGeldB�rse.addUserB�rse(p);
			}

			if (p.getName() != UserGeldB�rse.usergeldcfg.get(p.getUniqueId() + ".Name")) {
				UserGeldB�rse.usergeldcfg.set(p.getUniqueId() + ".Name", p.getName());
				UserGeldB�rse.safegeldcfg();
			}
		}

		if (Main.getInstance().getConfig().getString("source").equals("sql")) {

			try {
				MySQL.con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS usermoney (PlayerName VARCHAR(100), UUID VARCHAR(100), Money VARCHAR(100))");
				if (MySQL.hasBeutel(p.getUniqueId())) {
					UserGeldB�rse.addUserB�rse(p);
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}

		p.sendMessage("�a[Bank] Wir haben deine Daten analyziert und geladen!\n");

		if (!MySQL.hasKonto(p.getUniqueId())) {

			if (p.getName() != UserKontoBank.bankcfg.get(p.getUniqueId() + ".Name")) {
				UserKontoBank.bankcfg.set(p.getUniqueId() + ".Name", p.getName());
				UserKontoBank.safekontocfg();
			}

			p.sendMessage("�a[Bank] Beutel: �6" + UserGeldB�rse.getGeldb�rse(p) + " �a| Bankkonto: �6" + UserKontoBank.getKontoGeld(p));

		} else {

			p.sendMessage("�a[Bank] Beutel: �6" + UserGeldB�rse.getGeldb�rse(p));

		}
	}

}
