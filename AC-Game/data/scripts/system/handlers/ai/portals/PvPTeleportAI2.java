/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 * @Aion-Idk Dev.
 */
package ai.portals;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.portal.PortalPath;
import com.aionemu.gameserver.model.templates.portal.PortalUse;
import com.aionemu.gameserver.model.templates.teleport.TeleportLocation;
import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
import com.aionemu.gameserver.services.teleport.PortalService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author xTz
 */
@AIName("pvpteleport")
public class PvPTeleportAI2 extends ActionItemNpcAI2 {

    protected TeleporterTemplate teleportTemplate;
    protected PortalUse portalUse;

    @Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
        return true;
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        teleportTemplate = DataManager.TELEPORTER_DATA.getTeleporterTemplateByNpcId(getNpcId());
        portalUse = DataManager.PORTAL2_DATA.getPortalUse(getNpcId());
    }

    @Override
    protected void handleDialogStart(Player player) {
        AI2Actions.selectDialog(this, player, 0, -1);
        if (getTalkDelay() != 0) {
            super.handleDialogStart(player);
        } else {
            handleUseItemFinish(player);
        }
    }

    @Override
    protected void handleUseItemFinish(Player player) {
    	
    if (player.getCommonData().getRace() == Race.ELYOS) {
    	
    	int location = Rnd.get(1,3);
    	
    	if (location == 1) {
    	TeleportService2.teleportTo(player, 600060000, 677, 1580, 454); //Snow Tower
    	}
    	
    	if (location == 2) {
        TeleportService2.teleportTo(player, 600060000, 701, 1174, 419); //Tree
        }
    	
    	if (location == 3) {
        TeleportService2.teleportTo(player, 600060000, 746, 900, 394); //Brush
        }	
    	
            }
    
    if (player.getCommonData().getRace() == Race.ASMODIANS) {
    	
    	int location = Rnd.get(1,3);
    	
    	if (location == 1) {
    	TeleportService2.teleportTo(player, 600060000, 869, 1465, 458); //Snow Wall
    	}
    	
    	if (location == 2) {
        TeleportService2.teleportTo(player, 600060000, 826, 1045, 400); //Tree
        }
    	
    	if (location == 3) {
        TeleportService2.teleportTo(player, 600060000, 1025, 979, 380); //Brush    
    
    
        }
}
    }
}

