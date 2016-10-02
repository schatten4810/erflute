package org.insightech.er.editor.persistent.xml.reader;

import org.insightech.er.db.DBManagerFactory;
import org.insightech.er.editor.persistent.xml.PersistentXml;
import org.w3c.dom.Element;

/**
 * @author modified by jflute (originated in ermaster)
 */
public class ReadDatabaseLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final PersistentXml persistentXml;
    protected final ReadAssistLogic assistLogic;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public ReadDatabaseLogic(PersistentXml persistentXml, ReadAssistLogic assistLogic) {
        this.persistentXml = persistentXml;
        this.assistLogic = assistLogic;
    }

    // ===================================================================================
    //                                                                            Database
    //                                                                            ========
    public String loadDatabase(Element settingsElement) {
        String database = getStringValue(settingsElement, "database");
        if (database == null) {
            database = DBManagerFactory.getAllDBList().get(0);
        }
        return database;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private String getStringValue(Element element, String tagname) {
        return assistLogic.getStringValue(element, tagname);
    }
}