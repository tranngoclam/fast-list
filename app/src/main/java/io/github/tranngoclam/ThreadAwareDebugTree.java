package io.github.tranngoclam;

import timber.log.Timber;

/**
 * Created by lam on 5/3/17.
 */

public class ThreadAwareDebugTree extends Timber.DebugTree {

  @Override
  protected void log(int priority, String tag, String message, Throwable t) {
    if (tag != null) {
      String threadName = Thread.currentThread().toString();
      tag = "[" + threadName + "] " + tag;
    }
    super.log(priority, tag, message, t);
  }
}
