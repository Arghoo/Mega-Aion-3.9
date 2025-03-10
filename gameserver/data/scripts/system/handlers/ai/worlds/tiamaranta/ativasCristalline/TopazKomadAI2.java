package ai.worlds.tiamaranta.ativasCristalline;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;

/**
 * @author Ritsu
 */

@AIName("topazKomad")
public class TopazKomadAI2 extends AggressiveNpcAI2
{
	@Override
	protected void handleSpawned()
	{
		super.handleSpawned();
		int lifetime = (getNpcId() == 282709 ? 20000 : 10000);
		toDespawn(lifetime);
	}

	private void toDespawn(int delay)
	{
		ThreadPoolManager.getInstance().schedule(() -> AI2Actions.deleteOwner(TopazKomadAI2.this), delay);
	}
}
