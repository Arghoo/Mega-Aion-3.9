package ai;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * Reworked by Mega Aion
 */
@AIName("coinfountaininggisongelkmaros")
public class CoinFountainAI2 extends NpcAI2
{
	@Override
	protected void handleDialogStart(Player player)
	{
		if (player.getCommonData().getLevel() >= 50 && (player.getRace() == Race.ASMODIANS)) {
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011, 21050));
		} else {
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011, 11050));
		}
	}

	public boolean onDialogSelect(Player player, int dialogId, int questId)
	{
		switch (dialogId) {
			case 10000:
				if (hasItem(player, 186000030) && (player.getRace() == Race.ASMODIANS)) {
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getOwner().getObjectId(), 5, 21050));
				} else {
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getOwner().getObjectId(), 5, 11050));
				}
				break;
			case 18:
				Item item = player.getInventory().getFirstItemByItemId(186000030);
				player.getInventory().decreaseByItemId(item.getObjectId(), 1);
				giveItem(player);
				PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getOwner().getObjectId(), 1008, 21050));
				PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getOwner().getObjectId(), 1008, 11050));
				break;
		}
		return true;
	}

	private boolean hasItem(Player player, int itemId)
	{
		return player.getInventory().getItemCountByItemId(itemId) > 0;
	}

	private void giveItem(Player player)
	{
		int rnd = Rnd.get(0, 100);
		if (rnd < 25) {
			ItemService.addItem(player, 186000096, 1);
		}
	}
}
