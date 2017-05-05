package io.github.tranngoclam.fastlist.ui.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.List;

import io.github.tranngoclam.fastlist.RxRecyclerViewAdapterCallback;
import io.github.tranngoclam.fastlist.RxSortedDiffList;
import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.UserViewHolder;
import timber.log.Timber;

import static io.github.tranngoclam.fastlist.util.Utils.KEY_AVATAR;
import static io.github.tranngoclam.fastlist.util.Utils.KEY_DESC;
import static io.github.tranngoclam.fastlist.util.Utils.KEY_NAME;

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
    }, this, (oldItem, newItem) -> {
      Bundle bundle = new Bundle();
      if (!TextUtils.equals(oldItem.getAvatar(), newItem.getAvatar())) {
        bundle.putString(KEY_AVATAR, newItem.getAvatar());
      }
      if (!TextUtils.equals(oldItem.getName(), newItem.getName())) {
        bundle.putString(KEY_NAME, newItem.getName());
      }
      if (!TextUtils.equals(oldItem.getDesc(), newItem.getDesc())) {
        bundle.putString(KEY_DESC, newItem.getDesc());
      }
      if (bundle.size() == 0) {
        return null;
      }
      Timber.d("getChangePayload | size: %d", bundle.size());
      return bundle;
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

  public RxSortedDiffList<User> getRxSortedList() {
    return mUsers;
  }
}
