package pkgSlUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;


public class slWindowManager {
    private static long glfw_win = 0;
    private static slWindowManager my_window = null;

    private slWindowManager(){
    }

//    public int[] getCurrentWindowSize(){
//
//    }

    public static slWindowManager get(){
        if (my_window == null){
            my_window = new slWindowManager();
        }
        return my_window;
    }

    public void destroyGlfwWindow(){
        glfwDestroyWindow(glfw_win);
    }

    public void swapBuffers(){
        glfwSwapBuffers(glfw_win);
    }

    public boolean isGlfwWindowClosed(){
        return glfwWindowShouldClose(glfw_win);
    }

    public void initGLFWWindow(int win_width, int win_height, String title){
        if (!glfwInit()) {
                throw new IllegalStateException("Unable to initialize GLFW");
        }
        if (glfw_win == 0){
            glfw_win = glfwCreateWindow(win_width, win_height, title, NULL, NULL);
        }
        if (glfw_win == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
    }

//    public int[] getWindowSize(){
//        glfwGetWindowSize();
//    }

    public void updateContextToThis(){
        glfwMakeContextCurrent(glfw_win);
    }

}
