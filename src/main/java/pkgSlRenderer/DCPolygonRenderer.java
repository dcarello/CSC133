package pkgSlRenderer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class DCPolygonRenderer extends slRenderEngine{

    private float[][] center_coords;

    public void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS) {

        MAX_POLYGONS = numPolygons(NUM_ROWS, NUM_COLS);

        center_coords = new float[MAX_POLYGONS][NUM_3D_COORDS];
        rand_colors = new float[MAX_POLYGONS][NUM_RGBA];

        C_RADIUS = radiusFinder(NUM_ROWS, NUM_COLS);

        findCenterCoords();

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


            for (int polygon = 0; polygon < MAX_POLYGONS; polygon++){
                renderPolygon(center_coords[polygon][0], center_coords[polygon][1], rand_colors[polygon]);
            }
            TRIANGLES_PER_CIRCLE++;

            my_wm.swapBuffers();
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    } // public void render(...)

    private float radiusFinder(int NUM_ROWS, int NUM_COLS) {
        float radius = 1.0f / NUM_ROWS;
        return radius;
    }

    private int numPolygons(int NUM_ROWS, int NUM_COLS){
        return NUM_ROWS * NUM_COLS;
    }

    private void findCenterCoords(){
        float x = -1.0f + C_RADIUS;
        float y = 1.0f - C_RADIUS;
        for (int polygon = 0; polygon < MAX_POLYGONS; polygon++){
            center_coords[polygon][0] = x;
            center_coords[polygon][1] = y;
            System.out.print("Polygon " + polygon + ": [" + center_coords[polygon][0] + ", " + center_coords[polygon][1] + "]\n");
            x += (2 * C_RADIUS);
            if (x > 1.0f - C_RADIUS){
                x = -1.0f + C_RADIUS;
                y -= (2 * C_RADIUS);
            }

        }
    }
}
