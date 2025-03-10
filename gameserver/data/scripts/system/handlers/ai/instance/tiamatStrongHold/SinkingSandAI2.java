package ai.instance.tiamatStrongHold;

import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;

/**
 * @author Cheatkiller
 */
@AIName("sinkingsand")
public class SinkingSandAI2 extends NpcAI2
{
	@Override
	protected void handleSpawned()
	{
		super.handleSpawned();
		useskill();
	}

	private void useskill()
	{
		ThreadPoolManager.getInstance().schedule(() -> {
			AI2Actions.useSkill(SinkingSandAI2.this, 20721);
			getOwner().getController().die();
		}, 3000);
	}
}
