package fragnito.U5W2D4.payloads;

import java.time.LocalDateTime;

public record ErrorsPayload(
        String message,
        LocalDateTime timestamp
) {
}
