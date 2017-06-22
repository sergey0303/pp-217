package ru.rsreu.lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
   * @throws ExecutionException
   *           если во время вычисления произошла ошибка
   */
  public float findProbability(long experimentCount) throws ExecutionException, InterruptedException {
    final long experimentCountPerThread = experimentCount / threadCount;

    List<Future<Long>> futures = new ArrayList<Future<Long>>(threadCount);
    ExecutorService service = Executors.newFixedThreadPool(threadCount);
    for (int i = 0; i < threadCount; i++) {
      Future<Long> result = service.submit(() -> {
        return ProbabilityCalculator.findValidCasts(experimentCountPerThread);
      });
      futures.add(result);
    }

    long sumOfValid = 0;
    for (Future<Long> future : futures) {
      sumOfValid += future.get();
    }

    return (float) sumOfValid / (float) experimentCount;
  }

  private int threadCount;
}
