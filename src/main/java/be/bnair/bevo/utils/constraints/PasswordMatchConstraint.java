package be.bnair.bevo.utils.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import be.bnair.bevo.utils.constraints.validators.PasswordMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Annotation de contrainte personnalisée pour valider la correspondance entre un champ de mot de passe
 * et un champ de confirmation de mot de passe dans une classe.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatchConstraint {

    /**
     * Message d'erreur affiché en cas de non-correspondance entre les champs de mot de passe.
     *
     * @return Le message d'erreur par défaut.
     */
    String message() default "Le mot de passe et la confirmation de mot de passe doivent être idententiques.";

    /**
     * Groupes de validation auxquels cette contrainte appartient. Par défaut, la contrainte n'appartient à aucun groupe.
     *
     * @return Les groupes de validation par défaut.
     */
    Class<?>[] groups() default {};

    /**
     * Payload utilisé pour fournir des informations supplémentaires sur la contrainte lors de la validation.
     * Par défaut, aucun payload n'est défini.
     *
     * @return Les payloads par défaut.
     */
    Class<? extends Payload>[] payload() default {};
}