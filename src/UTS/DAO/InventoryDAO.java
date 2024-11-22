/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTS.DAO;

import UTS.InventoryTableModel;
import UTS.ServisInventory;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author User
 */
public class InventoryDAO implements ServisInventory{
    private Connection conn;

    @Override
    public void getData(InventoryTableModel model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void exportToExcel(JTable table, String filePath) {
        try{
            TableModel model table.getModel();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet  = workbook.createSheet("DATA");
            
            //MEMBUAT HEADER KOLOM
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(model.getColumnName(i));
            }
        }
    }
    
}
