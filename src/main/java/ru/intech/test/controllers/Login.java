/*
 * Code author Solonin Ivan (osmos_two@rambler.ru)
 */
package ru.intech.test.controllers;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import ru.intech.test.dbcontrollers.UsersFacade;
import ru.intech.test.dbobjects.Users;

@Named(value = "login")
@RequestScoped
public class Login {
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private Global global;
    
    private String ident;
    
    public void Loguot(){
        Users user = global.getCurrentSessionUser();
        if(user != null){
            if(global.removeSession(user.getUuid())){
                String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                redirect(contextPath);
            }            
        }
    }
    
    public void check(){        
        //Дабы не привязываться к настройкам безопасности на стороне сервера приложений аутентификация и авторизация реализуется на стороне приложения
        List<Users> users = usersFacade.findAll();
        if(users != null && ident != null && ident.isEmpty() == false){
            for(Users user:users){
                //Если введённый дентификатор и MSISDN пользователя совпадают
                if(user.getMsisdn().equalsIgnoreCase(ident)){
                    //создаём сессию в глобальном классе 
                    String uuid = global.addSession(user);
                    if(uuid != null){
                        //добавляем cookie по которому будем идентифицировать текущую сессию
                        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(Global.SESSION_COOKIE_NAME, uuid, null);                        
                        redirect("root/home.do");
                    }else {
                        Global.showMessage(FacesMessage.SEVERITY_ERROR, Global.INTERNAL_ERROR, "");
                    }
                    break;
                }
            }
            
            Global.showMessage(FacesMessage.SEVERITY_ERROR, "Идентификатор указан некорректно", "");
        }
    }
    
    private void redirect(String path){
        try {
            //Переходим на домашную страницу пользователя
            FacesContext.getCurrentInstance().getExternalContext().redirect(path);
        } catch (IOException ex) {
            Global.showMessage(FacesMessage.SEVERITY_ERROR, Global.INTERNAL_ERROR, "");
        }
    }
    

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }
}
