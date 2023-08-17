package be.bnair.bevo.utils.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import be.bnair.bevo.utils.constraints.validators.PasswordMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatchConstraint {
    String message() default "Le mot de passe et la confirmation de mot de passe doivent être idententiques.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}