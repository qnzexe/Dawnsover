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
 * player moveto command
 * 
 * @author Exe
 */
public class Medalex extends AdminCommand {

	public Medalex() {
		super("medalex");
	}

	@Override
	public void execute(final Player player, String... params) {
		if ((params.length != 2)) {

		PacketSendUtility.sendMessage(player, "Syntax:");
		PacketSendUtility.sendMessage(player, "//medalex gold <amount | all> { exchanges 1 plat to 4 gold.");
		PacketSendUtility.sendMessage(player, "//medalex mithril <amount | all> { exchanges 4 plat to 1 mithril.");	
		} else {
		
		Storage bag = player.getInventory();
		
		long pmedals = bag.getItemCountByItemId(186000096);
		int medaltype = 0;
		int medalamount = Integer.parseInt(params[1]);
		int medalcount = 0;
		long checkpmedals = bag.getItemCountByItemId(186000096);
		Item platins = bag.getFirstItemByItemId(186000096);		
		
		if(params[0].toLowerCase().equals("gold")) {
			medaltype = 186000030;
		}
		else if(params[0].toLowerCase().equals("mithril")) {
			medaltype = 186000147;
		}
		else {
			PacketSendUtility.sendMessage(player, "Invalid medal type. Use 'gold' or 'mithril'.");
		}	
		
	if (checkpmedals >= 1) {	
		if(params[1].toLowerCase().equals("all")) {
					if(medaltype == 186000147 && (pmedals % 4) == 0) {
							Item platinmedals = bag.getFirstItemByItemId(186000096);
								pmedals = platinmedals.getItemCount();
									medalcount = (int) (pmedals * 0.25);
					} else if(medaltype == 186000030) {
									Item platinmedals = bag.getFirstItemByItemId(186000096);
									pmedals = platinmedals.getItemCount();
									medalcount = (int) (pmedals * 4);
					} else if(medaltype == 186000147 && pmedals % 4 != 0) {
									while((pmedals % 4) != 0) {
									pmedals = pmedals - 1;	
											if ((pmedals % 4) == 0) {
											medalcount = (int) (pmedals * 0.25);
											PacketSendUtility.sendMessage(player, "The next lower valid number of platin medals was used for the Exchange.");
								}}
					}	 else {
						PacketSendUtility.sendMessage(player, "Seems like something went wrong! PM yandere.exe on disc please c:");
					}
		}
		else if(medalamount >= 1) {
					if(medaltype == 186000147 && (medalamount % 4) == 0) {
								pmedals = medalamount;
								if (checkpmedals >= medalamount) {
									medalcount = (int) (pmedals * 0.25);
								}	
								else {
									PacketSendUtility.sendMessage(player, "Insufficient amount of platinum medals.");
								}
				
					} else if(medaltype == 186000030) {
								pmedals = medalamount;
								if (checkpmedals >= medalamount) {
									medalcount = (int) (pmedals * 4);
								}
								else {
									PacketSendUtility.sendMessage(player, "Insufficient amount of platinum medals.");
								}
					}
					else if(medaltype == 186000147 && (medalamount %4) != 0) {
								PacketSendUtility.sendMessage(player, "Invalid amount of Medals. Please use a Number devidable through '4' or 'all'.");		  
					}	 					
					else {
								PacketSendUtility.sendMessage(player, "Seems like something went wrong! PM yandere.exe on disc please c:");
					}}
		else {
			PacketSendUtility.sendMessage(player, "Invalid amount type. Use a number or 'all'.");
		} 
	} else {
		PacketSendUtility.sendMessage(player, "You've got no platinum medals"); }
		

		ItemService.addItem(player, medaltype, medalcount);
        PacketSendUtility.sendMessage(player, "You've successfully exchanged " + pmedals + " platinum medals into " + medalcount + " " + params[0].toLowerCase() + " medals.");
    //  bag.decreaseByObjectId(platins.getObjectId(), medalcount);
        bag.decreaseByObjectId(platins.getObjectId(), ((platins.getItemCount()/platins.getItemCount())*medalamount));
	}
	

		
	}}



//	long MACrown = bag.getItemCountByItemId(186000051);
//	if (MACrown >= 1) {
//	Item MaCrownItem = bag.getFirstItemByItemId(186000051);
//	MACrown = MaCrownItem.getItemCount();
//	ApByRelics = ApByRelics + (MaCrownItem.getItemCount())*19200;	
//	bag.decreaseByObjectId(MaCrownItem.getObjectId(), MACrown);
//	PacketSendUtility.sendMessage(player, MACrown + " Count for .51");
//	RelicsAmount = RelicsAmount + MACrown;
//	}}