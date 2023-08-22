package be.bnair.bevo.models.dto;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.VoteEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteDTO {
    private Long id;
    private LocalDate date;
    private String user;

    public static VoteDTO toDTO(VoteEntity entity){
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

        return VoteDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .user(entity.getUser().getUsername())
                .build();
    }
}