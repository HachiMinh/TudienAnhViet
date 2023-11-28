package com.example.tudienanhviet;

import com.voicerss.tts.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.concurrent.Task;

import java.io.File;
import java.io.FileOutputStream;

public class WordListening extends Task<Clip> {
    private String text;

    public WordListening(String text) {
        this.text = text;
    }

    @Override
    protected Clip call() throws Exception {
        String PATH = "src/main/resources/audio.wav";
        VoiceProvider tts = new VoiceProvider("11e9655235c943cebe3df1a1a7abdb14");
        VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);
        byte[] voice;
        voice = tts.speech(params);
        FileOutputStream fos = new FileOutputStream(PATH);
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
        File audioFile = new File(PATH);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }
}
