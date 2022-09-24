package eu.shindapp.synthmoondiscord.commands.discord;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class AvatarCmd extends SlashCommand {
    public AvatarCmd() {
        this.name = "avatar";
        this.help = "Get the avatar of a user";
        this.guildOnly = false;
        this.arguments = "<user>";
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "The user to get the avatar of").setRequired(true));
        this.options = data;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.deferReply().queue();
        User user = event.getOption("user").getAsUser();
        event.getHook().sendMessageEmbeds(
                new EmbedBuilder()
                        .setTitle(user.getName() + "'s avatar")
                        .setImage(user.getAvatarUrl())
                        .build()
        ).queue();
    }
}
