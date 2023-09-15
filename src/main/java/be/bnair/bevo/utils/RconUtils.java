package be.bnair.bevo.utils;

import be.bnair.bevo.models.entities.ServerEntity;
import net.kronos.rkon.core.Rcon;

public class RconUtils {
    public static void sendCommandToServer(ServerEntity entity, String command) throws Exception {
        new Rcon(entity.getServerIp(), entity.getRconPort(), entity.getRconPassword().getBytes()).command(command);
    }
}
