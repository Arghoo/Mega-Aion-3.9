package ai.instance.beshmundirTemple;

import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;

import ai.AggressiveNpcAI2;

/**
 * @author Luzien
 * TODO: Random aggro switch
 */
@AIName("isbariyaServants")
public class IsbariyaServantsAI2 extends AggressiveNpcAI2
{
	@Override
	protected void handleSpawned()
	{
		super.handleSpawned();
		int lifetime = (getNpcId() == 281659 ? 20000 : 10000);
		toDespawn(lifetime);
	}

	private void toDespawn(int delay)
	{
		ThreadPoolManager.getInstance().schedule(() -> AI2Actions.deleteOwner(IsbariyaServantsAI2.this), delay);
	}
}
