package eu.shindapp.synthmoondiscord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import eu.shindapp.synthmoondiscord.client.SynthMoonClient;
import eu.shindapp.synthmoondiscord.models.BotSettings;
import eu.shindapp.synthmoondiscord.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.sql.SQLException;

public final class SynthMoonDiscord extends JavaPlugin {

    private static SynthMoonDiscord instance;
    private static JdbcConnectionSource connection;
    private static WebhookClient webhookClient;

    private static Dao<BotSettings, String> botSettingsDao;

    @Override
    public void onEnable() {
        instance = this;
        new ConfigUtils().loadConfiguration();

        try {
            connection = new JdbcConnectionSource("jdbc:mysql://" + getConfig().getString("mysql.hostname") + ":3306/" + getConfig().getString("mysql.database") + "?autoReconnect=true&wait_timeout=172800", getConfig().getString("mysql.username"), getConfig().getString("mysql.password"));
            getLogger().info("Successfully connected to SQL Database!");
            botSettingsDao = DaoManager.createDao(connection, BotSettings.class);
            TableUtils.createTableIfNotExists(connection, BotSettings.class);
        } catch (SQLException e) {
            e.printStackTrace();
            getLogger().severe("Error when trying to connect to SQL Database, shutting down!");
            Bukkit.getServer().shutdown();
        }

        if (new BotSettings().fetchByOwnerId(getConfig().getString("discord.bot-ownerId")) == null) {
            getLogger().severe("Error no settings found matching with this owner id ! " + getConfig().getString("discord.bot-ownerId"));
        } else {
            try {
                new SynthMoonClient().init(getConfig().getString("discord.bot-ownerId"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            WebhookClientBuilder webhookClientBuilder = new WebhookClientBuilder(SynthMoonDiscord.getInstance().getConfig().getString("minecraft.server-settings.webHookURL"));
            webhookClientBuilder.setThreadFactory((chatWatch) -> {
                Thread thread = new Thread(chatWatch);
                thread.setName("chatWatch");
                thread.setDaemon(true);
                return thread;
            });

            webhookClientBuilder.setWait(true);
            webhookClient = webhookClientBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerEvents();
    }

    @Override
    public void onDisable() {
        //...
    }

    private void registerEvents() {
        String packageName = getClass().getPackage().getName();
        for (Class<?> clazz : new Reflections(packageName + ".listeners").getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static SynthMoonDiscord getInstance() {
        return instance;
    }

    public static JdbcConnectionSource getConnection() {
        return connection;
    }

    public static Dao<BotSettings, String> getBotSettingsDao() {
        return botSettingsDao;
    }

    public void info(String msg) {
        instance.getLogger().info(msg);
    }

    public void severe(String msg) {
        instance.getLogger().severe(msg);
    }

    public static WebhookClient getWebhookClient() {
        return webhookClient;
    }
}
