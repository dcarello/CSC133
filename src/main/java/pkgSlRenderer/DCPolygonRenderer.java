package pkgSlRenderer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class DCPolygonRenderer extends slRenderEngine{

    public void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS) {

        long lastUpdateTime = System.currentTimeMillis();

        while (!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);

            // Check if the update interval has passed
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastUpdateTime >= UPDATE_INTERVAL) {
                // Update the random positions and colors
                updateRandVertices();
                lastUpdateTime = currentTime;  // Reset the last update time
            }


            for (int circle = 0; circle < MAX_CIRCLES; circle++){
                renderCircle(0, 0, rand_colors[circle]);
            }

            my_wm.swapBuffers();
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    } // public void render(...)
}
