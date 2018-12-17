package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class Giveap  extends AdminCommand{

	public Giveap() {
		super("giveap");
	}

	@Override
	public void execute(Player player, String... params) {

		if (params.length < 2) {
			onFail(player, null);
			return;
		}

		if (player.getAbyssRank().getRank().getId() < 14) {
			PacketSendUtility.sendMessage(player, "You must be 5 Stars Officer or higher to use this command.");
			return ;
		}
		
		Player receiver = World.getInstance().findPlayer(Util.convertName(params[0]));
		if (receiver == null) {
			PacketSendUtility.sendMessage(player, params[0] + " must be online.");
			return;
		}

		int amount = 0;
		try {
			amount = Integer.parseInt(params[1]);
		}
		catch (NumberFormatException ex) {
			PacketSendUtility.sendMessage(player, "Incorrect number.");
			return;
		}
		
		if (player.getAbyssRank().getAp() < amount) {
			PacketSendUtility.sendMessage(player, "You do not have enough ap.");
			return ;
		}
		
		AbyssPointsService.addAp(player, -amount);
		PacketSendUtility.sendMessage(player, amount 
				+ " Abyss Points were sent to "+ receiver.getName() +".");
		AbyssPointsService.addAp(receiver, amount);
		PacketSendUtility.sendMessage(receiver, amount 
				+ " Abyss Points received from "+ player.getName() +" !");
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Syntax : //giveap <player> <amount>");
	}
}