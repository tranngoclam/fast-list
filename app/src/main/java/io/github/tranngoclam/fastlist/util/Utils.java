package io.github.tranngoclam.fastlist.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.github.tranngoclam.fastlist.model.User;

/**
 * Created by lam on 5/3/17.
 */

public final class Utils {

  public static final String KEY_AVATAR = "key_avatar";

  public static final String KEY_DESC = "key_desc";

  public static final String KEY_NAME = "key_name";

  public static List<User> copyAndSwitchName(List<User> users) {
    List<User> newUsers = new ArrayList<>(users.size());
    for (User user : users) {
      User newUser = new User(user.age, user.gender, user.surname, user.password, user.phone, user.photo, user.name);
      newUsers.add(newUser);
    }
    Collections.shuffle(newUsers);
    return newUsers;
  }

  public static int randomize(int max) {
    return new Random().nextInt(max);
  }

  public static int randomize(int min, int max) {
    if (min == max) {
      return min;
    }
    if (max < min) {
      throw new IllegalArgumentException("Max should be greater than min");
    }

    int range = max - min;
    return (int) (Math.random() * range) + min;
  }
}
