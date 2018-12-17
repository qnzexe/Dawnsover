package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * @author Elusive
 */
public class FlushPlayerOut extends AdminCommand {

	public FlushPlayerOut() {
		super("flushplayerout");
	}

	@Override
	public void execute(Player player, String... params) {
		if (params.length < 1) {
			PacketSendUtility.sendMessage(player, "syntax //flushplayerout <character_name>");
			return;
		}
		Player toFlush = World.getInstance().findPlayer(params[0]);
		if (player != null) {
			World.getInstance().removeObject(toFlush);
			PacketSendUtility.sendMessage(player, "Flushed player : " + params[0]);
		} else {
			PacketSendUtility.sendMessage(player, "Couldn't find player : " + params[0]);
		}
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "syntax //flushplayerout <character_name>");
	}
}
