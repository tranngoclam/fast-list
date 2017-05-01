package io.github.tranngoclam.fastlist.ui.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.UserViewHolder;

/**
 * Created by lam on 4/30/17.
 */

public class DiffUtilAdapter extends RecyclerView.Adapter<UserViewHolder> {

  private final List<User> mUsers;

  public DiffUtilAdapter() {
    this.mUsers = new ArrayList<>();
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

  public void set(List<User> newUsers) {
    List<User> oldUsers = new ArrayList<>(this.mUsers);
    this.mUsers.clear();
    this.mUsers.addAll(newUsers);
    sortThenCalculateDiff(oldUsers);
  }

  private void sortThenCalculateDiff(List<User> oldUsers) {
    Collections.sort(oldUsers, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
      @Override
      public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        User oldUser = oldUsers.get(oldItemPosition);
        User newUser = mUsers.get(newItemPosition);
        return TextUtils.equals(oldUser.getName(), newUser.getName());
      }

      @Override
      public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        User oldUser = oldUsers.get(oldItemPosition);
        User newUser = mUsers.get(newItemPosition);
        return TextUtils.equals(oldUser.getId(), newUser.getId());
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