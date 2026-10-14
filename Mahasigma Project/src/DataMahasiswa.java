import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DataMahasiswa extends JFrame {

    private MahasiswaForm form;
    private Database database;
    private int selectedIndex = -1;

    public DataMahasiswa() {
        form = new MahasiswaForm();
        database = new Database();

        // Pasang model tabel ke JTable
        form.mahasiswaTabel.setModel(setTable());

        // Isi combo box status dengan nilai enum StatusMahasiswa
        form.statusComboBox.setModel(
                new DefaultComboBoxModel<>(Mahasiswa.StatusMahasiswa.values())
        );

        // Sembunyikan tombol update dan delete di awal
        form.updateButton.setVisible(false);
        form.deleteButton.setVisible(false);

        // ================= LISTENER =================

        form.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });

        form.updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });

        form.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int konfirmasi = JOptionPane.showConfirmDialog(
                        null,
                        "Yakin ingin menghapus data ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION
                );
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });

        form.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Listener klik baris tabel: membaca langsung dari JTable
        form.mahasiswaTabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = form.mahasiswaTabel.getSelectedRow();

                if (selectedIndex == -1) return;

                // Ambil data langsung dari tabel GUI
                String nim = form.mahasiswaTabel.getValueAt(selectedIndex, 0).toString();
                String nama = form.mahasiswaTabel.getValueAt(selectedIndex, 1).toString();
                String ttl = form.mahasiswaTabel.getValueAt(selectedIndex, 2).toString();
                String prodi = form.mahasiswaTabel.getValueAt(selectedIndex, 3).toString();
                String statusStr = form.mahasiswaTabel.getValueAt(selectedIndex, 4).toString();
                String email = form.mahasiswaTabel.getValueAt(selectedIndex, 5).toString();

                form.nimInput.setText(nim);
                form.namaInput.setText(nama);
                form.ttlInput.setText(ttl);
                form.prodiInput.setText(prodi);

                try {
                    form.statusComboBox.setSelectedItem(Mahasiswa.StatusMahasiswa.valueOf(statusStr));
                } catch (IllegalArgumentException ex) {
                    form.statusComboBox.setSelectedIndex(0);
                }

                form.emailInput.setText(email);

                form.updateButton.setVisible(true);
                form.deleteButton.setVisible(true);
            }
        });
    }

    // ================= SET TABEL =================

    public DefaultTableModel setTable() {
        Object[] cols = {"NIM", "Nama", "TTL", "Prodi", "Status", "Email" };
        DefaultTableModel model = new DefaultTableModel(null, cols);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasigma");
            while (resultSet.next()){
                Object[] row = new Object[6];
                row[0] = resultSet.getString("nim");
                row[1] = resultSet.getString("nama");
                row[2] = resultSet.getString("ttl");
                row[3] = resultSet.getString("prodi");
                row[4] = resultSet.getString("status_mahasiswa");
                row[5] = resultSet.getString("email");
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data: " + e.getMessage());
        }

        return model;
    }

    // ================= INSERT =================

    public void insertData() {
        String nim = form.nimInput.getText().trim();
        String nama = form.namaInput.getText().trim();
        String ttl = form.ttlInput.getText().trim();
        String prodi = form.prodiInput.getText().trim();
        Mahasiswa.StatusMahasiswa status_mahasiswa =
                (Mahasiswa.StatusMahasiswa) form.statusComboBox.getSelectedItem();
        String email = form.emailInput.getText().trim();

        if (nim.isEmpty() || nama.isEmpty() || ttl.isEmpty()
                || prodi.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Semua kolom input harus diisi!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // cek redundan nim
        try {
            ResultSet rs = database.selectQuery(
                    "SELECT nim FROM mahasigma WHERE nim='" + nim + "'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null,
                        "NIM " + nim + " sudah terdaftar!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal cek data: " + e.getMessage());
            return;
        }

        // Simpan ke Database
        String sqlQuery = "INSERT INTO mahasigma VALUES('" + nim + "','" + nama + "','" + ttl + "','" + prodi + "','" + email + "','" + status_mahasiswa.name() + "')";
        database.insertUpdateDeleteQuery(sqlQuery);

        form.mahasiswaTabel.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    // ================= UPDATE =================

    public void updateData() {
        if (selectedIndex < 0) return;

        String nim = form.nimInput.getText().trim();
        String nama = form.namaInput.getText().trim();
        String ttl = form.ttlInput.getText().trim();
        String prodi = form.prodiInput.getText().trim();
        Mahasiswa.StatusMahasiswa status_mahasiswa =
                (Mahasiswa.StatusMahasiswa) form.statusComboBox.getSelectedItem();
        String email = form.emailInput.getText().trim();

        if (nim.isEmpty() || nama.isEmpty() || ttl.isEmpty()
                || prodi.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Semua kolom input harus diisi!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ambil NIM lama dari baris terpilih sebagai primary key
        String oldNim = form.mahasiswaTabel.getValueAt(selectedIndex, 0).toString();

        // Update ke Database
        String sqlQuery = "UPDATE mahasigma SET " +
                "nim='" + nim + "', " +
                "nama='" + nama + "', " +
                "ttl='" + ttl + "', " +
                "prodi='" + prodi + "', " +
                "email='" + email + "', " +
                "status_mahasiswa='" + status_mahasiswa.name() + "' " +
                "WHERE NIM='" + oldNim + "'";

        database.insertUpdateDeleteQuery(sqlQuery);

        form.mahasiswaTabel.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    }

    // ================= DELETE =================

    public void deleteData() {
        if (selectedIndex >= 0) {
            // Ambil NIM data yang akan dihapus
            String nim = form.mahasiswaTabel.getValueAt(selectedIndex, 0).toString();

            // Hapus dari Database
            String sqlQuery = "DELETE FROM mahasigma WHERE NIM='" + nim + "'";
            database.insertUpdateDeleteQuery(sqlQuery);

            form.mahasiswaTabel.setModel(setTable());
            clearForm();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }
    }

    // ================= CLEAR FORM =================

    public void clearForm() {
        form.nimInput.setText("");
        form.namaInput.setText("");
        form.ttlInput.setText("");
        form.prodiInput.setText("");
        form.emailInput.setText("");

        form.statusComboBox.setSelectedIndex(0);

        form.updateButton.setVisible(false);
        form.deleteButton.setVisible(false);

        selectedIndex = -1;
    }

    // ================= MAIN =================

    public static void main(String[] args) {
        DataMahasiswa window = new DataMahasiswa();
        window.setContentPane(window.form.getMainPanel());
        window.setTitle("Data Mahasiswa");
        window.setSize(900, 600);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}