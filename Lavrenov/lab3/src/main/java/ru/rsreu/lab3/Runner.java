package ru.rsreu.lab3;

public class Runner {

  /**
   * Метод main.
   * @param args аргументы запуска
   */
  public static void main(String[] args) {

    final Thread CubeCaster = new Thread(new Runnable() {
      public void run() {
        System.out.println(ProbabilityCalculator.findProbability());
      }
    });
    CubeCaster.setName("CubeCaster");
    CubeCaster.start();

  }
}
