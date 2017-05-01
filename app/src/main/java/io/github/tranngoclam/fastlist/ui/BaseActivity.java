package io.github.tranngoclam.fastlist.ui;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import io.github.tranngoclam.fastlist.App;
import io.github.tranngoclam.fastlist.di.AppComponent;
import me.henrytao.mdcore.core.MdCore;

/**
 * Created by lam on 4/30/17.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    MdCore.init(this);
    super.onCreate(savedInstanceState);
  }

  public AppComponent getAppComponent() {
    return ((App) getApplication()).getAppComponent();
  }
}
