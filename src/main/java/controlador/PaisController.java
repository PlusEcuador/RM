package controlador;

import entidades.Pais;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import ejb.PaisFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.model.UploadedFile;

@Named("paisController")
@ViewScoped
public class PaisController implements Serializable {

    @EJB
    private ejb.PaisFacade ejbFacade;
    private List<Pais> items = null;
    private Pais selected;

    private UploadedFile file;

    public PaisController() {
        System.out.println("**Controlador...**");
    }

    @PostConstruct
    public void postConstructor() {
        System.out.println("**Post constructor...**");
    }

    //METODOS PROPIOS
    public void subirArchivo(FileUploadEvent event) throws IOException, InvalidFormatException {
        try {

            Boolean error = false;
            List<String> errores = new ArrayList<>();
            if (event.getFile() != null) {
                InputStream is = event.getFile().getInputstream();

                Workbook workbook = WorkbookFactory.create(is);
                Sheet sheet = workbook.getSheetAt(0);

                System.out.println("nombre de hoja: " + sheet.getSheetName());

                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {//iterador filas

                    Row row = rowIterator.next();

                    Iterator<Cell> cellIterator = row.cellIterator();
                    if (row.getRowNum() > 1) {
                        Pais paisNuevo = new Pais();
                        List<String> datosObjeto = new ArrayList<>();
                        error = false;

                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            CellStyle style = workbook.createCellStyle();

                            switch (cell.getCellTypeEnum()) {
                                case NUMERIC:
                                    error = true;
                                    errores.add(cell.getAddress().toString());
                                    style.setFillBackgroundColor(IndexedColors.RED.getIndex());
                                    cell.setCellStyle(style);
                                    break;
                                case STRING:
                                    System.out.println("Agregando celda: " + cell.getAddress().toString()
                                            + ": " + cell.getStringCellValue() + ";tipo: " + cell.getCellTypeEnum().name());
                                    datosObjeto.add(cell.getStringCellValue().trim());
                                    break;
                                case BOOLEAN:
                                    break;
                                case FORMULA:
                                    break;
                                case BLANK:
                                    System.out.println("Celda Vacía: " + cell.getAddress());
                                    error = true;
                                    errores.add(cell.getAddress().toString());
                                    style.setFillBackgroundColor(IndexedColors.RED.getIndex());
                                    cell.setCellStyle(style);
                                    break;
                                case _NONE:
                                    System.out.println("Celda Vacía: " + cell.getAddress());
                                    errores.add(cell.getAddress().toString());
                                    error = true;
                                    style.setFillBackgroundColor(IndexedColors.RED.getIndex());
                                    cell.setCellStyle(style);
                                    break;
                            }
                        }

                        //IMPRESION DE DATOS DEL OBJECTO
//                    System.out.println("--Impresion de objeto--");
//                    for (String string : datosObjeto) {
//                        System.out.println(string);
//                    }
                        //SI NO HAY ERRORES Y EL NUMERO DE ATRIBUTOS DEL OBJETO ES CORRECTO
                        if (!error) {
                            paisNuevo = new Pais(getFacade().asignarID(), datosObjeto.get(0), datosObjeto.get(1),
                                    null, "", datosObjeto.get(2), datosObjeto.get(3));

                            paisNuevo.setPaiNombre(datosObjeto.get(0));
                            paisNuevo.setPaiEstado(datosObjeto.get(1));
                            paisNuevo.setPaiCodigo(datosObjeto.get(2));
                            paisNuevo.setPaiInicial(datosObjeto.get(3));

                            getFacade().create(paisNuevo);
                            System.out.println("Objeto Ingresado!!");
                        } else {
                            
                            for (String errore : errores) {
                                FacesContext.getCurrentInstance().addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                            "Error 0005: Ocurrio un error en la carga de archivo ("+errore.toString(), ""));
                            }
                            

//                            FacesContext fc = FacesContext.getCurrentInstance();
//                            ExternalContext ec = fc.getExternalContext();
//
//                            String nombreArchivo = "ListaPaises_" + Calendar.getInstance().getTime().toString() + ".xlsx";
//                            ec.responseReset(); // limpia las cabeceras de la respuesta
//                            ec.setResponseContentType("vnd.ms-excel"); // se define el tipo de contenido del archivo
//                            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\""); //Se descarga desde el navegador
//
//                            OutputStream output = ec.getResponseOutputStream();
//                            workbook.write(output);//Se escribe el documento
//
//                            fc.responseComplete();//cierra la respuesta

                        }
                    }

                }//FIN DE ITERADOR DE FILAS
//            }
            } else {
                System.out.println("el archivo es nulo");

            }
            //ACTUALIZO LA LISTA DE PAISES
            items = getFacade().findAll();
        } catch (Exception e) {
        }

    }

    public void exportarExcel() throws IOException, InvalidFormatException {
        try {

            Workbook wb = WorkbookFactory.create(new File("F:\\RM\\Proyectos Netbeans\\RM\\src\\main\\webapp\\Templates\\listaPais.xlsx"));
            Sheet sheet = wb.getSheetAt(0);

//LLENANDO EL EXCEL CON LA LISTA DE OBJETOS 

            int i = 2;//comienza a escribir desde la fila 2
            for (Pais pais : items) {
                System.out.println("controlador.PaisController.exportarExcel()1");
                System.out.println("Creo la fila: " + i);

                Row row = sheet.createRow(i);
                Cell cell;

                System.out.println("Creo la columna 0");
                cell = row.createCell(0);
                cell.setCellValue(pais.getPaiNombre());
                System.out.println("Creo la columna 1");
                cell = row.createCell(1);
                cell.setCellValue(pais.getPaiCodigo());
                System.out.println("Creo la columna 2");
                cell = row.createCell(2);
                cell.setCellValue(pais.getPaiEstado());
                System.out.println("Creo la columna 3");
                cell = row.createCell(3);
                cell.setCellValue(pais.getPaiInicial());
                i++;
            }

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            String nombreArchivo = "ListaPaises_" + Calendar.getInstance().getTime().toString() + ".xlsx";
            ec.responseReset(); // limpia las cabeceras de la respuesta
            ec.setResponseContentType("vnd.ms-excel"); // se define el tipo de contenido del archivo
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\""); //Se descarga desde el navegador

            OutputStream output = ec.getResponseOutputStream();
            wb.write(output);//Se escribe el documento

            fc.responseComplete();//cierra la respuesta

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error 0004: Ocurrio un error con la carga y/o modificacion del template ListaPais", ""));

        }

    }

    //METODOS CREADOS POR ASISTENTE
    public Pais prepareCreate() {
        selected = new Pais();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        //esta linea asigna el id de acuerdo a una consulta del max valor de id
        //en la tabla respectiva
        selected.setPaiId(getFacade().asignarID());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PaisCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PaisUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PaisDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
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

    @FacesConverter(forClass = Pais.class)
    public static class PaisControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PaisController controller = (PaisController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "paisController");
            return controller.getPais(getKey(value));
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
            if (object instanceof Pais) {
                Pais o = (Pais) object;
                return getStringKey(o.getPaiId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Pais.class.getName()});
                return null;
            }
        }

    }

    //GETTERS Y SETTERS
    public Pais getSelected() {
        return selected;
    }

    public void setSelected(Pais selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PaisFacade getFacade() {
        return ejbFacade;
    }

    public List<Pais> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public Pais getPais(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Pais> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Pais> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
