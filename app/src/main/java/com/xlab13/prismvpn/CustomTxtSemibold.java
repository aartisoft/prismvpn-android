package com.xlab13.prismvpn;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CustomTxtSemibold extends AppCompatTextView {

    public CustomTxtSemibold(Context context) {
        super(context);
        init();
    }

    public CustomTxtSemibold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTxtSemibold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        if (!isInEditMode()){
            Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-SemiBold.ttf");
            setTypeface(normalTypeface);
        }
    }
}
