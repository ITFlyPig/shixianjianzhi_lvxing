// Generated code from Butter Knife. Do not modify!
package com.SelfTourGuide.singapore.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainTab02$$ViewBinder<T extends com.SelfTourGuide.singapore.fragment.MainTab02> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689694, "field 'mUIllempty'");
    target.mUIllempty = finder.castView(view, 2131689694, "field 'mUIllempty'");
    view = finder.findRequiredView(source, 2131689696, "field 'mAdView'");
    target.mAdView = finder.castView(view, 2131689696, "field 'mAdView'");
    view = finder.findRequiredView(source, 2131689861, "field 'LL'");
    target.LL = finder.castView(view, 2131689861, "field 'LL'");
  }

  @Override public void unbind(T target) {
    target.mUIllempty = null;
    target.mAdView = null;
    target.LL = null;
  }
}
