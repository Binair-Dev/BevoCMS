package be.bnair.bevo.models.responses;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageResponse {
    public final int code;
    public final String message;
}
