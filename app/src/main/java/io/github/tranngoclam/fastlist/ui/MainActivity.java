package io.github.tranngoclam.fastlist.ui;

import android.content.Intent;
import android.os.Bundle;

import butterknife.OnClick;
import io.github.tranngoclam.fastlist.R;

public class MainActivity extends BaseActivity {

  @Override
  protected int getLayoutRes() {
    return R.layout.activity_main;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @OnClick(R.id.regular)
  public void onRegularClick() {
    Intent intent = ListActivity.newIntent(this, ListActivity.MODE_REGULAR);
    startActivity(intent);
  }

  @OnClick(R.id.sorted_list)
  public void onSortedListClick() {
    Intent intent = ListActivity.newIntent(this, ListActivity.MODE_SORTED_LIST);
    startActivity(intent);
  }
}
