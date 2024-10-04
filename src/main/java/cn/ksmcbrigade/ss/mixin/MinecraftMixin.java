package cn.ksmcbrigade.ss.mixin;

import cn.ksmcbrigade.ss.Screensaver;
import cn.ksmcbrigade.ss.config.Config;
import cn.ksmcbrigade.ss.config.ImageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "<init>",at = @At("TAIL"))
    public void init(GameConfig p_91084_, CallbackInfo ci){
        Screensaver.image = ImageUtils.loadTexture(Config.getPath());
    }

    @Inject(method = "reloadResourcePacks(Z)Ljava/util/concurrent/CompletableFuture;",at = @At("TAIL"))
    public void reload(CallbackInfoReturnable<CompletableFuture<Void>> cir){
        Screensaver.image = ImageUtils.loadTexture(Config.getPath());
    }
}
