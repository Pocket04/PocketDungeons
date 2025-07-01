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
    private final Queue<Hero> heroQueue = new ArrayDeque<>();
    private Hero hero = new Hero();
    private Boss boss = new Boss();
    private boolean game = false;

    @Autowired
    public EventListener(BossService bossService, MiniBossService miniBossService, BossCharHelpService idfk, CharService charService) {
        this.bossService = bossService;
        this.miniBossService = miniBossService;
        this.idfk = idfk;
        this.charService = charService;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        if (game) {
            if (!event.getUser().getId().equals(hero.getPlayer())) {
                event.getChannel().sendMessage("Sorry, " + event.getUser().getName() + " it's not your turn yet.").queue();
                return;
            }
            switch (event.getName()){
                case "cast":
                    event.reply(charService.cast(event.getUser().getId(), event.getOption("spell").getAsInt())).queue();
                    break;
                default:
                    event.reply("Sorry, you can't do that during a game.").queue();
                    return;
            }
            if (bossService.getBoss().getHP() <= 0){
                event.getChannel().sendMessage("Yippie, Boss is Dead, Freedom is back on the menu!!!").queue();
                bossService.deleteBoss();
                game = false;
                return;
            }
            if (heroQueue.isEmpty()) {
                event.getChannel().sendMessage(idfk.bossDamage(-3)).queue();
                heroQueue.addAll(charService.getAll());
                if (heroQueue.isEmpty()) {
                    event.getChannel().sendMessage("Game over, no more heroes left, Boss won.").queue();
                    game = false;
                    return;
                }
            }
            hero = heroQueue.poll();
            event.getChannel().sendMessage("it's " + hero.getName() + "'s turn. " + "<@" + hero.getPlayer() + ">").queue();
        } else {
            switch (event.getName()) {
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
                    TextInput classType = TextInput.create("class", "Insert your class", TextInputStyle.SHORT)
                            .setRequired(true).setPlaceholder("Warrior, Rogue, Priest").build();
                    Modal modal = Modal.create("character-modal", "Create your character.")
                            .addActionRow(name)
                            .addActionRow(classType)
                            .build();
                    event.replyModal(modal).queue();
                    break;
                case "deletecharacter":
                    charService.deleteCharacter(event.getUser().getName());
                    event.reply("You have deleted your hero.").queue();
                    break;
                case "start":
                    heroQueue.addAll(charService.getAll());
                    event.reply("The game has started. Your enemy is Boss").queue();
                    game = true;
                    hero = heroQueue.poll();
                    event.getChannel().sendMessage("it's " + hero.getName() + "'s turn. " + "<@" + hero.getPlayer() + ">").queue();
                    break;
                default:
                    event.reply("Command unavailable while out of combat.").queue();
            }
        }
    }
}
