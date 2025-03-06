package instance.abyss;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.Npc;

/**
 * @author keqi, xTz
 * @reworked Luzien
 */
@InstanceID(300140000)
public class KrotanInstance extends GeneralInstanceHandler
{
	private boolean rewarded = false;

	@Override
	public void onDie(Npc npc)
	{
		switch (npc.getNpcId()) {
			case 215136: //bosses
			case 215135:
				spawnChests(npc);
				break;
			case 215413: //artifact spawns weak boss
				Npc boss = getNpc(215136);
				if (boss != null && !boss.getLifeStats().isAlreadyDead()) {
					spawn(215135, boss.getX(), boss.getY(), boss.getZ(), boss.getHeading());
					boss.getController().onDelete();
				}
		}
	}

	private void spawnChests(Npc npc)
	{
		if (!rewarded) {
			rewarded = true; //safety mechanism
			if (npc.getAi2().getRemainigTime() != 0) {
				long rtime = (600000 - npc.getAi2().getRemainigTime()) / 30000;
				spawn(700560, 477.072f, 814.9592f, 199.70894f, (byte) 10);
				if (rtime > 1)
					spawn(700560, 470.33636f, 834.1712f, 199.70894f, (byte) 3);
				if (rtime > 2)
					spawn(700560, 469.93774f, 854.7877f, 199.70894f, (byte) 116);
				if (rtime > 3)
					spawn(700560, 476.60123f, 874.31824f, 199.70894f, (byte) 110);
				if (rtime > 4)
					spawn(700560, 489.96918f, 890.23126f, 199.70894f, (byte) 103);
				if (rtime > 5)
					spawn(700560, 508.0373f, 900.56744f, 199.70894f, (byte) 96);
				if (rtime > 6)
					spawn(700560, 528.1224f, 904.3239f, 199.70894f, (byte) 90);
				if (rtime > 7)
					spawn(700560, 548.21313f, 900.2219f, 199.70894f, (byte) 83);
				if (rtime > 8)
					spawn(700560, 566.0011f, 890.4714f, 199.70894f, (byte) 76);
				if (rtime > 9)
					spawn(700560, 579.15533f, 874.7605f, 199.70894f, (byte) 69);
				if (rtime > 10)
					spawn(700560, 585.96625f, 855.66157f, 199.70894f, (byte) 63);
				if (rtime > 11 && npc.getNpcId() == 215136)
					spawn(700540, 585.7932f, 835.0304f, 199.70894f, (byte) 56);
			}
		}
	}
}
