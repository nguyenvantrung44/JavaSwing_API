package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import dto.KhachHangDto;
import dto.PhiTreHenDto;
import retrofit.RetrofitClientCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ChiTietPhiTreHen;
import service.KhachHangService;
import service.PhiTreHenService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Vector;

public class FrmPhiTreHen extends JPanel implements ActionListener, MouseListener {
    private JTextField txtMaKH;
    private JTextField txtTenKH;
    private JTextField txtTongPhiTre;
    private JButton btnThanhToanPhiTreHen;
    private JButton btnThanhToanAll;
    private JButton btnHuy;
    private JTable table;
    private JScrollPane scrPanel;
    private JPanel mainPanel;
    private JTable tableCT;
    private JScrollPane scrPanelCT;
    private JTextField txtTim;
    private JButton btnTim;
    private DefaultTableModel tableModel, tableModelct;
    private PhiTreHenService phiTreHenService;
    private ChiTietPhiTreHen chiTietPhiTreHen;
    private KhachHangService khachHangService;
    private double tienTra;
    private String role;

    public FrmPhiTreHen(String role) {
        add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(1366, 710));
        createTable();
        scrPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        phiTreHenService = RetrofitClientCreator.getClient().create(PhiTreHenService.class);
        chiTietPhiTreHen = RetrofitClientCreator.getClient().create(ChiTietPhiTreHen.class);
        khachHangService = RetrofitClientCreator.getClient().create(KhachHangService.class);
        getAllPhiTreHen();
        tableMouseListerner();
        btnTim.addActionListener(this);
        btnHuy.addActionListener(this);
        btnThanhToanPhiTreHen.addActionListener(this);
        btnThanhToanAll.addActionListener(this);
        this.role = role;
        setAuth();

    }

    private void setAuth() {
        if (role.equalsIgnoreCase("am")) {
            btnThanhToanAll.setVisible(false);
            btnThanhToanPhiTreHen.setVisible(false);

        }
        if (role.equalsIgnoreCase("nv")) {
            btnHuy.setVisible(false);
        }
    }

    private void createTable() {

        String[] header = "Mã phí trễ hẹn; Mã khách hàng; Tên khách hàng; Tiền đã trả; Tổng phí trễ hẹn ".split(";");
        tableModel = new DefaultTableModel(header, 0);
        table.setModel(tableModel);
        table.addMouseListener(this);

        String[] headerct = "Mã phí trễ hẹn; Mã đĩa; Ngày phải trả; Ngày trả; Số ngày trễ; Phí trễ".split(";");
        tableModelct = new DefaultTableModel(headerct, 0);
        tableCT.setModel(tableModelct);
        tableCT.addMouseListener(this);

    }

    public void tableMouseListerner() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableModelct.getDataVector().removeAllElements();
                super.mouseClicked(e);
                int row = table.getSelectedRow();
                long maphitrehen = Long.parseLong(tableModel.getValueAt(row, 0).toString());
                try {
                    chiTietPhiTreHen.getChiTietPhiTreHenById(maphitrehen).execute().body().getChiTietPhiTreHenDtos().forEach(x -> {
                        Vector vector = new Vector();
                        vector.add(x.getMaphiTreHen());
                        vector.add(x.getMaDia());
                        LocalDate ngayTra = LocalDate.parse(x.getNgayTra());
                        LocalDate ngayPhaiTra = ngayTra.minusDays(x.getSoNgayTreHen());
                        vector.add(ngayPhaiTra);
                        vector.add(x.getNgayTra());
                        vector.add(x.getSoNgayTreHen());
                        vector.add(x.getPhiTre());
                        tableModelct.addRow(vector);
                    });
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public String getTenKhachHang(Long maKhachHang) {
        try {
            return khachHangService.getKhachHangById(maKhachHang).execute().body().getHoTen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void xoaRong() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtTongPhiTre.setText("");
        txtTim.selectAll();
        txtTim.requestFocus();
        tableModelct.setRowCount(0);
    }

    public void tongPhiTreHen() {
        double tong = 0.0;
        double tienTra = 0.0;
        for (int i = 0; i < table.getRowCount(); i++) {
            tong += +Double.parseDouble(tableModel.getValueAt(i, 4).toString());
            tienTra += +Double.parseDouble(tableModel.getValueAt(i, 3).toString());
            txtMaKH.setText(tableModel.getValueAt(i, 1).toString());
            txtTenKH.setText(tableModel.getValueAt(i, 2).toString());
        }
        txtTongPhiTre.setText(String.valueOf(((tong - tienTra)) * 100 / 100));

    }

    public void updatePhiTre(Double tien) {
        tienTra = Double.valueOf(tien);
        if (tienTra == 0.0) {
            return;
        } else {
            try {
                phiTreHenService.getPhiTreHenByIdKH(Long.parseLong(txtMaKH.getText())).execute().body().getPhiTreHenDtos().forEach(x -> {
                    //phân tán tiền trả
                    if (x.getTienDaTra() + tienTra >= x.getTongPhiTreHen()) {
                        tienTra += x.getTienDaTra() - x.getTongPhiTreHen();
                        phiTreHenService.updateTienDaTraPhiTre(x.getMaPhiTreHen(), new PhiTreHenDto(
                                x.getTongPhiTreHen(), false)).enqueue(new Callback<Long>() {
                            @Override
                            public void onResponse(Call<Long> call, Response<Long> response) {

                            }

                            @Override
                            public void onFailure(Call<Long> call, Throwable t) {

                            }
                        });

                    } else {
                        if (tienTra == 0.0)
                            return;
                        else {
                            phiTreHenService.updateTienDaTraPhiTre(x.getMaPhiTreHen(), new PhiTreHenDto(
                                    x.getTienDaTra() + tienTra, true)).enqueue(new Callback<Long>() {
                                @Override
                                public void onResponse(Call<Long> call, Response<Long> response) {

                                }

                                @Override
                                public void onFailure(Call<Long> call, Throwable t) {

                                }
                            });
                            tienTra = 0.0;
                        }

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void timKiemPhiTreHenKhachHang() {
        try {
            tableModel.getDataVector().removeAllElements();
            long maKH = Long.parseLong(txtTim.getText());
            if (phiTreHenService.getPhiTreHenByIdKH(maKH).execute().body().getPhiTreHenDtos().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không tồn tại khách hàng có mã như trên !!!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                xoaRong();
            } else
                phiTreHenService.getPhiTreHenByIdKH(maKH).execute().body().getPhiTreHenDtos().forEach(x -> {
                    Vector vector = new Vector();
                    vector.add(x.getMaPhiTreHen());
                    vector.add(x.getMaKhachHang());
                    getTenKhachHang(x.getMaKhachHang());
                    vector.add(getTenKhachHang(x.getMaKhachHang()));
                    vector.add(x.getTienDaTra());
                    vector.add(x.getTongPhiTreHen());
                    tableModel.addRow(vector);
                });
            tongPhiTreHen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllPhiTreHen() {
        tableModel.getDataVector().removeAllElements();
        try {
            phiTreHenService.getAllPhiTreHen().execute().body().getPhiTreHenDtos().forEach(x -> {

                Vector vector = new Vector();
                vector.add(x.getMaPhiTreHen());
                vector.add(x.getMaKhachHang());
                getTenKhachHang(x.getMaKhachHang());
                vector.add(getTenKhachHang(x.getMaKhachHang()));
                vector.add(x.getTienDaTra());
                vector.add(x.getTongPhiTreHen());
                tableModel.addRow(vector);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void huyPhiTreHen() {
        int row = table.getSelectedRow();
        try {
            phiTreHenService.updateTienDaTraPhiTre(Long.parseLong(tableModel.getValueAt(row, 0).toString()),
                    new PhiTreHenDto(Double.parseDouble(tableModel.getValueAt(row, 4).toString()), false)).enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {

                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(btnTim)) {
            if (txtTim.getText().equals("") || txtTim.getText().matches("^[1-9][0-9]*") != true) {
                JOptionPane.showMessageDialog(null, "Nhập sai mã khách hàng !!!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                xoaRong();
            } else
                timKiemPhiTreHenKhachHang();
        } else if (obj.equals(btnThanhToanPhiTreHen)) {
            String value = "0.0";

            value = JOptionPane.showInputDialog(null, "Nhập số tiền khách muốn thanh toán !",
                    "Thanh toán phí trễ hẹn !!!", JOptionPane.INFORMATION_MESSAGE);

            if (value == null || value.toString().matches("[0-9]*") != true) {
                JOptionPane.showMessageDialog(null, "Thanh toán thất bại !!!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                xoaRong();
                return;
            } else {
                updatePhiTre(Double.valueOf(value));
                xoaRong();
                JOptionPane.showMessageDialog(null, "Thanh toán thành công !!!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                getAllPhiTreHen();
            }
        } else if (obj.equals(btnThanhToanAll)) {
            if (txtMaKH.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng tìm khách hàng muốn thanh toán !!!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                xoaRong();
            } else {
                updatePhiTre(Double.valueOf(txtTongPhiTre.getText()));
                xoaRong();
                JOptionPane.showMessageDialog(null, "Thanh toán thành công !!!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                getAllPhiTreHen();
            }
        } else if (obj.equals(btnHuy)) {
            int row = table.getSelectedRow();
            if (row != -1) {
                huyPhiTreHen();
                JOptionPane.showMessageDialog(null, "Hủy thành công !!!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn phí trễ muốn hủy !!!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
            }
        }

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(6, 6, new Insets(5, 10, 5, 10), -1, -1));
        mainPanel.setBackground(new Color(-4674729));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-4674729));
        mainPanel.add(panel1, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Thông tin khách hàng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-14801221)));
        txtMaKH = new JTextField();
        txtMaKH.setEditable(false);
        panel1.add(txtMaKH, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Tên khách hàng");
        panel1.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTenKH = new JTextField();
        txtTenKH.setEditable(false);
        panel1.add(txtTenKH, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Tổng tiền nợ phí trễ hẹn");
        panel1.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTongPhiTre = new JTextField();
        txtTongPhiTre.setEditable(false);
        panel1.add(txtTongPhiTre, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Mã khách hàng");
        panel1.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainPanel.add(spacer2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-4674729));
        mainPanel.add(panel2, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Danh sách phí trễ hẹn", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-14801221)));
        scrPanel = new JScrollPane();
        panel2.add(scrPanel, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table = new JTable();
        scrPanel.setViewportView(table);
        final JLabel label4 = new JLabel();
        label4.setText("Nhập mã khách hàng");
        panel2.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTim = new JTextField();
        panel2.add(txtTim, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        btnTim = new JButton();
        btnTim.setText("Tìm");
        panel2.add(btnTim, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 30), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(5, 10, 5, 10), -1, -1));
        panel3.setBackground(new Color(-4674729));
        mainPanel.add(panel3, new GridConstraints(4, 4, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrPanelCT = new JScrollPane();
        scrPanelCT.setBackground(new Color(-4674729));
        panel3.add(scrPanelCT, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrPanelCT.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Chi tiết phí trễ hẹn", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-14801221)));
        tableCT = new JTable();
        scrPanelCT.setViewportView(tableCT);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-4674729));
        mainPanel.add(panel4, new GridConstraints(2, 4, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Chức năng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-14801221)));
        btnThanhToanAll = new JButton();
        btnThanhToanAll.setText("Thanh toán tất cả");
        panel4.add(btnThanhToanAll, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        btnThanhToanPhiTreHen = new JButton();
        btnThanhToanPhiTreHen.setText("Thanh toán ");
        panel4.add(btnThanhToanPhiTreHen, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        btnHuy = new JButton();
        btnHuy.setText("Hủy phí trễ hẹn");
        panel4.add(btnHuy, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        final Spacer spacer3 = new Spacer();
        mainPanel.add(spacer3, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-6643315));
        mainPanel.add(panel5, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 24, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-14801221));
        label5.setText("QUẢN LÝ PHÍ TRỄ HẸN");
        panel5.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        mainPanel.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
