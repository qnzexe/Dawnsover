package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;


/**
 * 
 * @author Exe
 */
public class Compbox extends AdminCommand {

	public Compbox() {
		super("dual");
	}

	@Override
	public void execute(Player player, String... params) {
		if ((params.length != 1)) {

		PacketSendUtility.sendMessage(player, "Syntax:");
		PacketSendUtility.sendMessage(player, "//dual power { 12 mithril for 1 atk|mb dual manastone.");
		PacketSendUtility.sendMessage(player, "//dual accuracy { 12 mithril for 1 crit|macc dual manastone.");
		PacketSendUtility.sendMessage(player, "You can find a more specific rewards list pinned in the updates-log channel of our discord server.");
		}
		
		Storage bag = player.getInventory();
		int boxtype = 0;
		Item mithril = bag.getFirstItemByItemId(186000147);	
		long medalcount = mithril.getItemCount();
		
		
		if(params[0].toLowerCase().equals("power")) {
			if(medalcount >= 12) {
			boxtype = 188052184;	
			ItemService.addItem(player, boxtype, 1);
			bag.decreaseByObjectId(mithril.getObjectId(), 12);
			}
			if (medalcount <12){
				PacketSendUtility.sendMessage(player, "You need at least 12 mithril medals to perform the command.");
			}}
		
		
		if(params[0].toLowerCase().equals("accuracy")) {
			if(medalcount >= 12) {
			boxtype = 188052297;
			ItemService.addItem(player, boxtype, 1);
			bag.decreaseByObjectId(mithril.getObjectId(), 12);
			}if (medalcount <12){
				PacketSendUtility.sendMessage(player, "You need at least 12 mithril medals to perform the command.");
			}}
	
		
		if (!(params[0].toLowerCase().equals("power") || params[0].toLowerCase().equals("accuracy"))) {
			PacketSendUtility.sendMessage(player, "Invalid box type. Use 'power' or 'accuracy'.");
		}}
	
	@Override
	public void onFail(Player player, String message) {

	}

}
		
			