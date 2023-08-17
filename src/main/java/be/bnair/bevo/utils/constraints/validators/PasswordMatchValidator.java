package be.bnair.bevo.utils.constraints.validators;

import be.bnair.bevo.models.forms.RegisterForm;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatchConstraint, RegisterForm> {
    @Override
    public void initialize(PasswordMatchConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegisterForm string, ConstraintValidatorContext context) {
        if (string == null) {
            return true;
        }

        String password = string.getPassword();
        String passwordConfirm = string.getConfirmPassword();

        return password != null && password.equals(passwordConfirm);
    }
}