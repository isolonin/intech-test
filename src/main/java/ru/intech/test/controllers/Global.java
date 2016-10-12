/*
 * Code author Solonin Ivan (osmos_two@rambler.ru)
 */
package ru.intech.test.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import ru.intech.test.dbobjects.Users;

@Stateless
@Startup
@Singleton
@Named(value = "global")
//Предполагается, что в этом классе будут храниться общие для всего приложения константы, методы и возможно кэш запросов базы
public class Global {
    public static final String INTERNAL_ERROR = "Произошла внутренная ошибка";
    public static final String SESSION_COOKIE_NAME = "intech-session-id";
    private final Integer sessionTimeLive = 3600;
    
    private Map<String, Users> userSessions;
    
    @PostConstruct
    public void init(){
        userSessions = new HashMap<>();
    }
    
    public String addSession(Users user){
        try {
            String uuid = UUID.randomUUID().toString();
            user.setLastTimeSessionActive(new Date());
            user.setUuid(uuid);
            userSessions.put(uuid, user);
            return uuid;
        }catch (Exception ex){
            return null;
        }
    }
    
    public boolean removeSession(String uuid){
        try {
            if(userSessions.remove(uuid) != null){
                return true;
            }else {
                return false;
            }
        }catch (Exception ex){
            return false;
        }
    }
    
    public Users getCurrentSessionUser(){
        Map<String,Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        if(cookies != null && cookies.isEmpty() == false){
            Cookie cookie = (Cookie)cookies.get(SESSION_COOKIE_NAME);
            Users user = getUserBySessionId(cookie.getValue());
            return user;
        }
        return null;
    }
    
    public Users getUserBySessionId(String uuid){
        Users user = userSessions.get(uuid);
        if(user != null && user.getLastTimeSessionActive() != null){
            //Проверяем не протухла ли сессия
            Long sessionStartTime = user.getLastTimeSessionActive().getTime()/1000;
            Long currentTime = new Date().getTime()/1000;
            if(currentTime - sessionStartTime < sessionTimeLive){
                user.setLastTimeSessionActive(new Date());
                return user;
            }else {
                userSessions.remove(uuid);            
            }
        }
        return null;
    }
    
    public static void showMessage(FacesMessage.Severity severity, String summary, String detail){
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        currentInstance.addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
