package co2123.gamestudio.controller;

import co2123.gamestudio.GameStudioApplication;
import co2123.gamestudio.model.Studio;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class StudioValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Studio.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Studio studio = (Studio) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "Your studio needs a name!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "headquarters", "", "Your studio needs a headquarters!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specialism", "", "Your studio needs a specialism!");

        if (studio.getSpecialism() != null && studio.getSpecialism().length() > 50) {
            errors.rejectValue("specialism", "", "Specialism must be 50 characters or fewer.");
        }

        for(Studio a : GameStudioApplication.studioList){
            if(a.getName().equals(studio.getName())){
                errors.rejectValue("name", "", "Studio name already exists.");
            }
        }



    }
}
