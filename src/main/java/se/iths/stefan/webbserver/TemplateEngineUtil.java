package se.iths.stefan.webbserver;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import org.thymeleaf.web.IWebExchange;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;

public class TemplateEngineUtil {

    private static TemplateEngine templateEngine;
    private static JakartaServletWebApplication application;

    // initieras en gång
    private static TemplateEngine getTemplateEngine(ServletContext servletContext) {
        if (templateEngine == null) {
            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setTemplateMode(TemplateMode.HTML);
            resolver.setPrefix("templates/");
            resolver.setSuffix(".html");
            resolver.setCharacterEncoding("UTF-8");

            templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(resolver);

            // Bygg web application för WebContext (krävs i 3.1)
            application = JakartaServletWebApplication.buildApplication(servletContext);
        }
        return templateEngine;
    }

    public static void process(String templateName,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               ServletContext servletContext) throws Exception {

        response.setContentType("text/html;charset=UTF-8");

        // Bygg IWebExchange för 3.1 WebContext
        TemplateEngine engine = getTemplateEngine(servletContext);
        IWebExchange webExchange = application.buildExchange(request, response);

        WebContext ctx = new WebContext(webExchange);
        ctx.setVariable("title", "Hej från Thymeleaf 3.1!");

        // Request attributes blir automatiskt tillgängliga som variabler
        // t.ex. om du gör request.setAttribute("message", "Tja!") i servleten

        engine.process(templateName, ctx, response.getWriter());
    }
}
