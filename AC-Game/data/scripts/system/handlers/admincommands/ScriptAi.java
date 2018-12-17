package admincommands;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.stats.container.CreatureLifeStats;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_EXP;
import com.aionemu.gameserver.services.TownService;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.lang.System;
import java.util.Properties;
import static java.lang.System.*;


/**
 * @author Mrakobes, Loxo
 */
public class ScriptAi extends AdminCommand {

    public ScriptAi() {
        super("scriptai");
    }

    @Override
    public void execute(Player admin, String... params) {
        VisibleObject target = admin.getTarget();
        //Npc target = admin.getTarget();
        if (target == null) {
            PacketSendUtility.sendMessage(admin, "No target selected");
            return;
        }
        if (params == null) {
            PacketSendUtility.sendMessage(admin, "To use this command write //scriptai <mobname> <stepNumber>");
            return;
        }
        if (params[0] == null || params[1] == null) {
            PacketSendUtility.sendMessage(admin, "Mob name / step number missing.");
            PacketSendUtility.sendMessage(admin, "To use this command write //scriptai <mobname> <stepNumber>");
            return;
        }
        if (params != null && params[1].equals("1")) {
            PacketSendUtility.sendMessage(admin, " You are creating new AI Path named: " + params[0]);
            PacketSendUtility.sendMessage(admin, "\nLocation of " + params[0]
                    + "\nX: " + admin.getTarget().getX() + " / Y: " + admin.getTarget().getY()
                    + " / Z: " + admin.getTarget().getZ()
            );
           String walker_id = getSaltString();
            String str = "<walker_template route_id=\"" + walker_id + "\" pool=\"1\">" +
            "\n<routestep step=\"" + params[1] + "\" x=\"" + admin.getTarget().getX() + "\" y =\""
                    + admin.getTarget().getY() + "\" z=\"" + admin.getTarget().getZ() + "\"/>";

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(params[0] + ".ai"));
                writer.write(str);

                writer.close();
            }
            catch (Exception ex) {
                onFail(admin, null);
            }
        }
        if (params != null && !params[1].equals("1")) {
            boolean result = false;
            boolean stepExists = false;
            int stepNo = Integer.parseInt(params[1])-1;
            String oldContent = "";
            String searchString = "step=\""+ stepNo + "\"";
            String stringExists = "step=\""+ params[1] + "\"";
            String str = "\n<routestep step=\"" + params[1] + "\" x=\"" + admin.getTarget().getX() + "\" y =\""
                    + admin.getTarget().getY() + "\" z=\"" + admin.getTarget().getZ() + "\"/>";

            /*String str = params[0] + "   " + params[1];*/
            try {

                // BufferedReader reader = new BufferedReader(new FileReader(params[0] + ".ai"));
                // reader.indexOf("text");
                Scanner in = new Scanner(new FileReader(params[0] + ".ai"));

                while (in.hasNextLine() && !result) {
                    result = in.nextLine().indexOf(searchString) >= 0;
                }
                while (in.hasNextLine() && !stepExists) {
                    stepExists = in.nextLine().indexOf(stringExists) >= 0;
                }
                if (result == true) {

                    if (stepExists == true) {
                        BufferedReader reader = new BufferedReader(new FileReader(params[0] + ".ai"));
                        String line = reader.readLine();
                        //PacketSendUtility.sendMessage(admin, "Reading Line:" + line);
                        while (line != null) {
                            oldContent = oldContent + line + System.getProperty("line.separator");
                            //PacketSendUtility.sendMessage(admin, "Old content:" + oldContent);
                            line = reader.readLine();
                        }
                        String newContent = oldContent.replaceAll(stringExists, stringExists + "_old");
                        //PacketSendUtility.sendMessage(admin, "New Content:" + newContent);
                        FileWriter writerReplace = new FileWriter(params[0] + ".ai");
                        PacketSendUtility.sendMessage(admin, "Added step that already exists, renamed old step to "
                                + stringExists +"_old");
                        writerReplace.write(newContent);
                        writerReplace.close();
                    }

                    BufferedWriter writer = new BufferedWriter(new FileWriter(params[0] + ".ai", true));
                    writer.append(str);

                    writer.close();
                    PacketSendUtility.sendMessage(admin, "\nAdding step " + params[1] + " to mob: " + params[0]
                            + "\nX: " + admin.getTarget().getX() + " / Y: " + admin.getTarget().getY()
                            + " / Z: " + admin.getTarget().getZ()
                    );
                } else {
                    PacketSendUtility.sendMessage(admin, "Your file is missing step " + stepNo + ".");
                }
            }
            catch (Exception ex) {
                PacketSendUtility.sendMessage(admin, "That file does not exist, did you mistype the mob/file name?");
            }
        }
        else {
          //  int hp;
            try {

            }
            catch (Exception ex) {
                onFail(admin, null);
            }
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 40) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    @Override
    public void onFail(Player player, String message) {
        String syntax = "FAIL: To use this command write //scriptai <mobname> <stepNumber>";
        PacketSendUtility.sendMessage(player, syntax);
    }
}
