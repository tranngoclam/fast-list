package io.github.tranngoclam.fastlist.ui;

import com.facebook.drawee.view.SimpleDraweeView;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.tranngoclam.fastlist.R;
import io.github.tranngoclam.fastlist.model.User;

/**
 * Created by lam on 4/30/17.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {

  public static UserViewHolder create(ViewGroup parent) {
    return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
  }

  private SimpleDraweeView avatar;

  private TextView name, desc;

  private UserViewHolder(View itemView) {
    super(itemView);
    name = (TextView) itemView.findViewById(R.id.name);
    desc = (TextView) itemView.findViewById(R.id.desc);
    avatar = (SimpleDraweeView) itemView.findViewById(R.id.avatar);
  }

  public void bind(User user) {
    bindName(user.getName());
    bindDesc(user.getDesc());
    bindAvatar(user.getAvatar());
  }

  public void bindAvatar(String avatar) {
    this.avatar.setImageURI(Uri.parse(avatar));
  }

  public void bindDesc(String desc) {
    this.desc.setText(desc);
  }

  public void bindName(String name) {
    this.name.setText(name);
  }
}
