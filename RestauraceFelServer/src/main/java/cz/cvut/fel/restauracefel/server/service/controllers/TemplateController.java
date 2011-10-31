package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Template;
import cz.cvut.fel.restauracefel.hibernate.TemplateList;
import java.util.List;

/**
 * Kontroler pro práci se šablonami. Pro template a templatelist.
 * @author kosekm
 */
public class TemplateController {

    protected static TemplateController instance = null;
    protected Template template = null;

    private TemplateController() {
    }

    public static TemplateController getInstance() {
        if (instance == null) {
            instance = new TemplateController();
        }
        return instance;
    }

    public List getTemplates() {
        return Template.findAll();
    }

    public void createTemplate(Template template) {
        template.create();
    }
    
    public void createNewTemplateList(int idTemplate, int idTypeWorkShift) {
        TemplateList tl = new TemplateList();
        tl.setIdTemplate(idTemplate);
        tl.setIdTypeworkshift(idTypeWorkShift);
        tl.create();
    }

    public Template findTemplateByName(String name) {
        return Template.findByName(name);
    }
}
