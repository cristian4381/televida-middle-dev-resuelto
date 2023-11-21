package biz.televida.reclutamiento.ws.api.core;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import biz.televida.integration.core.Context;
import biz.televida.integration.core.SpringBeanContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class StartUp extends HttpServlet {

    private static final String SPRING_CONTEXT = "spring-context.xml";

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(SPRING_CONTEXT);
        SpringBeanContext beanContext = new SpringBeanContext(springContext);
        Context.setBeanContext(beanContext);
    }

    @Override
    public void destroy() {
        Context.closeContexts();
    }

}
