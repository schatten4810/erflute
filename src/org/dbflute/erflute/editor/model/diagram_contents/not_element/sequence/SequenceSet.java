package org.dbflute.erflute.editor.model.diagram_contents.not_element.sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dbflute.erflute.core.DisplayMessages;
import org.dbflute.erflute.editor.model.AbstractModel;
import org.dbflute.erflute.editor.model.ObjectListModel;

public class SequenceSet extends AbstractModel implements ObjectListModel, Iterable<Sequence> {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_CHANGE_SEQUENCE_SET = "SequenceSet";

    private List<Sequence> sequenceList;

    public SequenceSet() {
        this.sequenceList = new ArrayList<Sequence>();
    }

    public void addSequence(Sequence sequence) {
        this.sequenceList.add(sequence);
        Collections.sort(this.sequenceList);

        this.firePropertyChange(PROPERTY_CHANGE_SEQUENCE_SET, null, null);
    }

    public int remove(Sequence sequence) {
        int index = this.sequenceList.indexOf(sequence);
        this.sequenceList.remove(index);
        this.firePropertyChange(PROPERTY_CHANGE_SEQUENCE_SET, null, null);

        return index;
    }

    public boolean contains(String name) {
        for (Sequence sequence : sequenceList) {
            if (name.equalsIgnoreCase(sequence.getName())) {
                return true;
            }
        }

        return false;
    }

    public Sequence get(String name) {
        for (Sequence sequence : sequenceList) {
            if (name.equalsIgnoreCase(sequence.getName())) {
                return sequence;
            }
        }

        return null;
    }

    public List<Sequence> getSequenceList() {
        return this.sequenceList;
    }

    public Iterator<Sequence> iterator() {
        return this.sequenceList.iterator();
    }

    @Override
    public SequenceSet clone() {
        SequenceSet sequenceSet = (SequenceSet) super.clone();
        List<Sequence> newSequenceList = new ArrayList<Sequence>();

        for (Sequence sequence : sequenceList) {
            Sequence newSequence = (Sequence) sequence.clone();
            newSequenceList.add(newSequence);
        }

        sequenceSet.sequenceList = newSequenceList;

        return sequenceSet;
    }

    public String getDescription() {
        return "";
    }

    public String getName() {
        return DisplayMessages.getMessage("label.object.type.sequence_list");
    }

    public String getObjectType() {
        return "list";
    }
}
