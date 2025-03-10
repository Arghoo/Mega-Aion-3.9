package instance.pvparenas;

import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.playerreward.InstancePlayerReward;
import com.aionemu.gameserver.model.instance.playerreward.PvPArenaPlayerReward;

/**
 * @author xTz
 */
@InstanceID(300350000)
public class ArenaOfChaosInstance extends ChaosTrainingGroundsInstance
{
	@Override
	protected void reward()
	{
		int totalPoints = instanceReward.getTotalPoints();
		int size = instanceReward.getInstanceRewards().size();
		float totalScoreAP = (1.0f * size) * 100;
		float totalScoreCrucible = (0.01f * size) * 100;
		float totalScoreCourage = (0.01f * size) * 100;
		float rankingRate = 0;
		if (size > 1) {
			rankingRate = (0.077f * (8 - size));
		}
		float totalRankingAP = 1000 - 500 * rankingRate;
		float totalRankingCrucible = 200 - 100 * rankingRate;
		float totalRankingCourage = 50 - 25 * rankingRate;
		for (InstancePlayerReward playerReward : instanceReward.getInstanceRewards()) {
			PvPArenaPlayerReward reward = (PvPArenaPlayerReward) playerReward;
			if (!reward.isRewarded()) {
				int score = reward.getScorePoints();
				float scoreRate = ((float) score / (float) totalPoints);
				int rank = instanceReward.getRank(score);
				float percent = reward.getParticipation();
				float generalRate = 0.167f + rank * 0.095f;
				int basicAP = 100;
				float rankingAP = totalRankingAP;
				if (rank > 0) {
					rankingAP = rankingAP - rankingAP * generalRate;
				}
				int scoreAP = (int) (totalScoreAP * scoreRate);
				basicAP *= percent;
				rankingAP *= percent;
				reward.setBasicAP(basicAP);
				reward.setRankingAP((int) rankingAP);
				reward.setScoreAP(scoreAP);
				int basicCrI = 100;
				basicCrI *= percent;
				float rankingCrI = totalRankingCrucible;
				if (rank > 0) {
					rankingCrI = rankingCrI - rankingCrI * generalRate;
				}
				rankingCrI *= percent;
				int scoreCrI = (int) (totalScoreCrucible * scoreRate);
				reward.setBasicCrucible(basicCrI);
				reward.setRankingCrucible((int) rankingCrI);
				reward.setScoreCrucible(scoreCrI);
				int basicCoI = 25;
				basicCoI *= percent;
				float rankingCoI = totalRankingCourage;
				if (rank > 0) {
					rankingCoI = rankingCoI - rankingCoI * generalRate;
				}
				rankingCoI *= percent;
				int scoreCoI = (int) (totalScoreCourage * scoreRate);
				reward.setBasicCourage(basicCoI);
				reward.setRankingCourage((int) rankingCoI);
				reward.setScoreCourage(scoreCoI);
			}
		}
		super.reward();
	}
}
