package ru.rsreu.lab5;

import java.util.ArrayList;

public class ProbabilityCalculator {

  private ProbabilityCalculator() {
  }

  /**
   * Ищет вероятность.
   * 
   * @return вероятность
   */
  public static float findProbability(long experimentCount) {
    long validCount = findValidCasts(experimentCount);
    return (float) validCount / (float) experimentCount;
  }

  /**
   * Найти количество бросков, удовлетворяющих условию.
   * 
   * @param experimentCount
   *          количество бросков.
   * @return валидных бросков
   */
  public static long findValidCasts(long experimentCount) {
    int validCount = 0;
    long starttime = System.nanoTime();
    for (int i = 0; i < experimentCount; i++) {
      if (experiment() > VALID_THRESHHOLD) {
        validCount++;
      }
      if (i % (experimentCount * 0.1) == 0) {
        System.out.println(Thread.currentThread().getName() + "|" + i + " from " + experimentCount);
      }
    }
    System.out.println("Final time: " + (System.nanoTime() - starttime) / 1000000 + "ms");
    return validCount;
  }

  /**
   * Кидает кубик.
   * 
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

  private static final int VALID_THRESHHOLD = 80;
}
