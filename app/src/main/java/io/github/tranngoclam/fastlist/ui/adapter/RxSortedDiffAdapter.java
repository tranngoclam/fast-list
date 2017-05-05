package io.github.tranngoclam.fastlist.ui.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import io.github.tranngoclam.fastlist.RxRecyclerViewAdapterCallback;
import io.github.tranngoclam.fastlist.RxSortedDiffList;
import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.UserViewHolder;

/**
 * Created by lam on 4/30/17.
 */

public class RxSortedDiffAdapter extends RecyclerView.Adapter<UserViewHolder> {

  private final RxSortedDiffList<User> mUsers;

  public RxSortedDiffAdapter() {
    mUsers = new RxSortedDiffList<>(User.class, new RxRecyclerViewAdapterCallback<User>(this) {

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
    }, this, User::getChangePayload);
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
      holder.bind(bundle);
    } else {
      onBindViewHolder(holder, position);
    }
  }

  @Override
  public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return UserViewHolder.create(parent);
  }

  public RxSortedDiffList<User> getRxSortedList() {
    return mUsers;
  }
}
