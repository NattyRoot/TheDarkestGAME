package tdg.system;

import javax.sound.sampled.*;
import java.net.URL;

public class SoundSystem {

    private static Clip intro;
    private static Clip loop;
    private static Clip outro;

    static {
        URL[] soundURL = new URL[3];

        soundURL[0] = SoundSystem.class.getResource("/sound/music/1_Intro.wav");
        soundURL[1] = SoundSystem.class.getResource("/sound/music/The-Darkest-Game-Loop.wav");
        soundURL[2] = SoundSystem.class.getResource("/sound/music/The-Darkest-Game-outro.wav");

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[0]);
            intro = AudioSystem.getClip();
            intro.open(ais);

            AudioInputStream ais2 = AudioSystem.getAudioInputStream(soundURL[1]);
            loop = AudioSystem.getClip();
            loop.open(ais2);

            AudioInputStream ais3 = AudioSystem.getAudioInputStream(soundURL[2]);
            outro = AudioSystem.getClip();
            outro.open(ais3);
            outro.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playMainTheme() {
        /*
        // Start intro
        intro.start();

        // When intro ends, start main intro
        intro.addLineListener(listener -> {
            if (listener.getType() == LineEvent.Type.STOP) {
                setClipVolume(outro, 0);
                setClipVolume(loop, 100);

                loop.start();
                outro.start();
                loop.loop(Clip.LOOP_CONTINUOUSLY);
            }
        });*/
        setVolume(loop, 1.0f);
        setVolume(outro, 0.0f);

        loop.start();
        outro.start();
        loop.loop(Clip.LOOP_CONTINUOUSLY);
        outro.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void fadeLoopToOutro() {

        new Thread(() ->{
            while(getVolume(outro) < 0.9f) {
                setVolume(outro, getVolume(outro) + 0.01f);

                if (getVolume(loop) > 0.1f) {
                    setVolume(loop, getVolume(loop) - 0.01f);
                }

                try { Thread.sleep(30); } catch (InterruptedException e) {}
            }
            setVolume(loop, 0f);
            setVolume(outro, 1f);
        }).start();
    }

    public static void fadeOutroToLoop() {

        new Thread(() ->{
            while(getVolume(loop) < 0.9f) {
                setVolume(loop, getVolume(loop) + 0.01f);

                if (getVolume(outro) > 0.1f) {
                    setVolume(outro, getVolume(outro) - 0.01f);
                }

                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
            setVolume(outro, 0f);
            setVolume(loop, 1f);
        }).start();
    }


    /**
     * Set the volume to a value between 0 and 1.
     */
    private static float getVolume(Clip clip) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    private static void setVolume(Clip clip, float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
