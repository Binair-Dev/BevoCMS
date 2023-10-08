package be.bnair.bevo.models.responses;

import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * Cette classe représente une réponse contenant une liste d'images.
 *
 * @author Brian Van Bellinghen
 */
@RequiredArgsConstructor
public class ImageResponse {
    /**
     * La liste des noms de fichiers d'images.
     */
    public final List<String> images;
}
