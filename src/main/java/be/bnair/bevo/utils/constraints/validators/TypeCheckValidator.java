package be.bnair.bevo.utils.constraints.validators;

import be.bnair.bevo.models.enums.EnumRewardType;
import be.bnair.bevo.models.forms.VoteRewardForm;
import be.bnair.bevo.utils.constraints.TypeCheckConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validateur personnalisé pour la contrainte de type de récompense.
 * Ce validateur est utilisé pour vérifier si le type de récompense et ses valeurs associées sont valides.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
public class TypeCheckValidator implements ConstraintValidator<TypeCheckConstraint, VoteRewardForm> {

    /**
     * Initialise le validateur.
     *
     * @param constraintAnnotation L'annotation de contrainte associée au validateur.
     */
    @Override
    public void initialize(TypeCheckConstraint constraintAnnotation) {
    }

    /**
     * Valide si le type de récompense et ses valeurs associées sont valides.
     *
     * @param form     L'objet VoteRewardForm contenant le type de récompense et ses valeurs associées.
     * @param context  Le contexte de validation.
     * @return true si le type de récompense est valide, sinon false.
     */
    @Override
    public boolean isValid(VoteRewardForm form, ConstraintValidatorContext context) {
        if (form == null) {
            return false;
        }

        if(form.getRewardType().equalsIgnoreCase(EnumRewardType.COMMAND.toString())) {
            if(form.getCommand() != null && !form.getCommand().isEmpty() && form.getCommand().length() >= 3) return true;
        } else {
            if(form.getCredit() > 1 && (form.getCommand() == null || form.getCommand().isEmpty())) return true;
        }
        return false;
    }
}