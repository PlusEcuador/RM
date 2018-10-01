package controlador;

import entidades.Parroquia;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import ejb.ParroquiaFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("parroquiaController")
@SessionScoped
public class ParroquiaController implements Serializable {

    @EJB
    private ejb.ParroquiaFacade ejbFacade;
    private List<Parroquia> items = null;
    private Parroquia selected;

    public ParroquiaController() {
    }

    public Parroquia getSelected() {
        return selected;
    }

    public void setSelected(Parroquia selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ParroquiaFacade getFacade() {
        return ejbFacade;
    }

    public Parroquia prepareCreate() {
        selected = new Parroquia();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        //esta linea asigna el id de acuerdo a una consulta del max valor de id
        //en la tabla respectiva
        selected.setParId(getFacade().asignarID());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ParroquiaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ParroquiaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ParroquiaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Parroquia> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Parroquia getParroquia(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Parroquia> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Parroquia> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Parroquia.class)
    public static class ParroquiaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParroquiaController controller = (ParroquiaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "parroquiaController");
            return controller.getParroquia(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = new java.lang.Long(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Parroquia) {
                Parroquia o = (Parroquia) object;
                return getStringKey(o.getParId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Parroquia.class.getName()});
                return null;
            }
        }

    }

}
