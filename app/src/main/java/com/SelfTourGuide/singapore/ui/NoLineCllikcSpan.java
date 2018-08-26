package com.SelfTourGuide.singapore.ui;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by a on 2017/5/11.
 */

public class NoLineCllikcSpan extends ClickableSpan {

    public NoLineCllikcSpan() {
        super();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        /**set textColor**/
        ds.setColor(ds.linkColor);
        /**Remove the underline**/
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
    }

}
