// Generated code from Butter Knife. Do not modify!
package com.SelfTourGuide.singapore.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class IntroductionActivity$$ViewBinder<T extends com.SelfTourGuide.singapore.ui.IntroductionActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689752, "field 'content'");
    target.content = finder.castView(view, 2131689752, "field 'content'");
    view = finder.findRequiredView(source, 2131689690, "field 'll_back'");
    target.ll_back = finder.castView(view, 2131689690, "field 'll_back'");
    view = finder.findRequiredView(source, 2131689694, "field 'mUIllempty'");
    target.mUIllempty = finder.castView(view, 2131689694, "field 'mUIllempty'");
  }

  @Override public void unbind(T target) {
    target.content = null;
    target.ll_back = null;
    target.mUIllempty = null;
  }
}
