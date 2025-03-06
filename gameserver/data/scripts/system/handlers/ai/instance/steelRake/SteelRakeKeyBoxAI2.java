package ai.instance.steelRake;

import ai.ChestAI2;

import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;

/**
 * @author xTz
 */
@AIName("steel_rake_key_box")
public class SteelRakeKeyBoxAI2 extends ChestAI2
{
	@Override
	protected void handleSpawned()
	{
		super.handleSpawned();
		ThreadPoolManager.getInstance().schedule(() -> {
			if (!isAlreadyDead() && getOwner().isSpawned()) {
				AI2Actions.deleteOwner(SteelRakeKeyBoxAI2.this);
			}
		}, 180000);
	}
}
