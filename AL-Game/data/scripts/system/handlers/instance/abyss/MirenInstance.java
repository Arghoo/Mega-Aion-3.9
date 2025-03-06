package instance.abyss;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.Npc;

/**
 * @author keqi, xTz
 * @reworked Luzien
 */
@InstanceID(300130000)
public class MirenInstance extends GeneralInstanceHandler {

	private boolean rewarded = false;

	@Override
	public void onDie(Npc npc) {
		switch(npc.getNpcId()) {
			case 215222: // bosses
			case 215221:
				spawnChests(npc);
				break;
			case 215415: // artifact spawns weak boss
				Npc boss = getNpc(215222);
				if (boss != null && !boss.getLifeStats().isAlreadyDead()) {
					spawn(215221, boss.getX(), boss.getY(), boss.getZ(), boss.getHeading());
					boss.getController().onDelete();
				}	
		}
	}

	private void spawnChests(Npc npc) {
		if (!rewarded) {
			rewarded = true; //safety mechanism
			if (npc.getAi2().getRemainigTime() != 0) {
				long rtime = (600000 - npc.getAi2().getRemainigTime()) / 30000;
					spawn(700560, 477.8385f, 815.2885f, 199.70894f, (byte) 10);
					if (rtime > 1)
						spawn(700560, 470.61395f, 834.0493f, 199.70894f, (byte) 3);
					if (rtime > 2)
						spawn(700560, 470.45483f, 854.59467f, 199.70894f, (byte) 116);
					if (rtime > 3)
						spawn(700560, 477.13693f, 873.8804f, 199.70882f, (byte) 109);
					if (rtime > 4)
						spawn(700560, 489.75616f, 890.04535f, 199.70882f, (byte) 103);
					if (rtime > 5)
						spawn(700560, 507.70544f, 900.7769f, 199.70882f, (byte) 96);
					if (rtime > 6)
						spawn(700560, 527.9411f, 904.31696f, 199.70882f, (byte) 90);
					if (rtime > 7)
						spawn(700560, 548.3512f, 900.91455f, 199.70882f, (byte) 83);
					if (rtime > 8)
						spawn(700560, 565.88983f, 890.50494f, 199.70882f, (byte) 76);
					if (rtime > 9)
						spawn(700560, 579.3421f, 875.15894f, 199.70882f, (byte) 70);
					if (rtime > 10)
						spawn(700560, 586.4477f, 855.63904f, 199.70882f, (byte) 63);
					if (rtime > 11 && npc.getNpcId() == 215222)
						spawn(700540, 586.8586f, 835.00684f, 199.70882f, (byte) 57);
			}
		}
	}
}
