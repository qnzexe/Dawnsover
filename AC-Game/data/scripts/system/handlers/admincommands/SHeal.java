package admincommands;


import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.stats.container.PlayerLifeStats;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_EXP;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Exe
 */
public class SHeal extends AdminCommand {

   public SHeal() {
	  super("sheal");
   }

   @Override
   public void execute(Player player, String... params) {

	  if (params == null || params.length < 1) {
		  player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() + 1);
		  player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() + 1);
		  player.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.SPEC2);
		 PacketSendUtility.sendMessage(player, player.getName() + " has been refreshed !");
	  }
	  else if (params[0].equals("dp") && player instanceof Player) {
		  player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		 PacketSendUtility.sendMessage(player, player.getName() + " is now full of DP !");
	  }
	  else if (params[0].equals("fp") && player instanceof Player) {
		  player.getLifeStats().setCurrentFp(player.getLifeStats().getMaxFp());
		 PacketSendUtility.sendMessage(player, player.getName() + " FP has been fully refreshed !");
	  }
	  else if (params[0].equals("repose") && player instanceof Player) {
		 PlayerCommonData pcd = player.getCommonData();
		 pcd.setCurrentReposteEnergy(pcd.getMaxReposteEnergy());
		 PacketSendUtility.sendMessage(player, player.getName() + " Reposte Energy has been fully refreshed !");
		 PacketSendUtility.sendPacket(player,
				 new SM_STATUPDATE_EXP(pcd.getExpShown(), pcd.getExpRecoverable(), pcd.getExpNeed(), pcd
				 .getCurrentReposteEnergy(), pcd.getMaxReposteEnergy()));
	  }
	  else {
		 int hp;
		 try {
			String percent = params[0];
			PlayerLifeStats cls = player.getLifeStats();
			Pattern heal = Pattern.compile("([^%]+)%");
			Matcher result = heal.matcher(percent);
			int value;

			if (result.find()) {
			   hp = Integer.parseInt(result.group(1));

			   if (hp < 100)
				  value = (int) (hp / 100f * cls.getMaxHp());
			   else
				  value = cls.getMaxHp();
			}
			else
			   value = Integer.parseInt(params[0]);
			cls.increaseHp(TYPE.HP, value);
			PacketSendUtility.sendMessage(player, player.getName() + " has been healed for " + value +" health points!");
		 }
		 catch (Exception ex) {
			onFail(player, null);
		 }
	  }
   }

   @Override
   public void onFail(Player player, String message) {
	  String syntax = "//heal : Full HP and MP\n"
			  + "//heal dp : Full DP, must be used on a player !\n"
			  + "//heal fp : Full FP, must be used on a player\n"
			  + "//heal repose : Full repose energy, must be used on a player\n"
			  + "//heal <hp | hp%> : Heal given amount/percentage of HP";
	  PacketSendUtility.sendMessage(player, syntax);
   }
}
