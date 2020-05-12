package au.edu.jcu.cp3406.brainbusters.views;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import au.edu.jcu.cp3406.brainbusters.R;


public class NumberView extends androidx.appcompat.widget.AppCompatEditText {

    private int row;
    private int col;
    int number;

    public NumberView(Context context) {
        super(context, null, R.style.TextAppearance_AppCompat_Button);
    }

    public NumberView(Context context, int number, int row, int col){
        super(context);//, null, R.style.Widget_AppCompat_Button_Borderless_Colored);
        this.number = number;
        this.row = row;
        this.col = col;
        if(number == -1){
            setInputType(InputType.TYPE_CLASS_PHONE);
        } else {
            setText(String.valueOf(number));
            //setBackgroundColor(getResources().getColor(R.color.material_on_background_disabled));
            setClickable(false);
            setFocusable(false);
        }
        Drawable background = getResources().getDrawable(R.drawable.soduku_btn);
        setBackground(getResources().getDrawable(R.drawable.soduku_btn));
        setCursorVisible(false);
        setGravity(1);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }



    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        try {
            number = Integer.parseInt(text.toString());
            if(!(number<10 && number>0)){
                number = -1;
            }
        } catch (Exception e){
            number = -1;
        }

    }
}
