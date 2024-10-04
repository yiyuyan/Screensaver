package cn.ksmcbrigade.ss;

import cn.ksmcbrigade.ss.config.Config;
import cn.ksmcbrigade.ss.config.ImageUtils;
import cn.ksmcbrigade.ss.screen.ScreenSaverScreen;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Mod(Screensaver.MODID)
public class Screensaver {

    //---main---

    public static final String MODID = "ss";

    public static final Logger LOGGER = LogUtils.getLogger();

    private long waitTicks = 0;

    public static int image = 0;

    public Screensaver() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);

        File file = new File("config/screensaver.png");
        if(!file.exists()){
            FileUtils.writeByteArrayToFile(file, IOUtils.toByteArray(Objects.requireNonNull(Screensaver.class.getResourceAsStream("/assets/ss/gui/screensaver.png"))));
        }

        LOGGER.info("Screensaver mod loaded.");
    }

    //---debug---

    @SubscribeEvent
    public void command(RegisterClientCommandsEvent event){
        event.getDispatcher().register(Commands.literal("debug-image").then(Commands.literal("list").executes(commandContext -> {
            for (String s : ImageUtils.loaded.keySet()) {
                commandContext.getSource().sendSystemMessage(Component.literal(s).append("---").append(String.valueOf(ImageUtils.loaded.get(s))));
            }
            return 0;
        })).then(Commands.literal("remove").then(Commands.argument("key", StringArgumentType.string()).executes(commandContext -> {
            ImageUtils.loaded.remove(StringArgumentType.getString(commandContext,"key"));
            commandContext.getSource().sendSystemMessage(Component.nullToEmpty("Done."));
            return 0;
        }))).then(Commands.literal("reload").executes(commandContext -> {
            Minecraft.getInstance().reloadResourcePacks();
            return 0;
        })));
    }

    //---events---

    @SubscribeEvent
    public void tick(TickEvent.PlayerTickEvent event){
        if(Config.TIMEOUT.get()<=0) return;
        if (event.phase == TickEvent.Phase.END) {
            if(Minecraft.getInstance().screen instanceof ScreenSaverScreen) return;

            waitTicks++;

            if(waitTicks/20 >= Config.TIMEOUT.get()){
                Minecraft.getInstance().screen = new ScreenSaverScreen();
                waitTicks = 0;
            }
        }
    }

    @SubscribeEvent
    public void interact(PlayerInteractEvent event){
        if(Config.TIMEOUT.get()<=0) return;
        waitTicks = 0;
    }

    @SubscribeEvent
    public void key(InputEvent.Key event){
        if(Config.TIMEOUT.get()<=0) return;
        waitTicks = 0;
    }

    @SubscribeEvent
    public void key(InputEvent.InteractionKeyMappingTriggered event){
        if(Config.TIMEOUT.get()<=0) return;
        waitTicks = 0;
    }

    @SubscribeEvent
    public void mouse(InputEvent.MouseScrollingEvent event){
        if(Config.TIMEOUT.get()<=0) return;
        waitTicks = 0;
    }

    @SubscribeEvent
    public void mouse(InputEvent.MouseButton event){
        if(Config.TIMEOUT.get()<=0) return;
        waitTicks = 0;
    }
}
