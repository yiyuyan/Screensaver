package cn.ksmcbrigade.ss.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder().comment("Screensaver mod config");

    public static final ForgeConfigSpec.ConfigValue<String> PATH = BUILDER.comment("the image path for the screensaver,case-sensitive.").comment("You can use the command \"/debug-image list\" command to view all the loaded images.").comment("If you want to reload the same path image that you can use the command \"/debug-image remove <path>\" and F3+T to reload.").comment("default = config/screensaver.png").define("path","default");
    public static final ForgeConfigSpec.LongValue TIMEOUT = BUILDER.comment("After what seconds to display the screensaver").defineInRange("timeout",300,0,Long.MAX_VALUE);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static String getPath(){
        return PATH.get().equals("default")?"config/screensaver.png":PATH.get();
    }
}
