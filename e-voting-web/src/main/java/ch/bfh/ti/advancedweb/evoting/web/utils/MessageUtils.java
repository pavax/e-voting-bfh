package ch.bfh.ti.advancedweb.evoting.web.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

public final class MessageUtils {

    public static String translate(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        return bundle.getString(key);
    }

    public static void addErrorMessage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, translate(key), null));
    }

    public static void addWarnMessage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, translate(key), null));
    }

    public static void addInfoMessage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, translate(key), null));
    }

}
