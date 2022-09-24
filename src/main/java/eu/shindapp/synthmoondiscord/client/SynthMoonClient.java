package eu.shindapp.synthmoondiscord.client;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import eu.shindapp.synthmoondiscord.SynthMoonDiscord;
import eu.shindapp.synthmoondiscord.commands.discord.AvatarCmd;
import eu.shindapp.synthmoondiscord.listeners.discord.MessageListener;
import eu.shindapp.synthmoondiscord.models.BotSettings;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class SynthMoonClient {

    private JDA jda;
    public void init(String ownerId) {
        try {
            BotSettings botSettings = SynthMoonDiscord.getBotSettingsDao().queryBuilder().where().eq("ownerId", ownerId).queryForFirst();
            if (botSettings != null) {
                CommandClientBuilder commandClientBuilder = new CommandClientBuilder()
                        .setOwnerId(botSettings.getOwnerId())
                        .setPrefix("!")
                        .setAlternativePrefix("<@!" + botSettings.getClientId() + "> ")
                        .setActivity(Activity.competing("SynthMoon"))
                        .addSlashCommands(
                                new AvatarCmd()
                        );

                CommandClient client = commandClientBuilder.build();

                JDABuilder jdaBuilder = JDABuilder.createDefault(botSettings.getClientAuthKey())
                        .addEventListeners(client)
                        .addEventListeners(
                                new MessageListener()
                        )
                        .setChunkingFilter(ChunkingFilter.ALL)
                        .setMemberCachePolicy(MemberCachePolicy.ALL)
                        .enableIntents(
                                GatewayIntent.GUILD_PRESENCES,
                                GatewayIntent.GUILD_MEMBERS,
                                GatewayIntent.MESSAGE_CONTENT
                        )
                        .enableCache(CacheFlag.ACTIVITY);

                jda = jdaBuilder.build();
            }

        } catch (SQLException | LoginException e) {
            e.printStackTrace();
        }
    }

    public JDA getJda() {
        return jda;
    }
}
