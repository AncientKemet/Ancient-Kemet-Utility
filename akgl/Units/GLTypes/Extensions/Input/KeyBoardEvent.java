package akgl.Units.GLTypes.Extensions.Input;

/**
 * @author Robert Kollar
 */
public class KeyBoardEvent {

    private final int key;
    private final boolean down;

    public KeyBoardEvent(int key, boolean down) {
        this.key = key;
        this.down = down;
    }

    /**
     * @return the key
     */
    public int getKey() {
        return key;
    }

    /**
     * @return the letter
     */
    public char getLetter() {
        return (char) key;
    }

    /**
     * @return the down
     */
    public boolean isDown() {
        return down;
    }

    /**
     * @return is released
     */
    public boolean wasReleased() {
        return !down;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof KeyBoardEvent) {
                KeyBoardEvent secondEvent = (KeyBoardEvent) obj;

                if (secondEvent.key == key && secondEvent.down == down) {
                    return true;
                }
            }
        }

        return false;

    }

}
