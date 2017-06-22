package ru.rsreu.lab5;


public class ThreadedCalculator {

  public ThreadedCalculator(int threadCount) {
    this.threadCount = threadCount;
  }

  public ThreadedCalculator() {
    this(Runtime.getRuntime().availableProcessors());
  }

  /**
   * Вероятность.
   * 
   * @throws InterruptedException
   *           если вычисление прервано
   */
  public float findProbability(long experimentCount) throws InterruptedException {
    final long experimentCountPerThread = experimentCount / threadCount;

    final long[] validCasts = new long[threadCount];
    for (int i = 0; i < threadCount; i++) {
      validCasts[i] = -1;
      final int threadNum = i;
      Thread thread = new Thread(() -> {
        long result = ProbabilityCalculator.findValidCasts(experimentCountPerThread);
        synchronized (validCasts) {
          validCasts[threadNum] = result;
          validCasts.notify();
        }
      });
      thread.setName(Integer.toString(i));
      thread.start();
    }

    long sumOfValid = 0;
    for (int i = 0; i < threadCount; i++) {
      synchronized (validCasts) {
        while (validCasts[i] == -1) {
          validCasts.wait();
        }
        sumOfValid += validCasts[i];
      }
    }

    return (float) sumOfValid / (float) experimentCount;
  }

  private int threadCount;
}
