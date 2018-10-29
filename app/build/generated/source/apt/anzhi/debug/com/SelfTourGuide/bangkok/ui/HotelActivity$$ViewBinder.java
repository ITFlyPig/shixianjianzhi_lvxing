// Generated code from Butter Knife. Do not modify!
package com.SelfTourGuide.bangkok.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HotelActivity$$ViewBinder<T extends com.SelfTourGuide.bangkok.ui.HotelActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689693, "field 'mUIListView'");
    target.mUIListView = finder.castView(view, 2131689693, "field 'mUIListView'");
    view = finder.findRequiredView(source, 2131689690, "field 'mUILLback'");
    target.mUILLback = finder.castView(view, 2131689690, "field 'mUILLback'");
    view = finder.findRequiredView(source, 2131689694, "field 'mUIllempty'");
    target.mUIllempty = finder.castView(view, 2131689694, "field 'mUIllempty'");
    view = finder.findRequiredView(source, 2131689696, "field 'mAdView'");
    target.mAdView = finder.castView(view, 2131689696, "field 'mAdView'");
  }

  @Override public void unbind(T target) {
    target.mUIListView = null;
    target.mUILLback = null;
    target.mUIllempty = null;
    target.mAdView = null;
  }
}
