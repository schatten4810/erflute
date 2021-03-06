package org.dbflute.erflute.editor.persistent.xml;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dbflute.erflute.editor.model.ERDiagram;
import org.dbflute.erflute.editor.model.diagram_contents.DiagramContents;
import org.dbflute.erflute.editor.model.diagram_contents.element.connection.Relationship;
import org.dbflute.erflute.editor.model.diagram_contents.element.connection.WalkerConnection;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.DiagramWalker;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.ermodel.ERVirtualDiagram;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.table.ERTable;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.table.TableView;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.table.column.ERColumn;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.table.column.NormalColumn;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.table.unique_key.CompoundUniqueKey;
import org.dbflute.erflute.editor.model.diagram_contents.not_element.dictionary.Word;
import org.dbflute.erflute.editor.model.diagram_contents.not_element.group.ColumnGroup;
import org.dbflute.erflute.editor.model.diagram_contents.not_element.tablespace.Tablespace;
import org.dbflute.erflute.editor.model.settings.Environment;
import org.dbflute.erflute.editor.persistent.Persistent;
import org.dbflute.erflute.editor.persistent.xml.reader.ErmXmlReader;
import org.dbflute.erflute.editor.persistent.xml.writer.ErmXmlWriter;

/**
 * @author modified by jflute (originated in ermaster)
 */
public class PersistentXml extends Persistent {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public class PersistentContext {
        public final Map<DiagramWalker, String> walkerMap = new LinkedHashMap<>();
        public final Map<WalkerConnection, String> connectionMap = new LinkedHashMap<>();
        public final Map<ColumnGroup, String> columnGroupMap = new LinkedHashMap<>(); // group = groupName
        public final Map<ERColumn, String> columnMap = new LinkedHashMap<>(); // column = ID
        public final Map<CompoundUniqueKey, Integer> complexUniqueKeyMap = new LinkedHashMap<>();
        public final Map<Word, Integer> wordMap = new LinkedHashMap<>();
        public final Map<Tablespace, Integer> tablespaceMap = new LinkedHashMap<>();
        public final Map<Environment, Integer> environmentMap = new LinkedHashMap<>();
        public final Map<ERVirtualDiagram, Integer> virtualDiagramMap = new LinkedHashMap<>();
    }

    public PersistentContext getCurrentContext(ERDiagram diagram) { // called by writer
        return createContext(diagram.getDiagramContents());
    }

    private PersistentContext createContext(DiagramContents diagramContents) {
        final PersistentContext context = new PersistentContext();
        setupNodeElement(diagramContents, context); // contains table, column
        setupColumnGroup(diagramContents, context);
        setupWord(diagramContents, context);
        setupTablespace(diagramContents, context);
        setupEnvironment(diagramContents, context);
        setupVirtualModel(diagramContents, context);
        return context;
    }

    private void setupNodeElement(DiagramContents diagramContents, final PersistentContext context) {
        int nodeElementNo = 1;
        int connectionNo = 1;
        int complexUniqueKeyNo = 1;
        for (final DiagramWalker walker : diagramContents.getDiagramWalkers()) {
            final String nodeElementId;
            if (walker instanceof TableView) {
                nodeElementId = ((TableView) walker).buildTableViewId();
            } else {
                nodeElementId = String.valueOf(nodeElementNo);
            }
            context.walkerMap.put(walker, nodeElementId);
            nodeElementNo++;
            final List<WalkerConnection> connections = walker.getIncomings();
            for (final WalkerConnection connection : connections) {
                final String connectionId;
                if (walker instanceof TableView && connection instanceof Relationship) {
                    // basically relationship's parent is table but just in case
                    connectionId = ((Relationship) connection).buildRelationshipId();
                } else {
                    connectionId = String.valueOf(connectionNo);
                }
                context.connectionMap.put(connection, connectionId);
                connectionNo++;
            }
            if (walker instanceof ERTable) {
                final ERTable table = (ERTable) walker;
                final List<ERColumn> columns = table.getColumns();
                for (final ERColumn column : columns) {
                    if (column instanceof NormalColumn) {
                        context.columnMap.put(column, ((NormalColumn) column).buildColumnId(table));
                    }
                }
                for (final CompoundUniqueKey complexUniqueKey : table.getCompoundUniqueKeyList()) {
                    context.complexUniqueKeyMap.put(complexUniqueKey, complexUniqueKeyNo);
                    complexUniqueKeyNo++;
                }
            }
        }
    }

    private void setupColumnGroup(DiagramContents diagramContents, final PersistentContext context) {
        for (final ColumnGroup columnGroup : diagramContents.getColumnGroupSet()) {
            context.columnGroupMap.put(columnGroup, columnGroup.getGroupName()); // #for_erflute
            for (final NormalColumn normalColumn : columnGroup.getColumns()) {
                context.columnMap.put(normalColumn, normalColumn.buildColumnIdAsGroup(columnGroup));
            }
        }
    }

    private void setupWord(DiagramContents diagramContents, final PersistentContext context) {
        int wordNo = 1;
        for (final Word word : diagramContents.getDictionary().getWordList()) {
            context.wordMap.put(word, wordNo);
            wordNo++;
        }
    }

    private void setupTablespace(DiagramContents diagramContents, final PersistentContext context) {
        int tablespaceNo = 1;
        for (final Tablespace tablespace : diagramContents.getTablespaceSet()) {
            context.tablespaceMap.put(tablespace, tablespaceNo);
            tablespaceNo++;
        }
    }

    private void setupEnvironment(DiagramContents diagramContents, final PersistentContext context) {
        int environmentNo = 1;
        for (final Environment environment : diagramContents.getSettings().getEnvironmentSettings().getEnvironments()) {
            context.environmentMap.put(environment, environmentNo);
            environmentNo++;
        }
    }

    private void setupVirtualModel(DiagramContents diagramContents, final PersistentContext context) {
        int virtualModelNo = 1;
        for (final ERVirtualDiagram model : diagramContents.getVirtualDiagramSet()) {
            context.virtualDiagramMap.put(model, virtualModelNo);
            virtualModelNo++;
        }
    }

    // ===================================================================================
    //                                                                               Read
    //                                                                              ======
    @Override
    public ERDiagram read(InputStream ins) {
        return new ErmXmlReader(this).read(ins);
    }

    // ===================================================================================
    //                                                                               Write
    //                                                                               =====
    @Override
    public InputStream write(ERDiagram diagram) {
        return new ErmXmlWriter(this).write(diagram);
    }
}