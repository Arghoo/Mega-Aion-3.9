package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

import pirate.events.EventManager;
import pirate.events.enums.EventType;

/**
 * @author flashman
 */
public class EventUtil extends AdminCommand
{
	private static final StringBuilder info = new StringBuilder();

	static {
		info.append("Informationen zum Team:\n");
		info.append("//emanager - zeigt die Informationen, die Sie gerade lesen\n");
		info.append("//emanager start <event type> - wenn die Daten Ereignis nicht ausgeführt wird, führen Sie eine Zwangsstart\n");
		info.append("//emanager rcd all - entfernt die Abklingzeit von allen Spielern in der ganzen Veranstaltung, an denen sie teilgenommen\n");
		info.append("//emanager rcd <event type> <player name> - entfernt die Abklingzeit von Ereignis für das angegebene Ziel Spieler\n");
		info.append("Verfügbaren Typen Veranstaltung(event type):\n");
		for (EventType et : EventType.values()) {
			if (et.IsDone()) {
				info.append(et.getEventTemplate().getCmdName()).append("\n");
			}
		}
	}

	public EventUtil()
	{
		super("emanager");
	}

	@Override
	public void execute(Player admin, String... params)
	{
		if (params.length == 0) {
			showCommandInfo(admin);
		} else {
			// start event cmd
			if (params[0].equals("start")) {
				if (admin.getAccessLevel() < 3) {
					PacketSendUtility.sendMessage(admin, "Sie können diesen Befehl nicht verwenden.");
					return;
				}
				EventType et = parseType(params[1]);
				if (et == null) {
					PacketSendUtility.sendMessage(admin, "Falsche Event Art.");
					return;
				}
				PacketSendUtility.sendMessage(admin, EventManager.getInstance().CMD_StartEvent(et));
				return;
			}
			// remove event cd cmd
			if (params[0].equals("rcd")) {
				Player p;
				EventType type;
				if (params.length == 2 && params[1].equals("all")) {
					for (EventType et : EventType.values()) {
						EventManager.getInstance().createNewEventSession(et);
					}
				} else if (params.length == 3) {
					type = parseType(params[1]);
					p = World.getInstance().findPlayer(Util.convertName(params[2]));
					if (type == null) {
						PacketSendUtility.sendMessage(admin, "Falsche Event Art.");
						return;
					}
					EventManager.getInstance().removePlayerFromPlayedList(p, type);
				}
				return;
			}
			PacketSendUtility.sendMessage(admin, "Unbekannter Benutzer.");
		}
	}

	private void showCommandInfo(Player p)
	{
		PacketSendUtility.sendMessage(p, info.toString());
	}

	private EventType parseType(String str)
	{
		for (EventType et : EventType.values()) {
			if (!et.IsDone()) {
				continue;
			}
			if (str.equals(et.getEventTemplate().getCmdName())) {
				return et;
			}
		}
		return null;
	}
}
