package be.bnair.bevo.models;

import lombok.Data;

@Data
public class Information {
    private long register_count;

    public Information(long register_count){
        this.register_count = register_count;
    }
}
