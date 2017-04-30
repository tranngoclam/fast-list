package io.github.tranngoclam.fastlist.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.tranngoclam.fastlist.R;
import io.github.tranngoclam.fastlist.model.User;

/**
 * Created by lam on 4/30/17.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {

  public static UserViewHolder create(ViewGroup parent, int viewType) {
    return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
  }

  private UserViewHolder(View itemView) {
    super(itemView);
  }

  public void bind(User user) {

  }
}
