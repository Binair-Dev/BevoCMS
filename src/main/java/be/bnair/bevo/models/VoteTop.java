package be.bnair.bevo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteTop {
    private String user_name;
    private int vote_amount;
}
