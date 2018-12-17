package admincommands;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.SkillLearnService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;

/**
 * @author ATracer
 * @author Exe - update
 */
public class PGiveMissingSkills extends AdminCommand {

	public PGiveMissingSkills() {
		super("pgivemissingskills");
	}

	@Override
	public void execute(Player player, String... params) {
		Player target = null;
		VisibleObject creature = player.getTarget();
		
		if (creature == null) {
			PacketSendUtility.sendMessage(player, "You should select a target first!");
			return;
		}
			
			
			if(creature instanceof Player) {
					target = (Player) creature;
		SkillLearnService.addMissingSkills(target);
	}}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
