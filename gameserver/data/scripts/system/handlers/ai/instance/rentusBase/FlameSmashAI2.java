package ai.instance.rentusBase;

import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author xTz
 */
@AIName("flame_smash")
public class FlameSmashAI2 extends NpcAI2
{
	@Override
	protected void handleSpawned()
	{
		super.handleSpawned();
		starLifeTask();
		ThreadPoolManager.getInstance().schedule(() -> {
			if (!isAlreadyDead()) {
				SkillEngine.getInstance().getSkill(getOwner(), getNpcId() == 283008 ? 20540 : 20539, 60, getOwner()).useNoAnimationSkill();
			}
		}, 500);
	}

	private void starLifeTask()
	{
		ThreadPoolManager.getInstance().schedule(() -> despawn(), 7000);
	}

	private void despawn()
	{
		if (!isAlreadyDead()) {
			AI2Actions.deleteOwner(this);
		}
	}
}
