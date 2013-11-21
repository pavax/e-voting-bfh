package ch.bfh.ti.advancedweb.web.utils;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.util.Assert;

import javax.faces.context.FacesContext;
import java.util.Map;


public class ViewScope implements Scope {

    public Object get(String name, ObjectFactory objectFactory) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        Assert.notNull(facesContext, "FacesContext is not available in current thread: " + Thread.currentThread());
        Map<String, Object> viewMap = facesContext.getViewRoot().getViewMap();
        if (viewMap.containsKey(name)) {
            return viewMap.get(name);
        } else {
            Object object = objectFactory.getObject();
            viewMap.put(name, object);

            return object;
        }
    }

    @Override
    public Object remove(String name) {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
    }

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // Not supported
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

}