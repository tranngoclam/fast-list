package io.github.tranngoclam.fastlist.ui.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.UserViewHolder;
import timber.log.Timber;

/**
 * Created by lam on 4/30/17.
 */

public class DiffUtilAdapter extends BehavioralAdapter<User, UserViewHolder> {

  static final String KEY_AVATAR = "key_avatar";

  static final String KEY_DESC = "key_desc";

  static final String KEY_NAME = "key_name";

  private final List<User> mUsers;

  public DiffUtilAdapter() {
    this.mUsers = new ArrayList<>();
  }

  @Override
  public void add(User data) {
    List<User> users = new ArrayList<>(mUsers);
    mUsers.add(data);
    sortThenCalculateDiff(users);
  }

  @Override
  public void addAll(List<User> data) {
    List<User> users = new ArrayList<>(mUsers);
    mUsers.addAll(data);
    sortThenCalculateDiff(users);
  }

  @Override
  public void add(int index, User data) {
    List<User> users = new ArrayList<>(mUsers);
    mUsers.add(index, data);
    sortThenCalculateDiff(users);
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
  public void onBindViewHolder(UserViewHolder holder, int position, List<Object> payloads) {
    if (payloads != null && !payloads.isEmpty() && payloads.get(0) instanceof Bundle) {
      Bundle bundle = (Bundle) payloads.get(0);
      if (bundle.containsKey(KEY_AVATAR)) {
        Timber.d("onBindViewHolder | avatar");
        holder.bindAvatar(bundle.getString(KEY_AVATAR));
      }
      if (bundle.containsKey(KEY_NAME)) {
        Timber.d("onBindViewHolder | name");
        holder.bindName(bundle.getString(KEY_NAME));
      }
      if (bundle.containsKey(KEY_DESC)) {
        Timber.d("onBindViewHolder | desc");
        holder.bindDesc(bundle.getString(KEY_DESC));
      }
    } else {
      onBindViewHolder(holder, position);
    }
  }

  @Override
  public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return UserViewHolder.create(parent);
  }

  @Override
  public void remove(User data) {
    List<User> users = new ArrayList<>(mUsers);
    mUsers.remove(data);
    sortThenCalculateDiff(users);
  }

  @Override
  public void remove(int index) {
    List<User> users = new ArrayList<>(mUsers);
    mUsers.remove(index);
    sortThenCalculateDiff(users);
  }

  @Override
  public void set(int index, User data) {
    List<User> users = new ArrayList<>(mUsers);
    mUsers.set(index, data);
    sortThenCalculateDiff(users);
  }

  @Override
  public void set(List<User> data) {
    List<User> users = new ArrayList<>(mUsers);
    mUsers.clear();
    mUsers.addAll(data);
    sortThenCalculateDiff(users);
  }

  private void sortThenCalculateDiff(List<User> users) {
    Collections.sort(mUsers, User::compare);
    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
      @Override
      public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        User oldUser = users.get(oldItemPosition);
        User newUser = mUsers.get(newItemPosition);
        return User.areContentsTheSame(oldUser, newUser);
      }

      @Override
      public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        User oldUser = users.get(oldItemPosition);
        User newUser = mUsers.get(newItemPosition);
        return User.areItemsTheSame(oldUser, newUser);
      }

      @Nullable
      @Override
      public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        User oldUser = users.get(oldItemPosition);
        User newUser = mUsers.get(newItemPosition);
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

      @Override
      public int getNewListSize() {
        return mUsers.size();
      }

      @Override
      public int getOldListSize() {
        return users.size();
      }
    });
    diffResult.dispatchUpdatesTo(this);
  }
}