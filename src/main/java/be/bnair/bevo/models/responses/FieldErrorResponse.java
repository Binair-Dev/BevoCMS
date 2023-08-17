package be.bnair.bevo.models.responses;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class FieldErrorResponse {
    public final int code;
    public final List<String> messages;
}
