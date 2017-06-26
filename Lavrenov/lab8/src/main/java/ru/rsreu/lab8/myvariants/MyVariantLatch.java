package ru.rsreu.lab8.myvariants;

public class MyVariantLatch {

  public MyVariantLatch(int count) {
    this.count = count;
  }

  public void await() throws InterruptedException {
    synchronized (monitor) {
      if (count > 0) {
        monitor.wait();
      }
    }
  }

  public void countDown() {
    synchronized (monitor) {
      count--;

      if (count == 0) {
        monitor.notifyAll();
      }
    }
  }

  private volatile int count;
  private Object monitor = new Object();

}
