package admincommands;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * Like cut / copy / paste in text editor, but for mobs
 * @author Angry-Cub
 */
public class Copy extends AdminCommand {
	
	public Copy() {
		super("copy");
	}

	@Override
	public void execute(Player player, String... params) {

		VisibleObject cre = player.getTarget();
		if (!(cre instanceof Npc)) {
			PacketSendUtility.sendMessage(player, "Wrong target");
			return;
		}
		Npc npc = (Npc) cre;
		SpawnTemplate template = npc.getSpawn();

		World.getInstance().clipboard.copyToClipboard(player.getName(), template.getNpcId(), template.getRespawnTime());

		PacketSendUtility.sendMessage(player, "Spawn with id " + template.getNpcId() + " copied to clipBoard");
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
