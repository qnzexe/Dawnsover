package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Angry-Cub
 */
public class ToggleTag extends AdminCommand {

	public ToggleTag() {
		super("toggleTag");
	}

	@Override
	public void execute(Player player, String... params) {
		player.toggleTag(!player.isTagEnabled());
		// Refresh name's display (lazy mode)
		TeleportService2.teleportTo(player, player.getPosition());
	}

	private void showHelp(Player player) {
		PacketSendUtility.sendMessage(player, "Usage : //toggleTag");
	}

	@Override
	public void onFail(Player player, String message) {
		showHelp(player);
	}
}
