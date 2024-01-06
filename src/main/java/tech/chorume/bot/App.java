package tech.chorume.bot;


import tech.chorume.bot.core.DiscordBot;

public class  App {
    public static void main(String[] args) {
       var discordBot = new DiscordBot();
       discordBot.start();
    }
}
