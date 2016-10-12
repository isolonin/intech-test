/*
 * Code author Solonin Ivan (osmos_two@rambler.ru)
 */
package ru.intech.test.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import ru.intech.test.dbcontrollers.MelodiesFacade;
import ru.intech.test.dbobjects.Melodies;
import ru.intech.test.dbobjects.Users;

@Named(value = "userMenu")
@RequestScoped
public class UserMenu {
    @EJB
    private Global global;
    @EJB
    private MelodiesFacade melodiesFacade;
    
    public void playMusic(){
        String message = "<i class='uk-icon-music uk-margin-right'/><i class='uk-icon-music uk-margin-right'/><i class='uk-icon-music uk-margin-right'/>Тирлим-бом-бом";
        Global.showMessage(FacesMessage.SEVERITY_INFO, message, "");
    }
    
    public void saveMusic(){
        try{
            Users user = global.getCurrentSessionUser();
            if(user != null){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                Melodies melodie = new Melodies();
                melodie.setUserId(user);
                melodie.setName(format.format(new Date()));
                melodiesFacade.create(melodie);

                List<Melodies> melodiesList = new ArrayList<>(user.getMelodiesCollection());
                melodiesList.add(melodie);
                user.setMelodiesCollection(melodiesList);

                Global.showMessage(FacesMessage.SEVERITY_INFO, "Мелодия успешно сохранена", "");
            }
        }catch (Exception ex){
            Global.showMessage(FacesMessage.SEVERITY_ERROR, Global.INTERNAL_ERROR, "");
        }        
    }
}
