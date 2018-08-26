// Generated code from Butter Knife. Do not modify!
package com.SelfTourGuide.singapore.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WebViewActivity$$ViewBinder<T extends com.SelfTourGuide.singapore.ui.WebViewActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689764, "field 'webview'");
    target.webview = finder.castView(view, 2131689764, "field 'webview'");
    view = finder.findRequiredView(source, 2131689548, "field 'goback'");
    target.goback = finder.castView(view, 2131689548, "field 'goback'");
    view = finder.findRequiredView(source, 2131689549, "field 'goForward'");
    target.goForward = finder.castView(view, 2131689549, "field 'goForward'");
    view = finder.findRequiredView(source, 2131689766, "field 'linear_right'");
    target.linear_right = finder.castView(view, 2131689766, "field 'linear_right'");
    view = finder.findRequiredView(source, 2131689765, "field 'linear_left'");
    target.linear_left = finder.castView(view, 2131689765, "field 'linear_left'");
    view = finder.findRequiredView(source, 2131689767, "field 'linear_close'");
    target.linear_close = finder.castView(view, 2131689767, "field 'linear_close'");
    view = finder.findRequiredView(source, 2131689768, "field 'close'");
    target.close = finder.castView(view, 2131689768, "field 'close'");
  }

  @Override public void unbind(T target) {
    target.webview = null;
    target.goback = null;
    target.goForward = null;
    target.linear_right = null;
    target.linear_left = null;
    target.linear_close = null;
    target.close = null;
  }
}
