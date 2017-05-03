package io.github.tranngoclam.fastlist.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import io.github.tranngoclam.fastlist.R;
import io.github.tranngoclam.fastlist.RestService;
import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.adapter.BehavioralAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.RegularAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.RxSortedDiffAdapter;
import io.github.tranngoclam.fastlist.util.Transformer;
import io.github.tranngoclam.fastlist.util.Utils;
import timber.log.Timber;

public class ListActivity extends BaseActivity {

  public static final int MODE_DIFF_UTIL = 2;

  public static final int MODE_REACTIVE = 3;

  public static final int MODE_REGULAR = 0;

  public static final int MODE_RX_SORTED_DIFF = 4;

  public static final int MODE_SORTED_LIST = 1;

  static final int DEFAULT_AMOUNT = 5;

  static final String EXTRA_MODE = "extra_mode";

  public static Intent newIntent(Context context, int mode) {
    Intent intent = new Intent(context, ListActivity.class);
    intent.putExtra(EXTRA_MODE, mode);
    return intent;
  }

  @Inject RestService mRestService;

  private BehavioralAdapter<User, UserViewHolder> mBehavioralAdapter;

  private RecyclerView mRecyclerView;

  private RxSortedDiffAdapter mRxSortedDiffAdapter;

  private Toolbar mToolbar;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_action, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_add_single:
        mRestService.getUser()
            .compose(Transformer.applyIoSingleTransformer())
            .compose(bindToLifecycle())
            .subscribe(user -> {
              int index = Utils.randomize(mBehavioralAdapter.getItemCount() - 1);
              mBehavioralAdapter.add(index, user);
            }, throwable -> {
              Timber.w(throwable);
            });
        return true;
      case R.id.action_add_multi:
        mRestService.getUsers(DEFAULT_AMOUNT)
            .compose(Transformer.applyIoSingleTransformer())
            .compose(bindToLifecycle())
            .subscribe(users -> {
              mBehavioralAdapter.add(users);
            }, throwable -> {
              Timber.w(throwable);
            });
        return true;
      case R.id.action_remove_one_item:
        int index = Utils.randomize(mBehavioralAdapter.getItemCount() - 1);
        mBehavioralAdapter.remove(index);
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

    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    setSupportActionBar(mToolbar);

    int mode = getIntent().getIntExtra(EXTRA_MODE, MODE_REACTIVE);

    mRecyclerView = (RecyclerView) findViewById(R.id.list);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    mRecyclerView.setHasFixedSize(true);
    switch (mode) {
      case MODE_REGULAR:
        mBehavioralAdapter = new RegularAdapter();
        mBehavioralAdapter.registerAdapterDataObserver(new RecyclerViewAdapterDataObserver());
        mRecyclerView.setAdapter(mBehavioralAdapter);
        break;
      case MODE_SORTED_LIST:
        break;
      case MODE_DIFF_UTIL:
        break;
      case MODE_RX_SORTED_DIFF:
        break;
    }

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
            case MODE_RX_SORTED_DIFF:
              break;
          }
        }, throwable -> {
          Timber.w(throwable);
        });
  }

  private void onModeRegular(List<User> users) {
    mBehavioralAdapter.set(users);
  }

  private static class RecyclerViewAdapterDataObserver extends RecyclerView.AdapterDataObserver {

    @Override
    public void onChanged() {
      super.onChanged();
      Timber.d("onChanged");
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
      super.onItemRangeChanged(positionStart, itemCount, payload);
      Timber.d("onItemRangeChangedWithPayload | positionStart: %d, itemCount: %d", positionStart, itemCount);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
      super.onItemRangeChanged(positionStart, itemCount);
      Timber.d("onItemRangeChanged | positionStart: %d, itemCount: %d", positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
      super.onItemRangeInserted(positionStart, itemCount);
      Timber.d("onItemRangeInserted | positionStart: %d, itemCount: %d", positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
      super.onItemRangeMoved(fromPosition, toPosition, itemCount);
      Timber.d("onItemRangeMoved | fromPosition: %d, toPosition: %d, itemCount: %d", fromPosition, toPosition, itemCount);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
      super.onItemRangeRemoved(positionStart, itemCount);
      Timber.d("onItemRangeRemoved | positionStart: %d, itemCount: %d", positionStart, itemCount);
    }
  }
}
