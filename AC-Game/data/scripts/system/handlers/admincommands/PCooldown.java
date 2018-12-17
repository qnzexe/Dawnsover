package admincommands;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Cura
 */
public class PCooldown extends AdminCommand {

	public PCooldown() {
		super("pcooldown");
	}

	@Override
	public void execute(Player admin, String... params) {
		Player target = null;
		VisibleObject creature = admin.getTarget();
		
		if (creature == null) {
			PacketSendUtility.sendMessage(admin, "You should select a target first!");
			return;
		}
		
		if(creature instanceof Player) {
			target = (Player) creature;
		if (target.isCoolDownZero()) {
			PacketSendUtility.sendMessage(admin, creature.getName() + "'s Cooldown time of all skills has been recovered.");
			target.setCoolDownZero(false);
		}
		else {
			PacketSendUtility.sendMessage(admin, creature.getName() + "'s Cooldown time of all skills is set to 0.");
			target.setCoolDownZero(true);
		}}
	}

	@Override
	public void onFail(Player admin, String message) {
		// TODO Auto-generated method stub
	}
}
