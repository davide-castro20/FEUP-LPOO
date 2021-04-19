package com.fleeTheRoom.view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.util.Random;

public class Audio {
    private Clip clip;
    private final String audio_path = "src\\main\\resources\\music\\";

    public enum STATE {
        LEVEL,
        COLORSEQ,
        FINDSEQ,
        TILE
    }

    public Audio() throws LineUnavailableException {
        clip = AudioSystem.getClip();
    }

    public void start() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src\\main\\resources\\music\\1.wav"));
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if(clip.isOpen())
            clip.close();
    }

    public String randomLevelMusic() {
        Random rand = new Random();
        int musicN = rand.nextInt(6) + 1;
        return audio_path + musicN + ".wav";
    }

    public void update(STATE state) {
        close();
        AudioInputStream audioInputStream;
        try {
            switch (state) {
                case LEVEL:
                    audioInputStream = AudioSystem.getAudioInputStream(new File(randomLevelMusic()));
                    break;
                case COLORSEQ:
                    audioInputStream = AudioSystem.getAudioInputStream(new File(audio_path + "colorSequence.wav"));
                    break;
                case FINDSEQ:
                    audioInputStream = AudioSystem.getAudioInputStream(new File(audio_path + "numberSequence2.wav"));
                    break;
                case TILE:
                    audioInputStream = AudioSystem.getAudioInputStream(new File(audio_path + "tiles.wav"));
                    break;
                default:
                    return;
            }
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
