package cn.ksmcbrigade.ss.screen;

import cn.ksmcbrigade.ss.Screensaver;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class ScreenSaverScreen extends Screen {
    public ScreenSaverScreen() {
        super(Component.nullToEmpty("ScreenSaver"));
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void render(@NotNull GuiGraphics p_281549_, int p_281550_, int p_282878_, float p_282465_) {
        this.blit(p_281549_,Screensaver.image,0,0,0,0,p_281549_.guiWidth(),p_281549_.guiHeight(),p_281549_.guiWidth(),p_281549_.guiHeight());
    }

    @Override
    public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
        this.onClose();
        return true;
    }

    @Override
    public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
        this.onClose();
        return true;
    }

    @Override
    public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
        this.onClose();
        return true;
    }

    @Override
    public void mouseMoved(double p_94758_, double p_94759_) {
        this.onClose();
    }

    @Override
    public boolean keyPressed(int p_96552_, int p_96553_, int p_96554_) {
        this.onClose();
        return true;
    }

    @Override
    public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
        this.onClose();
        return true;
    }

    public void blit(GuiGraphics guiGraphics,int tex,int x,int y,float u,float v,int width,int height,int texWidth,int texHeight){
        this.innerBlit(guiGraphics,tex,x,x+width,y,y+height,0,(u+0.0F)/texWidth,(u+width)/texWidth,(v+0.0F)/texHeight,(u+height)/texHeight);
    }

    public void innerBlit(GuiGraphics guiGraphics,int p_283461_, int p_281399_, int p_283222_, int p_283615_, int p_283430_, int p_281729_, float p_283247_, float p_282598_, float p_282883_, float p_283017_) {
        RenderSystem.setShaderTexture(0, p_283461_);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, (float)p_281399_, (float)p_283615_, (float)p_281729_).uv(p_283247_, p_282883_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_281399_, (float)p_283430_, (float)p_281729_).uv(p_283247_, p_283017_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_283222_, (float)p_283430_, (float)p_281729_).uv(p_282598_, p_283017_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_283222_, (float)p_283615_, (float)p_281729_).uv(p_282598_, p_282883_).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
    }
}
