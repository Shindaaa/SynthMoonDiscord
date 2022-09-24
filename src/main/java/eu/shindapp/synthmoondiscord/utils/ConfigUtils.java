package eu.shindapp.synthmoondiscord.utils;

import eu.shindapp.synthmoondiscord.SynthMoonDiscord;

public class ConfigUtils {

    public ConfigUtils() {
        super();
    }

    public void loadConfiguration() {
        SynthMoonDiscord.getInstance().getConfig().options().copyDefaults(true);
        SynthMoonDiscord.getInstance().saveConfig();
    }

}
