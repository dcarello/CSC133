package pkgSlRenderer;

import pkgSlUtils.slWindowManager;

import java.util.Random;

import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class slRenderEngine {
    private final int NUM_RGBA = 4;
    private final int NUM_3D_COORDS = 3;
    private final int TRIANGLES_PER_CIRCLE = 40;
    private final float C_RADIUS = 0.05f;
    private final int MAX_CIRCLES = 1;
    private final int UPDATE_INTERVAL = 30;

    private float[][] rand_colors;
    private float[][] rand_coords;

    private slWindowManager my_wm = slWindowManager.get();
    Random my_rand = new Random();

    public void initOpenGL(slWindowManager my_wm){
        my_wm.updateContextToThis();

        GL.createCapabilities();
        float CC_RED = 0.0f, CC_GREEN = 0.0f, CC_BLUE = 1.0f, CC_ALPHA = 1.0f;
        glClearColor(CC_RED, CC_GREEN, CC_BLUE, CC_ALPHA);
    }

    private void generateCircleSegmentVertices(){

    }

    private void updateRandVertices(){
//        for (int circle = 0; circle < MAX_CIRCLES; circle++){
//            rand_coords[circle][0] = (my_rand.nextFloat() * 2.0f - 1.0f);
//        }
    }



    public void render() {
        updateRandVertices();

        float theta = 0.0f;
        final float end_angle = (float) (2.0f * Math.PI);

        float delTheta = end_angle / TRIANGLES_PER_CIRCLE;

        float x, y, oldX = 0, oldY = 0;

        while (!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            glBegin(GL_TRIANGLES);

            // Each triangle will require color + 3 vertices as below.
            // For each circle you need 40 of these for the assignment.
            for (int cir_seg = 1; cir_seg <= TRIANGLES_PER_CIRCLE; cir_seg++){
                theta += delTheta;

                x =  C_RADIUS * (float) Math.cos(theta);
                y = C_RADIUS * (float) Math.sin(theta);
                glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                glVertex3f(0.0f, 0.0f, 0.0f);
                glVertex3f(x, y, 0.0f);
                glVertex3f(oldX, oldY, 0.0f);

                oldX = x;
                oldY = y;
            }

            glEnd();
            my_wm.swapBuffers();
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    } // public void render(...)


}
