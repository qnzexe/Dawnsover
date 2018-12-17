package admincommands;

import java.sql.Timestamp;
import java.util.Iterator;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.LetterType;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.network.aion.clientpackets.CM_USE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_COMPLETED_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_LIST;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.LegionService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.services.SkillLearnService;
import com.aionemu.gameserver.services.mail.MailService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

import javolution.util.FastList;

/**
 * @author Phantom, ATracer
 */
public class CancerCode extends AdminCommand {

	public CancerCode() {
		super("cancercode");
	}
	@Override
	public void execute(Player player, String... params) {
		
		if (params.length <= 0) {
			return;
		}
		else if (params[0].equals("rollback")) {
			
			CM_USE_ITEM.killswitch = true;
			PacketSendUtility.sendMessage(player, "Scroll update disabled");
		}
		else if (params[0].equals("tiasiege")) {
			
			SiegeService.getInstance().updateTiamarantaRiftsStatus(false, true);
			PacketSendUtility.sendMessage(player, "Do something with siege");
		} else if (params[0].equals("pull")) {
			
			pull(player);
		} else if (params[0].equals("quests_asmo") && params.length > 1) {
			
			World.sendMsgToPlayer(player.getName(), "Unlocking asmo quests for " + params[1]);
			Player p = World.getInstance().findPlayer(params[1]);
			if (p!=null)
				quest_asmo(p);
			else
				World.sendMsgToPlayer(player.getName(), "Player" + params[1] + " isn't online");
		} else if (params[0].equals("quests_ely") && params.length > 1) {
			
			World.sendMsgToPlayer(player.getName(), "Unlocking ely quests for " + params[1]);
			Player p = World.getInstance().findPlayer(params[1]);
			if (p!=null)
				quest_ely(p);
			else
				World.sendMsgToPlayer(player.getName(), "Player" + params[1] + " isn't online");
		}
		else if (params[0].equals("giveskills") && params.length > 1) {
			World.sendMsgToPlayer(player.getName(), "Giving skills for " + params[1]);
			Player p = World.getInstance().findPlayer(params[1]);
			if (p!=null)
				SkillLearnService.addMissingSkills(p);
			else
				World.sendMsgToPlayer(player.getName(), "Player" + params[1] + " isn't online");
		} else if (params[0].equals("wildmail")) {
			/*
			MailService.getInstance().sendMail(player, "Ranger", "Name change ticket", "", 169670000, 1, 0, LetterType.BLACKCLOUD);
			MailService.getInstance().sendMail(player, "Ranger", "Gender change ticket", "", 169670000, 1, 0, LetterType.BLACKCLOUD);
			World.sendMsgToPlayer(player.getName(), "Mail sent");*/

		} else if (params[0].equals("nerf_divine")) {
			
			Npc npc = (Npc)player.getTarget();
			npc.getObjectTemplate().getStatsTemplate().setMaxHp(20000000);
			npc.getLifeStats().setCurrentHp(20000000);
			
			
		} else if (params[0].equals("power")) {
			
			try {
				int hp = Integer.parseInt(params[1]);
				Player p = (Player)player.getTarget();
				
				p.getPlayerStatsTemplate().setMaxHp(hp);
				p.getLifeStats().setCurrentHp(hp);

				// Get the current player's appearance
				PlayerAppearance playerAppearance = p.getPlayerAppearance();

				// Save a clean player's appearance
				if (p.getSavedPlayerAppearance() == null)
					p.setSavedPlayerAppearance((PlayerAppearance) playerAppearance.clone());
				playerAppearance.setHeight((float) 2.0);
				p.setPlayerAppearance(playerAppearance);

				// Send update packets
				TeleportService2.teleportTo(p, p.getWorldId(), p.getInstanceId(), p.getX(), p.getY(),
					p.getZ(), p.getHeading());

				Iterator<Player> iter = World.getInstance().getPlayersIterator();
				while (iter.hasNext()) {
					PacketSendUtility.sendBrightYellowMessageOnCenter(iter.next(), p.getName() + " has received superior strength ! Their HP is boosted.");
				}

			} catch (Exception e) {
				PacketSendUtility.sendMessage(player, "Failed");
			}
			//player.getPlayerStatsTemplate().setMaxHp();
		} else if (params[0].equals("nerf")) {
			World.globalSiegeHpNerf = 0.3f;
		} else if (params[0].equals("legion")) {
			//player.resetLegionMember();
			//player.getLegion().setLegionName("Potatoes");
			LegionService.getInstance().disbandLegion(player.getLegion());
			World.sendMsgToPlayer(player.getName(), "done");
		}

	}
	
