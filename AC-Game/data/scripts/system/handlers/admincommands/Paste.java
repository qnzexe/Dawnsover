package admincommands;

import java.io.IOException;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.World.Clipboard.ClipboardEntry;

/**
 * Like cut / copy / paste in text editor, but for mobs
 * @author Angry-Cub
 */
public class Paste extends AdminCommand {

	public Paste() {
		super("paste");
	}

	@Override
	public void execute(Player admin, String... params) {
		
	
		ClipboardEntry c = World.getInstance().clipboard.pasteFromClipboard(admin.getName());
		if (c == null) {
			PacketSendUtility.sendMessage(admin, "There is no data in your clipboard.");
			return;
		}

		int respawnTime = c.getResawnTime();
		int templateId = c.getId();
		float x = admin.getX();
		float y = admin.getY();
		float z = admin.getZ();
		byte heading = admin.getHeading();
		int worldId = admin.getWorldId();

		SpawnTemplate spawn = SpawnEngine.addNewSpawn(worldId, templateId, x, y, z, heading, respawnTime);

		if (spawn == null) {
			PacketSendUtility.sendMessage(admin, "There is no template with id " + templateId);
			return;
		}

		VisibleObject visibleObject = SpawnEngine.spawnObject(spawn, admin.getInstanceId());

		if (visibleObject == null) {
			PacketSendUtility.sendMessage(admin, "Spawn id " + templateId + " was not found!");
		}
		else if (respawnTime > 0) {
			try {
				DataManager.SPAWNS_DATA2.saveSpawn(admin, visibleObject, false);
			}
			catch (IOException e) {
				e.printStackTrace();
				PacketSendUtility.sendMessage(admin, "Could not save spawn");
			}
		}

		String objectName = visibleObject.getObjectTemplate().getName();
		PacketSendUtility.sendMessage(admin, objectName + " spawned");
	}

	@Override
	public void onFail(Player player, String message) {
		
	}
}
