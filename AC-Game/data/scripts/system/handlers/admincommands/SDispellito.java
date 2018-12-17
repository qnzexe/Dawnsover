package admincommands;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.DispelCategoryType;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Jeez
 * @modified By Exe
 */
public class SDispellito extends AdminCommand {

	public SDispellito() {
		super("sdispellito");
	}

	@Override
	public void execute(Player admin, String... params) {
		
			for (Effect ef : admin.getEffectController().getAbnormalEffects()) {
				DispelCategoryType category = ef.getSkillTemplate().getDispelCategory();
				if (category == DispelCategoryType.DEBUFF
						|| category == DispelCategoryType.DEBUFF_MENTAL
						|| category == DispelCategoryType.DEBUFF_PHYSICAL
						|| category == DispelCategoryType.ALL) {
					ef.endEffect();
					admin.getEffectController().clearEffect(ef);
				}
			}
			PacketSendUtility.sendMessage(admin, admin.getName() + " had all Debuff effects dispelled ! Ole! ");
		}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
