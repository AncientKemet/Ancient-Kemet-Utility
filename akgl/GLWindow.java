package akgl;

import akgl.AKGL;
import java.io.IOException;
import java.util.logging.*;
import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public class GLWindow {
//this is a test
    public static void main(String[] args) {
        GLWindow game = new GLWindow();
        try {
            game.create();
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
    }

    public GLWindow() {
    }

    public void create() throws LWJGLException {
        //Display
        DisplayMode[] modes = Display.getAvailableDisplayModes();

        for (int i = 0; i < modes.length; i++) {
            if (modes[i].getWidth() == 1280 && modes[i].getHeight() == 720) {
                Display.setDisplayMode(modes[i]);
                break;
            }
        }

        Display.create(new PixelFormat(), new ContextAttribs(1, 3));
        Display.setResizable(true);
        Keyboard.create();
        Mouse.create();

        try {
            run();
        } catch (IOException ex) {
            Logger.getLogger(GLWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("LWJGL version: " + Sys.getVersion());
    }

    public void render() {
        //3d renderFrame
        AKGL.renderFrame();
    }

    public void run() throws IOException {
        while (!Display.isCloseRequested()) {
            if (Display.isVisible()) {
                render();
            } else {
                if (Display.isDirty()) {
                    render();
                }
            }
            Display.update();
            Display.sync(100);
        }
        System.exit(0);
    }

}
