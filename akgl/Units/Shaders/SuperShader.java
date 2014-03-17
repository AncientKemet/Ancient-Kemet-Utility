package akgl.Units.Shaders;

import aku.AncientKemetRegistry;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.logging.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * @author Robert Kollar
 */
public class SuperShader {

    public enum ShaderType {

        VERTEX_SHADER,
        FRAGMENT_SHADER
    }

    private final ShaderType shaderType;
    private int id = -1;
    private String glslCode = null;
    private boolean reconstructRequired;

    public SuperShader(ShaderType shaderType) {
        this.shaderType = shaderType;
    }

    public int getId() {
        if (id == -1 && glslCode != null || reconstructRequired) {
            constructAndCompileShader();
        }
        return id;
    }

    public void downloadGLSL(String pathToShader) {
        try {
            InputStream in = new URL(AncientKemetRegistry.getDataHost() + "shaders/" + pathToShader).openConnection().getInputStream();

            byte[] bytes = new byte[in.available()];

            in.read(bytes);

            String code = new String(bytes);

            setGlslCode(code);
        } catch (MalformedURLException ex) {
            Logger.getLogger(SuperShader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SuperShader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setGlslCode(String glslCode) {
        this.glslCode = glslCode;
        reconstructRequired = true;
    }

    public void setReconstructRequired(boolean reconstructRequired) {
        this.reconstructRequired = reconstructRequired;
    }

    private void constructAndCompileShader() {
        if (shaderType == ShaderType.VERTEX_SHADER) {
            id = glCreateShader(GL_VERTEX_SHADER);
        } else {
            id = glCreateShader(GL_FRAGMENT_SHADER);
        }
        glShaderSource(id, glslCode);
        glCompileShader(id);
        printLogInfo();
        reconstructRequired = false;
    }

    private void printLogInfo() {
        IntBuffer iVal = BufferUtils.createIntBuffer(1);

        GL20.glGetShader(id, GL20.GL_INFO_LOG_LENGTH, iVal);
        int length = iVal.get();
        if (length > 1) {
            // We have some info we need to output.
            ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            iVal.flip();

            GL20.glGetShaderInfoLog(id, iVal, infoLog);

            byte[] infoBytes = new byte[length];

            infoLog.get(infoBytes);

            String out = new String(infoBytes);
            System.out.println("Info log:\n" + out);
        }
        GL11.glGetError();
    }

}
