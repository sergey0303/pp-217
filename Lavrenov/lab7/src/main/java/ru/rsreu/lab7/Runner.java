package ru.rsreu.lab7;

import java.util.concurrent.ExecutionException;

public class Runner {

  /**
   * Метод main.
   * 
   * @param args
   *          аргументы запуска
   */
  public static void main(String[] args) {
    try {
      ThreadedCalculator calculator = new ThreadedCalculator();
      float result = calculator.findProbability(5000000);
      System.out.println(result);
    } catch (InterruptedException e) {
      System.err.println("Interrupted");
    } catch (ExecutionException e) {
      System.err.println(e.getCause().getMessage());
    }

  }
}
