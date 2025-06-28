package org.example.pocketdungeons.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.example.pocketdungeons.eventListeners.EventListener;
import org.example.pocketdungeons.eventListeners.ModalListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JDAInitializer implements CommandLineRunner {
    private final EventListener eventListener;
    private final ModalListener modalListener;
    private final String token;

    @Autowired
    public JDAInitializer(EventListener eventListener, ModalListener modalListener, @Value("${bot.token}") String token) {
        this.eventListener = eventListener;
        this.modalListener = modalListener;
        this.token = token;
    }

    @Override
    public void run(String... args) throws Exception {
        JDA jda = JDABuilder.createDefault(token)
                .setActivity(Activity.playing(" with ur mum."))
                .addEventListeners(eventListener)
                .addEventListeners(modalListener)
                .build();

        jda.awaitReady();

        Guild guild = jda.getGuildById("1376496435318620180");

        if (guild != null) {
            guild.upsertCommand("start", "Start a game with all the existing characters.").queue();
            guild.upsertCommand("createboss", "Creates a boss with custom stats.")
                    .addOption(OptionType.STRING, "name", "The name of your boss", true)
                    .addOption(OptionType.INTEGER, "strength", "The strength of your boss", true)
                    .addOption(OptionType.INTEGER, "hp", "The health of your boss", true)
                    .queue();
            guild.upsertCommand("createminiboss", "Creates a boss with custom stats.")
                    .addOption(OptionType.STRING, "name", "The name of your boss", true)
                    .addOption(OptionType.INTEGER, "strength", "The strength of your boss", true)
                    .addOption(OptionType.INTEGER, "hp", "The health of your boss", true)
                    .queue();
            guild.upsertCommand("createcharacter", "Creates a character with custom stats.")
                    .queue();
            guild.upsertCommand("cast", "Cast a spell.")
                    .addOption(OptionType.INTEGER, "spell", "Select the number of the spell you want to cast.", true)
                    .queue();
            guild.upsertCommand("deletecharacter", "Delete your character in order to create a new one.")
                    .queue();
        }
    }
}
