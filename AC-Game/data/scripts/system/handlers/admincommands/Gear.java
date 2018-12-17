package admincommands;

import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Angry-Cub
 */
public class Gear extends AdminCommand {

	public Gear() {
		super("gear");
	}

	@Override
	public void execute(Player player, String... params) {
		
		if (params.length <= 0) {
			showHelp(player);
		}
		else if (params[0].equals("switch_weapon")) {
			action_switchWeapon(player);
			PacketSendUtility.sendMessage(player, "The weapon has been changed.");
		}
	}

	private void showHelp(Player player) {
		PacketSendUtility.sendMessage(player, "Usage : //gear switch_weapon");
	}
	
	private void action_switchWeapon(Player player) {
		if (player.getController().hasTask(TaskId.ITEM_USE) && !player.getController().getTask(TaskId.ITEM_USE).isDone()
				|| player.isCasting() ) {

			PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CANT_EQUIP_ITEM_IN_ACTION);
			return;
		} else {
			player.getEquipment().switchHands();
			PacketSendUtility.broadcastPacket(player, 
					new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), player.getEquipment().getEquippedForApparence()), true);	
		}
	}

	@Override
	public void onFail(Player player, String message) {
		showHelp(player);
	}
}
