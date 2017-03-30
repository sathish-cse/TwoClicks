package team.unicorn.vsb.twoclicks.activity.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class CustomFontsAutoCompleteTextView extends AutoCompleteTextView {

    public CustomFontsAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomFontsAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomFontsAutoCompleteTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Light.ttf");
            setTypeface(tf);
        }
    }

}