package ru.rsreu.lab3;

import java.util.ArrayList;

public class ProbabilityCalculator {

  private ProbabilityCalculator() {
  }

  /**
   * Ищет вероятность.
   * @return вероятность
   */
  public static float findProbability() {
    int validCount = 0;
    long starttime = System.nanoTime();
    for (int i = 0; i < DEFAULT_EXPERIMENT_COUNT; i++) {
      if (experiment() > VALID_THRESHHOLD) {
        validCount++;
      }
      if (i % (DEFAULT_EXPERIMENT_COUNT * 0.1) == 0) {
        System.out.println(i + " iterations from " + DEFAULT_EXPERIMENT_COUNT);
      }
    }
    System.out.println("Final time: " + (System.nanoTime() - starttime) / 1000000 + "ms");
    return (float) validCount / (float) DEFAULT_EXPERIMENT_COUNT;
  }

  /**
   * Кидает кубик.
   * @return количество очков
   */
  public static int experiment() {
    ArrayList<Cube> cubes = new ArrayList<Cube>();
    for (int i = 0; i < 10; i++) {
      cubes.add(new Cube(10));
    }
    int sum = 0;
    for (Cube cube : cubes) {
      int currentCast = cube.cast();

      while (currentCast == 10) {
        sum += currentCast;
        currentCast = cube.cast();
      }

      sum += currentCast;
    }

    return sum;
  }
  
  private static final int DEFAULT_EXPERIMENT_COUNT = 5000000;
  private static final int VALID_THRESHHOLD = 80;
}
