package ch.hsr.testing.unittest.WorldCupCalculator;

public class WorldCupCalculator {

    private int mGoalsHomeTeamEstimation;

    private int mGoalsGuestTeamEstimation;

    private int mGoalsHomeTeamEffective;

    private int mGoalsGuestTeamEffective;

    public WorldCupCalculator()
    {
        mGoalsHomeTeamEstimation = -1;
        mGoalsGuestTeamEstimation = -1;
        mGoalsHomeTeamEffective = -1;
        mGoalsGuestTeamEffective = -1;
    }

    public int calculateTipResult() {
        if (mGoalsGuestTeamEstimation != mGoalsGuestTeamEffective
                && mGoalsHomeTeamEstimation != mGoalsHomeTeamEffective) {
            return 0;
        }
        return -1;
    }

    public void setEstimationGoalsHomeTeam(int aGoals) {
        if (aGoals >= 0) {
            mGoalsHomeTeamEstimation = aGoals;
        } else {
            throw new IllegalArgumentException("Goals must be a non-negative integer.");
        }
    }

    public void setEstimationGoalsGuestTeam(int aGoals) {
        if (aGoals >= 0) {
            mGoalsGuestTeamEstimation = aGoals;
        } else {
            throw new IllegalArgumentException("Goals must be a non-negative integer.");
        }
    }

    public void setEffectiveGoalsHomeTeam(int aGoals) {
        if (aGoals >= 0) {
            mGoalsHomeTeamEffective = aGoals;
        } else {
            throw new IllegalArgumentException("Goals must be a non-negative integer.");
        }
    }

    public void setEffectiveGoalsGuestTeam(int aGoals) {
        if (aGoals >= 0) {
            mGoalsGuestTeamEffective = aGoals;
        } else {
            throw new IllegalArgumentException("Goals must be a non-negative integer.");
        }
    }
}
