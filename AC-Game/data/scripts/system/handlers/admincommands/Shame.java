package admincommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Shame extends AdminCommand {
	
	public Shame() {
		super("shame");
	}

	@Override
	public void execute(Player admin, String... params) {
		if (params == null || params.length < 1) {
			PacketSendUtility.sendMessage(admin, "Syntax: //shame <player> [reason]");
			return;
		}

		String name = Util.convertName(params[0]);
		if (!DAOManager.getDAO(PlayerDAO.class).isNameUsed(name)) {
			PacketSendUtility.sendMessage(admin, "Player " + name + " was not found !");
			PacketSendUtility.sendMessage(admin, "Syntax: //shame <player> [reason]");
			return;
		}
		// The commands goes into the logs natively, just use grep
		PacketSendUtility.sendMessage(admin, "Your report has been submitted.");
	}
}
