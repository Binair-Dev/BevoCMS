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

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties
public class BevoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BevoApplication.class, args);
    }
}
