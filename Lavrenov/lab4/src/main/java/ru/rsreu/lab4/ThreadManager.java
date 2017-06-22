package ru.rsreu.lab4;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadManager {

  private AtomicInteger counter;
  private Map<Integer, Thread> threads;

  public ThreadManager() {
    counter = new AtomicInteger(0);
    threads = new ConcurrentHashMap<>();
  }

  /**
   * Стартует новый поток.
   * 
   * @param runnable
   *          задача для потока
   * @return номер потока
   */
  public int start(Runnable runnable) {
    int number = counter.incrementAndGet();

    Thread thread = new Thread(() -> {
      runnable.run();
      System.out.println("[THREAD " + Thread.currentThread().getName() + "]" + " ended");
      threads.remove(number);
    });

    threads.put(number, thread);

    thread.setName(String.valueOf(number));
    thread.start();

    return number;
  }

  /**
   * Останавливает поток.
   * 
   * @param number
   *          номер потока для остановки
   * @return остановился ли поток?
   */
  public boolean stop(int number) {
    Thread thread = threads.get(number);

    if (thread == null) {
      return false;
    }

    thread.interrupt();

    return true;
  }

  /**
   * Остановить все потоки.
   */
  public void stopAll() {
    for (Thread thread : threads.values()) {
      thread.interrupt();
    }
  }

  /**
   * Дождаться выполнения n-ного потока.
   * 
   * @param number
   *          номер потока
   * @return дождался ли?
   */
  public boolean await(int number) {
    Thread thread = threads.get(number);

    if (thread == null) {
      return false;
    }

    try {
      thread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    return true;
  }

  /**
   * Дождаться выполнения всех потоков.
   */
  public void awaitAll() {
    for (int number : threads.keySet()) {
      await(number);
    }
  }

}
