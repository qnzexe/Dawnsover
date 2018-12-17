package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Exe
 */
public class SDispel extends AdminCommand {

	public SDispel() {
		super("sdispel");
	}

	@Override
	public void execute(Player admin, String... params) {

			admin.getEffectController().removeAllEffects();
			PacketSendUtility.sendMessage(admin, admin.getName() + " had all buff effects dispelled !");
		}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
