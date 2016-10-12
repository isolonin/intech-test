package ru.intech.test.filters;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.annotation.WebFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.intech.test.controllers.Global;
import ru.intech.test.dbobjects.Users;

/**
 *
 * @author isolonin
 */
@WebFilter(urlPatterns = {"/root/*"})
//Задача класса отлавливать все запросы к файлам в домашней директории
public class CookieCheck implements Filter{
    @EJB
    private Global global;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie c:cookies){
                if(c.getName().equals(Global.SESSION_COOKIE_NAME)){
                    Users user = global.getUserBySessionId(c.getValue());
                    if(user != null){
                        chain.doFilter(request, response);
                        return;
                    }          
                    break;
                }
            }
        }

        String contextPath = req.getContextPath();
        res.sendRedirect(contextPath+"/error403.do");
    }

    @Override
    public void destroy() {
    }
    
}
