package io.github.tranngoclam.fastlist.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by lam on 4/16/17.
 */

public class User implements Comparable<User> {

  public static boolean areContentsTheSame(User oldUser, User newUser) {
    return oldUser.age == newUser.age && TextUtils.equals(oldUser.gender, newUser.gender) && TextUtils.equals(oldUser.name, newUser.name);
  }

  public static boolean areItemsTheSame(User user1, User user2) {
    return TextUtils.equals(user1.getId(), user2.getId());
  }

  public String name;

  private int age;

  private Birthday birthday;

  private CreditCard credit_card;

  private String email;

  private String gender;

  private String password;

  private String phone;

  private String photo;

  private String region;

  private String surname;

  private String title;

  @Override
  public int compareTo(@NonNull User user) {
    return getName().compareTo(user.getName());
  }

  public String getAvatar() {
    return photo;
  }

  public String getDesc() {
    return gender + ", " + age + ", " + phone;
  }

  public String getId() {
    return password;
  }

  public String getName() {
    return title + ". " + name + " " + surname;
  }
}
