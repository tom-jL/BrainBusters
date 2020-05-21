package au.edu.jcu.cp3406.brainbusters.views;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

import au.edu.jcu.cp3406.brainbusters.ImageManager;

public class MineView extends AppCompatImageView {

    boolean revealed;
    boolean flagged;
    boolean unflagged;
    ImageManager imageManager;


    public MineView(Context context) {
        super(context);
    }

    public MineView(Context context, ImageManager imageManager) {
        super(context);
        revealed = false;
        flagged = false;
        this.imageManager = imageManager;
        setImageBitmap(imageManager.getBlankMine());
    }

    public void revealMine(int id) {
        revealed = true;
        setImageBitmap(imageManager.getMineImage(id));
    }

    public void flagMine(){
        if(!revealed && !flagged) {
            setImageBitmap(imageManager.getMineImage(11));
            flagged = true;
        } else if (!revealed){
            flagged = false;
            setImageBitmap(imageManager.getMineImage(10));
        }
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isUnFlagged() {
        return unflagged;
    }
}
