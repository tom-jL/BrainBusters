package au.edu.jcu.cp3406.brainbusters.views;

import android.content.Context;
import android.text.InputType;
import android.util.TypedValue;
import android.widget.EditText;

import au.edu.jcu.cp3406.brainbusters.R;


public class NumberView extends androidx.appcompat.widget.AppCompatEditText {

    int number;

    public NumberView(Context context) {
        super(context, null, R.style.TextAppearance_AppCompat_Button);
    }

    public NumberView(Context context, int number){
        super(context);//, null, R.style.Widget_AppCompat_Button_Borderless_Colored);
        this.number = number;
        if(number == 0){
            setInputType(InputType.TYPE_CLASS_PHONE);
        } else {
            setText(String.valueOf(number));
            //setBackgroundColor(getResources().getColor(R.color.material_on_background_disabled));
            setClickable(false);
            setFocusable(false);
        }
        setBackground(getResources().getDrawable(R.drawable.soduku_btn));
        setCursorVisible(false);
        setGravity(1);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
    }


}
