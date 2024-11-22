/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package UTS;

import javax.swing.JTable;

/**
 *
 * @author User
 */
public interface ServisInventory {
    public void  getData(InventoryTableModel model);
    void exportToExcel(JTable table, String filePath); 
}
