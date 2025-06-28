package org.example.pocketdungeons.eventListeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.example.pocketdungeons.boss.model.Boss;
import org.example.pocketdungeons.boss.service.BossService;
import org.example.pocketdungeons.character.model.Hero;
import org.example.pocketdungeons.character.service.CharService;
import org.example.pocketdungeons.helpmeimconfused.BossCharHelpService;
import org.example.pocketdungeons.miniboss.model.MiniBoss;
import org.example.pocketdungeons.miniboss.service.MiniBossService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Queue;

@Component
public class EventListener extends ListenerAdapter {

    private final BossService bossService;
    private final MiniBossService miniBossService;
    private final BossCharHelpService idfk;
    private final CharService charService;
    private final Queue<String> heroQueue = new ArrayDeque<>();
    private String hero;

    @Autowired
    public EventListener(BossService bossService, MiniBossService miniBossService, BossCharHelpService idfk, CharService charService) {
        this.bossService = bossService;
        this.miniBossService = miniBossService;
        this.idfk = idfk;
        this.charService = charService;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        if (!event.getUser().getName().equals(hero) && !heroQueue.isEmpty()){
            event.getChannel().sendMessage("Sorry, " + event.getUser().getName() + " it's not your turn yet.").queue();
            return;
        }

        switch (event.getName()){
            case "createboss":
                Boss boss = bossService.createBoss(event.getOption("name").getAsString(), event.getOption("strength").getAsInt(), event.getOption("hp").getAsInt());
                event.reply("Boss " + boss.getName() + " has been created.").queue();
                break;
            case "createminiboss":
                MiniBoss miniboss = miniBossService.createBoss(event.getOption("name").getAsString(), event.getOption("strength").getAsInt(), event.getOption("hp").getAsInt());
                event.reply("Mini boss " + miniboss.getName() + " has been created.").queue();
                break;
            case "createcharacter":
                TextInput name = TextInput.create("name", "The name of your character", TextInputStyle.SHORT)
                        .setRequired(true).build();
                TextInput hp = TextInput.create("hp", "The health of your character", TextInputStyle.SHORT)
                        .setRequired(true).build();
                TextInput strength = TextInput.create("str", "The strength of your character", TextInputStyle.SHORT)
                        .setRequired(true).build();
                TextInput defense = TextInput.create("def", "The defense of your character", TextInputStyle.SHORT)
                        .setRequired(true).build();
                TextInput classType = TextInput.create("class", "Insert your class", TextInputStyle.SHORT)
                        .setRequired(true).setPlaceholder("Warrior, Rogue, Priest").build();
                Modal modal = Modal.create("character-modal", "Create your character.")
                        .addActionRow(name)
                        .addActionRow(hp)
                        .addActionRow(strength)
                        .addActionRow(defense)
                        .addActionRow(classType)
                        .build();
                event.replyModal(modal).queue();
                break;
            case "cast":
                event.reply(charService.cast(event.getUser().getName(), event.getOption("spell").getAsInt())).queue();
                break;
            case "deletecharacter":
                charService.deleteCharacter(event.getUser().getName());
                event.reply("You have deleted your hero.").queue();
                break;
            case "start":
                heroQueue.addAll(charService.getAll());
                heroQueue.add("Boss");
                event.reply("The game has started. Your enemy is Boss").queue();
                break;
        }
        if (!heroQueue.isEmpty()) {
            hero = heroQueue.poll();
            event.getChannel().sendMessage("It's " + hero + "'s turn.").queue();
            if (heroQueue.peek() == null){
                heroQueue.addAll(charService.getAll());
                heroQueue.add("Boss");
            }
            if (hero.equals("Boss")) {
                event.getChannel().sendMessage(idfk.bossDamage(-5)).queue();
                hero = heroQueue.poll();
                event.getChannel().sendMessage("It's " + hero + "'s turn.").queue();
            }
        }
    }
}