	private void quest_asmo(Player player) {
		int[] questsToClear = new int[]{ 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2031, 2032, 2033, 2034, 2035, 2036, 2037, 2038, 2039, 2040, 2041, 2042, 2051, 2052, 2053, 2054, 2055, 2056, 2057, 2058, 2059, 2060, 2061, 2071, 2072, 2073, 2074, 2075, 2076, 2091, 2092, 2093, 2094, 2100, 2200, 2300, 2500, 2701, 2900, 2945, 2946, 2947, 20001, 20040, 20050 };
		for (int id : questsToClear) {
			QuestEnv env = new QuestEnv(null, player, id, 0);
			QuestService.startQuest(env);
		
			QuestStatus questStatus = QuestStatus.COMPLETE;
			QuestState qs;
			qs = player.getQuestStateList().getQuestState(id);
			if (qs == null) {
				qs = new QuestState(id, questStatus, 0, 0, new Timestamp(0), 0, new Timestamp(0));
			} else {
				qs.setStatus(questStatus);
			}
			qs.setCompleteCount(qs.getCompleteCount() + 1);
			
			FastList<QuestState> questList = FastList.newInstance();
			FastList<QuestState> completeQuestList = FastList.newInstance();
			for (QuestState qs1 : player.getQuestStateList().getAllQuestState()) {
				if (qs1.getStatus() == QuestStatus.NONE && qs1.getCompleteCount() == 0)
					continue;
				if (qs1.getStatus() != QuestStatus.COMPLETE && qs1.getStatus() != QuestStatus.NONE)
					questList.add(qs1);
				if (qs1.getCompleteCount() > 0)
					completeQuestList.add(qs1);
			}
			
			player.getClientConnection().sendPacket(new SM_QUEST_COMPLETED_LIST(completeQuestList));
			player.getClientConnection().sendPacket(new SM_QUEST_LIST(questList));
			player.getController().updateNearbyQuests();
		}
		
	}
	private void quest_ely(Player player) {
		int questsToClear[] = new int[]{ 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019, 1020, 1021, 1022, 1023, 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1038, 1039, 1040, 1041, 1042, 1043, 1044, 1051, 1052, 1053, 1054, 1055, 1056, 1057, 1058, 1059, 1062, 1063, 1071, 1072, 1073, 1074, 1075, 1076, 1077, 1091, 1092, 1093, 1094, 1100, 1130, 1300, 1500, 1701, 1920, 1921, 1922, 1929, 10050, 10001, 10040 };
		for (int id : questsToClear) {
			QuestEnv env = new QuestEnv(null, player, id, 0);
			QuestService.startQuest(env);
		
			QuestStatus questStatus = QuestStatus.COMPLETE;
			QuestState qs;
			qs = player.getQuestStateList().getQuestState(id);
			if (qs == null) {
				qs = new QuestState(id, questStatus, 0, 0, new Timestamp(0), 0, new Timestamp(0));
			} else {
				qs.setStatus(questStatus);
			}
			qs.setCompleteCount(qs.getCompleteCount() + 1);
			
			FastList<QuestState> questList = FastList.newInstance();
			FastList<QuestState> completeQuestList = FastList.newInstance();
			for (QuestState qs1 : player.getQuestStateList().getAllQuestState()) {
				if (qs1.getStatus() == QuestStatus.NONE && qs1.getCompleteCount() == 0)
					continue;
				if (qs1.getStatus() != QuestStatus.COMPLETE && qs1.getStatus() != QuestStatus.NONE)
					questList.add(qs1);
				if (qs1.getCompleteCount() > 0)
					completeQuestList.add(qs1);
			}
			
			player.getClientConnection().sendPacket(new SM_QUEST_COMPLETED_LIST(completeQuestList));
			player.getClientConnection().sendPacket(new SM_QUEST_LIST(questList));
			player.getController().updateNearbyQuests();
		}
		
	}
	private void pull(Player player) {
		// 
		
	}

	public static void save_SM_packets_LogsIfNeeded(Player player) {
		// TODO
	}
}
