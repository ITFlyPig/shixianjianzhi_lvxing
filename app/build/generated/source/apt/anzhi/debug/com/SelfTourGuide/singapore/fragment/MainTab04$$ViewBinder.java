// Generated code from Butter Knife. Do not modify!
package com.SelfTourGuide.singapore.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainTab04$$ViewBinder<T extends com.SelfTourGuide.singapore.fragment.MainTab04> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689869, "field 'tvPrivacy'");
    target.tvPrivacy = finder.castView(view, 2131689869, "field 'tvPrivacy'");
    view = finder.findRequiredView(source, 2131689870, "field 'tvUse'");
    target.tvUse = finder.castView(view, 2131689870, "field 'tvUse'");
    view = finder.findRequiredView(source, 2131689696, "field 'mAdView'");
    target.mAdView = finder.castView(view, 2131689696, "field 'mAdView'");
    view = finder.findRequiredView(source, 2131689871, "field 'tv_Get_PRO'");
    target.tv_Get_PRO = finder.castView(view, 2131689871, "field 'tv_Get_PRO'");
  }

  @Override public void unbind(T target) {
    target.tvPrivacy = null;
    target.tvUse = null;
    target.mAdView = null;
    target.tv_Get_PRO = null;
  }
}
