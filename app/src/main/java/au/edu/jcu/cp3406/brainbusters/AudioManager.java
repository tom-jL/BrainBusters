package au.edu.jcu.cp3406.brainbusters;

import android.content.Context;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private SoundPool soundPool;
    private Map<Integer, Integer> streams;

    AudioManager(Context context) {
        streams = new HashMap<>();
        soundPool = new SoundPool(5, android.media.AudioManager.FLAG_PLAY_SOUND, 0);
        streams.put(R.raw.explode, soundPool.load(context, R.raw.explode, 0));
        streams.put(R.raw.shuffle, soundPool.load(context, R.raw.shuffle, 0));
        streams.put(R.raw.win, soundPool.load(context, R.raw.win, 0));

    }

    void playSound(int sampleId) {
        if (!streams.containsKey(sampleId)) return;
        assert streams.get(sampleId) != null;
        soundPool.play(streams.get(sampleId), 0.5f, 0.5f, 1, 0, 1);
    }

    void resume() {
        soundPool.autoResume();
    }
    void pause() {
        soundPool.autoPause();
    }
}

