/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTS;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author revv
 */
public class InventoryController {

    DefaultListModel<String> model = new DefaultListModel<>();

    private final InventoryTableModel itm = new InventoryTableModel();
    private static DefaultListModel<String> listModelRiwayat = new DefaultListModel<>();
    private static JList<String> listRiwayat = new JList<>(listModelRiwayat);

    public void setTableModel(InventoryApp ip) {
        try {
            InventoryApp.tabledata.setModel(itm);
        } catch (Exception error) {
            System.err.println("Error at InventoryController-setTableModel, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at InventoryController-setTableModel, details : " + error.toString());
        }
    }

    public void loadData(InventoryApp ip) {
        try {
            Statement statement;
            ResultSet rs;

            String sqlSelect = "SELECT * FROM tabel ORDER BY kode_barang ASC";

            statement = config.getInventory().createStatement();
            rs = statement.executeQuery(sqlSelect);

            List<inventory> list = new ArrayList<>();
            while (rs.next()) {
                String keadaan = "";
                if (rs.getString("keadaan").equalsIgnoreCase("baik")) {
                    keadaan = "BAIK";
                } else if (rs.getString("keadaan").equalsIgnoreCase("kurang baik")) {
                    keadaan = "KURANG BAIK";
                } else if (rs.getString("keadaan").equalsIgnoreCase("rusak")) {
                    keadaan = "RUSAK";
                }
                inventory i = new inventory();
                i.setKode_barang(rs.getString("kode_barang"));
                i.setNama_barang(rs.getString("nama_barang"));
                i.setJumlah_barang(rs.getString("jumlah_barang"));
                i.setKeadaan(keadaan);
                i.setHarga_barang(rs.getString("harga_barang"));
                i.setKeterangan(rs.getString("keterangan"));
                list.add(i);
            }
            itm.setList(list);
        } catch (Exception error) {
            System.err.println("Error at InventoryController-loadData, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at InventoryController-loadData, details : " + error.toString());
        }
    }

    public void searchData(InventoryApp ip) {
        try {
            String parameter = "";
            if (InventoryApp.cbCari.getSelectedIndex() == 0) {
                parameter = "kode_barang";
            } else if (InventoryApp.cbCari.getSelectedIndex() == 1) {
                parameter = "nama_barang";
            } else if (InventoryApp.cbCari.getSelectedIndex() == 2) {
                parameter = "jumlah_barang";
            } else if (InventoryApp.cbCari.getSelectedIndex() == 3) {
                parameter = "keadaan";
            } else if (InventoryApp.cbCari.getSelectedIndex() == 4) {
                parameter = "harga_barang";
            } else if (InventoryApp.cbCari.getSelectedIndex() == 5) {
                parameter = "keterangan";
            }

            String keyword = InventoryApp.txtCari.getText();

            Statement statement;
            ResultSet rs;

            String sqlSelect = "SELECT * FROM tabel WHERE " + parameter + " LIKE '%" + keyword + "%' ORDER BY kode_barang ASC";

            statement = config.getInventory().createStatement();
            rs = statement.executeQuery(sqlSelect);

            List<inventory> list = new ArrayList<>();
            while (rs.next()) {
                String keadaan = "";
                if (rs.getString("keadaan").equalsIgnoreCase("baik")) {
                    keadaan = "BAIK";
                } else if (rs.getString("keadaan").equalsIgnoreCase("kurang baik")) {
                    keadaan = "KURANG BAIK";
                } else if (rs.getString("keadaan").equalsIgnoreCase("rusak")) {
                    keadaan = "RUSAK";
                }
                inventory i = new inventory();
                i.setKode_barang(rs.getString("kode_barang"));
                i.setNama_barang(rs.getString("nama_barang"));
                i.setJumlah_barang(rs.getString("jumlah_barang"));
                i.setKeadaan(keadaan);
                i.setHarga_barang(rs.getString("harga_barang"));
                i.setKeterangan(rs.getString("keterangan"));
                list.add(i);
            }
            itm.setList(list);
        } catch (Exception error) {
            System.err.println("Error at InventoryController-searchData, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at InventoryController-searchData, details : " + error.toString());
        }
    }

