package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;

/**
 * @author Andy
 * @author Divinity - update
 * @author Exe - update
 */
public class pinvul extends AdminCommand {

	public pinvul() {
		super("pinvul");
	}

	@Override
	public void execute(Player admin, String... params) {
		Player target = null;
		VisibleObject creature = admin.getTarget();
		
		if (creature == null) {
			PacketSendUtility.sendMessage(admin, "You should select a target first!");
			return; }
		
		
		if(creature instanceof Player) {
			target = (Player) creature;
		if (target.isInvul()) {
			target.setInvul(false);
			PacketSendUtility.sendMessage(admin, creature.getName() + " is now mortal.");
		}
		else {
			target.setInvul(true);
			PacketSendUtility.sendMessage(admin, creature.getName() + " is now immortal.");
		}}
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
