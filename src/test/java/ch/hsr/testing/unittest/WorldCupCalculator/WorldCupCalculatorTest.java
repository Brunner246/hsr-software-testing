package ch.hsr.testing.unittest.WorldCupCalculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldCupCalculatorTest {
    @Test
    void wrongResultGameLost() {
        WorldCupCalculator worldCupCalculator = new WorldCupCalculator();
        worldCupCalculator.setEstimationGoalsHomeTeam(1);
        worldCupCalculator.setEffectiveGoalsHomeTeam(4);

        worldCupCalculator.setEstimationGoalsGuestTeam(3);
        worldCupCalculator.setEffectiveGoalsGuestTeam(0);
        assertEquals(0, worldCupCalculator.calculateTipResult());
    }
}