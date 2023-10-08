package be.bnair.bevo.utils.constraints.validators;

import be.bnair.bevo.models.forms.RegisterForm;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validateur personnalisé pour la contrainte de correspondance des mots de passe.
 * Ce validateur est utilisé pour vérifier si le champ de mot de passe et le champ de confirmation du mot de passe correspondent.
 *
 * @author Brian Van Bellinghen
 */
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatchConstraint, RegisterForm> {

    /**
     * Initialise le validateur.
     *
     * @param constraintAnnotation L'annotation de contrainte associée au validateur.
     */
    @Override
    public void initialize(PasswordMatchConstraint constraintAnnotation) {
    }

    /**
     * Valide si les champs de mot de passe et de confirmation du mot de passe correspondent.
     *
     * @param string   L'objet RegisterForm contenant le mot de passe et la confirmation du mot de passe.
     * @param context  Le contexte de validation.
     * @return true si les mots de passe correspondent, sinon false.
     */
    @Override
    public boolean isValid(RegisterForm string, ConstraintValidatorContext context) {
        if (string == null) {
            return false;
        }

        String password = string.getPassword();
        String passwordConfirm = string.getConfirmPassword();

        return password != null && password.equals(passwordConfirm);
    }
}