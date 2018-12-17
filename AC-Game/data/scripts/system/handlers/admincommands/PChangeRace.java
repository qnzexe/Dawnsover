package admincommands;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOTION;


/**
* @author ginho1
* @author Exe - update
*
*/
public class PChangeRace extends AdminCommand {
	public PChangeRace() {
		super("pchangerace");
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
		if(target.getCommonData().getRace() == Race.ELYOS)
			target.getCommonData().setRace(Race.ASMODIANS);
		else
			target.getCommonData().setRace(Race.ELYOS);

		target.clearKnownlist();
		PacketSendUtility.sendPacket(target, new SM_PLAYER_INFO(target, false));
		target.updateKnownlist();
	}
}}
