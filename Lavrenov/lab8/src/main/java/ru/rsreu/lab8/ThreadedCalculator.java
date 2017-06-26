package ru.rsreu.lab8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import ru.rsreu.lab8.myvariants.MyVariantLatch;
import ru.rsreu.lab8.myvariants.MyVariantSemaphore;

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
    final long[] time = new long[threadCount];

    PercentPrinter printer = new PercentPrinter(experimentCount);
    List<Future<Long>> futures = new ArrayList<Future<Long>>(threadCount);
    ExecutorService service = Executors.newFixedThreadPool(threadCount);
    MyVariantSemaphore semaphore = new MyVariantSemaphore(threadCount / 2);
    MyVariantLatch latch = new MyVariantLatch(threadCount);
    for (int i = 0; i < threadCount; i++) {
      final int threadNumber = i;
      Future<Long> result = service.submit(() -> {
        long localResult = 0;
        try {
          semaphore.acquire();
          localResult = ProbabilityCalculator.findValidCasts(experimentCountPerThread,printer);
          time[threadNumber] = System.nanoTime();
        } finally {
          semaphore.release();
          latch.countDown();
        }
        return localResult;
      });
      futures.add(result);
    }

    latch.await();
    long globalTime = System.nanoTime();
    for (int i = 0; i < threadCount; i++) {
      final long executionTime = globalTime - time[i];
      System.out.println("time of " + i + " th thread = " + executionTime + "ns");
    }

    long sumOfValid = 0;
    for (Future<Long> future : futures) {
      sumOfValid += future.get();
    }

    return (float) sumOfValid / (float) experimentCount;
  }

  private int threadCount;
}
