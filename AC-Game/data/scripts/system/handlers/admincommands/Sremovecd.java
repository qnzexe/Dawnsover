package admincommands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.aionemu.gameserver.model.gameobjects.HouseObject;
import com.aionemu.gameserver.model.gameobjects.UseableItemObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.ItemCooldown;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_COOLDOWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_COOLDOWN;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Exe
 */
public class Sremovecd extends AdminCommand {

	public Sremovecd() {
		super("sremovecd");
	}

	@Override
	public void execute(Player admin, String... params) {

			if (params.length == 0) {
				List<Integer> delayIds = new ArrayList<Integer>();
				if (admin.getSkillCoolDowns() != null) {
					long currentTime = System.currentTimeMillis();
					for (Entry<Integer, Long> en : admin.getSkillCoolDowns().entrySet())
						delayIds.add(en.getKey());

					for (Integer delayId : delayIds)
						admin.setSkillCoolDown(delayId, currentTime);

					delayIds.clear();
					PacketSendUtility.sendPacket(admin, new SM_SKILL_COOLDOWN(admin.getSkillCoolDowns()));
				}

				if (admin.getItemCoolDowns() != null) {
					for (Entry<Integer, ItemCooldown> en : admin.getItemCoolDowns().entrySet())
						delayIds.add(en.getKey());

					for (Integer delayId : delayIds)
						admin.addItemCoolDown(delayId, 0, 0);

					delayIds.clear();
					PacketSendUtility.sendPacket(admin, new SM_ITEM_COOLDOWN(admin.getItemCoolDowns()));
				}

				if (admin.getHouseRegistry() != null && admin.getHouseObjectCooldownList().getHouseObjectCooldowns().size() > 0) {
					Iterator<HouseObject<?>> iter = admin.getHouseRegistry().getObjects().iterator();
					while (iter.hasNext()) {
						HouseObject<?> obj = iter.next();
						if (obj instanceof UseableItemObject) {
							if (!admin.getHouseObjectCooldownList().isCanUseObject(obj.getObjectId()))
								admin.getHouseObjectCooldownList().addHouseObjectCooldown(obj.getObjectId(), 0);
						}
					
			
					PacketSendUtility.sendMessage(admin, "Your cooldowns were removed");
	
				}
			}
			else if (params[0].contains("instance")) {
				if (admin.getPortalCooldownList() == null || admin.getPortalCooldownList().getPortalCoolDowns() == null)
					return;
				if (params.length >= 2) {
					if (params[1].equalsIgnoreCase("all")) {
						List<Integer> mapIds = new ArrayList<Integer>();
						for (Entry<Integer, Long> mapId : admin.getPortalCooldownList().getPortalCoolDowns().entrySet())
							mapIds.add(mapId.getKey());

						for (Integer id : mapIds)
							admin.getPortalCooldownList().addPortalCooldown(id, 0);

						mapIds.clear();
							PacketSendUtility.sendMessage(admin, "Your instance cooldowns were removed");
					}
					else {
						int worldId = 0;
						try {
							worldId = Integer.parseInt(params[1]);
						}
						catch (NumberFormatException e) {
							PacketSendUtility.sendMessage(admin, "WorldId has to be integer or use \"all\"");
							return;
						}

						if (admin.getPortalCooldownList().isPortalUseDisabled(worldId)) {
							admin.getPortalCooldownList().addPortalCooldown(worldId, 0);


								PacketSendUtility.sendMessage(admin, "Your instance cooldown worldId: " + worldId + " was removed");
						}
						else
							PacketSendUtility.sendMessage(admin, "You or your target can enter given instance");

					}
				}
				else
					PacketSendUtility.sendMessage(admin, "syntax: //removecd instance <all|worldId>");
			}
	
		else
			PacketSendUtility.sendMessage(admin, "Only players are allowed as target");
	}
}}
