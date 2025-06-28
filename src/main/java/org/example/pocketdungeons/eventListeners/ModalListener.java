package org.example.pocketdungeons.eventListeners;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.pocketdungeons.character.service.CharService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModalListener extends ListenerAdapter {

    private final CharService charService;

    @Autowired
    public ModalListener(CharService charService) {
        this.charService = charService;
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {

        if (event.getModalId().equals("character-modal")){
            String player = event.getUser().getId();
            String name = event.getValue("name").getAsString();
            int hp = Integer.parseInt(event.getValue("hp").getAsString());
            int str = Integer.parseInt(event.getValue("str").getAsString());
            int def = Integer.parseInt(event.getValue("def").getAsString());
            String classType = event.getValue("class").getAsString();
            charService.createCharacter(player, name, hp, str, def, classType);
            event.deferReply().queue();

            event.getHook().sendMessage("You created your character! " + name).queue();
        }

    }
}
