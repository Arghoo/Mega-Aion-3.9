package ai.instance.argentManor;

import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author xTz
 */
@AIName("magical_sap")
public class MagicalSapAI2 extends NpcAI2
{
	@Override
	protected void handleSpawned()
	{
		super.handleSpawned();
		startEventTask(1000);
		startEventTask(4000);
		startEventTask(7000);
		startEventTask(10000);
	}

	private void startEventTask(final int time)
	{
		ThreadPoolManager.getInstance().schedule(() -> {
			if (!isAlreadyDead()) {
				SkillEngine.getInstance().getSkill(getOwner(), 19306, 55, getOwner()).useNoAnimationSkill();
				if (time == 10000) {
					AI2Actions.deleteOwner(MagicalSapAI2.this);
				}
			}
		}, time);

	}
}
