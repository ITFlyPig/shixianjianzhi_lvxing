// Generated code from Butter Knife. Do not modify!
package com.SelfTourGuide.bangkok.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainTab03$$ViewBinder<T extends com.SelfTourGuide.bangkok.fragment.MainTab03> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689846, "field 'tvRemoveId'");
    target.tvRemoveId = finder.castView(view, 2131689846, "field 'tvRemoveId'");
    view = finder.findRequiredView(source, 2131689868, "field 'rl_collection'");
    target.rl_collection = finder.castView(view, 2131689868, "field 'rl_collection'");
    view = finder.findRequiredView(source, 2131689510, "field 'title'");
    target.title = finder.castView(view, 2131689510, "field 'title'");
    view = finder.findRequiredView(source, 2131689869, "field 'image_arrow'");
    target.image_arrow = finder.castView(view, 2131689869, "field 'image_arrow'");
    view = finder.findRequiredView(source, 2131689870, "field 'nodata'");
    target.nodata = finder.castView(view, 2131689870, "field 'nodata'");
    view = finder.findRequiredView(source, 2131689693, "field 'listView'");
    target.listView = finder.castView(view, 2131689693, "field 'listView'");
    view = finder.findRequiredView(source, 2131689694, "field 'mUIllempty'");
    target.mUIllempty = finder.castView(view, 2131689694, "field 'mUIllempty'");
    view = finder.findRequiredView(source, 2131689696, "field 'mAdView'");
    target.mAdView = finder.castView(view, 2131689696, "field 'mAdView'");
  }

  @Override public void unbind(T target) {
    target.tvRemoveId = null;
    target.rl_collection = null;
    target.title = null;
    target.image_arrow = null;
    target.nodata = null;
    target.listView = null;
    target.mUIllempty = null;
    target.mAdView = null;
  }
}
