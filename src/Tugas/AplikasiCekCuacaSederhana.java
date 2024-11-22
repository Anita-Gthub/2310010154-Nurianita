package Tugas;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;

public class AplikasiCekCuacaSederhana extends javax.swing.JFrame {
private DefaultTableModel tabelModel;

    public AplikasiCekCuacaSederhana() {
        setTitle("Aplikasi Cek Cuaca Sederhana");
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Aplikasi Cuaca");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Kolom tabel
        String[] kolom = {"Kota", "Kondisi", "Suhu", "Kelembapan", "Angin"};

        // Inisialisasi tabel model
        tabelModel = new DefaultTableModel(kolom, 0);
        tabelCuacaFavorit = new JTable(tabelModel);

        // Tambahkan komponen lainnya
        initializeGUI();
    }

    private void cekCuaca() {
        String kota = kotaField.getText().isEmpty() ? (String) favoritComboBox.getSelectedItem() : kotaField.getText();
        if (kota == null || kota.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan nama kota", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + kota + "&appid=" + API_KEY + "&units=metric";
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");
            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
            JSONObject jsonObject = new JSONObject(response.toString());

            // Parsing data cuaca
            String kondisi = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            double suhu = jsonObject.getJSONObject("main").getDouble("temp");
            int kelembapan = jsonObject.getJSONObject("main").getInt("humidity");
            double angin = jsonObject.getJSONObject("wind").getDouble("speed");

            // Tambahkan data ke dalam tabel
            tabelModel.addRow(new Object[]{
                kota,
                kondisi,
                String.format("%.2f°C", suhu),
                kelembapan + "%",
                String.format("%.2f m/s", angin)
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metode untuk menyimpan kota favorit
    private void simpanFavorit() {
        String kota = kotaField.getText();
        if (kota == null || kota.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan nama kota untuk disimpan sebagai favorit", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!kotaFavorit.contains(kota)) {
            kotaFavorit.add(kota);
            favoritComboBox.addItem(kota);
            simpanFavoritKeFile();
        }
    }

    private void simpanFavoritKeFile() {
        try ( FileWriter writer = new FileWriter("favorit_kota.txt")) {
            for (String kota : kotaFavorit) {
                writer.write(kota + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFavorit() {
        try ( Scanner scanner = new Scanner(new File("favorit_kota.txt"))) {
            while (scanner.hasNextLine()) {
                kotaFavorit.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            // File tidak ditemukan
        }
    }

    private void muatData() {
        JOptionPane.showMessageDialog(this, "Data cuaca favorit telah dimuat.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        kotaField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        favoritComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelCuacaFavorit = new javax.swing.JTable();
        simpanFavoritButton = new javax.swing.JButton();
        muatDataButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Masukkan Nama Kota");

        kotaField.setText(".");
        kotaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kotaFieldActionPerformed(evt);
            }
        });

        jButton1.setText("CEK CUACA");

        favoritComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tabelCuacaFavorit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelCuacaFavorit);

        simpanFavoritButton.setText("SIMPAN FAVORIT");

        muatDataButton.setText("MUAT DATA ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kotaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(favoritComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(simpanFavoritButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(muatDataButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(kotaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(favoritComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpanFavoritButton)
                    .addComponent(muatDataButton))
                .addContainerGap(122, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kotaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kotaFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kotaFieldActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuacaSederhana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuacaSederhana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuacaSederhana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuacaSederhana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AplikasiCekCuacaSederhana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> favoritComboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField kotaField;
    private javax.swing.JButton muatDataButton;
    private javax.swing.JButton simpanFavoritButton;
    private javax.swing.JTable tabelCuacaFavorit;
    // End of variables declaration//GEN-END:variables
}
