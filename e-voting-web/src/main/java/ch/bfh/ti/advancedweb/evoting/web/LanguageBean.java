package ch.bfh.ti.advancedweb.evoting.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Bean in order to switch the Languages
 */
@Component
@Scope(value = "session")
public class LanguageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String localeCode;

    private Locale locale;

    private static Map<String, Locale> countries;

    static {
        countries = new LinkedHashMap<>();
        countries.put("English", Locale.ENGLISH);
        countries.put("German", Locale.GERMAN);
        countries.put("French", Locale.FRANCE);
    }

    @PostConstruct
    public void init() {
        final Locale defaultLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        this.setNewLocale(defaultLocale);
    }

    public Map<String, Locale> getCountriesInMap() {
        return countries;
    }


    public String getLocaleCode() {
        return localeCode;
    }


    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    /**
     * Invoked by the valueChangeListener
     *
     * @param e the ValueChangeEvent containig the new key of the selected language
     */
    public void countryLocaleCodeChanged(ValueChangeEvent e) {
        setNewLocaleByKey(e.getNewValue().toString());
    }

    public void setNewLocaleByKey(String newLocaleValue) {
        //loop country map to compare the locale code
        for (Map.Entry<String, Locale> entry : countries.entrySet()) {
            if (entry.getValue().toString().equals(newLocaleValue)) {
                final Locale entryValue = (Locale) entry.getValue();
                setNewLocale(entryValue);

            }
        }
    }

    public void setNewLocale(Locale entryValue) {
        FacesContext.getCurrentInstance()
                .getViewRoot().setLocale(entryValue);
        this.locale = entryValue;
        this.localeCode = entryValue.toString();
    }

    public Locale getLocale() {
        return locale;
    }
}