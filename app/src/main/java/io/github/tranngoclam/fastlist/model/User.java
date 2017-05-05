package io.github.tranngoclam.fastlist.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import timber.log.Timber;

import static io.github.tranngoclam.fastlist.util.Utils.KEY_AVATAR;
import static io.github.tranngoclam.fastlist.util.Utils.KEY_DESC;
import static io.github.tranngoclam.fastlist.util.Utils.KEY_NAME;

/**
 * Created by lam on 4/16/17.
 */

public class User implements Comparable<User> {

  public static boolean areContentsTheSame(User oldUser, User newUser) {
    return TextUtils.equals(oldUser.getName(), newUser.getName()) && oldUser.age == newUser.age && TextUtils
        .equals(oldUser.gender, newUser.gender);
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

  public static Object getChangePayload(User oldUser, User newUser) {
    Bundle bundle = new Bundle();
    if (!TextUtils.equals(oldUser.getAvatar(), newUser.getAvatar())) {
      bundle.putString(KEY_AVATAR, newUser.getAvatar());
    }
    if (!TextUtils.equals(oldUser.getName(), newUser.getName())) {
      bundle.putString(KEY_NAME, newUser.getName());
    }
    if (!TextUtils.equals(oldUser.getDesc(), newUser.getDesc())) {
      bundle.putString(KEY_DESC, newUser.getDesc());
    }
    if (bundle.size() == 0) {
      return null;
    }
    Timber.d("getChangePayload | size: %d", bundle.size());
    return bundle;
  }

  public int age;

  public String gender;

  public String name;

  public String password;

  public String phone;

  public String photo;

  public String surname;

  public User(int age, String gender, String name, String password, String phone, String photo, String surname) {
    this.age = age;
    this.gender = gender;
    this.name = name;
    this.password = password;
    this.phone = phone;
    this.photo = photo;
    this.surname = surname;
  }

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
