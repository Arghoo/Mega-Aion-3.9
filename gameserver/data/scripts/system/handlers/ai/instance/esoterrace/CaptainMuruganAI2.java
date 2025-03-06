package ai.instance.esoterrace;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("captain_murugan")
public class CaptainMuruganAI2 extends AggressiveNpcAI2
{
	private AtomicBoolean isAggred = new AtomicBoolean(false);
	private Future<?> task;
	private Future<?> specialSkillTask;

	@Override
	protected void handleAttack(Creature creature)
	{
		super.handleAttack(creature);
		if (isAggred.compareAndSet(false, true)) {
			startTaskEvent();
		}
	}

	private void startTaskEvent()
	{
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 19324, 10, target).useNoAnimationSkill();
		}
		task = ThreadPoolManager.getInstance().scheduleAtFixedRate(() -> {
			if (isAlreadyDead()) {
				cancelTask();
			} else {
				sendMsg(1500194);
				SkillEngine.getInstance().getSkill(getOwner(), 19325, 5, getOwner()).useNoAnimationSkill();
				if (getLifeStats().getHpPercentage() < 50) {
					specialSkillTask = ThreadPoolManager.getInstance().schedule(() -> {
						if (!isAlreadyDead()) {
							sendMsg(1500193);
							VisibleObject target1 = getTarget();
							if (target1 != null && target1 instanceof Player) {
								SkillEngine.getInstance().getSkill(getOwner(), 19324, 10, target1).useNoAnimationSkill();
							}
							specialSkillTask = ThreadPoolManager.getInstance().schedule(() -> {
								if (!isAlreadyDead()) {
									VisibleObject target2 = getTarget();
									if (target2 != null && target2 instanceof Player) {
										SkillEngine.getInstance().getSkill(getOwner(), 19324, 10, target2).useNoAnimationSkill();
									}
								}

							}, 4000);
						}

					}, 10000);
				}
			}
		}, 20000, 20000);

	}

	private void cancelTask()
	{
		if (task != null && !task.isDone()) {
			task.cancel(true);
		}
	}

	private void cancelSpecialSkillTask()
	{
		if (specialSkillTask != null && !specialSkillTask.isDone()) {
			specialSkillTask.cancel(true);
		}
	}

	@Override
	protected void handleBackHome()
	{
		cancelTask();
		cancelSpecialSkillTask();
		super.handleBackHome();
	}

	@Override
	protected void handleDespawned()
	{
		cancelTask();
		cancelSpecialSkillTask();
		super.handleDespawned();
	}

	private void sendMsg(int msg)
	{
		NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), 0, 0);
	}

	@Override
	protected void handleDied()
	{
		cancelTask();
		cancelSpecialSkillTask();
		sendMsg(1500195);
		super.handleDied();
	}
}
