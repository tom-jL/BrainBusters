package au.edu.jcu.cp3406.brainbusters.views;

import android.content.Context;

import au.edu.jcu.cp3406.brainbusters.ImageManager;
import au.edu.jcu.cp3406.brainbusters.models.Card;


/**
 * Subclass of an image view. Displays
 * a card, stores card model object.
 */
public class CardView extends androidx.appcompat.widget.AppCompatImageView {

    Card card;
    ImageManager imageManager;


    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, Card card, ImageManager imageManager) {
        super(context);
        this.card = card;
        this.imageManager = imageManager;
        setImageBitmap(imageManager.getCardImage(card));
    }

    public Card getCard() {
        return card;
    }

    public void showCard() {
        setImageBitmap(imageManager.getCardFace(card));
    }

    public void hideCard() {
        setImageBitmap(imageManager.getCardBack());
    }


}
