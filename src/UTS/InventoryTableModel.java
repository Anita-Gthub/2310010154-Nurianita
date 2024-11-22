/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTS;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author revv
 */
public class InventoryTableModel extends AbstractTableModel{
     private List<inventory> list = new ArrayList<>();
    
    public void insert(inventory i){
        list.add(i);
        fireTableDataChanged();
    }
    public void update(int row, inventory i){
        list.set(row, i);
        fireTableDataChanged();
    }
    public void delete(int row){
        list.remove(row);
        fireTableDataChanged();
    }
    public inventory get(int row){
        return list.get(row);
    }
    public void setList(List<inventory> list){
        this.list = list;
        fireTableDataChanged();
    }
    @Override
    public int getRowCount(){
        return list.size();
    }
    @Override
    public int getColumnCount(){
        return 7;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        switch(columnIndex){
            case 0:
                return rowIndex + 1;
            case 1:
                return list.get(rowIndex).getKode_barang();
            case 2:
                return list.get(rowIndex).getNama_barang();
            case 3:
                return list.get(rowIndex).getJumlah_barang();
            case 4:
                return list.get(rowIndex).getKeadaan();
            case 5:
                return list.get(rowIndex).getHarga_barang();
            case 6:
                return list.get(rowIndex).getKeterangan();
            default:
                return null;   
        }
    }
    
    public String getColumnName(int column){
        switch(column){
            case 0:
                return "No";
            case 1:
                return "Kode Barang";
            case 2:
                return "Nama Barang";
            case 3:
                return "Jumlah Barang";
            case 4:
                return "Keadaan";
            case 5:
                return "Harga Barang";
            case 6:
                return "Keterangan";
            default:
                return null;
        }
    }
    
}

    

