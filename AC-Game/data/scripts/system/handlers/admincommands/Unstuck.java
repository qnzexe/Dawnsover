package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * @author Nemiroff Date: 11.01.2010
 */
public class Unstuck extends AdminCommand {

	public Unstuck() {
		super("unstuck");
	}

	@Override
	public void execute(Player admin, String... params) {
		
		Player player = World.getInstance().findPlayer(Util.convertName(params[0]));
		
		if (player.getLifeStats().isAlreadyDead()) {
			PacketSendUtility.sendMessage(admin, "Player is dead.");
			return;
		}
		if (player.isInPrison()) {
			PacketSendUtility.sendMessage(admin, "Player is in prison.");
			return;
		}

		TeleportService2.moveToBindLocation(player, true);
	}

	@Override
	public void onFail(Player admin, String message) {
		// TODO Auto-generated method stub
	}
}
