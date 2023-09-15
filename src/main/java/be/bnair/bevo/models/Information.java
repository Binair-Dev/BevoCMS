package be.bnair.bevo.models;

import lombok.Data;

@Data
public class Information {
    private long register_count;
    private int view_count;
    private String connected_players;
    private int staff;

    public Information(long register_count, int view_count, String connected_players, int staff) {
        this.register_count = register_count;
        this.view_count = view_count;
        this.connected_players = connected_players;
        this.staff = staff;
    }
}
