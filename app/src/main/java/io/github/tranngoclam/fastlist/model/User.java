package io.github.tranngoclam.fastlist.model;

/**
 * Created by lam on 4/16/17.
 */

public class User {

  private String cell;

  private String dob;

  private String email;

  private String gender;

  private Id id;

  private Location location;

  private Name name;

  private String nat;

  private String password;

  private String phone;

  private Picture picture;

  private String registered;

  public String getId() {
    return id.value;
  }

  public String getName() {
    return name.first + " " + name.last;
  }
}
