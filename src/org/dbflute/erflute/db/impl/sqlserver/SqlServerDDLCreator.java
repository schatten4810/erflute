package org.dbflute.erflute.db.impl.sqlserver;

import org.dbflute.erflute.core.util.Check;
import org.dbflute.erflute.db.impl.db2.tablespace.DB2TablespaceProperties;
import org.dbflute.erflute.editor.model.ERDiagram;
import org.dbflute.erflute.editor.model.dbexport.ddl.DDLCreator;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.table.column.NormalColumn;
import org.dbflute.erflute.editor.model.diagram_contents.not_element.sequence.Sequence;
import org.dbflute.erflute.editor.model.diagram_contents.not_element.tablespace.Tablespace;

public class SqlServerDDLCreator extends DDLCreator {

    public SqlServerDDLCreator(ERDiagram diagram, boolean semicolon) {
        super(diagram, semicolon);
    }

    @Override
    protected String buildColumnPart(NormalColumn normalColumn) {
        final StringBuilder ddl = new StringBuilder();

        ddl.append(super.buildColumnPart(normalColumn));

        if (normalColumn.isAutoIncrement()) {
            ddl.append(" IDENTITY ");

            final Sequence sequence = normalColumn.getAutoIncrementSetting();

            if (sequence.getIncrement() != null || sequence.getStart() != null) {
                ddl.append("(");
                if (sequence.getStart() != null) {
                    ddl.append(sequence.getStart());

                } else {
                    ddl.append("1");
                }

                if (sequence.getIncrement() != null) {
                    ddl.append(", ");
                    ddl.append(sequence.getIncrement());
                }

                ddl.append(")");
            }
        }

        return ddl.toString();
    }

    @Override
    protected String doBuildCreateTablespace(Tablespace tablespace) {
        final DB2TablespaceProperties tablespaceProperties =
                (DB2TablespaceProperties) tablespace.getProperties(this.environment, this.getDiagram());

        final StringBuilder ddl = new StringBuilder();

        ddl.append("CREATE ");
        if (!Check.isEmpty(tablespaceProperties.getType())) {
            ddl.append(tablespaceProperties.getType());
            ddl.append(" ");
        }

        ddl.append("TABLESPACE ");
        ddl.append(filter(tablespace.getName()));
        ddl.append("\r\n");

        if (!Check.isEmpty(tablespaceProperties.getPageSize())) {
            ddl.append(" PAGESIZE ");
            ddl.append(tablespaceProperties.getPageSize());
            ddl.append("\r\n");
        }

        ddl.append(" MANAGED BY ");
        ddl.append(tablespaceProperties.getManagedBy());
        ddl.append(" USING(");
        ddl.append(tablespaceProperties.getContainer());
        ddl.append(")\r\n");

        if (!Check.isEmpty(tablespaceProperties.getExtentSize())) {
            ddl.append(" EXTENTSIZE ");
            ddl.append(tablespaceProperties.getExtentSize());
            ddl.append("\r\n");
        }

        if (!Check.isEmpty(tablespaceProperties.getPrefetchSize())) {
            ddl.append(" PREFETCHSIZE ");
            ddl.append(tablespaceProperties.getPrefetchSize());
            ddl.append("\r\n");
        }

        if (!Check.isEmpty(tablespaceProperties.getBufferPoolName())) {
            ddl.append(" BUFFERPOOL ");
            ddl.append(tablespaceProperties.getBufferPoolName());
            ddl.append("\r\n");
        }

        if (this.semicolon) {
            ddl.append(";");
        }

        return ddl.toString();
    }
}
