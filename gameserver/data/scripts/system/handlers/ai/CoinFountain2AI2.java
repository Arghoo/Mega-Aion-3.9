package ai;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RewardType;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * Reworked by Mega Aion
 */
@AIName("coinfountaintiamarantabasilika")
public class CoinFountain2AI2 extends NpcAI2
{
	@Override
	protected void handleDialogStart(Player player)
	{
		if (player.getCommonData().getLevel() >= 50) {
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 10));
		} else {
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 10));
		}
	}

	public boolean onDialogSelect(Player player, int dialogId, int questId)
	{
		switch (dialogId) {
			case 10000:
				if (hasItem(player, 186000096) && (player.getRace() == Race.ASMODIANS)) {
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getOwner().getObjectId(), 5, 22061));
				} else {
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getOwner().getObjectId(), 5, 12061));
				}
				break;
			case 18:
				Item item = player.getInventory().getFirstItemByItemId(186000096);
				player.getInventory().decreaseByItemId(item.getObjectId(), 1);
				giveItem(player);
				player.getCommonData().addExp(1043900, RewardType.QUEST);
				PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getOwner().getObjectId(), 1008, 22061));
				PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getOwner().getObjectId(), 1008, 12061));
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
			ItemService.addItem(player, 186000147, 1);
		} else {
			ItemService.addItem(player, 182005205, 1);
		}
	}
}
