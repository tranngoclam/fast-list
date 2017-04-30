package io.github.tranngoclam.fastlist;

import android.support.v7.util.DiffUtil;
import android.support.v7.util.SortedList;

import io.github.tranngoclam.fastlist.model.User;

/**
 * Created by lam on 4/16/17.
 */

public class UserDiffCallback extends DiffUtil.Callback {

  private final SortedList<User> mNewUsers;

  private final SortedList<User> mOldUsers;

  public UserDiffCallback(SortedList<User> oldUsers, SortedList<User> newUsers) {
    mOldUsers = oldUsers;
    mNewUsers = newUsers;
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return false;
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return false;
  }

  @Override
  public int getNewListSize() {
    return mNewUsers.size();
  }

  @Override
  public int getOldListSize() {
    return mOldUsers.size();
  }
}
