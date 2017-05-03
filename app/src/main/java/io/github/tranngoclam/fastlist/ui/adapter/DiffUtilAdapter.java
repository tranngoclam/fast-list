package io.github.tranngoclam.fastlist.ui.adapter;

import android.support.v7.util.DiffUtil;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.UserViewHolder;

/**
 * Created by lam on 4/30/17.
 */

public class DiffUtilAdapter extends BehavioralAdapter<User, UserViewHolder> {

  private final List<User> mUsers;

  public DiffUtilAdapter() {
    this.mUsers = new ArrayList<>();
  }

  @Override
  public void add(User data) {

  }

  @Override
  public void add(List<User> data) {

  }

  @Override
  public void add(int index, User data) {

  }

  @Override
  public void clear() {
    mUsers.clear();
  }

  @Override
  public User get(int index) {
    if (index >= 0 && index < getItemCount()) {
      return mUsers.get(index);
    }
    return null;
  }

  @Override
  public int getItemCount() {
    return mUsers.size();
  }

  @Override
  public void onBindViewHolder(UserViewHolder holder, int position) {
    holder.bind(mUsers.get(position));
  }

  @Override
  public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return UserViewHolder.create(parent);
  }

  @Override
  public void remove(User data) {

  }

  @Override
  public void remove(int index) {

  }

  @Override
  public void set(int index, User data) {
    List<User> oldUsers = new ArrayList<>(mUsers);
    mUsers.set(index, data);
    sortThenCalculateDiff(oldUsers);
  }

  @Override
  public void set(List<User> data) {
    List<User> oldUsers = new ArrayList<>(mUsers);
    mUsers.clear();
    mUsers.addAll(data);
    sortThenCalculateDiff(oldUsers);
  }

  private void sortThenCalculateDiff(List<User> oldUsers) {
    Collections.sort(oldUsers, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
      @Override
      public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        User oldUser = oldUsers.get(oldItemPosition);
        User newUser = mUsers.get(newItemPosition);
        return User.areContentsTheSame(oldUser, newUser);
      }

      @Override
      public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        User oldUser = oldUsers.get(oldItemPosition);
        User newUser = mUsers.get(newItemPosition);
        return User.areItemsTheSame(oldUser, newUser);
      }

      @Override
      public int getNewListSize() {
        return oldUsers.size();
      }

      @Override
      public int getOldListSize() {
        return mUsers.size();
      }
    });
    diffResult.dispatchUpdatesTo(this);
  }
}