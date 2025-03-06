package instance.abyss;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.Npc;

/**
 * @author keqi, xTz
 * @reworked Luzien
 */
@InstanceID(300120000)
public class KysisInstance extends GeneralInstanceHandler
{
	private boolean rewarded = false;

	@Override
	public void onDie(Npc npc)
	{
		switch (npc.getNpcId()) {
			case 215179: // bosses
			case 215178:
				spawnChests(npc);
				break;
			case 215414: // artifact spawns weakened boss
				Npc boss = getNpc(215179);
				if (boss != null && !boss.getLifeStats().isAlreadyDead()) {
					spawn(215178, boss.getX(), boss.getY(), boss.getZ(), boss.getHeading());
					boss.getController().onDelete();
				}
		}
	}

	private void spawnChests(Npc npc)
	{
		if (!rewarded) {
			rewarded = true; //safety mechanism
			if (npc.getAi2().getRemainigTime() != 0) {
				long rtime = (600000 - npc.getAi2().getRemainigTime()) / 30000; // Spawn chests depending on time needed for boss kill
				spawn(700560, 477.73404f, 814.9683f, 199.76048f, (byte) 10);
				if (rtime > 1)
					spawn(700560, 470.6889f, 834.18195f, 199.76048f, (byte) 3);
				if (rtime > 2)
					spawn(700560, 470.41586f, 854.92883f, 199.76048f, (byte) 116);
				if (rtime > 3)
					spawn(700560, 476.91608f, 874.42975f, 199.76048f, (byte) 109);
				if (rtime > 4)
					spawn(700560, 490.30203f, 890.54584f, 199.76048f, (byte) 103);
				if (rtime > 5)
					spawn(700560, 508.31192f, 900.53174f, 199.76048f, (byte) 97);
				if (rtime > 6)
					spawn(700560, 528.5654f, 904.8123f, 199.76048f, (byte) 89);
				if (rtime > 7)
					spawn(700560, 548.9561f, 901.37714f, 199.76048f, (byte) 83);
				if (rtime > 8)
					spawn(700560, 566.63776f, 890.9151f, 199.76048f, (byte) 76);
				if (rtime > 9)
					spawn(700560, 579.9134f, 875.0929f, 199.76048f, (byte) 70);
				if (rtime > 10)
					spawn(700560, 586.98816f, 855.7699f, 199.76048f, (byte) 62);
				if (rtime > 11 && npc.getNpcId() == 215179)
					spawn(700540, 586.25903f, 835.3925f, 199.76048f, (byte) 56); //last chest only for big boss
			}
		}
	}
}
