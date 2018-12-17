package admincommands;

import java.util.Iterator;

import com.aionemu.gameserver.configs.administration.CommandsConfig;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;
import com.aionemu.gameserver.configs.main.RateConfig;
import com.aionemu.gameserver.model.DescriptionId;
import javax.xml.bind.annotation.XmlEnum;
import com.aionemu.gameserver.model.gameobjects.player.AbyssRank;



/**
 * Admin announce faction
 * 
 * @author Exe
 */
public class CChat extends AdminCommand {

	public CChat() {
		super("cchat");
	}

	public int governor = AbyssRankEnum.SUPREME_COMMANDER.getId();
	public int commander = AbyssRankEnum.COMMANDER.getId();
	
	@Override
	public void execute(Player player, String... params) {
		
			if (!(player.getAbyssRank().getRank().getId() >= 17)){
			PacketSendUtility.sendMessage(player, "You need the title Commander or higher to use this command.");}
			
			else {
			
			Iterator<Player> iter = World.getInstance().getPlayersIterator();
			String message = "";
			
			for (int i = 0; i < params.length - 1; i++)
				message += params[i] + " ";

			// Add the last without the end space
			message += params[params.length - 1];

			Player target = null;

			while (iter.hasNext()) {
				target = iter.next();

				if(player.getRace() == Race.ELYOS) {
					if (target.getCommonData().getRace() == Race.ELYOS) {
					PacketSendUtility.sendBrightYellowMessageOnCenter(target, player.getName() + ": " + message);
				}}
				else if (player.getRace() == Race.ASMODIANS) {
					if (target.getCommonData().getRace() == Race.ASMODIANS) {
					PacketSendUtility.sendBrightYellowMessageOnCenter(target, player.getName() + ": " + message);
			}}
		}}}
	

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Seems like something wrent wrong, /w Exe o/.");
	}
}
