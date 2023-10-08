package be.bnair.bevo.services;

import java.util.List;
import java.util.Optional;

/**
 * Interface générique pour les services CRUD (Create, Read, Update, Delete).
 * Cette interface définit les opérations de base pour la gestion des entités.
 *
 * @author Brian Van Bellinghen
 *
 * @param <T>   Le type de l'entité.
 * @param <TKey> Le type de la clé primaire de l'entité.
 */
public interface CrudService<T, TKey> {
    /**
     * Crée une nouvelle entité.
     *
     * @param creater L'entité à créer.
     * @return L'entité créée.
     */
    T create(T creater);

    /**
     * Supprime une entité en fonction de sa clé primaire.
     *
     * @param id La clé primaire de l'entité à supprimer.
     * @throws Exception En cas d'erreur lors de la suppression de l'entité.
     */
    default void remove(TKey id) throws Exception { throw new Exception(); }

    /**
     * Met à jour une entité en fonction de sa clé primaire.
     *
     * @param id      La clé primaire de l'entité à mettre à jour.
     * @param updater L'entité avec les nouvelles données.
     * @return L'entité mise à jour.
     * @throws Exception En cas d'erreur lors de la mise à jour de l'entité.
     */
    default T update(TKey id, T updater) throws Exception { throw new Exception(); }

    /**
     * Récupère toutes les entités.
     *
     * @return Une liste de toutes les entités.
     */
    List<T> getAll();

    /**
     * Récupère une entité en fonction de sa clé primaire.
     *
     * @param id La clé primaire de l'entité à récupérer.
     * @return L'entité correspondant à la clé primaire, ou une option vide si aucune entité correspondante n'est trouvée.
     */
    Optional<T> getOneById(TKey id);
}
