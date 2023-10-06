package be.bnair.bevo;

import be.bnair.bevo.utils.MCUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * Classe principale de l'application Bevo.
 * Cette classe sert de point d'entrée pour l'application Spring Boot.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@SpringBootApplication
@EnableJpaAuditing // Active l'audition JPA
@EnableConfigurationProperties // Active la configuration de propriétés personnalisées
public class BevoApplication {

    /**
     * Méthode principale qui démarre l'application.
     * @param args Les arguments de ligne de commande.
     */
    public static void main(String[] args) {
        SpringApplication.run(BevoApplication.class, args);
    }
}
