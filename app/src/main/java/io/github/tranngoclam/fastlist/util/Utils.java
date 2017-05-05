package io.github.tranngoclam.fastlist.util;

import java.util.Random;

/**
 * Created by lam on 5/3/17.
 */

public final class Utils {

  public static int randomize(int min, int max) {
    if (min == max) return min;
    if (max < min) throw new IllegalArgumentException("Max should be greater than min");

    int range = max - min;
    return (int) (Math.random() * range) + min;
  }

  public static int randomize(int max) {
    return new Random().nextInt(max);
  }
}
