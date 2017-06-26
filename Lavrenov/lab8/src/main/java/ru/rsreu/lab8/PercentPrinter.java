package ru.rsreu.lab8;

import ru.rsreu.lab8.myvariants.MyVariantLock;

public class PercentPrinter {

  private static final double DELTA = 0.1;
  private long number = 0;
  private float percent = 0.0f;
  private final long maxNumber;
  private MyVariantLock lock = new MyVariantLock();

  public PercentPrinter(long maxNumber) {
    this.maxNumber = maxNumber;
  }

  public long getNumber() {
    lock.lock();
    long result = number;
    lock.unlock();
    return result;
  }

  public boolean needPrint() {
    lock.lock();
    boolean result = false;
    try {
      result = getCurrentPercent() >= percent;
    } finally {
      lock.unlock();
    }
    return result;
  }

  public void increaseIterationNumber(long delta) {
    lock.lock();
    number += delta;
    lock.unlock();
  }

  public void increasePercent() {
    lock.lock();
    percent += DELTA;
    lock.unlock();
  }

  public void printPercent() {
    lock.lock();
    try {
      int percents = (int) (getCurrentPercent() * 100);
      System.out.println(Thread.currentThread().getName() + " ready on " + percents + "%");
    } finally {
      lock.unlock();
    }
  }
  
  private float getCurrentPercent() {
    return (float) number / (float) maxNumber;
  }

}
