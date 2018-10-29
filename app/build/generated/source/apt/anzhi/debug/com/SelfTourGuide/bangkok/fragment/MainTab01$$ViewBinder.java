// Generated code from Butter Knife. Do not modify!
package com.SelfTourGuide.bangkok.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainTab01$$ViewBinder<T extends com.SelfTourGuide.bangkok.fragment.MainTab01> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689843, "field 'mUITabLayoutBbs'");
    target.mUITabLayoutBbs = finder.castView(view, 2131689843, "field 'mUITabLayoutBbs'");
    view = finder.findRequiredView(source, 2131689846, "field 'mUITab_attractions'");
    target.mUITab_attractions = finder.castView(view, 2131689846, "field 'mUITab_attractions'");
    view = finder.findRequiredView(source, 2131689849, "field 'rl_hotel'");
    target.rl_hotel = finder.castView(view, 2131689849, "field 'rl_hotel'");
    view = finder.findRequiredView(source, 2131689852, "field 'rl_restaurant'");
    target.rl_restaurant = finder.castView(view, 2131689852, "field 'rl_restaurant'");
    view = finder.findRequiredView(source, 2131689855, "field 'rl_shop'");
    target.rl_shop = finder.castView(view, 2131689855, "field 'rl_shop'");
    view = finder.findRequiredView(source, 2131689858, "field 'rl_entertainment'");
    target.rl_entertainment = finder.castView(view, 2131689858, "field 'rl_entertainment'");
    view = finder.findRequiredView(source, 2131689696, "field 'mAdView'");
    target.mAdView = finder.castView(view, 2131689696, "field 'mAdView'");
    view = finder.findRequiredView(source, 2131689842, "field 'tvRemoveId'");
    target.tvRemoveId = finder.castView(view, 2131689842, "field 'tvRemoveId'");
  }

  @Override public void unbind(T target) {
    target.mUITabLayoutBbs = null;
    target.mUITab_attractions = null;
    target.rl_hotel = null;
    target.rl_restaurant = null;
    target.rl_shop = null;
    target.rl_entertainment = null;
    target.mAdView = null;
    target.tvRemoveId = null;
  }
}
