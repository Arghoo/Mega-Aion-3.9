package instance.pvparenas;

import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.playerreward.InstancePlayerReward;
import com.aionemu.gameserver.model.instance.playerreward.PvPArenaPlayerReward;

/**
 * @author xTz
 */
@InstanceID(300360000)
public class ArenaOfDisciplineInstance extends DisciplineTrainingGroundsInstance
{
	@Override
	protected void reward()
	{
		int totalPoints = instanceReward.getTotalPoints();
		int size = instanceReward.getInstanceRewards().size();
		// 100 * (rate * size) * (playerScore / playersScore)
		float totalAP = (4.0f * size) * 100; // to do config
		float totalCrucible = (2.0f * size) * 100; // to do config
		float totalCourage = (0.1f * size) * 100; // to do config
		for (InstancePlayerReward playerReward : instanceReward.getInstanceRewards()) {
			PvPArenaPlayerReward reward = (PvPArenaPlayerReward) playerReward;
			if (!reward.isRewarded()) {
				float playerRate = 1;
				Player player = instance.getPlayer(playerReward.getOwner());
				if (player != null) {
					playerRate = player.getRates().getDisciplineRewardRate();
				}
				int score = reward.getScorePoints();
				float scoreRate = ((float) score / (float) totalPoints);
				int rank = instanceReward.getRank(score);
				float percent = reward.getParticipation();
				int basicAP = 100;
				// to do other formula
				int rankingAP = 500;
				if (size > 1) {
					rankingAP = rank == 0 ? 1000 : 500;
				}
				int scoreAP = (int) (totalAP * scoreRate);
				basicAP *= percent;
				basicAP *= playerRate;
				rankingAP *= percent;
				rankingAP *= playerRate;
				reward.setBasicAP(basicAP);
				reward.setRankingAP(rankingAP);
				reward.setScoreAP(scoreAP);
				int basicCrI = 250;
				basicCrI *= percent;
				// to do other formula
				int rankingCrI = 250;
				if (size > 1) {
					rankingCrI = rank == 0 ? 500 : 250;
				}
				rankingCrI *= percent;
				rankingCrI *= playerRate;
				int scoreCrI = (int) (totalCrucible * scoreRate);
				reward.setBasicCrucible(basicCrI);
				reward.setRankingCrucible(rankingCrI);
				reward.setScoreCrucible(scoreCrI);
				int basicCoI = 10;
				basicCoI *= percent;
				// to do other formula
				int rankingCoI = 25;
				if (size > 1) {
					rankingCoI = rank == 0 ? 50 : 25;
				}
				rankingCoI *= percent;
				rankingCoI *= playerRate;
				int scoreCoI = (int) (totalCourage * scoreRate);
				reward.setBasicCourage(basicCoI);
				reward.setRankingCourage(rankingCoI);
				reward.setScoreCourage(scoreCoI);
				if (instanceReward.canRewardOpportunityToken(reward)) {
					reward.setOpportunity(10);
				}
			}
		}
		super.reward();
	}
}
