package pkgSlRenderer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class DCPolygonRenderer extends slRenderEngine{

    private float[][] center_coords;
    private int NUM_ROWS;
    private int NUM_COLS;


    private void initializeArrays(){
        center_coords = new float[MAX_POLYGONS][NUM_3D_COORDS];
        rand_colors = new float[MAX_POLYGONS][NUM_RGBA];
        rand_coords = new float[MAX_POLYGONS][NUM_3D_COORDS];
    }


    public void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS) {

        C_RADIUS = radiusFinder(NUM_ROWS, NUM_COLS);

        MAX_POLYGONS = numPolygons(NUM_ROWS, NUM_COLS);

        initializeArrays();

        findCenterCoords(NUM_COLS);

        while (!my_wm.isGlfwWindowClosed()) {
            if (TRIANGLES_PER_CIRCLE >= 20){
                TRIANGLES_PER_CIRCLE = 3;
            }

            updateRandVertices();

            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);

            if (FRAME_DELAY != 0){
                Delay(FRAME_DELAY);
            }


            for (int polygon = 0; polygon < MAX_POLYGONS; polygon++){
                renderPolygon(center_coords[polygon][0], center_coords[polygon][1], rand_colors[0]);
            }
            // Increases number of sides on polygon by 1
            TRIANGLES_PER_CIRCLE++;

            my_wm.swapBuffers();
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    } // public void render(...)



    public void render(float RADIUS) {

        C_RADIUS = RADIUS;

        rowColFinder();

        MAX_POLYGONS = numPolygons(NUM_ROWS, NUM_COLS);

        int FRAME_DELAY = 500;

        initializeArrays();

        findCenterCoords(NUM_COLS);

        while (!my_wm.isGlfwWindowClosed()) {
            if (TRIANGLES_PER_CIRCLE >= 20){
                TRIANGLES_PER_CIRCLE = 3;
            }

            updateRandVertices();

            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);

            if (FRAME_DELAY != 0){
                Delay(FRAME_DELAY);
            }

            for (int polygon = 0; polygon < MAX_POLYGONS; polygon++){
                renderPolygon(center_coords[polygon][0], center_coords[polygon][1], rand_colors[0]);
            }
            // Increases number of sides on polygon by 1
            TRIANGLES_PER_CIRCLE++;

            my_wm.swapBuffers();
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    } // public void render(...)


    private float radiusFinder(int NUM_ROWS, int NUM_COLS) {
        float radius;
        if (NUM_COLS > NUM_ROWS){
            radius = 1.0f / NUM_COLS;
        }else{
            radius = 1.0f / NUM_ROWS;
        }
        return radius;
    }

    private void rowColFinder(){
        NUM_COLS = (int) (1 / C_RADIUS);
        NUM_ROWS = (int) (1 / C_RADIUS);
    }

    private int numPolygons(int NUM_ROWS, int NUM_COLS){
        return NUM_ROWS * NUM_COLS;
    }

    private void findCenterCoords(int NUM_COLS) {

        float stepDiameter = 2 * C_RADIUS;
        float leftBorder = -1.0f;
        float floatingPointAdjust = 0.000001f;
        float rightBorder = 1.0f + floatingPointAdjust;
        float topBorder = 1.0f;

        float x = leftBorder + C_RADIUS;
        float y = topBorder - C_RADIUS;
//        System.out.println("Radius: " + C_RADIUS);
        int colNum = 1;
        for (int polygon = 0; polygon < MAX_POLYGONS; polygon++){
            center_coords[polygon][0] = x;
            center_coords[polygon][1] = y;
//            System.out.print("Polygon " + polygon + ": [" + center_coords[polygon][0] + ", " + center_coords[polygon][1] + "]\n");

            if (colNum == NUM_COLS){
                x = leftBorder + C_RADIUS;
                y -= stepDiameter;
                colNum = 1;
                continue;
            }
            colNum++;
            x += stepDiameter;

        }
    }

    private void Delay(int FRAME_DELAY){
        try {
            Thread.sleep(FRAME_DELAY);
        } catch (InterruptedException e) {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
            System.err.println("Thread was interrupted during sleep.");
        }
    }
}
