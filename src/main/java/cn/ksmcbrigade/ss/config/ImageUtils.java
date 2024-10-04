package cn.ksmcbrigade.ss.config;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.stb.STBImage.*;

public class ImageUtils {

    public static final Map<String,Integer> loaded = new HashMap<>();

    public static int loadTexture(String filePath) {
        if(loaded.containsKey(filePath)) return loaded.get(filePath);
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        try (MemoryStack ignored = MemoryStack.stackPush()) {
            ByteBuffer imageBuffer = stbi_load(filePath, w, h, channels, 0);
            if (imageBuffer == null) {
                throw new RuntimeException("Failed to load texture: " + stbi_failure_reason());
            }

            int width = w.get(0);
            int height = h.get(0);
            int components = channels.get(0);

            int textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            glTexImage2D(GL_TEXTURE_2D, 0, components == 4 ? GL_RGBA : GL_RGB, width, height, 0,
                    components == 4 ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, imageBuffer);

            stbi_image_free(imageBuffer); // Free native memory
            imageBuffer.clear();

            loaded.put(filePath,textureID);

            return textureID;
        }
    }
}
