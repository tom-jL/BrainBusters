package au.edu.jcu.cp3406.brainbusters;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;


import au.edu.jcu.cp3406.brainbusters.models.Card;

public class ImageManager{

    private AssetManager assetManager;
    private String color;

    ImageManager(AssetManager assetManager, String color){
        this.assetManager = assetManager;
        this.color = color;
    }


    Bitmap getCardImage(Card card){
        String fileName = card.isPaired() ? color + "_back.png" : card.getFileName()+".png";
        InputStream stream = null;
        try {
            stream = assetManager.open("cards/" + fileName);
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}