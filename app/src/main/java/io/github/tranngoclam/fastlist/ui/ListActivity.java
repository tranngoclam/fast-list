package io.github.tranngoclam.fastlist.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import io.github.tranngoclam.fastlist.R;
import io.github.tranngoclam.fastlist.RestService;
import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.adapter.BehavioralAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.DiffUtilAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.RegularAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.RxAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.RxSortedDiffAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.SortedDiffAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.SortedListAdapter;
import io.github.tranngoclam.fastlist.util.Transformer;
import me.henrytao.mdcore.utils.Ln;

public class ListActivity extends BaseActivity {

  public static final int MODE_DIFF_UTIL = 2;

  public static final int MODE_REACTIVE = 3;

  public static final int MODE_REGULAR = 0;

  public static final int MODE_SORTED_LIST = 1;

  static final int DEFAULT_AMOUNT = 10;

  static final String EXTRA_MODE = "extra_mode";

  public static Intent newIntent(Context context, int mode) {
    Intent intent = new Intent(context, ListActivity.class);
    intent.putExtra(EXTRA_MODE, mode);
    return intent;
  }

  @Inject RestService mRestService;

  private BehavioralAdapter<User> mBehavioralAdapter;

  private DiffUtilAdapter mDiffUtilAdapter;

  private RecyclerView mRecyclerView;

  private RegularAdapter mRegularAdapter;

  private RxAdapter mRxAdapter;

  private RxSortedDiffAdapter mRxSortedDiffAdapter;

  private SortedDiffAdapter mSortedDiffAdapter;

  private SortedListAdapter mSortedListAdapter;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_action, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_add_single:
        mRestService.getUsers(1)
            .filter(users -> users != null && !users.isEmpty())
            .map(users -> users.get(0))
            .compose(Transformer.applyIoMaybeTransformer())
            .compose(bindToLifecycle())
            .subscribe(user -> {
              int index = (int) (Math.random() * mBehavioralAdapter.getItemCount());
              mBehavioralAdapter.add(index, user);
            }, throwable -> {
              Ln.w(throwable);
            });
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected int getLayoutRes() {
    return R.layout.activity_list;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getAppComponent().inject(this);

    int mode = getIntent().getIntExtra(EXTRA_MODE, MODE_REACTIVE);

    mRecyclerView = (RecyclerView) findViewById(R.id.list);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    mRecyclerView.setHasFixedSize(true);

    mRestService.getUsers(DEFAULT_AMOUNT)
        .compose(Transformer.applyIoSingleTransformer())
        .compose(bindToLifecycle())
        .subscribe(users -> {
          switch (mode) {
            case MODE_REGULAR:
              onModeRegular(users);
              break;
            case MODE_SORTED_LIST:
              break;
            case MODE_DIFF_UTIL:
              break;
          }
        }, throwable -> {
          Ln.w(throwable);
        });
  }

  private void onModeRegular(List<User> users) {
    mBehavioralAdapter = new RegularAdapter();
    mRecyclerView.setAdapter(mRegularAdapter);
    mRegularAdapter.set(users);
  }
}
