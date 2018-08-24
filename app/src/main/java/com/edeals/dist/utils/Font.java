package com.edeals.dist.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ContextThemeWrapper;

/**
 * Created by team edeals on 23-01-2018.
 */
public class Font extends ContextThemeWrapper {
    public Typeface exo_regular;

    public Font(Context context) {
        exo_regular = Typeface.createFromAsset(context.getAssets(),"fonts/exo.ttf");
    }
}
