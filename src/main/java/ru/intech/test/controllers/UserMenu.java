/*
 * Code author Solonin Ivan (osmos_two@rambler.ru)
 */
package ru.intech.test.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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
    
    public void removeMusic(){
        try{
            Users user = global.getCurrentSessionUser();
            if(user != null){
                Collection<Melodies> melodies = user.getMelodiesCollection();
                if(melodies == null || melodies.isEmpty()){
                    Global.showMessage(FacesMessage.SEVERITY_INFO, "У Вас больше не осталось мелодий", "");
                }else {
                    Iterator<Melodies> iterator = melodies.iterator();
                    Melodies melodie = iterator.next();                    
                    String name = melodie.getName();
                    melodiesFacade.remove(melodie);                    
                    iterator.remove();
                    
                    Global.showMessage(FacesMessage.SEVERITY_INFO, "Мелодия от "+name+" удалена", "");
                }
            }
        }catch (Exception ex){
            Global.showMessage(FacesMessage.SEVERITY_ERROR, Global.INTERNAL_ERROR, "");
        }        
    }
    
    public void saveMusic(){
        try{
            Users user = global.getCurrentSessionUser();
            if(user != null){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
            Global.showMessage(FacesMessage.SEVERITY_ERROR, "Не нужно так часто нажимать, а то логи все в эксепшинах будут :)", "");
        }        
    }
}
