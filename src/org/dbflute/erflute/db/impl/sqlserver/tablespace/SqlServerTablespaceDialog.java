package org.dbflute.erflute.db.impl.sqlserver.tablespace;

import org.dbflute.erflute.core.util.Format;
import org.dbflute.erflute.core.widgets.CompositeFactory;
import org.dbflute.erflute.editor.model.diagram_contents.not_element.tablespace.TablespaceProperties;
import org.dbflute.erflute.editor.view.dialog.outline.tablespace.TablespaceDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class SqlServerTablespaceDialog extends TablespaceDialog {

    // (REGULAR/LARGI/SYSTEM TEMPORARY/USER TEMPORARY)
    private Text type;

    private Text pageSize;

    private Text managedBy;

    private Text container;

    // private Text containerDirectoryPath;
    //
    // private Text containerFilePath;
    //
    // private Text containerPageNum;
    //
    // private Text containerDevicePath;

    private Text extentSize;

    private Text prefetchSize;

    private Text bufferPoolName;

    @Override
    protected void initComponent(Composite composite) {
        super.initComponent(composite);

        this.type = CompositeFactory.createText(this, composite, "label.tablespace.type", false);
        this.pageSize = CompositeFactory.createText(this, composite, "label.tablespace.page.size", false);
        this.managedBy = CompositeFactory.createText(this, composite, "label.tablespace.managed.by", false);
        this.container = CompositeFactory.createText(this, composite, "label.tablespace.container", false);
        // this.containerDirectoryPath = this.createText(composite,
        // "label.tablespace.container.directory.path");
        // this.containerFilePath = this.createText(composite,
        // "label.tablespace.container.file.path");
        // this.containerPageNum = this.createText(composite,
        // "label.tablespace.container.page.num");
        // this.containerDevicePath = this.createText(composite,
        // "label.tablespace.container.device.path");
        this.extentSize = CompositeFactory.createText(this, composite, "label.tablespace.extent.size", false);
        this.prefetchSize = CompositeFactory.createText(this, composite, "label.tablespace.prefetch.size", false);
        this.bufferPoolName = CompositeFactory.createText(this, composite, "label.tablespace.buffer.pool.name", false);
    }

    @Override
    protected TablespaceProperties setTablespaceProperties() {
        SqlServerTablespaceProperties tablespaceProperties = new SqlServerTablespaceProperties();

        tablespaceProperties.setType(this.type.getText().trim());
        tablespaceProperties.setPageSize(this.pageSize.getText().trim());
        tablespaceProperties.setManagedBy(this.managedBy.getText().trim());
        tablespaceProperties.setContainer(this.container.getText().trim());
        // tablespaceProperties.setContainerDirectoryPath(this.containerDirectoryPath.getText()
        // .trim());
        // tablespaceProperties.setContainerFilePath(this.containerFilePath.getText().trim());
        // tablespaceProperties.setContainerPageNum(this.containerPageNum.getText().trim());
        // tablespaceProperties
        // .setContainerDevicePath(this.containerDevicePath.getText()
        // .trim());
        tablespaceProperties.setExtentSize(this.extentSize.getText().trim());
        tablespaceProperties.setPrefetchSize(this.prefetchSize.getText().trim());
        tablespaceProperties.setBufferPoolName(this.bufferPoolName.getText().trim());

        return tablespaceProperties;
    }

    @Override
    protected void setData(TablespaceProperties tablespaceProperties) {
        if (tablespaceProperties instanceof SqlServerTablespaceProperties) {
            SqlServerTablespaceProperties properties = (SqlServerTablespaceProperties) tablespaceProperties;

            this.type.setText(Format.toString(properties.getType()));
            this.pageSize.setText(Format.toString(properties.getPageSize()));
            this.managedBy.setText(Format.toString(properties.getManagedBy()));
            this.container.setText(Format.toString(properties.getContainer()));
            // this.containerDirectoryPath.setText(Format.toString(properties
            // .getContainerDirectoryPath()));
            // this.containerFilePath.setText(Format.toString(properties
            // .getContainerFilePath()));
            // this.containerPageNum.setText(Format.toString(properties
            // .getContainerPageNum()));
            // this.containerDevicePath.setText(Format.toString(properties
            // .getContainerDevicePath()));
            this.extentSize.setText(Format.toString(properties.getExtentSize()));
            this.prefetchSize.setText(Format.toString(properties.getPrefetchSize()));
            this.bufferPoolName.setText(Format.toString(properties.getBufferPoolName()));
        }
    }
}
