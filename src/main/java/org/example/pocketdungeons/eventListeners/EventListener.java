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
import org.example.pocketdungeons.helper.CombatHelper;
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
    private final CombatHelper idfk;
    private final CharService charService;
    private final Queue<Hero> heroQueue = new ArrayDeque<>();
    private Hero hero = new Hero();
    private Boss boss = new Boss();
    private boolean game = false;

    @Autowired
    public EventListener(BossService bossService, MiniBossService miniBossService, CombatHelper idfk, CharService charService) {
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
                    int spell = event.getOption("spell").getAsInt();
                    switch (spell){
                        case 1:
                            event.reply(idfk.cast(event.getOption("target").getAsInt(), charService.getHeroByPlayer(event.getUser().getId()).getSpell1())).queue();
                            break;
                        case 2:
                            event.reply(idfk.cast(event.getOption("target").getAsInt(), charService.getHeroByPlayer(event.getUser().getId()).getSpell2())).queue();
                            break;
                        case 3:
                            event.reply(idfk.cast(event.getOption("target").getAsInt(), charService.getHeroByPlayer(event.getUser().getId()).getSpell3())).queue();
                            break;
                    }
                    break;
                case "reset":
                    game = false;
                    idfk.reset();
                    heroQueue.clear();
                    event.reply("Game has been reset, everyone is full hp now.").queue();
                    return;
                default:
                    event.reply("Sorry, you can't do that during combat.").queue();
                    return;
            }
            boss = bossService.getBossById(100);
            if (boss.getHP() <= 0){
                event.getChannel().sendMessage("Yippie, Boss is Dead, Freedom is back on the menu!!!").queue();
                bossService.deleteBoss();
                game = false;
                return;
            }
            if (heroQueue.isEmpty()) {
                if (boss.getTurns() % 3 != 0){
                    event.getChannel().sendMessage(idfk.cast(1, -2)).queue();
                }else {
                    event.getChannel().sendMessage(idfk.cast(1, -5)).queue();
                }
                boss.setTurns(boss.getTurns() + 1);
                bossService.saveBoss(boss);
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
                    Boss createdBoss = bossService.createBoss(event.getOption("name").getAsString(), event.getOption("strength").getAsInt(), event.getOption("hp").getAsInt());
                    event.reply("Boss " + createdBoss.getName() + " has been created.").queue();
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
                    boss = bossService.getBoss();
                    hero = heroQueue.poll();
                    event.getChannel().sendMessage("it's " + hero.getName() + "'s turn. " + "<@" + hero.getPlayer() + ">").queue();
                    break;
                default:
                    event.reply("Command unavailable while out of combat.").queue();
                    break;
            }
        }
    }
}
