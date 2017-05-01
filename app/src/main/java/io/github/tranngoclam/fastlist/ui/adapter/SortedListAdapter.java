package io.github.tranngoclam.fastlist.ui.adapter;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.Collection;

import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.UserViewHolder;

/**
 * Created by lam on 4/30/17.
 */

public class SortedListAdapter extends RecyclerView.Adapter<UserViewHolder> {

  private final SortedList<User> mUsers;

  public SortedListAdapter() {
    mUsers = new SortedList<>(User.class, new SortedListAdapterCallback<User>(this) {
      @Override
      public boolean areContentsTheSame(User oldItem, User newItem) {
        return TextUtils.equals(oldItem.getName(), newItem.getName());
      }

      @Override
      public boolean areItemsTheSame(User item1, User item2) {
        return TextUtils.equals(item1.getId(), item2.getId());
      }

      @Override
      public int compare(User o1, User o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
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

  public void add(User user) {
    mUsers.add(user);
  }

  public void addAll(User... users) {
    mUsers.addAll();
  }

  public void addAll(Collection<User> users) {
    mUsers.addAll(users);
  }

  public boolean remove(User user) {
    return mUsers.remove(user);
  }

  public void updateItemAt(int index, User user) {
    mUsers.updateItemAt(index, user);
  }
}
