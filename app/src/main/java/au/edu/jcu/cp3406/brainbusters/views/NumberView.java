package au.edu.jcu.cp3406.brainbusters.views;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import au.edu.jcu.cp3406.brainbusters.MainActivity;
import au.edu.jcu.cp3406.brainbusters.R;


public class NumberView extends androidx.appcompat.widget.AppCompatEditText {

    private int row;
    private int col;
    int number;

    public NumberView(Context context) {
        super(context);
    }

    public NumberView(Context context, int number, int row, int col){
        super(context);
        this.number = number;
        this.row = row;
        this.col = col;
        if(number == -1){
            setUnsolved();
        } else {
            setSolved();
        }
        setCursorVisible(false);
        setGravity(1);
    }

    public void setSolved(){
        setText(String.valueOf(number));
        setClickable(false);
        setFocusable(false);
        setBackground(getResources().getDrawable(R.drawable.soduku_solved));
    }

    public void setUnsolved(){
        setInputType(InputType.TYPE_CLASS_PHONE);
        setBackground(getResources().getDrawable(R.drawable.soduku_unsolved));
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
        if(length()>1){
            number = -1;
            setText("");
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
        } else {
            try {
                number = Integer.parseInt(text.toString());
                if (!(number < 10 && number > 0)) {
                    number = -1;
                }
            } catch (Exception e) {
                number = -1;
            }
        }
    }
}
