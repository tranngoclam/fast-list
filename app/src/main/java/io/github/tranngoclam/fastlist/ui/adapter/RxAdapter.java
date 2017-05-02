package io.github.tranngoclam.fastlist.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.UserViewHolder;

/**
 * Created by lam on 5/1/17.
 */

public class RxAdapter extends RecyclerView.Adapter<UserViewHolder> {

  private final List<User> mUsers;

  public RxAdapter() {
    mUsers = new ArrayList<>();
  }

  @Override
  public int getItemCount() {
    return 0;
  }

  @Override
  public void onBindViewHolder(UserViewHolder holder, int position) {

  }

  @Override
  public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }
}
