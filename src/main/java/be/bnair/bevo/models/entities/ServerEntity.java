package be.bnair.bevo.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Cette classe représente une entité pour stocker des informations sur un serveur dans le système.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Entity
@Table(name = "bevo_servers")
@Data
public class ServerEntity {
    /**
     * L'identifiant unique du serveur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Le titre ou nom du serveur.
     */
    private String title;

    /**
     * L'adresse IP du serveur.
     */
    private String serverIp;

    /**
     * Le port du serveur.
     */
    private int serverPort;

    /**
     * Le port RCON (Remote Console) du serveur.
     */
    private int rconPort;

    /**
     * Le mot de passe RCON du serveur.
     */
    private String rconPassword;
}
