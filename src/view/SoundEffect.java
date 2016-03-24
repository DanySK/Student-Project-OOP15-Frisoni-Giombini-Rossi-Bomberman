package view;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This enum encapsulates all the sound effects of the game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all sound effect names and the associated wave file and volume (for a "3D effect").
 * 2. Optionally invoke the static method SoundEffect.init() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 3. The static variable SoundEffect.musicOn can be used to mute the sound.
 * 
 * N.B.: A Java Sound based Clip uses it's own (daemon) Thread.
 * 
 */
public enum SoundEffect {

    /**
     * Bomberman's theme.
     */
    THEME("/sounds/bombermanTheme.wav", 0.2),
    /**
     * 8-bit game's theme.
     */
    GAME_THEME("/sounds/backInBlack.wav", 0.1),
    /**
     * Button focus sound.
     */
    FOCUS("/sounds/focus.wav", 0.1),
    /**
     * Button selection sound.
     */
    SELECT("/sounds/select.wav", 0.3),
    /**
     * Game advice sound.
     */
    ADVICE("/sounds/advice.wav", 1.0),
    /**
     * Hero's footsteps sound.
     */
    WALK("/sounds/walk.wav", 0.6),
    /**
     * Bomb's explosion sound.
     */
    EXPLOSION("/sounds/explosion.wav", 1.0),
    /**
     * Powerup obtained sound.
     */
    POWERUP("/sounds/powerup.wav", 1.0),
    /**
     * Key found sound.
     */
    KEY("/sounds/key.wav", 1.0),
    /**
     * Level completed sound.
     */
    NEXT_LEVEL("/sounds/nextLevel.wav", 1.0),
    /**
     * Dark mode selected sound.
     */
    BOO_LAUGH("/sounds/boo_laugh.wav", 0.5),
    /**
     * Hero's death sound.
     */
    DEAD("/sounds/dead.wav", 1.0);


    // Indicates if sound effects are on or not
    private static volatile boolean musicOn = true;

    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;

    /**
     * Constructor to build each element of the enum with its own sound file.
     * 
     * @param soundFileName
     *          sound file's path
     * @param volume
     *          number between 0 and 1 (loudest)
     */
    SoundEffect(final String soundFileName, final double volume) {
        try {
            final URL url = this.getClass().getResource(soundFileName);
            final AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            this.clip = AudioSystem.getClip();
            this.clip.open(audio);
            final FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue((float) (Math.log(volume) / Math.log(10.0) * 20.0));
            audio.close();
        } catch (UnsupportedAudioFileException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Plays or Re-plays the sound effect from the beginning, by rewinding.
     */
    public void playOnce() {
        if (SoundEffect.musicOn) {
            if (this.clip.isRunning()) {
                this.clip.stop();            // Stop the player if it is still running
            }
            this.clip.setFramePosition(0);   // Rewind to the beginning
            this.clip.start();               // Start playing
        }
    }

    /**
     * Plays the song in loop.
     */
    public void playLoop() {
        if (SoundEffect.musicOn) {
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Stops the song.
     */
    public void stop() {
        this.clip.stop();
    }

    /**
     *  Optional static method to pre-load all the sound files.
     */
    public static void init() {
        values(); // calls the constructor for all the elements
    }

    /**
     * Sets music on.
     */
    public static void setMusicOn() {
        SoundEffect.musicOn = true;
    }

    /**
     * Sets music off.
     */
    public static void setMusicOff() {
        stopAll();
        SoundEffect.musicOn = false;
    }

    /**
     * @return true if music is on, false otherwise.
     */
    public static boolean isMusicOn() {
        return SoundEffect.musicOn;
    }

    // Stop all clips.
    private static void stopAll() {
        for (final SoundEffect sound: SoundEffect.values()) {
            sound.stop();
        }
    }
}
