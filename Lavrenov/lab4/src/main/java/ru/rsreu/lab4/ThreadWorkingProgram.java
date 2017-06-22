package ru.rsreu.lab4;

import java.util.Scanner;

public class ThreadWorkingProgram {

  private static final String START_COMMAND = "start";
  private static final String STOP_COMMAND = "stop";
  private static final String AWAIT_COMMAND = "await";
  private static final String EXIT_COMMAND = "exit";

  private Scanner scanner;
  private ThreadManager threadManager;

  public ThreadWorkingProgram() {
    scanner = new Scanner(System.in);
    threadManager = new ThreadManager();
  }

  /**
   * Запускает.
   */
  public void launch() {
    boolean exit = false;

    System.out.println("������� �������");
    while (!exit) {
      String[] args = readArgs();

      final long argument;
      if (args.length >= 2) {
        try {
          argument = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
          continue;
        }
      } else {
        argument = -1;
      }

      if (args[0].equalsIgnoreCase(START_COMMAND) && argument != -1) {
        int number = threadManager.start(() -> {
          double probability = ProbabilityCalculator.findProbability(argument);

          System.out.print("����������� ����� = ");
          System.out.println(probability);
        });

        System.out.printf("Создан поток с номером %d\n", number);
      } else if (args[0].equalsIgnoreCase(STOP_COMMAND)) {
        if (!threadManager.stop((int) argument)) {
          System.out.printf("Поток с номером %d не существует\n", argument);
        }
      } else if (args[0].equalsIgnoreCase(AWAIT_COMMAND)) {
        if (!threadManager.await((int) argument)) {
          System.out.printf("Поток с номером %d не существует\n", argument);
        }
      } else if (args[0].equalsIgnoreCase(EXIT_COMMAND)) {
        threadManager.stopAll();
        exit = true;
      } else {
        System.out.println("Неверная комманда");
      }
    }
  }

  private String[] readArgs() {
    String line = "";
    if (scanner.hasNextLine()) {
      line = scanner.nextLine();
    }
    return line.trim().split(" ");
  }

}
