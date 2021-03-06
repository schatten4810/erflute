package org.dbflute.erflute.editor.view.figure.table.column;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;

public class GroupColumnFigure extends Figure {

    public GroupColumnFigure() {
        final FlowLayout layout = new FlowLayout();
        layout.setStretchMinorAxis(true);
        layout.setMajorSpacing(0);
        layout.setMinorSpacing(0);
        this.setLayoutManager(layout);
    }

    public void clearLabel() {
        this.removeAll();
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        if (graphics.getBackgroundColor().equals(this.getParent().getBackgroundColor())) {
            graphics.setAlpha(0);
        }

        super.paintFigure(graphics);
    }
}
