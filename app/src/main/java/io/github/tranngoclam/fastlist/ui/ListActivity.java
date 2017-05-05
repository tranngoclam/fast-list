package io.github.tranngoclam.fastlist.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.github.tranngoclam.fastlist.PreferenceService;
import io.github.tranngoclam.fastlist.R;
import io.github.tranngoclam.fastlist.RestService;
import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.adapter.BehavioralAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.DiffUtilAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.RegularAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.RxSortedDiffAdapter;
import io.github.tranngoclam.fastlist.ui.adapter.SortedListAdapter;
import io.github.tranngoclam.fastlist.util.Transformer;
import io.github.tranngoclam.fastlist.util.Utils;
import timber.log.Timber;

public class ListActivity extends BaseActivity {

  public static final int MODE_DIFF_UTIL = 2;

  public static final int MODE_REACTIVE = 3;

  public static final int MODE_REGULAR = 0;

  public static final int MODE_RX_SORTED_DIFF = 4;

  public static final int MODE_SORTED_LIST = 1;

  static final String DEFAULT_REGION = "United States";

  static final String EXTRA_MODE = "extra_mode";

  public static Intent newIntent(Context context, int mode) {
    Intent intent = new Intent(context, ListActivity.class);
    intent.putExtra(EXTRA_MODE, mode);
    return intent;
  }

  @Inject PreferenceService mPreferenceService;

  @Inject RestService mRestService;

