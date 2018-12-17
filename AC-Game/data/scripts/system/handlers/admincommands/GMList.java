package admincommands;

import java.util.Collection;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.FriendList.Status;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.audit.GMService;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;

/**
 * @author Aion Gates
 */
public class GMList extends AdminCommand {

	public GMList() {
		super("gmlist");
	}

	@Override
	public void execute(Player admin, String... params) {

		String sGMNames = "";
		Collection<Player> gms = GMService.getInstance().getGMs();
		int GMCount = 0;

		for (Player pPlayer : gms) {
			if (pPlayer.isGM() && !pPlayer.isProtectionActive()
				&& pPlayer.getFriendList().getStatus() != FriendList.Status.OFFLINE) {
				String nameFormat = "%s";
				GMCount++;
				if (AdminConfig.CUSTOMTAG_ENABLE) {
					if(pPlayer.getName().toLowerCase().equals("mayu") || pPlayer.getName().toLowerCase().equals("ma") || pPlayer.getName().toLowerCase().equals("labs")) {
							nameFormat = AdminConfig.CUSTOMTAG_MARO;
					}
					else if(pPlayer.getName().toLowerCase().equals("qnz")|| pPlayer.getName().toLowerCase().equals("exe") || pPlayer.getName().toLowerCase().equals("yaboku")) {				
							nameFormat = AdminConfig.CUSTOMTAG_EXE;
					}
					else if(pPlayer.getName().toLowerCase().equals("ivo")) {
						nameFormat = AdminConfig.CUSTOMTAG_IVO;
					}
					else if(pPlayer.getName().toLowerCase().equals("liatt") || pPlayer.getName().toLowerCase().equals("ranger")) {
						nameFormat = AdminConfig.CUSTOMTAG_ACCESS5;
					}
					else if(pPlayer.getName().toLowerCase().equals("shiro") || pPlayer.getName().toLowerCase().equals("snowflake") || pPlayer.getName().toLowerCase().equals("rin")) {
						nameFormat = AdminConfig.CUSTOMTAG_SHIRO;
					}
					else if(pPlayer.getName().toLowerCase().equals("baguettepowah")) {
						nameFormat = AdminConfig.CUSTOMTAG_BAGUETTE;
					}
					else if(pPlayer.getAccessLevel() == 3) {
						nameFormat = AdminConfig.CUSTOMTAG_ACCESS3;
					}
					else if(pPlayer.getAccessLevel() == 2) {
						nameFormat = AdminConfig.CUSTOMTAG_ACCESS2;
					}
					else {
						nameFormat = AdminConfig.CUSTOMTAG_ACCESS1;
					}
										}

				sGMNames += String.format(nameFormat, pPlayer.getName()) + " : "
					+ returnStringStatus(pPlayer.getFriendList().getStatus()) + ";\n";
			}
		}

		if (GMCount == 0) {
			PacketSendUtility.sendMessage(admin, "There is no GM online !");
		}
		else if (GMCount == 1) {
			PacketSendUtility.sendMessage(admin, "There is " + GMCount + " GM online !");
		}
		else {
			PacketSendUtility.sendMessage(admin, "There are " + GMCount + " GMs online !");
		}
		if (GMCount != 0)
			PacketSendUtility.sendMessage(admin, "List : \n" + sGMNames);
	}

	private String returnStringStatus(Status p_status) {
		String return_string = "";
		if (p_status == FriendList.Status.ONLINE)
			return_string = "online";
		if (p_status == FriendList.Status.AWAY)
			return_string = "away";
		return return_string;
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
