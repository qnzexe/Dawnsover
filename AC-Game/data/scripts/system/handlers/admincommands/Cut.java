package admincommands;

import java.io.IOException;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.siegespawns.SiegeSpawnTemplate;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * Like cut / copy / paste in text editor, but for mobs
 * @author Angry-Cub
 */
public class Cut extends AdminCommand {
	
	

	public Cut() {
		super("cut");
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
		if (template.hasPool()) {
			PacketSendUtility.sendMessage(player, "Can't delete pooled spawn template");
			return;
		}
		if (template instanceof SiegeSpawnTemplate) {
			PacketSendUtility.sendMessage(player, "Can't delete siege spawn template");
			return;
		}
		
		World.getInstance().clipboard.copyToClipboard(player.getName(), template.getNpcId(), template.getRespawnTime());
		
		
		npc.getController().onDelete();
		try {
			DataManager.SPAWNS_DATA2.saveSpawn(player, npc, true);
		}
		catch (IOException e) {
			e.printStackTrace();
			PacketSendUtility.sendMessage(player, "Could not remove spawn");
			return;
		}

		PacketSendUtility.sendMessage(player, "Spawn with id " + template.getNpcId() + " cut to clipBoard");
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
