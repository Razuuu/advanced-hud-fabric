package de.razuuu.fabric.advancedhud.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "advancedhud")
public class AdvancedHudConfig implements ConfigData {

    public boolean enabled = true;

    public boolean drawWithShadows = false;

	public float textSize = 1;

    @ConfigEntry.ColorPicker
    public int textColor = 0xEEEEEE;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 255)
    public int textAlpha = 230;

    public int offsetTop = 4;

    public int offsetLeft = 4;

    public boolean holdKeyToShowFps = false;
}
