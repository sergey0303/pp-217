package ru.rsreu.lab8;

import org.junit.Assert;
import org.junit.Test;

import ru.rsreu.lab8.ProbabilityCalculator;

public class TestCube {

  @Test
  public void testProbability() {
    float result = ProbabilityCalculator.findProbability(5000000, null) * 100;
    Assert.assertEquals(result, EXPECTED_VALUE, INACCURACY);
  }

  private static final double EXPECTED_VALUE = 8.86;
  private static final double INACCURACY = 0.05;
}
