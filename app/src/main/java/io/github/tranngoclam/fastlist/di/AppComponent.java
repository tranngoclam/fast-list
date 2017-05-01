package io.github.tranngoclam.fastlist.di;

import dagger.Component;
import io.github.tranngoclam.fastlist.App;
import io.github.tranngoclam.fastlist.ui.ListActivity;

/**
 * Created by lam on 4/30/17.
 */
@Component(modules = AppModule.class)
public interface AppComponent {

  void inject(ListActivity listActivity);

  void inject(App app);
}
