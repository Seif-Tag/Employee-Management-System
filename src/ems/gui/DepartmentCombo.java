package ems.gui;

import ems.model.Department;

import javax.swing.JComboBox;
import java.util.List;

/**
 * Shared refresh path for the department dropdowns used across sections, so
 * every combo stays in sync with the department list from the service.
 */
final class DepartmentCombo {

    private DepartmentCombo() {
    }

    /**
     * Rebuilds the dropdown items from {@code departments}, keeping the
     * current selection when it is still available and otherwise falling
     * back to {@code promptItem}.
     */
    static void refresh(JComboBox<String> comboBox, String promptItem, List<Department> departments) {
        String previousSelection = (String) comboBox.getSelectedItem();

        comboBox.removeAllItems();
        comboBox.addItem(promptItem);
        for (Department department : departments) {
            comboBox.addItem(department.getDepartmentName());
        }

        if (previousSelection != null && !promptItem.equals(previousSelection)) {
            comboBox.setSelectedItem(previousSelection);
        } else {
            comboBox.setSelectedItem(promptItem);
        }
    }
}
