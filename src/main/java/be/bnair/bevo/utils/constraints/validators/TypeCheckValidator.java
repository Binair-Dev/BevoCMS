package be.bnair.bevo.utils.constraints.validators;

import be.bnair.bevo.models.enums.EnumRewardType;
import be.bnair.bevo.models.forms.VoteRewardForm;
import be.bnair.bevo.utils.constraints.TypeCheckConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TypeCheckValidator implements ConstraintValidator<TypeCheckConstraint, VoteRewardForm> {
    @Override
    public void initialize(TypeCheckConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(VoteRewardForm form, ConstraintValidatorContext context) {
        if (form == null) {
            return false;
        }
        
        if(form.getRewardType() == EnumRewardType.COMMAND.toString()) {
            if(form.getCommand() != null && !form.getCommand().isEmpty() && form.getCommand().length() > 3 && form.getCredit() == 0) return true;
            return false;
        } else {
            if(form.getCredit() > 1 && form.getCommand() == null) return true;
            return false;
        }
    }
}