    public void tableInventoryAction(final InventoryApp ip) {
        InventoryApp.tabledata.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = InventoryApp.tabledata.getSelectedRow();
                if (row != -1) {
                    inventory i = itm.get(row);

                    InventoryApp.txtKodeBarang.setText(i.getKode_barang());
                    InventoryApp.txtNamaBarang.setText(i.getNama_barang());
                    InventoryApp.txtJumlahBarang.setText(i.getJumlah_barang());
                    if (i.getKeadaan().equals("Baik")) {
                        InventoryApp.baik.setSelected(true);
                        InventoryApp.kurangbaik.setSelected(false);
                        InventoryApp.rusak.setSelected(false);
                    } else if (i.getKeadaan().equals("Kurang Baik")) {
                        InventoryApp.baik.setSelected(false);
                        InventoryApp.kurangbaik.setSelected(true);
                        InventoryApp.rusak.setSelected(false);
                    } else if (i.getKeadaan().equals("Rusak")) {
                        InventoryApp.baik.setSelected(false);
                        InventoryApp.kurangbaik.setSelected(false);
                        InventoryApp.rusak.setSelected(true);
                    }
                    InventoryApp.txtHarga.setText(i.getHarga_barang());
                    InventoryApp.txtKeterangan.setText(i.getKeterangan());
                }
            }
        });
    }

    public void refresh(InventoryApp ip) {
        try {
            InventoryApp.labelStatus.setText("");
            InventoryApp.txtNamaBarang.setText("");
            InventoryApp.txtCari.setText("");
            InventoryApp.baik.setSelected(true);
            InventoryApp.txtHarga.setText("");
            InventoryApp.txtJumlahBarang.setText("");
            InventoryApp.txtKodeBarang.setText("");
            InventoryApp.txtKeterangan.setText("");
            InventoryApp.tabledata.clearSelection();
            InventoryApp.buttonSimpan.setEnabled(false);
            InventoryApp.btnBatal.setEnabled(false);

            InventoryApp.cbCari.setEnabled(true);
            InventoryApp.txtCari.setEnabled(true);
            InventoryApp.tabledata.setEnabled(true);
            InventoryApp.buttonTambah.setEnabled(true);
            InventoryApp.buttonUpdate.setEnabled(true);
            InventoryApp.btnHapus.setEnabled(true);
            InventoryApp.buttonRefresh.setEnabled(true);
            InventoryApp.btnKeluar.setEnabled(true);

            loadData(ip);
        } catch (Exception error) {
            System.err.println("Error at InventoryController-refresh, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at InventoryApp-refresh, details : " + error.toString());
        }
    }

    public void buttonTambahAction(InventoryApp ip) {
        try {
            InventoryApp.labelStatus.setText("INSERT");
            InventoryApp.txtKodeBarang.setText("");
            InventoryApp.txtNamaBarang.setText("");
            InventoryApp.baik.setSelected(true);
            InventoryApp.txtKeterangan.setText("");
            InventoryApp.cbCari.setSelectedIndex(0);
            InventoryApp.txtCari.setText("");
            InventoryApp.tabledata.clearSelection();
            InventoryApp.txtJumlahBarang.setText("");
            InventoryApp.txtHarga.setText("");
            InventoryApp.txtCari.setText("");

            InventoryApp.txtKodeBarang.setEnabled(true);
            InventoryApp.txtNamaBarang.setEnabled(true);
            InventoryApp.baik.setEnabled(true);
            InventoryApp.rusak.setEnabled(true);
            InventoryApp.kurangbaik.setEnabled(true);
            InventoryApp.txtJumlahBarang.setEnabled(true);
            InventoryApp.txtHarga.setEnabled(true);
            InventoryApp.txtKeterangan.setEnabled(true);
            InventoryApp.buttonSimpan.setEnabled(true);
            InventoryApp.btnBatal.setEnabled(true);

            InventoryApp.btnHapus.setEnabled(false);
            InventoryApp.buttonRefresh.setEnabled(false);
            InventoryApp.buttonUpdate.setEnabled(false);

            InventoryApp.txtKodeBarang.requestFocus();
        } catch (Exception error) {
            System.err.println("Error at InventoryApp-buttonTambahAction, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at InventoryApp-buttonTambahAction, details : " + error.toString());
        }
    }

    public void buttonUbahAction(InventoryApp ip) {
        try {
            int row = InventoryApp.tabledata.getSelectedRow();
            //Nilai -1 menandakan bahwa tidak ada data sama sekali yang dikllik pada table
            if (row == -1) {
                JOptionPane.showMessageDialog(ip, "Silahkan klik data yang ingin diubah");
            } else {
                InventoryApp.labelStatus.setText("UPDATE");

                InventoryApp.txtKodeBarang.setEnabled(true);
                InventoryApp.txtNamaBarang.setEnabled(true);
                InventoryApp.baik.setEnabled(true);
                InventoryApp.rusak.setEnabled(true);
                InventoryApp.kurangbaik.setEnabled(true);
                InventoryApp.txtJumlahBarang.setEnabled(true);
                InventoryApp.txtHarga.setEnabled(true);
                InventoryApp.txtKeterangan.setEnabled(true);
                InventoryApp.buttonSimpan.setEnabled(true);
                InventoryApp.btnBatal.setEnabled(true);

                InventoryApp.cbCari.setEnabled(false);
                InventoryApp.txtCari.setEnabled(false);
                InventoryApp.tabledata.setEnabled(false);
                InventoryApp.buttonTambah.setEnabled(false);
                InventoryApp.buttonUpdate.setEnabled(false);
                InventoryApp.btnHapus.setEnabled(false);
                InventoryApp.buttonRefresh.setEnabled(false);
                InventoryApp.btnKeluar.setEnabled(false);

                InventoryApp.txtKodeBarang.requestFocus();

            }
        } catch (Exception error) {
            System.err.println("Error at InventoryApp-buttonUbahAction, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at buttonUbahAction-setMaximunFrame, details : " + error.toString());
        }
    }

    public void buttonHapusAction(InventoryApp ip) {
        try {
            int row = InventoryApp.tabledata.getSelectedRow();
            // nilai -1 menandakan bahwa tidak ada data sama sekali yg diklik pada table
            if (row == -1) {
                JOptionPane.showMessageDialog(ip, "Silahkan klik data yang ingin dihapus");
            } else {
                String kode_barang = itm.get(row).getKode_barang();

                int konfirmasi = JOptionPane.showConfirmDialog(ip, "Apakah anda yakin ingin menghapus data Kode Barang " + kode_barang + "?",
                        "Konfirmasi", JOptionPane.YES_NO_OPTION);

                if (konfirmasi == JOptionPane.YES_OPTION) {
                    PreparedStatement ps;

                    String sqlDelete = "DELETE FROM tabel WHERE kode_barang = ?";

                    ps = config.getInventory().prepareStatement(sqlDelete);
                    ps.setString(1, kode_barang);
                    int isSuccess = ps.executeUpdate();
                    if (isSuccess == 0) {
                        JOptionPane.showMessageDialog(ip, "Data gagal Dihapus", "Pesan", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ip, "Data berhasil Dihapus", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                refresh(ip);
            }
        } catch (HeadlessException | SQLException error) {
            System.err.println("Error at InventoryApp-buttonHapusAction, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at InventoryApp-buttonHapusAction, details : " + error.toString());
        }
    }

    public void buttonSegarkanAction(InventoryApp ip) {
        try {
            refresh(ip);
        } catch (Exception error) {
            System.err.println("Error at InventoryApp-buttonSegarkanAction, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at InventoryApp-buttonSegarkanAction, details : " + error.toString());
        }
    }

    public void buttonTutupAction(InventoryApp ip) {
        try {
            ip.dispose();
        } catch (Exception error) {
            System.err.println("Error at InventoryApp-buttonTutupAction, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at InventoryApp-buttonTutupAction, details : " + error.toString());
        }
    }

    public void buttonSimpanAction(InventoryApp ip) {
        try {
            // Validasi data
            boolean b = validasiData(ip);
            if (!b) {
                // Ambil input dari form
                String keadaan = "";
                if (InventoryApp.baik.isSelected()) {
                    keadaan = "BAIK";
                } else if (InventoryApp.kurangbaik.isSelected()) {
                    keadaan = "KURANG BAIK";
                } else if (InventoryApp.rusak.isSelected()) {
                    keadaan = "RUSAK";
                }

                inventory i = new inventory();
                i.setKode_barang(InventoryApp.txtKodeBarang.getText());
                i.setNama_barang(InventoryApp.txtNamaBarang.getText());
                i.setJumlah_barang(InventoryApp.txtJumlahBarang.getText());
                i.setHarga_barang(InventoryApp.txtHarga.getText());
                i.setKeadaan(keadaan);
                i.setKeterangan(InventoryApp.txtKeterangan.getText());

                PreparedStatement ps;
                if (InventoryApp.labelStatus.getText().equals("INSERT")) {
                    String sqlInsert = "INSERT INTO tabel VALUES (?, ?, ?, ?, ?, ?)";
                    ps = config.getInventory().prepareStatement(sqlInsert);
                    ps.setString(1, i.getKode_barang());
                    ps.setString(2, i.getNama_barang());
                    ps.setString(3, i.getJumlah_barang());
                    ps.setString(4, i.getKeadaan());
                    ps.setString(5, i.getHarga_barang());
                    ps.setString(6, i.getKeterangan());

                    int isSuccess = ps.executeUpdate();
                    if (isSuccess == 1) {
                        JOptionPane.showMessageDialog(ip, "Data berhasil disimpan", "Pesan", JOptionPane.INFORMATION_MESSAGE);

                        // Tambahkan data ke JList
                        DefaultListModel<String> model = (DefaultListModel<String>) InventoryApp.listRiwayat.getModel();
                        model.addElement("Kode Barang: " + i.getKode_barang());
                        model.addElement("Nama Barang: " + i.getNama_barang());
                        model.addElement("Jumlah Barang: " + i.getJumlah_barang());
                        model.addElement("Harga Barang: " + i.getHarga_barang());
                        model.addElement("Keadaan: " + i.getKeadaan());
                        model.addElement("========================");
                    } else {
                        JOptionPane.showMessageDialog(ip, "Data gagal disimpan", "Pesan", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (InventoryApp.labelStatus.getText().equals("UPDATE")) {
                    String sqlUpdate = "UPDATE tabel SET nama_barang = ?, jumlah_barang = ?, keadaan = ?, harga_barang = ?, keterangan = ? WHERE kode_barang = ?";
                    ps = config.getInventory().prepareStatement(sqlUpdate);
                    ps.setString(1, i.getNama_barang());
                    ps.setString(2, i.getJumlah_barang());
                    ps.setString(3, i.getKeadaan());
                    ps.setString(4, i.getHarga_barang());
                    ps.setString(5, i.getKeterangan());
                    ps.setString(6, i.getKode_barang());

                    int isSuccess = ps.executeUpdate();
                    if (isSuccess == 1) {
                        JOptionPane.showMessageDialog(ip, "Data berhasil diperbarui", "Pesan", JOptionPane.INFORMATION_MESSAGE);

                        // Tambahkan data ke JList
                        DefaultListModel<String> model = (DefaultListModel<String>) InventoryApp.listRiwayat.getModel();
                        model.addElement("Kode Barang: " + i.getKode_barang());
                        model.addElement("Nama Barang: " + i.getNama_barang());
                        model.addElement("Jumlah Barang: " + i.getJumlah_barang());
                        model.addElement("Harga Barang: " + i.getHarga_barang());
                        model.addElement("Keadaan: " + i.getKeadaan());
                        model.addElement("========================");
                    } else {
                        JOptionPane.showMessageDialog(ip, "Data gagal diperbarui", "Pesan", JOptionPane.ERROR_MESSAGE);
                    }
                }
                refresh(ip);
            }
        } catch (SQLException | HeadlessException error) {
            System.err.println("Error at buttonSimpanAction: " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at buttonSimpanAction: " + error.toString());
        }
    }

    public void buttonBatalAction(InventoryApp ip) {
        try {
            refresh(ip);
        } catch (Exception error) {
            System.err.println("Error at InventoryApp-buttonBatalAction, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at InventoryApp-buttonBatalAction, details : " + error.toString());
        }
    }

    private boolean validasiData(InventoryApp ip) {
        boolean b = true;
        try {
            if (InventoryApp.txtKodeBarang.getText().equals("")) {
                JOptionPane.showMessageDialog(ip, "Kode Barang tidak boleh kosong!");
            } else if (InventoryApp.txtNamaBarang.getText().equals("")) {
                JOptionPane.showMessageDialog(ip, "Nama Barang tidak boleh kosong!");
            } else if (InventoryApp.txtJumlahBarang.getText().equals("")) {
                JOptionPane.showMessageDialog(ip, "Jumlah Barang tidak boleh kosong!");
            } else if (InventoryApp.txtHarga.getText().equals("")) {
                JOptionPane.showMessageDialog(ip, "Harga Barang tidak boleh kosong!");
            } else if (InventoryApp.txtKeterangan.getText().equals("")) {
                JOptionPane.showMessageDialog(ip, "Keterangan tidak boleh kosong!");
            } else {
                b = false;
            }
        } catch (Exception error) {
            System.err.println("Error at InventoryApp-validasiData, details : " + error.toString());
            JOptionPane.showMessageDialog(ip, "Error at InventoryApp-validasiData, details : " + error.toString());
        }
        return b;
    }

    public static void tambahRiwayat(String kodeBarang, String namaBarang, String jumlahBarang, String harga, String keadaan) {
        String riwayat = String.format("Kode: %s | Nama: %s | Jumlah: %s | Harga: %s | Keadaan: %s",
                kodeBarang, namaBarang, jumlahBarang, harga, keadaan);
        listModelRiwayat.addElement(riwayat);

        // Batasi riwayat jika diperlukan (misalnya hanya 50 data terakhir)
        if (listModelRiwayat.size() > 50) {
            listModelRiwayat.remove(0);
        }
    }
    
    

    
}
