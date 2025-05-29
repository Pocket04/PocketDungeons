package org.example.pocketdungeons;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.pocketdungeons.boss.model.Boss;
import org.example.pocketdungeons.boss.repository.BossRepository;
import org.example.pocketdungeons.boss.service.BossService;
import org.example.pocketdungeons.eventListeners.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class PocketDungeonsApplication {

    public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(PocketDungeonsApplication.class, args);
	}

}
