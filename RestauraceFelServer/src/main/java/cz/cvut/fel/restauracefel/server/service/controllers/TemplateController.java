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

    public boolean createTemplate(Template template) {
        if (template != null) {
            template.create();
            return true;
        } else {
            return false;
        }

    }

    public boolean createNewTemplateList(int idTemplate, int idTypeWorkShift) {
        if (idTemplate > 0 && idTypeWorkShift > 0) {
            TemplateList tl = new TemplateList();
            tl.setIdTemplate(idTemplate);
            tl.setIdTypeworkshift(idTypeWorkShift);
            tl.create();
            return true;
        } else {
            return false;
        }

    }

    public Template findTemplateByName(String name) {
        return Template.findByName(name);
    }

    public List getTemplateListByTemplateId(int idTemplate) {
        return TemplateList.getTemplateListByTemplateId(idTemplate);
    }

    public boolean deleteTemplateByName(String name) {
        Template temp = Template.findByName(name);

        if (temp != null) {
            int id = temp.getIdTemplate();
            List list = TemplateList.getTemplateListByTemplateId(id);
            TemplateList templateList = null;
            for (Object o : list) {
                templateList = (TemplateList) o;
                templateList.delete();
            }
            temp.delete();
            return true;
        } else {
            return false;
        }
    }
}