  private RecyclerView.AdapterDataObserver mAdapterDataObserver;

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
      case R.id.action_insert_single:
        mRestService.getUser(DEFAULT_REGION)
            .compose(Transformer.applyIoSingleTransformer())
            .compose(bindToLifecycle())
            .subscribe(user -> {
              if (mBehavioralAdapter != null) {
                if (mBehavioralAdapter instanceof SortedListAdapter) {
                  mBehavioralAdapter.add(user);
                } else {
                  int index = Utils.randomize(mBehavioralAdapter.getItemCount() - 1);
                  mBehavioralAdapter.add(index, user);
                }
              } else {
                mRxSortedDiffAdapter.getRxSortedList().add(user)
                    .subscribe(() -> Timber.d("RxSortedDiffAdapter | add"));
              }
            }, throwable -> {
              Timber.w(throwable);
            });
        return true;
      case R.id.action_insert_multiple:
        mRestService.getUsers(mPreferenceService.getDefaultAmount(), DEFAULT_REGION)
            .compose(Transformer.applyIoSingleTransformer())
            .compose(bindToLifecycle())
            .subscribe(users -> {
              if (mBehavioralAdapter != null) {
                mBehavioralAdapter.addAll(users);
              } else {
                mRxSortedDiffAdapter.getRxSortedList().addAll(users)
                    .subscribe(() -> Timber.d("RxSortedDiffAdapter | addAll"));
              }
            }, throwable -> {
              Timber.w(throwable);
            });
        return true;
      case R.id.action_update_single:
        User user = mBehavioralAdapter.get(1);
        User newUser = new User(user.age, user.gender.equalsIgnoreCase("male") ? "female" : "male", user.name, user.password, user.phone,
            user.photo, user.surname);
        if (mBehavioralAdapter != null) {
          mBehavioralAdapter.set(1, newUser);
        }
        return true;
      case R.id.action_update_and_insert_multiple:
        if (mBehavioralAdapter != null) {
          if (mBehavioralAdapter instanceof SortedListAdapter) {
            SortedList<User> users = ((SortedListAdapter) mBehavioralAdapter).getUsers();
            int size = users.size();
            List<User> newUsers = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
              newUsers.add(users.get(i));
            }
            Collections.shuffle(newUsers);
            mBehavioralAdapter.set(newUsers);
          }
        } else {
          List<User> curUsers = mRxSortedDiffAdapter.getRxSortedList().getData();
          List<User> newUsers = Utils.copyAndSwitchName(curUsers);
          mRestService.getUsers(mPreferenceService.getDefaultAmount(), DEFAULT_REGION)
              .map(users -> {
                newUsers.addAll(users);
                return newUsers;
              })
              .flatMapCompletable(users -> mRxSortedDiffAdapter.getRxSortedList().set2(users))
              .compose(Transformer.applyCompletableTransformer())
              .compose(bindToLifecycle())
              .subscribe(() -> Timber.d("RxSortedDiffAdapter | update and insert"));
        }
        return true;
      case R.id.action_remove_single:
        int index;
        if (mBehavioralAdapter != null) {
          index = Utils.randomize(mBehavioralAdapter.getItemCount() - 1);
          if (index >= 0 && index < mBehavioralAdapter.getItemCount()) {
            mBehavioralAdapter.remove(index);
          } else {
            Timber.w(IndexOutOfBoundsException.class.getSimpleName());
          }
        } else {
          index = Utils.randomize(mRxSortedDiffAdapter.getItemCount() - 1);
          if (index >= 0 && index < mRxSortedDiffAdapter.getRxSortedList().size()) {
            mRxSortedDiffAdapter.getRxSortedList().removeItemAt(index)
                .subscribe(() -> Timber.d("RxSortedDiffAdapter | removeItemAt"));
          } else {
            Timber.w(IndexOutOfBoundsException.class.getSimpleName());
          }
        }
        return true;
      case R.id.action_clear:
        if (mBehavioralAdapter != null) {
          mBehavioralAdapter.clear();
        } else {
          mRxSortedDiffAdapter.getRxSortedList().clear()
              .subscribe(() -> Timber.d("RxSortedDiffAdapter | clear"));
        }
        return true;
      case R.id.notify_all:
        mRestService.getUsers(mPreferenceService.getDefaultAmount(), DEFAULT_REGION)
            .compose(Transformer.applyIoSingleTransformer())
            .compose(bindToLifecycle())
            .subscribe(users -> {
              if (mBehavioralAdapter != null) {
                mBehavioralAdapter.setAndNotifyAll(users);
              }
            }, throwable -> {
              Timber.w(throwable);
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

    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    setSupportActionBar(mToolbar);

    int mode = getIntent().getIntExtra(EXTRA_MODE, MODE_REACTIVE);

    mRecyclerView = (RecyclerView) findViewById(R.id.list);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    mRecyclerView.setHasFixedSize(true);

    mAdapterDataObserver = new RecyclerViewAdapterDataObserver();

    switch (mode) {
      case MODE_REGULAR:
        mBehavioralAdapter = new RegularAdapter();
        mBehavioralAdapter.registerAdapterDataObserver(mAdapterDataObserver);
        mRecyclerView.setAdapter(mBehavioralAdapter);
        break;
      case MODE_SORTED_LIST:
        mBehavioralAdapter = new SortedListAdapter();
        mBehavioralAdapter.registerAdapterDataObserver(mAdapterDataObserver);
        mRecyclerView.setAdapter(mBehavioralAdapter);
        break;
      case MODE_DIFF_UTIL:
        mBehavioralAdapter = new DiffUtilAdapter();
        mBehavioralAdapter.registerAdapterDataObserver(mAdapterDataObserver);
        mRecyclerView.setAdapter(mBehavioralAdapter);
        break;
      case MODE_RX_SORTED_DIFF:
        mRxSortedDiffAdapter = new RxSortedDiffAdapter();
        mRxSortedDiffAdapter.registerAdapterDataObserver(mAdapterDataObserver);
        mRecyclerView.setAdapter(mRxSortedDiffAdapter);
        break;
    }

    mRestService.getUsers(mPreferenceService.getDefaultAmount(), DEFAULT_REGION)
        .compose(Transformer.applyIoSingleTransformer())
        .compose(bindToLifecycle())
        .subscribe(users -> {
          switch (mode) {
            case MODE_REGULAR:
            case MODE_SORTED_LIST:
            case MODE_DIFF_UTIL:
              if (mBehavioralAdapter != null) {
                mBehavioralAdapter.set(users);
              }
              break;
            case MODE_RX_SORTED_DIFF:
              if (mRxSortedDiffAdapter != null) {
                mRxSortedDiffAdapter.getRxSortedList().set(users)
                    .subscribe(() -> Timber.d("RxSortedDiffAdapter | set"));
              }
              break;
          }
        }, throwable -> {
          Timber.w(throwable);
        });
  }

  @Override
  protected void onDestroy() {
    RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
    if (adapter != null && mAdapterDataObserver != null) {
      adapter.unregisterAdapterDataObserver(mAdapterDataObserver);
    }
    super.onDestroy();
  }

  private static class RecyclerViewAdapterDataObserver extends RecyclerView.AdapterDataObserver {

    @Override
    public void onChanged() {
      super.onChanged();
      Timber.d("AdapterDataObserver | onChanged");
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
      super.onItemRangeChanged(positionStart, itemCount, payload);
      Timber.d("AdapterDataObserver | onItemRangeChangedWithPayload | positionStart: %d, itemCount: %d", positionStart, itemCount);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
      super.onItemRangeChanged(positionStart, itemCount);
      Timber.d("AdapterDataObserver | onItemRangeChanged | positionStart: %d, itemCount: %d", positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
      super.onItemRangeInserted(positionStart, itemCount);
      Timber.d("AdapterDataObserver | onItemRangeInserted | positionStart: %d, itemCount: %d", positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
      super.onItemRangeMoved(fromPosition, toPosition, itemCount);
      Timber.d("AdapterDataObserver | onItemRangeMoved | fromPosition: %d, toPosition: %d, itemCount: %d", fromPosition, toPosition,
          itemCount);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
      super.onItemRangeRemoved(positionStart, itemCount);
      Timber.d("AdapterDataObserver | onItemRangeRemoved | positionStart: %d, itemCount: %d", positionStart, itemCount);
    }
  }
}
