package io.github.tranngoclam.fastlist.util;

import java.util.Random;

/**
 * Created by lam on 5/3/17.
 */

public final class Utils {

  public static int randomize(int min, int max) {
    return min + new Random().nextInt(max);
  }

  public static int randomize(int max) {
    return new Random().nextInt(max);
  }
}
