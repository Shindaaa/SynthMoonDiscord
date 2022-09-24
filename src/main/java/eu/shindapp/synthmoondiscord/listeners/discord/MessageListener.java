package eu.shindapp.synthmoondiscord.listeners.discord;

import eu.shindapp.synthmoondiscord.SynthMoonDiscord;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class MessageListener implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof MessageReceivedEvent) {
            this.onMessageReceived((MessageReceivedEvent) event);
        }
    }

    private void onMessageReceived(MessageReceivedEvent event) {
        if (SynthMoonDiscord.getInstance().getConfig().getBoolean("discord.server-settings.enabled")) {
            Bukkit.getServer().broadcastMessage(
                    SynthMoonDiscord.getInstance()
                            .getConfig()
                            .getString("discord.server-settings.format")
                            .replace("%user_tag%", event.getAuthor().getAsTag())
                            .replace("%user_name%", event.getAuthor().getName())
                            .replace("%discord_msg%", event.getMessage().getContentRaw())
                            .replace("&", "§"));
        }
    }
}
