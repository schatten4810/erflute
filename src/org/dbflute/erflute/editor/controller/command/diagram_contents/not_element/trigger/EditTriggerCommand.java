package org.dbflute.erflute.editor.controller.command.diagram_contents.not_element.trigger;

import org.dbflute.erflute.editor.controller.command.AbstractCommand;
import org.dbflute.erflute.editor.model.ERDiagram;
import org.dbflute.erflute.editor.model.diagram_contents.not_element.trigger.Trigger;
import org.dbflute.erflute.editor.model.diagram_contents.not_element.trigger.TriggerSet;

public class EditTriggerCommand extends AbstractCommand {

    private TriggerSet triggerSet;

    private Trigger oldTrigger;

    private Trigger newTrigger;

    public EditTriggerCommand(ERDiagram diagram, Trigger oldTrigger, Trigger newTrigger) {
        this.triggerSet = diagram.getDiagramContents().getTriggerSet();
        this.oldTrigger = oldTrigger;
        this.newTrigger = newTrigger;
    }

    @Override
    protected void doExecute() {
        this.triggerSet.remove(this.oldTrigger);
        this.triggerSet.addTrigger(this.newTrigger);
    }

    @Override
    protected void doUndo() {
        this.triggerSet.remove(this.newTrigger);
        this.triggerSet.addTrigger(this.oldTrigger);
    }
}
