package io.github.tranngoclam.fastlist.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import io.github.tranngoclam.fastlist.RxSortedList;
import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.UserViewHolder;

/**
 * Created by lam on 4/30/17.
 */

public class RxSortedDiffAdapter extends RecyclerView.Adapter<UserViewHolder> {

  private final RxSortedList<User> mUsers;

  public RxSortedDiffAdapter() {
    mUsers = new RxSortedList<>();
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

  public RxSortedList<User> getRxSortedList() {
    return mUsers;
  }
}
