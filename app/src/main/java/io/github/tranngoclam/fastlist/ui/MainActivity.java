package io.github.tranngoclam.fastlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.github.tranngoclam.fastlist.R;

public class MainActivity extends BaseActivity {

  @Override
  protected int getLayoutRes() {
    return R.layout.activity_main;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ListView listView = (ListView) findViewById(R.id.list);
    listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
        new String[]{
            "Regular Adapter",
            "Adapter with DiffUtil",
            "Adapter with SortedList",
            "Adapter with RxSortedDiffList"
        }));
    listView.setOnItemClickListener((parent, view, position, id) -> {
      Intent intent;
      switch (position) {
        default:
        case 0:
          intent = ListActivity.newIntent(this, ListActivity.MODE_REGULAR);
          break;
        case 1:
          intent = ListActivity.newIntent(this, ListActivity.MODE_DIFF_UTIL);
          break;
        case 2:
          intent = ListActivity.newIntent(this, ListActivity.MODE_SORTED_LIST);
          break;
        case 3:
          intent = ListActivity.newIntent(this, ListActivity.MODE_RX_SORTED_DIFF);
          break;
      }
      startActivity(intent);
    });
  }
}
