package admincommands;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class Debug extends AdminCommand{

	public Debug() {
		super("debug");
	}

	@Override
	public void execute(Player player, String... params) {

		if (params == null || params.length < 1) {
			showHelp(player);
		
		} else if (params[0].equals("cpackets")){ 
			AionConnection.sendPacketNamesToDevs = !AionConnection.sendPacketNamesToDevs;
			World.sendMsgToPlayer(player.getName(), "Toggled client packet bouncing.");
		} else if (params[0].equals("aroundme")){ 
			showObjectsAroundMe(player);
		}
	}

	private void showObjectsAroundMe(Player player) {
		World.sendMsgToPlayer(player.getName(), "List of AionObjects around :");
		
		for (VisibleObject o : World.getInstance().getAllVisibleObjects()) {
			if (o.getWorldId() != player.getWorldId() || MathUtil.getDistance(player, o) > 50)
				continue;
			//else
			World.sendMsgToPlayer(player.getName(), o.getName() + "("+o.getObjectTemplate().getTemplateId()+")");
		}
		
	}

	private void showHelp(Player player) {
		World.sendMsgToPlayer(player.getName(), "debug <cpakets>");
	}

}
