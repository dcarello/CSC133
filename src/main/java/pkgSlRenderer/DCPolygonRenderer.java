package pkgSlRenderer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class DCPolygonRenderer extends slRenderEngine{

    private float[][] center_coords;
    private float[] rand_colors;


    private void updateRandColors(){
        for (int RGB = 0; RGB < NUM_RGBA; RGB++) {
            // Random RGBA color
            rand_colors[RGB] = my_rand.nextFloat();
            rand_colors[RGB] = my_rand.nextFloat();
            rand_colors[RGB] = my_rand.nextFloat();
        }
    }

    public void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS) {

        MAX_POLYGONS = numPolygons(NUM_ROWS, NUM_COLS);

        center_coords = new float[MAX_POLYGONS][NUM_3D_COORDS];
        rand_colors = new float[NUM_RGBA];

        C_RADIUS = radiusFinder(NUM_ROWS, NUM_COLS);

        findCenterCoords();

        while (!my_wm.isGlfwWindowClosed()) {
            if (TRIANGLES_PER_CIRCLE >= 40){
                TRIANGLES_PER_CIRCLE = 3;
            }

            updateRandColors();

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
                renderPolygon(center_coords[polygon][0], center_coords[polygon][1], rand_colors);
            }
            TRIANGLES_PER_CIRCLE++;

            my_wm.swapBuffers();
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    } // public void render(...)

    private float radiusFinder(int NUM_ROWS, int NUM_COLS) {
        float radius = 1.0f / NUM_COLS;
        return radius;
    }

    private int numPolygons(int NUM_ROWS, int NUM_COLS){
        return NUM_ROWS * NUM_COLS;
    }

    private void findCenterCoords() {

        float stepDiameter = 2 * C_RADIUS;
        float leftBorder = -1.0f;
        float floatingPointAdjust = 0.000001f;
        float rightBorder = 1.0f + floatingPointAdjust;
        float topBorder = 1.0f;

        float x = leftBorder + C_RADIUS;
        float y = topBorder - C_RADIUS;
        System.out.println("Radius: " + C_RADIUS);
        for (int polygon = 0; polygon < MAX_POLYGONS; polygon++){
            center_coords[polygon][0] = x;
            center_coords[polygon][1] = y;
            System.out.print("Polygon " + polygon + ": [" + center_coords[polygon][0] + ", " + center_coords[polygon][1] + "]\n");
            x += stepDiameter;
            if (x > rightBorder - C_RADIUS){
                x = leftBorder + C_RADIUS;
                y -= stepDiameter;
            }

        }
    }
}
