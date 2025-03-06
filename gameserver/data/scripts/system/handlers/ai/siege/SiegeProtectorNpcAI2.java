package ai.siege;

import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;

/**
 * @author ATracer, Source
 */
@AIName("siege_protector")
public class SiegeProtectorNpcAI2 extends SiegeNpcAI2
{
	//spawn for quest
	@Override
	protected void handleDied()
	{
		super.handleDied();
		int npc = getOwner().getNpcId();
		switch (npc) {
			case 259614:
				spawn(701237, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
				despawnClaw();
				break;
		}
	}

	private void despawnClaw()
	{
		final Npc claw = getPosition().getWorldMapInstance().getNpc(701237);
		ThreadPoolManager.getInstance().schedule(() -> claw.getController().onDelete(), 60000 * 5);
	}
}
