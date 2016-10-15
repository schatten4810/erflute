package org.dbflute.erflute.editor.view.action.dbexport;

import org.dbflute.erflute.Activator;
import org.dbflute.erflute.core.DisplayMessages;
import org.dbflute.erflute.core.ImageKey;
import org.dbflute.erflute.editor.MainDiagramEditor;
import org.dbflute.erflute.editor.controller.command.common.ChangeSettingsCommand;
import org.dbflute.erflute.editor.model.ERDiagram;
import org.dbflute.erflute.editor.model.settings.Settings;
import org.dbflute.erflute.editor.view.action.AbstractBaseAction;
import org.dbflute.erflute.editor.view.dialog.dbexport.ExportToDDLDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * @author modified by jflute (originated in ermaster)
 */
public class ExportToDDLAction extends AbstractBaseAction {

    public static final String ID = ExportToDDLAction.class.getName();

    public ExportToDDLAction(MainDiagramEditor editor) {
        super(ID, DisplayMessages.getMessage("action.title.export.ddl"), editor);
        this.setImageDescriptor(Activator.getImageDescriptor(ImageKey.EXPORT_DDL));
    }

    @Override
    public void execute(Event event) {
        final ERDiagram diagram = getDiagram();
        final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        final ExportToDDLDialog dialog = new ExportToDDLDialog(shell, diagram, this.getEditorPart(), getGraphicalViewer());
        dialog.open();
        refreshProject();
        if (dialog.getExportSetting() != null
                && !diagram.getDiagramContents().getSettings().getExportSetting().equals(dialog.getExportSetting())) {
            final Settings newSettings = (Settings) diagram.getDiagramContents().getSettings().clone();
            newSettings.setExportSetting(dialog.getExportSetting());
            final ChangeSettingsCommand command = new ChangeSettingsCommand(diagram, newSettings);
            execute(command);
        }
    }
}
