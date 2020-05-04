package au.edu.jcu.cp3406.brainbusters;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import au.edu.jcu.cp3406.brainbusters.models.Card;

public class ImageManager {

    private AssetManager assetManager;


    public ImageManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Bitmap getMineImage(int id) {
        InputStream stream = null;
        try {
            stream = assetManager.open("mines/" + id + ".png");
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Bitmap getCardImage(Card card) {
        String fileName = card.isPaired() ? card.getFileName() + ".png" : "blue_back.png";
        InputStream stream = null;
        try {
            stream = assetManager.open("cards/" + fileName);
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Bitmap getCardFace(Card card) {
        String fileName = card.getFileName() + ".png";
        InputStream stream = null;
        try {
            stream = assetManager.open("cards/" + fileName);
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap getCardBack() {
        String fileName = "blue_back.png";
        InputStream stream = null;
        try {
            stream = assetManager.open("cards/" + fileName);
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Bitmap getBlankMine() {
        InputStream stream = null;
        try {
            stream = assetManager.open("mines/" + 10 + ".png");
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
