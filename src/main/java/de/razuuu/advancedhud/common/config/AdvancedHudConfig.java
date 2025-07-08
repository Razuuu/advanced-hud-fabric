package de.razuuu.advancedhud.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "advancedhud")
public class AdvancedHudConfig implements ConfigData {

    public boolean enabled = true;

    public boolean drawWithShadows = false;

    public int textSpacing = 5;

    @ConfigEntry.ColorPicker
    public int textColor = 0xEEEEEE;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 255)
    public int textAlpha = 230;

    public int offsetTop = 4;

    public int offsetLeft = 4;

    public boolean holdKeyToShowFps = false;

    public boolean enableFpsHud = true;

    public String fps = "FPS: ";

    public boolean enableCoordinatesHud = true;

    public String coordinates = "Coordinates: ";

    public boolean enablePingHud = true;

    public String ping = "Ping: ";

}
