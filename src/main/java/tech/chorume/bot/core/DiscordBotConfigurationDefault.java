package tech.chorume.bot.core;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import tech.chorume.bot.core.interfaces.DiscordBotConfiguration;
import tech.chorume.bot.core.interfaces.SlashCommandHandler;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiscordBotConfigurationDefault implements DiscordBotConfiguration {
    private Dotenv dotenv;
    public DiscordBotConfigurationDefault() {
        dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .filename(".env")
                .load();
    }
    @Override
    public String getDiscordToken() {
        var token = dotenv.get("bot.token");
        return token;
    }
    /**
     * Default gateways
     * @return {@link Collection<GatewayIntent>}
     */
    @Override
    public Collection<GatewayIntent> getGatewayIntents() {
        //https://docs.jda.wiki/net/dv8tion/jda/api/requests/GatewayIntent.html
        return Set.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
    }

    /**
     * Define only the DispatcherListener, which will be responsible for handling all Slash Command Events and delegating them to their respective handlers.
     * @param handlersMap
     * @return {@link Collection<ListenerAdapter>}
     */
    @Override
    public Collection<EventListener> getListeners(Map<String, SlashCommandHandler> handlersMap) {
        return List.of(new DispatcherListener(handlersMap));
    }
}
