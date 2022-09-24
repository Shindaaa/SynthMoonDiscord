package eu.shindapp.synthmoondiscord.listeners;

import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import eu.shindapp.synthmoondiscord.SynthMoonDiscord;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {
        if (SynthMoonDiscord.getInstance().getConfig().getBoolean("minecraft.server-settings.enabled")) {
            try {
                WebhookMessageBuilder builder = new WebhookMessageBuilder()
                        .setUsername(event.getPlayer().getName())
                        .setContent(event.getMessage())
                        .setAvatarUrl("https://minotar.net/avatar/" + event.getPlayer().getName());
                SynthMoonDiscord.getWebhookClient().send(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
