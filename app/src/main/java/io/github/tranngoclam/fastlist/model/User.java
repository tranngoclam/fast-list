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

  public static int compare(User user1, User user2) {
    int nameCompare = user1.getName().compareToIgnoreCase(user2.getName());
    int ageCompare = user1.age - user2.age;
    int genderCompare = user1.gender.compareToIgnoreCase(user2.gender);
    return nameCompare != 0 ? nameCompare : ageCompare != 0 ? ageCompare : genderCompare;
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
    return User.compare(this, user);
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
    return name + " " + surname;
  }

  public void switchGender() {
    if (gender.equals("male")) {
      gender = "female";
    } else {
      gender = "male";
    }
  }
}
