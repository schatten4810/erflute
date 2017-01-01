package org.dbflute.erflute.editor.controller.command.common.notation;

import org.dbflute.erflute.editor.controller.command.AbstractCommand;
import org.dbflute.erflute.editor.model.ERDiagram;
import org.dbflute.erflute.editor.model.settings.DiagramSettings;

public class ChangeNotationCommand extends AbstractCommand {

    private ERDiagram diagram;

    private String oldNotation;

    private String newNotation;

    private DiagramSettings settings;

    public ChangeNotationCommand(ERDiagram diagram, String notation) {
        this.diagram = diagram;
        this.settings = diagram.getDiagramContents().getSettings();
        this.newNotation = notation;
        this.oldNotation = this.settings.getNotation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doExecute() {
        this.settings.setNotation(this.newNotation);
        this.diagram.changeAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doUndo() {
        this.settings.setNotation(this.oldNotation);
        this.diagram.changeAll();
    }
}
