package io.github.tranngoclam.fastlist.ui;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.tranngoclam.fastlist.App;
import io.github.tranngoclam.fastlist.di.AppComponent;
import me.henrytao.mdcore.core.MdCore;

/**
 * Created by lam on 4/30/17.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

  protected abstract int getLayoutRes();

  private Unbinder mUnbinder;

  @CallSuper
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    MdCore.init(this);
    super.onCreate(savedInstanceState);
    setContentView(getLayoutRes());
    mUnbinder = ButterKnife.bind(this);
  }

  @Override
  protected void onDestroy() {
    mUnbinder.unbind();
    super.onDestroy();
  }

  public AppComponent getAppComponent() {
    return ((App) getApplication()).getAppComponent();
  }
}
