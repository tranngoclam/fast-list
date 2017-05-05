package io.github.tranngoclam.fastlist.ui.adapter;

import android.support.v7.util.SortedList;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.ViewGroup;

import java.util.List;

import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.UserViewHolder;
import timber.log.Timber;

/**
 * Created by lam on 4/30/17.
 */

public class SortedListAdapter extends BehavioralAdapter<User, UserViewHolder> {

  private final SortedList<User> mUsers;

  public SortedListAdapter() {
    mUsers = new SortedList<>(User.class, new SortedListAdapterCallback<User>(this) {
      @Override
      public boolean areContentsTheSame(User oldItem, User newItem) {
        return User.areContentsTheSame(oldItem, newItem);
      }

      @Override
      public boolean areItemsTheSame(User item1, User item2) {
        return User.areItemsTheSame(item1, item2);
      }

      @Override
      public int compare(User o1, User o2) {
        return User.compare(o1, o2);
      }
    });
  }

  @Override
  public void add(User data) {
    mUsers.add(data);
  }

  @Override
  public void addAll(List<User> data) {
    long begin = System.currentTimeMillis();
    mUsers.addAll(data);
    long end = System.currentTimeMillis();
    Timber.v("begin: %d, end: %d, duration: %d", begin, end, end - begin);
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
    mUsers.remove(data);
  }

  @Override
  public void remove(int index) {
    mUsers.removeItemAt(index);
  }

  @Override
  public void set(int index, User data) {
    mUsers.updateItemAt(index, data);
  }

  @Override
  public void set(List<User> data) {
    clear();
    addAll(data);
  }

  @Override
  public void setAndNotifyAll(List<User> users) {
    set(users);
  }

  public SortedList<User> getUsers() {
    return mUsers;
  }
}
