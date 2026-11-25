package com.mycompany.lab_7;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class lab7frame extends javax.swing.JFrame {
    private int selectedId =-1;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(lab7frame.class.getName());

    private EmployeeManager manager = new EmployeeManager();
    private DepartmentManager departmentManager = new DepartmentManager();

    public lab7frame() {
        initComponents();
        jComboBox2ActionPerformed(null);

        try {
            manager.loadData();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Could not load saved data: " + e.getMessage());
        }

        refreshTable();
    }

    private void refreshTable() {
        showEmployees(manager.getAll());
    }
    private void loadSelectedEmployee() {
    int row = table_of_employees.getSelectedRow();
    if (row == -1) {
        return;
    }

    int id = (Integer) table_of_employees.getValueAt(row, 0);
    Employee emp = manager.findById(id);
    if (emp == null) {
        return;
    }

    selectedId = id;

    jTextField1.setText(String.valueOf(emp.getId()));
    jTextField2.setText(emp.getFullName());
    jTextField3.setText(String.valueOf(emp.getAge()));
    jTextField4.setText(emp.getPosition());
    jComboBox1.setSelectedItem(emp.getDepartment().getDepartmentName());
    jComboBox2.setSelectedItem(emp.getType());

    if (emp instanceof FullTimeEmployee) {
        FullTimeEmployee ft = (FullTimeEmployee) emp;
        jTextField5.setText(String.valueOf(ft.getBasicSalary()));
        jTextField6.setText(String.valueOf(ft.getBonus()));
    } else if (emp instanceof PartTimeEmployee) {
        PartTimeEmployee pt = (PartTimeEmployee) emp;
        jTextField7.setText(String.valueOf(pt.getHourlyRate()));
        jTextField8.setText(String.valueOf(pt.getHoursWorked()));
    }
}

    private void showEmployees(ArrayList<Employee> list) {
        DefaultTableModel model = (DefaultTableModel) table_of_employees.getModel();
        model.setRowCount(0);

        for (Employee e : list) {
            model.addRow(new Object[] {
                e.getId(),
                e.getFullName(),
                e.getAge(),
                e.getDepartment().getDepartmentName(),
                e.getPosition(),
                e.getType(),
                e.calculateSalary()
            });
        }
    }

    private Department getSelectedDepartment() {
        String deptName = (String) jComboBox1.getSelectedItem();
        for (Department d : departmentManager.getDepartments()) {   
            if (d.getDepartmentName().equalsIgnoreCase(deptName)) {
                return d;
            }
        }
        return null;
    }

    
        private Employee createEmployeeFromForm() {
        int id = Integer.parseInt(jTextField1.getText().trim());
        String name = jTextField2.getText().trim();
        int age = Integer.parseInt(jTextField3.getText().trim());
        String position = jTextField4.getText().trim();

        Department dept = getSelectedDepartment();
        if (dept == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid department.");
            return null;
        }

        if (jComboBox2.getSelectedItem().equals("Full-time")) {
            double salary = Double.parseDouble(jTextField5.getText().trim());
            double bonus = Double.parseDouble(jTextField6.getText().trim());
            return new FullTimeEmployee(id, name, age, dept, position, salary, bonus);
        } else {
            double rate = Double.parseDouble(jTextField7.getText().trim());
            double hours = Double.parseDouble(jTextField8.getText().trim());
            return new PartTimeEmployee(id, name, age, dept, position, rate, hours);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_of_employees = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTextField9 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setText(" EMPLOYEES :");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel2.setText("Employee id :");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel3.setText("Full name:");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel4.setText("Age:");

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel5.setText("Department:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IT", "Human Resources", "Finance", "Marketing", "Sales" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel6.setText("position :");

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel7.setText("Employee type :");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "part-time", "Full-time" }));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

        jTextField2.addActionListener(this::jTextField2ActionPerformed);

        jTextField3.addActionListener(this::jTextField3ActionPerformed);

        jTextField4.addActionListener(this::jTextField4ActionPerformed);

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel8.setText("Salary:");

        jTextField5.addActionListener(this::jTextField5ActionPerformed);

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel9.setText("Bonus:");

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel10.setText("Hourly-rate:");

        jTextField7.addActionListener(this::jTextField7ActionPerformed);

        jLabel11.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel11.setText("Hours-worked :");

        table_of_employees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NAME", "AGE", "DEPARTMENT", "POSITION", "TYPE", "SALARY"
            }
        ));
        table_of_employees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_of_employeesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_of_employees);

        jButton1.setText("Add");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Clear");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Update");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Delete");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Department" }));
        jComboBox3.addActionListener(this::jComboBox3ActionPerformed);

        jButton5.setText("Search");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jButton6.setText("show all");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(324, 324, 324))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 5, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(40, 40, 40))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField3)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, 261, Short.MAX_VALUE)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField9))))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField8)
                    .addComponent(jTextField7))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton6)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
      boolean fullTime = jComboBox2.getSelectedItem().equals("Full-time");

    jLabel8.setVisible(fullTime);
    jTextField5.setVisible(fullTime);
    jLabel9.setVisible(fullTime);
    jTextField6.setVisible(fullTime);

    jLabel10.setVisible(!fullTime);
    jTextField7.setVisible(!fullTime);
    jLabel11.setVisible(!fullTime);
    jTextField8.setVisible(!fullTime);
    }//GEN-LAST:event_jComboBox2ActionPerformed
    private boolean validateForm() {
    String idText = jTextField1.getText().trim();
    String name = jTextField2.getText().trim();
    String ageText = jTextField3.getText().trim();
    String position = jTextField4.getText().trim();

   
    if (idText.isEmpty() || name.isEmpty() || ageText.isEmpty() || position.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        return false;
    }

    
    try {
        int id = Integer.parseInt(idText);
        if (id <= 0) {
            JOptionPane.showMessageDialog(this, "Employee ID must be a positive number.");
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Employee ID must be a whole number.");
        return false;
    }

   
    try {
        int age = Integer.parseInt(ageText);
        if (age <= 0) {
            JOptionPane.showMessageDialog(this, "Age must be a positive number.");
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Age must be a whole number.");
        return false;
    }

    boolean fullTime = jComboBox2.getSelectedItem().equals("Full-time");
    try {
        if (fullTime) {
            double salary = Double.parseDouble(jTextField5.getText().trim());
            double bonus = Double.parseDouble(jTextField6.getText().trim());
            if (salary < 0 || bonus < 0) {
                JOptionPane.showMessageDialog(this, "Salary and bonus cannot be negative.");
                return false;
            }
        } else {
            double rate = Double.parseDouble(jTextField7.getText().trim());
            double hours = Double.parseDouble(jTextField8.getText().trim());
            if (rate < 0 || hours < 0) {
                JOptionPane.showMessageDialog(this, "Hourly rate and hours cannot be negative.");
                return false;
            }
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Please enter valid numbers for the salary fields.");
        return false;
    }

    return true;
}
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (!validateForm()) {
            return;
        }

        Employee emp = createEmployeeFromForm();
        if (emp == null) {
            return;
        }

        try {
            if (manager.addEmployee(emp)) {
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                refreshTable();
                jButton2ActionPerformed(null);   
            } else {
                JOptionPane.showMessageDialog(this, "An employee with this ID already exists.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not save data: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
    jTextField1.setText(""); 
    jTextField2.setText("");   
    jTextField3.setText("");  
    jTextField4.setText("");  
    jTextField5.setText("");   
    jTextField6.setText("");  
    jTextField7.setText("");  
    jTextField8.setText("");   
    jComboBox1.setSelectedIndex(0);  
    jComboBox2.setSelectedIndex(0);  

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      if (selectedId == -1) {
    JOptionPane.showMessageDialog(this, "Please select an employee from the table first.");
    return;
}
if (!validateForm()) {
    return;
}
Employee updated = createEmployeeFromForm();
if (updated == null) {
    return;
}
try {
    if (manager.updateEmployee(selectedId, updated)) {
        JOptionPane.showMessageDialog(this, "Employee updated successfully!");
        refreshTable();
        jButton2ActionPerformed(null);
    } else {
        JOptionPane.showMessageDialog(this, "Another employee already has this ID.");
    }
} catch (IOException e) {
    JOptionPane.showMessageDialog(this, "Could not save data: " + e.getMessage());
}
    }//GEN-LAST:event_jButton3ActionPerformed

    private void table_of_employeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_of_employeesMouseClicked
       
    int row = table_of_employees.getSelectedRow();
    if (row == -1) {
        return;
    }

    int id = (Integer) table_of_employees.getValueAt(row, 0);
    Employee emp = manager.findById(id);
    if (emp == null) {
        return;
    }

    selectedId = id;

    jTextField1.setText(String.valueOf(emp.getId()));
    jTextField2.setText(emp.getFullName());
    jTextField3.setText(String.valueOf(emp.getAge()));
    jTextField4.setText(emp.getPosition());
    jComboBox1.setSelectedItem(emp.getDepartment().getDepartmentName());
    jComboBox2.setSelectedItem(emp.getType());

    if (emp instanceof FullTimeEmployee) {
        FullTimeEmployee ft = (FullTimeEmployee) emp;
        jTextField5.setText(String.valueOf(ft.getBasicSalary()));
        jTextField6.setText(String.valueOf(ft.getBonus()));
    } else if (emp instanceof PartTimeEmployee) {
        PartTimeEmployee pt = (PartTimeEmployee) emp;
        jTextField7.setText(String.valueOf(pt.getHourlyRate()));
        jTextField8.setText(String.valueOf(pt.getHoursWorked()));
    }

    }//GEN-LAST:event_table_of_employeesMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (selectedId == -1) {
    JOptionPane.showMessageDialog(this, "Please select an employee from the table first.");
    return;
}

int choice = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to delete this employee?",
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION);

if (choice != JOptionPane.YES_OPTION) {
    return;  
}

try {
    if (manager.deleteEmployee(selectedId)) {
        JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
        refreshTable();
        jButton2ActionPerformed(null);   
    } else {
        JOptionPane.showMessageDialog(this, "Employee not found.");
    }
} catch (IOException e) {
    JOptionPane.showMessageDialog(this, "Could not save data: " + e.getMessage());
}
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       String text = jTextField9.getText().trim();
if (text.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Please enter a value to search for.");
    return;
}

ArrayList<Employee> results = new ArrayList<>();

if (jComboBox3.getSelectedItem().equals("ID")) {
    try {
        int id = Integer.parseInt(text);
        Employee emp = manager.findById(id);
        if (emp != null) {
            results.add(emp);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "ID must be a whole number.");
        return;
    }
} else {
    results = manager.searchByDepartment(text);
}

if (results.isEmpty()) {
    JOptionPane.showMessageDialog(this, "No matching employees found.");
} else {
    showEmployees(results);
}
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      jTextField9.setText("");
refreshTable();
    }//GEN-LAST:event_jButton6ActionPerformed

   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new lab7frame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable table_of_employees;
    // End of variables declaration//GEN-END:variables

  
}
