package eu.shindapp.synthmoondiscord.listeners;

import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import eu.shindapp.synthmoondiscord.SynthMoonDiscord;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (SynthMoonDiscord.getInstance().getConfig().getBoolean("minecraft.server-settings.join-quit-log")) {
            try {
                WebhookMessageBuilder builder = new WebhookMessageBuilder()
                        .setUsername("SynthMoon")
                        .setContent(SynthMoonDiscord.getInstance()
                                .getConfig()
                                .getString("minecraft.server-settings.join-format")
                                .replace("%player_name%", event.getPlayer().getName()))
                        .setAvatarUrl(SynthMoonDiscord.getInstance()
                                .getConfig()
                                .getString("minecraft.server-settings.join-avatarURL"));
                SynthMoonDiscord.getWebhookClient().send(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (SynthMoonDiscord.getInstance().getConfig().getBoolean("minecraft.server-settings.join-quit-log")) {
            try {
                WebhookMessageBuilder builder = new WebhookMessageBuilder()
                        .setUsername("SynthMoon")
                        .setContent(SynthMoonDiscord.getInstance()
                                .getConfig()
                                .getString("minecraft.server-settings.quit-format")
                                .replace("%player_name%", event.getPlayer().getName()))
                        .setAvatarUrl(SynthMoonDiscord.getInstance()
                                .getConfig()
                                .getString("minecraft.server-settings.quit-avatarURL"));
                SynthMoonDiscord.getWebhookClient().send(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
