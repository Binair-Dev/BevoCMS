package be.bnair.bevo.utils.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import be.bnair.bevo.utils.constraints.validators.TypeCheckValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Annotation de contrainte personnalisée pour valider le type d'une classe.
 * Cette annotation peut être utilisée pour vérifier si une classe hérite ou implémente un certain type.
 *
 * @author Brian Van Bellinghen
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TypeCheckValidator.class)
public @interface TypeCheckConstraint {

    /**
     * Message d'erreur affiché en cas de non-correspondance avec le type attendu.
     *
     * @return Le message d'erreur par défaut.
     */
    String message() default "";

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