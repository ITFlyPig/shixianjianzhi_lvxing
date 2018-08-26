// Generated code from Butter Knife. Do not modify!
package com.SelfTourGuide.singapore.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class OverViewActivity$$ViewBinder<T extends com.SelfTourGuide.singapore.ui.OverViewActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689752, "field 'mUITextContent'");
    target.mUITextContent = finder.castView(view, 2131689752, "field 'mUITextContent'");
    view = finder.findRequiredView(source, 2131689690, "field 'mUILLback'");
    target.mUILLback = finder.castView(view, 2131689690, "field 'mUILLback'");
    view = finder.findRequiredView(source, 2131689694, "field 'mUIllempty'");
    target.mUIllempty = finder.castView(view, 2131689694, "field 'mUIllempty'");
    view = finder.findRequiredView(source, 2131689653, "field 'mScrollView'");
    target.mScrollView = finder.castView(view, 2131689653, "field 'mScrollView'");
  }

  @Override public void unbind(T target) {
    target.mUITextContent = null;
    target.mUILLback = null;
    target.mUIllempty = null;
    target.mScrollView = null;
  }
}
