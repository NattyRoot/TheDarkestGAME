package tdg.system;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.net.URL;

public class SoundSystem {
    Clip clip;
    URL[] soundURL = new URL[3];

    public SoundSystem() {
        soundURL[0] = getClass().getResource("/sound/music/1_Intro.wav");
        soundURL[1] = getClass().getResource("/sound/music/2_MainStart.wav");
        soundURL[2] = getClass().getResource("/sound/music/3_MainLoop.wav");
    }

    public void playMainTheme() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[0]);
            Clip intro = AudioSystem.getClip();
            intro.open(ais);

            AudioInputStream ais2 = AudioSystem.getAudioInputStream(soundURL[2]);
            Clip mainLoop = AudioSystem.getClip();
            mainLoop.open(ais2);

            // Start intro
            intro.start();

            // When intro ends, start main intro
            intro.addLineListener(listener -> {
                if (listener.getType() == LineEvent.Type.STOP) {
                    mainLoop.start();
                    mainLoop.loop(Clip.LOOP_CONTINUOUSLY);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {

        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
