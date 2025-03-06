package com.aionemu.loginserver.network.gameserver.clientpackets;

import java.sql.Timestamp;

import com.aionemu.loginserver.dao.AccountDAO;
import com.aionemu.loginserver.model.Account;
import com.aionemu.loginserver.network.gameserver.GsClientPacket;
import com.aionemu.loginserver.network.gameserver.serverpackets.SM_LS_CONTROL_RESPONSE;

/**
 * @author Aionchs-Wylovech
 */
public class CM_LS_CONTROL extends GsClientPacket
{
	private String accountName;

	private int param;

	private int type;

	private String playerName;

	private String adminName;

	private boolean result;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readImpl()
	{

		type = readC();
		adminName = readS();
		accountName = readS();
		playerName = readS();
		param = readC();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void runImpl()
	{

		Account account = AccountDAO.getAccount(accountName);
		switch (type) {
			case 1:
				account.setAccessLevel((byte) param);
				break;
			case 2:
				account.setMembership((byte) param);
				if (param == (byte) 2) {
					long _30days = 30L * 24 * 60 * 60 * 1000;
					Timestamp oldDate = account.getMembershipExpiry();
					Timestamp date = oldDate != null ? new Timestamp(oldDate.getTime() + _30days) : new Timestamp(
						System.currentTimeMillis() + _30days);
					account.setMembershipExpiry(date);
				} else {
					account.setMembershipExpiry(null);
				}
				break;
		}
		result = AccountDAO.updateAccount(account);
		sendPacket(new SM_LS_CONTROL_RESPONSE(type, result, playerName, account.getId(), param, adminName));
	}
}
