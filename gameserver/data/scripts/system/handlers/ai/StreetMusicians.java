package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.concurrent.Future;

/**
 * @author Antraxx
 */
@AIName("street_musicians")
public class StreetMusicians extends GeneralNpcAI2
{
	private Future<?> task;

	@Override
	protected void handleSpawned()
	{
		super.handleSpawned();
		// Start Task with Delay

		ThreadPoolManager.getInstance().schedule(this::startTask, 1000);
	}

	@Override
	protected void handleDied()
	{
		super.handleDied();

		if (task != null && !task.isCancelled())
			task.cancel(true);
	}

	@Override
	protected void handleDespawned()
	{
		super.handleDespawned();

		if (task != null && !task.isCancelled())
			task.cancel(true);
	}

	private void startTask()
	{
		task = ThreadPoolManager.getInstance().scheduleAtFixedRate(this::applySkillToPlayers, 2000, 2000);
	}

	private void applySkillToPlayers()
	{
		int skillId = 8751; // Light Of Repose

		for (Player player : getKnownList().getKnownPlayers().values()) {
			if (isInRange(player, 5)) {
				long rMax = player.getCommonData().getMaxReposteEnergy();
				long rCur = player.getCommonData().getCurrentReposteEnergy();
				float rPercent = (100f / rMax) * rCur;
				if (rPercent <= 15.0f)
					if (!player.getEffectController().hasAbnormalEffect(skillId))
						SkillEngine.getInstance().applyEffectDirectly(skillId, getOwner(), player, DataManager.SKILL_DATA.getSkillTemplate(skillId).getDuration());
			}
		}
	}
}
