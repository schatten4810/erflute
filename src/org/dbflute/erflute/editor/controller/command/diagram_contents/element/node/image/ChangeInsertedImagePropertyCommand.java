package org.dbflute.erflute.editor.controller.command.diagram_contents.element.node.image;

import org.dbflute.erflute.editor.controller.command.AbstractCommand;
import org.dbflute.erflute.editor.model.ERDiagram;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.image.InsertedImage;

public class ChangeInsertedImagePropertyCommand extends AbstractCommand {

    protected InsertedImage insertedImage;

    protected InsertedImage oldInsertedImage;

    protected InsertedImage newInsertedImage;

    public ChangeInsertedImagePropertyCommand(ERDiagram diagram, InsertedImage insertedImage, InsertedImage newInsertedImage,
            InsertedImage oldInsertedImage) {
        this.insertedImage = insertedImage;
        this.oldInsertedImage = oldInsertedImage;
        this.newInsertedImage = newInsertedImage;
    }

    @Override
    protected void doExecute() {
        this.insertedImage.setHue(this.newInsertedImage.getHue());
        this.insertedImage.setSaturation(this.newInsertedImage.getSaturation());
        this.insertedImage.setBrightness(this.newInsertedImage.getBrightness());
        this.insertedImage.setFixAspectRatio(this.newInsertedImage.isFixAspectRatio());
        this.insertedImage.setAlpha(this.newInsertedImage.getAlpha());

        this.insertedImage.setDirty();
    }

    @Override
    protected void doUndo() {
        this.insertedImage.setHue(this.oldInsertedImage.getHue());
        this.insertedImage.setSaturation(this.oldInsertedImage.getSaturation());
        this.insertedImage.setBrightness(this.oldInsertedImage.getBrightness());
        this.insertedImage.setFixAspectRatio(this.oldInsertedImage.isFixAspectRatio());
        this.insertedImage.setAlpha(this.oldInsertedImage.getAlpha());

        this.insertedImage.setDirty();
    }
}
