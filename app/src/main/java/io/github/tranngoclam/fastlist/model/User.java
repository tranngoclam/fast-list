package io.github.tranngoclam.fastlist.model;

/**
 * Created by lam on 4/16/17.
 */

public class User {

  private int age;

  private Birthday birthday;

  private CreditCard credit_card;

  private String email;

  private String gender;

  private String name;

  private String password;

  private String phone;

  private String photo;

  private String region;

  private String surname;

  private String title;

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
