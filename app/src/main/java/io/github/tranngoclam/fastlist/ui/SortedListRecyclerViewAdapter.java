package io.github.tranngoclam.fastlist.ui;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.ViewGroup;

import io.github.tranngoclam.fastlist.model.User;

/**
 * Created by lam on 4/30/17.
 */

public class SortedListRecyclerViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

  private final SortedList<User> mUsers;

  public SortedListRecyclerViewAdapter() {
    mUsers = new SortedList<>(User.class, new SortedListAdapterCallback<User>(this) {
      @Override
      public boolean areContentsTheSame(User oldItem, User newItem) {
        return false;
      }

      @Override
      public boolean areItemsTheSame(User item1, User item2) {
        return false;
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
    return UserViewHolder.create(parent, viewType);
  }
}
