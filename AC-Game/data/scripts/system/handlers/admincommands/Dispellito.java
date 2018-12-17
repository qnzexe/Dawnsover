package admincommands;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.DispelCategoryType;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Jeez
 */
public class Dispellito extends AdminCommand {

	public Dispellito() {
		super("dispellito");
	}

	@Override
	public void execute(Player admin, String... params) {
		Player target = null;
		VisibleObject creature = admin.getTarget();

		if (creature == null) {
			PacketSendUtility.sendMessage(admin, "You should select a target first!");
			return;
		}

		if (creature instanceof Player) {
			target = (Player) creature;
			for (Effect ef : target.getEffectController().getAbnormalEffects()) {
				DispelCategoryType category = ef.getSkillTemplate().getDispelCategory();
				if (category == DispelCategoryType.DEBUFF
						|| category == DispelCategoryType.DEBUFF_MENTAL
						|| category == DispelCategoryType.DEBUFF_PHYSICAL
						|| category == DispelCategoryType.ALL) {
					ef.endEffect();
					target.getEffectController().clearEffect(ef);
				}
			}
			PacketSendUtility.sendMessage(admin, creature.getName() + " had all Debuff effects dispelled ! Ole! ");
		}
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
