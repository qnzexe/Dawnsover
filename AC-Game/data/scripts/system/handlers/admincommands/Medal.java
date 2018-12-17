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
public class Medal extends AdminCommand {

	public Medal() {
		super("medal");
	}

	@Override
	public void execute(Player player, String... params) {
		if ((params.length != 1)) {

		PacketSendUtility.sendMessage(player, "Syntax:");
		PacketSendUtility.sendMessage(player, "//medal gold { exchanges 1 plat to 4 gold.");
		PacketSendUtility.sendMessage(player, "//medal mithril { exchanges 4 plat to 1 mithril.");	
		}
		
		Storage bag = player.getInventory();
		int medaltype = 0;
		Item platins = bag.getFirstItemByItemId(186000096);	
		long medalcount = platins.getItemCount();
		
		
		if(params[0].toLowerCase().equals("gold")) {
			if(medalcount >= 1) {
			medaltype = 186000030;	
			ItemService.addItem(player, medaltype, 4);
			bag.decreaseByObjectId(platins.getObjectId(), 1);
			}
			if (medalcount <1){
				PacketSendUtility.sendMessage(player, "You need at least 1 platinum medal to perform the command.");
			}}
		
		
		if(params[0].toLowerCase().equals("mithril")) {
			if(medalcount >= 4) {
			medaltype = 186000147;
			ItemService.addItem(player, medaltype, 1);
			bag.decreaseByObjectId(platins.getObjectId(), 4);
			}if (medalcount <4){
				PacketSendUtility.sendMessage(player, "You need at least 4 platinum medals to perform the command.");
			}}
	
		
		if (!(params[0].toLowerCase().equals("mithril") || params[0].toLowerCase().equals("gold"))) {
			PacketSendUtility.sendMessage(player, "Invalid medal type. Use 'gold' or 'mithril'.");
		}}
	
	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Check if you got platinum medals in your Inv. Else:");
		PacketSendUtility.sendMessage(player, "//medal gold { exchanges 1 plat to 4 gold.");
		PacketSendUtility.sendMessage(player, "//medal mithril { exchanges 4 plat to 1 mithril.");	
	}

}
		
			
	



//	long MACrown = bag.getItemCountByItemId(186000051);
//	if (MACrown >= 1) {
//	Item MaCrownItem = bag.getFirstItemByItemId(186000051);
//	MACrown = MaCrownItem.getItemCount();
//	ApByRelics = ApByRelics + (MaCrownItem.getItemCount())*19200;	
//	bag.decreaseByObjectId(MaCrownItem.getObjectId(), MACrown);
//	PacketSendUtility.sendMessage(player, MACrown + " Count for .51");
//	RelicsAmount = RelicsAmount + MACrown;
//	}}

//	int medalamount = Integer.parseInt(params[1]);
//	long checkpmedals = bag.getItemCountByItemId(186000096);