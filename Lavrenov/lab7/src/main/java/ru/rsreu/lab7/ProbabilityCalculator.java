package ru.rsreu.lab7;

import java.util.ArrayList;

public class ProbabilityCalculator {

  private ProbabilityCalculator() {
  }

  /**
   * Ищет вероятность.
   * 
   * @return вероятность
   */
  public static float findProbability(long experimentCount, PercentPrinter printer) {
    long validCount = findValidCasts(experimentCount, printer);
    return (float) validCount / (float) experimentCount;
  }

  /**
   * Найти количество бросков, удовлетворяющих условию.
   * 
   * @param experimentCount
   *          количество бросков.
   * @return валидных бросков
   */
  public static long findValidCasts(long experimentCount, PercentPrinter printer) {
    if (printer == null) {
      printer = new PercentPrinter(experimentCount);
    }
    final long delta = (long) (experimentCount * 0.1);
    int validCount = 0;
    for (long i = 0; i < experimentCount; i++) {
      if (experiment() > VALID_THRESHHOLD) {
        validCount++;
      }
      if (i % delta == 0) {
        printer.increaseIterationNumber(delta);
        if (printer.needPrint()) {
          printer.printPercent();
        }
      }
    }
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
