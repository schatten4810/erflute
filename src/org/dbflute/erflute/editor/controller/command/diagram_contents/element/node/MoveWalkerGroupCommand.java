package org.dbflute.erflute.editor.controller.command.diagram_contents.element.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dbflute.erflute.editor.model.ERDiagram;
import org.dbflute.erflute.editor.model.diagram_contents.element.connection.Bendpoint;
import org.dbflute.erflute.editor.model.diagram_contents.element.connection.WalkerConnection;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.DiagramWalker;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.Location;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.ermodel.WalkerGroup;
import org.eclipse.swt.graphics.Rectangle;

public class MoveWalkerGroupCommand extends MoveElementCommand {

    private final boolean move;
    private final List<DiagramWalker> walkerList;
    private Map<DiagramWalker, Rectangle> walkerOldLocationMap;
    private final WalkerGroup category;
    private int diffX;
    private int diffY;
    private Map<WalkerConnection, List<Bendpoint>> bendpointListMap;

    public MoveWalkerGroupCommand(ERDiagram diagram, int x, int y, int width, int height, WalkerGroup category,
            List<WalkerGroup> otherCategories, boolean move) {
        super(diagram, null, x, y, width, height, category);

        this.walkerList = new ArrayList<DiagramWalker>(category.getDiagramWalkerList());
        this.category = category;
        this.move = move;

        if (!this.move) {
            for (final DiagramWalker nodeElement : this.walkerList) {
                final int nodeElementX = nodeElement.getX();
                final int nodeElementY = nodeElement.getY();
                int nodeElementWidth = nodeElement.getWidth();
                int nodeElementHeight = nodeElement.getHeight();

                if (x > nodeElementX) {
                    nodeElementWidth += x - nodeElementX;
                    x = nodeElementX;
                }
                if (y > nodeElementY) {
                    nodeElementHeight += y - nodeElementY;
                    y = nodeElementY;
                }

                if (nodeElementX - x + nodeElementWidth > width) {
                    width = nodeElementX - x + nodeElementWidth;
                }

                if (nodeElementY - y + nodeElementHeight > height) {
                    height = nodeElementY - y + nodeElementHeight;
                }

            }

            this.setNewRectangle(x, y, width, height);

        } else {
            this.walkerOldLocationMap = new HashMap<DiagramWalker, Rectangle>();
            this.diffX = x - category.getX();
            this.diffY = y - category.getY();

            for (final Iterator<DiagramWalker> iter = this.walkerList.iterator(); iter.hasNext();) {
                final DiagramWalker nodeElement = iter.next();
                for (final WalkerGroup otherCategory : otherCategories) {
                    if (otherCategory.contains(nodeElement)) {
                        iter.remove();
                        break;
                    }
                }
            }

            for (final DiagramWalker nodeElement : this.walkerList) {
                this.walkerOldLocationMap.put(nodeElement, new Rectangle(nodeElement.getX(), nodeElement.getY(), nodeElement.getWidth(),
                        nodeElement.getHeight()));
            }
        }
    }

    @Override
    protected void doExecute() {
        if (this.move) {
            this.bendpointListMap = new HashMap<WalkerConnection, List<Bendpoint>>();

            for (final DiagramWalker nodeElement : this.walkerList) {
                nodeElement.setLocation(new Location(nodeElement.getX() + diffX, nodeElement.getY() + diffY, nodeElement.getWidth(),
                        nodeElement.getHeight()));
                this.moveBendpoints(nodeElement);

            }
        }
        super.doExecute();
    }

    @Override
    protected void doUndo() {
        if (this.move) {
            for (final DiagramWalker nodeElement : this.walkerList) {
                final Rectangle rectangle = this.walkerOldLocationMap.get(nodeElement);
                nodeElement.setLocation(new Location(rectangle.x, rectangle.y, rectangle.width, rectangle.height));
            }

            this.restoreBendpoints();
        }

        super.doUndo();
    }

    private void moveBendpoints(DiagramWalker source) {
        for (final WalkerConnection connectionElement : source.getOutgoings()) {
            final DiagramWalker target = connectionElement.getWalkerTarget();

            if (this.category.contains(target)) {
                final List<Bendpoint> bendpointList = connectionElement.getBendpoints();

                final List<Bendpoint> oldBendpointList = new ArrayList<Bendpoint>();

                for (int index = 0; index < bendpointList.size(); index++) {
                    final Bendpoint oldBendPoint = bendpointList.get(index);

                    if (oldBendPoint.isRelative()) {
                        break;
                    }

                    final Bendpoint newBendpoint = new Bendpoint(oldBendPoint.getX() + this.diffX, oldBendPoint.getY() + this.diffY);
                    connectionElement.replaceBendpoint(index, newBendpoint);

                    oldBendpointList.add(oldBendPoint);
                }

                this.bendpointListMap.put(connectionElement, oldBendpointList);
            }
        }
    }

    private void restoreBendpoints() {
        for (final WalkerConnection connectionElement : this.bendpointListMap.keySet()) {
            final List<Bendpoint> oldBendpointList = this.bendpointListMap.get(connectionElement);

            for (int index = 0; index < oldBendpointList.size(); index++) {
                connectionElement.replaceBendpoint(index, oldBendpointList.get(index));
            }
        }
    }

}