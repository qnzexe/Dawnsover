package admincommands;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class Listspawns extends AdminCommand {

	public Listspawns() {
		super("listspawns");
	}

	@Override
	public void execute(Player admin, String... params) {

		int id = 0;
		if (admin.getTarget() == null) {
			if (params.length < 1) {
				PacketSendUtility.sendMessage(admin, "Select a target or pass a npcid as argument.");
			} else {
				try {
					id = Integer.parseInt(params[0]);
				} catch (Exception e) {
					PacketSendUtility.sendMessage(admin, "The Npc id must be a number");
					return ;
				}
			}
			
		} else if (!(admin.getTarget() instanceof Npc)) {
			PacketSendUtility.sendMessage(admin, "Can only be used on an Npc");
			return;
		} else {
			Npc target = (Npc)admin.getTarget();
			id = target.getNpcId();
		}
		PacketSendUtility.sendMessage(admin, "Npc with id " + id + " is spawned at those locations : ");
		for (Npc npc :  World.getInstance().getNpcs()) {
			
			if (npc.getNpcId() == id) {
				PacketSendUtility.sendMessage(admin,
						"[pos:Location;"
				+npc.getWorldId()
				+" "
				+npc.getX()
				+" "
				+npc.getY()
				+" "
				+npc.getZ()
				+" "
				+0
				+"]");
			}
		}
	}


	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}

}