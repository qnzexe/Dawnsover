package admincommands;

import com.aionemu.gameserver.cache.HTMLCache;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.HTMLService;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Phantom, ATracer
 */
public class Help extends AdminCommand {

	public Help() {
		super("help");
	}

	@Override
	public void execute(Player player, String... params) {
		HTMLService.showHTML(player, HTMLCache.getInstance().getHTML("help.xhtml"));
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
