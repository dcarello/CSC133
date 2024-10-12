package pkgSlRenderer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class DCPolygonRenderer extends slRenderEngine{

    public void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS) {

        while (!my_wm.isGlfwWindowClosed()) {
            if (TRIANGLES_PER_CIRCLE >= 40){
                TRIANGLES_PER_CIRCLE = 3;
            }
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);

            if (FRAME_DELAY != 0){
                try {
                    Thread.sleep(FRAME_DELAY);
                } catch (InterruptedException e) {
                    // Restore the interrupted status
                    Thread.currentThread().interrupt();
                    System.err.println("Thread was interrupted during sleep.");
                }
            }


            for (int circle = 0; circle < MAX_CIRCLES; circle++){
                renderCircle(0, 0, rand_colors[circle]);
            }
            TRIANGLES_PER_CIRCLE++;

            my_wm.swapBuffers();
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    } // public void render(...)
}
