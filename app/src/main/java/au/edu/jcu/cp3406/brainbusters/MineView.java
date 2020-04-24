package au.edu.jcu.cp3406.brainbusters;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

public class MineView extends AppCompatImageView {

    boolean revealed;
    ImageManager imageManager;


    public MineView(Context context) {
        super(context);
    }

    public MineView(Context context, ImageManager imageManager){
        super(context);
        revealed = false;
        this.imageManager = imageManager;
        setImageBitmap(imageManager.getBlankMine());
    }

    public void revealMine(int id){
        revealed = true;
        setImageBitmap(imageManager.getMineImage(id));
    }

    public boolean isRevealed() {
        return revealed;
    }
}
