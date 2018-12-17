package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * player moveto command
 * 
 * @author Exe
 */
public class Recharger extends AdminCommand {

	public Recharger() {
		super("recharger");
	}

	@Override
	public void execute(final Player player, String... params) {

		int	worldId = 300520000;
		int	x = 503;
		int	y = 517;
		int	z = 205;

		if(!player.isCommandInUse()) {
			TeleportService2.teleportTo(player, worldId, x, y, z);
			PacketSendUtility.sendMessage(player, "Teleported to the Recharger Area. You got a 1h CD now.");
			
			if(!player.isGM()) {
			player.setCommandUsed(true);
			
			ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					player.setCommandUsed(false);
				}
			}, 60 * 60 * 1000);
		}}
		else{
			PacketSendUtility.sendMessage(player, "Only 1 TP per hour.");
	}
		
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Syntax: //recharger");
	}}

