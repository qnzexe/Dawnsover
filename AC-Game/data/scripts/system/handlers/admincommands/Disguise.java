package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * @author Exe
 */
public class Disguise extends AdminCommand {

	public Disguise() {
		super("disguise");
	}	
	
	@Override
	public void execute(Player admin, String... params) {
		if (params == null || params.length < 1) {
			PacketSendUtility.sendMessage(admin, "syntax //disguise <player> [cancel]");
			return;
		}
		
		Player player = World.getInstance().findPlayer(Util.convertName(params[0]));
		if (player == null) {
			PacketSendUtility.sendMessage(player, "The specified player is not online.");
			return;
		}
		
		
			PlayerAppearance apc = player.getSavedPlayerAppearance();
			PlayerAppearance apcadmin = admin.getSavedPlayerAppearance();
	//		String name = player.getName();
	//		String adminname = admin.getName();
	
	
			  	
	   	if (player.getGender() == admin.getGender() && apc != apcadmin) {
	    		if (player.getName() != admin.getName()) {
	    			admin.setPlayerAppearance(apc);
	    	//		admin.setName(name);	}
	    	}}
	   	
	   	
		if (params[1].equals("cancel")) {
			if (player.getGender() == admin.getGender() && apc == apcadmin) {
				if (player.getName() == admin.getName()) {
				admin.setPlayerAppearance(apcadmin);
			//	admin.setName(adminname);	}	
		}
			}
				
	   	
	   	
	    }}}
		
				

				

	
