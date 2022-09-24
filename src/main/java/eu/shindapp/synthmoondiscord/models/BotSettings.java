package eu.shindapp.synthmoondiscord.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import eu.shindapp.synthmoondiscord.SynthMoonDiscord;
import lombok.Data;

import java.sql.SQLException;

@Data
@DatabaseTable(tableName = "synthmoon_bot_settings")
public class BotSettings {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField()
    private String clientId;

    @DatabaseField()
    private String ownerId;

    @DatabaseField()
    private String mainGuildId;

    @DatabaseField()
    private String clientAuthKey;

    public BotSettings fetchByOwnerId(String ownerId) {
        try {
            return SynthMoonDiscord.getBotSettingsDao().queryBuilder().where().eq("ownerId", ownerId).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
