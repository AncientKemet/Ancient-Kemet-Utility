/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package akgl.Units.GLTypes.Extensions.Renderer;

import akgl.Units.GLTypes.Extensions.GLRenderer;
import akgl.Units.Geometry.Animation.Bone;
import akgl.Units.Geometry.Animation.Frame;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Von Bock
 */
public class ArmatureRenderer extends GLRenderer {

    private Frame[] frames;
    private float currentFrame = 0;

    public void setFrames(Frame[] frames) {
        this.frames = frames;
    }

    @Override
    public void render() {
        if (frames != null) {
            glDisable(GL_LIGHTING);
            glEnable(GL_BLEND);
            Frame frame = frames[(int) currentFrame];
            for (Bone bone : frame.bones) {
                //make sure this bone is a parent
                if (bone.getParent() == null) {
                    renderBoneAnditsSubbones(bone);
                }
            }
            currentFrame += 0.5f;
            if (currentFrame >= frames.length) {
                currentFrame = 0;
            }
        }
    }

    @Override
    public void render2D() {
    }

    private void renderBoneAnditsSubbones(Bone bone) {
        glPushMatrix();
        glMultMatrix(bone.getMatrixBuffer());
        {
            glBegin(GL_LINES);
            glVertex3f(0, 0, 0);
            glVertex3f(0, 1f, 0);
            glEnd();
            for (Bone child : bone.getChildren()) {
                renderBoneAnditsSubbones(child);
            }
        }
        glPopMatrix();
    }
}
