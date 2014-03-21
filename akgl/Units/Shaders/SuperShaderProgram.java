package akgl.Units.Shaders;

import java.nio.*;
import java.util.HashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * @author Robert Kollar
 */
public class SuperShaderProgram {

    private HashMap<String, Integer> uniforms = new HashMap<String, Integer>();

    private static int currentProgramInUse = -1;

    private int programId = -1;
    private SuperShader vertexShader, fragmentShader;
    private boolean reattachRequired = true;

    protected int getProgramId() {
        if (programId == -1) {
            programId = glCreateProgram();
        }
        return programId;
    }

    public final void setFragmentShader(SuperShader fragmentShader) {
        this.fragmentShader = fragmentShader;
        reattachRequired = true;
    }

    public final void setVertexShader(SuperShader vertexShader) {
        this.vertexShader = vertexShader;
        reattachRequired = true;
    }

    public final void useShaderProgram() {
        if (currentProgramInUse != getProgramId()) {
            glUseProgram(getProgramId());
        }
        if (reattachRequired) {
            attachAndLinkShaders();
        }
        onUpdateUniforms();
    }

    public static void unloadShaderProgram() {
        currentProgramInUse = -1;
        glUseProgram(0);
    }

    /**
     * Is called if the program has been successfully binded (used).
     */
    protected void onUpdateUniforms() {
    }

    /**
     * Attaches and links the vertex and fragment shaders to this program.
     */
    private final void attachAndLinkShaders() {
        if (vertexShader != null) {
            glAttachShader(getProgramId(), vertexShader.getId());
        } else {
            throw new Error("Missing vertex shader.");
        }
        if (fragmentShader != null) {
            glAttachShader(getProgramId(), fragmentShader.getId());
        } else {
            throw new Error("Missing fragment shader.");
        }
        glLinkProgram(getProgramId());

        reattachRequired = false;
    }

    /**
     * Returns an in-shader uniform pointer.
     *
     * @param uniformName the name of uniform field
     * @return the pointer
     */
    protected final int getUniformLocation(String uniformName) {
        if (uniforms.containsKey(uniformName)) {
            return uniforms.get(uniformName);
        } else {
            int location = glGetUniformLocation(programId, uniformName);
            uniforms.put(uniformName, location);
            return location;
        }
    }
}
