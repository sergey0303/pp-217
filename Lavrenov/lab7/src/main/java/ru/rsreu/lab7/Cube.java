package ru.rsreu.lab7;

import java.util.Random;

public class Cube {

  private Random generator;
  private int sides;

  public Cube(int s) {
    sides = s;
    generator = new Random();
  }

  public int cast() {
    return 1 + generator.nextInt(sides);
  }
}
