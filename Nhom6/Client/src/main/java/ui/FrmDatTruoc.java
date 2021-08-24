package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import dto.KhachHangDto;
import dto.Page.TieuDePage;
import dto.Post.PostPhieuDatTruoc;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import retrofit.RetrofitClientCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ChiTietPhieuDatTruoc;
import service.KhachHangService;
import service.PhieuDatTruocService;
import service.TieuDeService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class FrmDatTruoc extends JPanel implements ActionListener, MouseListener {
    private JButton btnNhapTD;
    private JTextField txtMaKHNhap;
    private JButton btnNhapKH;
    private JTable tableTD;
    private JTextField txtMaKH;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    private JPanel mainPanel;
    private JScrollPane scrPanelTD;
    private JTextField txtTenKH;
    private JButton btnXoa;
    private JButton btnXoaAll;
    private JButton btnXacNhanDat;
    private JButton btnXoaKH;
    private JTextField txtMaKHSearch;
    private JButton btnTimKH;
    private JTable tableDatTruoc;
    private JTable tableCTDatTruoc;
    private JScrollPane scrDatTruoc;
    private JScrollPane scrCTDatTruoc;
    private JComboBox cbTenTieuDe;
    private JButton btnLamMoi;
    private DefaultTableModel tableModelTD, tableModelDT, tableModel;
    private TieuDeService tieuDeService;
    private KhachHangService khachHangService;
    private PhieuDatTruocService phieuDatTruocService;
    private ChiTietPhieuDatTruoc chiTietPhieuDatTruoc;
    private List<Long> maTieuDe;

    public FrmDatTruoc() {
        add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(1366, 710));
        createTable();
        scrPanelTD.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPanelTD.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tieuDeService = RetrofitClientCreator.getClient().create(TieuDeService.class);
        khachHangService = RetrofitClientCreator.getClient().create(KhachHangService.class);
        phieuDatTruocService = RetrofitClientCreator.getClient().create(PhieuDatTruocService.class);
        chiTietPhieuDatTruoc = RetrofitClientCreator.getClient().create(ChiTietPhieuDatTruoc.class);
        createComBoBoxTenTieuDe();
        maTieuDe = new ArrayList<>();
        btnNhapTD.addActionListener(this);
        btnNhapKH.addActionListener(this);
        btnXacNhanDat.addActionListener(this);
        btnTimKH.addActionListener(this);
        btnLamMoi.addActionListener(this);
        getAllPhieuDatTruoc();
        tableDatTruocMouseListerner();

    }

    public void tableDatTruocMouseListerner() {

        tableDatTruoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableModel.getDataVector().removeAllElements();
                super.mouseClicked(e);
                int row = tableDatTruoc.getSelectedRow();
                long maPhieuDatTruoc = Long.parseLong(tableModelDT.getValueAt(row, 0).toString());
                try {
                    chiTietPhieuDatTruoc.getChiTietPhieuDatTruocById(maPhieuDatTruoc).execute().body().getChiTietPhieuDatTruocDtos().forEach(x -> {
                        Vector vector = new Vector();
                        vector.add(x.getMaDatTruoc());
                        vector.add(x.getMaTieuDe());
                        vector.add(x.getMaDia());
                        vector.add(x.getTrangThaiDatTruoc());
                        tableModel.addRow(vector);
                    });
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void createComBoBoxTenTieuDe() {
        cbTenTieuDe.setEditable(true);
        cbTenTieuDe.removeAllItems();
        Call<TieuDePage> call = tieuDeService.getTieuDe();
        try {
            Response<TieuDePage> response;
            response = call.execute();
            response.body().getTieuDeDtos().forEach(x -> {
                cbTenTieuDe.addItem(x.getTenTieuDe());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        cbTenTieuDe.setSelectedItem(" ");
        AutoCompleteDecorator.decorate(cbTenTieuDe);
    }

    private void createTable() {
        String[] headerTD = "Mã tiêu đề; Tên tiêu đề;".split(";");
        tableModelTD = new DefaultTableModel(headerTD, 0);
        tableTD.setModel(tableModelTD);
        tableTD.addMouseListener(this);

        String[] headerDT = "Mã đặt trước; Mã khách hàng; Ngày đặt; Trạng thái ".split(";");
        tableModelDT = new DefaultTableModel(headerDT, 0);
        tableDatTruoc.setModel(tableModelDT);
        tableDatTruoc.addMouseListener(this);

        String[] header = "Mã đặt trước; Mã tiêu đề; Mã đĩa; Trạng thái ".split(";");
        tableModel = new DefaultTableModel(header, 0);
        tableCTDatTruoc.setModel(tableModel);
        tableCTDatTruoc.addMouseListener(this);
    }

    public void getAllPhieuDatTruoc() {
        tableModelDT.setRowCount(0);
        tableModel.setRowCount(0);
        try {
            phieuDatTruocService.getAllPhieuDatTruoc().execute().body().getPhieuDatTruocDtos().forEach(x -> {
                Vector vector = new Vector();
                vector.add(x.getMaPhieuDatTruoc());
                vector.add(x.getMakhachHang());
                vector.add(x.getNgayDat());
                vector.add(x.isActive());
                tableModelDT.addRow(vector);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTablePhieuDatTruoc(Long idKH) {
        tableModelDT.getDataVector().removeAllElements();
        try {
            phieuDatTruocService.getPhieuDatTruocByIdKhachHang(idKH).execute().body().getPhieuDatTruocDtos().forEach(x -> {
                Vector vector = new Vector();
                vector.add(x.getMaPhieuDatTruoc());
                vector.add(x.getMakhachHang());
                vector.add(x.getNgayDat());
                vector.add(x.isActive());
                tableModelDT.addRow(vector);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean kiemTraMaTieuDeNhap() {
        for (int i = 0; i < tableModelTD.getRowCount(); i++) {
            if (tableModelTD.getValueAt(i, 1).equals(cbTenTieuDe.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(null, "Trùng tiêu đề đã có !", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                cbTenTieuDe.requestFocus();
                return false;
            }
        }
        return true;
    }

    public void xoaRong() {
        tableModel.setRowCount(0);
        cbTenTieuDe.setSelectedItem("");
        txtMaKHSearch.setText("");
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(btnNhapTD)) {
            if (kiemTraMaTieuDeNhap())
                try {
                    tieuDeService.getMaTieuDeByTen(cbTenTieuDe.getSelectedItem().toString()).execute().body().getTieuDeDtos().forEach(x -> {
                        Vector vector = new Vector();
                        vector.add(x.getMaTieuDe());
                        maTieuDe.add(x.getMaTieuDe());
                        vector.add(cbTenTieuDe.getSelectedItem().toString());
                        tableModelTD.addRow(vector);
                    });
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        } else if (obj.equals(btnNhapKH)) {
            try {
                khachHangService.getKhachHangById(Long.parseLong(txtMaKHNhap.getText())).enqueue(new Callback<KhachHangDto>() {
                    @Override
                    public void onResponse(Call<KhachHangDto> call, Response<KhachHangDto> response) {
                        if (response.body() == null) {
                            JOptionPane.showMessageDialog(null, "Thông tin ID khách hàng sai !", "Thông báo !",
                                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                            txtMaKHNhap.selectAll();
                            txtMaKHNhap.requestFocus();
                        } else {
                            txtMaKH.setText(String.valueOf(response.body().getMaKhachHang()));
                            txtTenKH.setText(response.body().getHoTen());
                            txtDiaChi.setText(response.body().getDiaChi());
                            txtSDT.setText(response.body().getSoDienThoai());
                        }
                    }

                    @Override
                    public void onFailure(Call<KhachHangDto> call, Throwable t) {

                    }
                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (obj.equals(btnXacNhanDat)) {
            if (tableModelTD.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn tiêu đề cần đặt !", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
            } else if (txtMaKH.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin khách hàng !", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
            } else {
                try {
                    phieuDatTruocService.addPhieuDatTruoc(new PostPhieuDatTruoc(Long.parseLong(txtMaKH.getText()))).enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            JOptionPane.showMessageDialog(null, "Đặt phiếu thành công !", "Thông báo !",
                                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                            Long maPhieuDatTruoc = response.body().longValue();
                            for (int i = 0; i < maTieuDe.size(); i++) {
                                chiTietPhieuDatTruoc.addChiTietPhieuDatTruoc(maPhieuDatTruoc, maTieuDe.get(i)).enqueue(new Callback<Long>() {
                                    @Override
                                    public void onResponse(Call<Long> call, Response<Long> response) {
                                        getAllPhieuDatTruoc();
                                    }

                                    @Override
                                    public void onFailure(Call<Long> call, Throwable t) {

                                    }
                                });
                            }


                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable t) {

                        }

                    });

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                xoaRong();
            }
        } else if (obj.equals(btnTimKH)) {
            addTablePhieuDatTruoc(Long.parseLong(txtMaKHSearch.getText()));
        } else if (obj.equals(btnLamMoi)) {
            getAllPhieuDatTruoc();
            txtMaKHSearch.selectAll();
            txtMaKHSearch.requestFocus();
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
        mainPanel.setLayout(new GridLayoutManager(4, 2, new Insets(5, 10, 5, 10), -1, -1));
        mainPanel.setBackground(new Color(-4674729));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-4674729));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Tên tiêu đề");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnNhapTD = new JButton();
        btnNhapTD.setText("Nhập tiêu đề");
        panel1.add(btnNhapTD, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        cbTenTieuDe = new JComboBox();
        panel1.add(cbTenTieuDe, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 40), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-4674729));
        mainPanel.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Mã khách hàng");
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaKHNhap = new JTextField();
        panel2.add(txtMaKHNhap, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 40), null, 0, false));
        btnNhapKH = new JButton();
        btnNhapKH.setText("Nhập khách hàng");
        panel2.add(btnNhapKH, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-4674729));
        mainPanel.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Danh sách tiêu đề đặt trước", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-14801221)));
        scrPanelTD = new JScrollPane();
        scrPanelTD.setBackground(new Color(-4674729));
        panel3.add(scrPanelTD, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableTD = new JTable();
        scrPanelTD.setViewportView(tableTD);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-4674729));
        panel3.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnXoa = new JButton();
        btnXoa.setText("Xóa");
        panel4.add(btnXoa, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        btnXoaAll = new JButton();
        btnXoaAll.setText("Xóa tất cả");
        panel4.add(btnXoaAll, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(6, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-4674729));
        mainPanel.add(panel5, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Thông tin khách hàng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-14801221)));
        final JLabel label3 = new JLabel();
        label3.setText("THÔNG TIN KHÁCH HÀNG");
        panel5.add(label3, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Mã khách hàng");
        panel5.add(label4, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaKH = new JTextField();
        txtMaKH.setEditable(false);
        panel5.add(txtMaKH, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Tên khách hàng");
        panel5.add(label5, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTenKH = new JTextField();
        txtTenKH.setEditable(false);
        panel5.add(txtTenKH, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Địa chỉ");
        panel5.add(label6, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDiaChi = new JTextField();
        txtDiaChi.setEditable(false);
        panel5.add(txtDiaChi, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Số điện thoại");
        panel5.add(label7, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtSDT = new JTextField();
        txtSDT.setEditable(false);
        panel5.add(txtSDT, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        btnXoaKH = new JButton();
        btnXoaKH.setText("Xóa khách hàng");
        panel5.add(btnXoaKH, new GridConstraints(5, 1, 1, 2, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel5.add(spacer1, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel5.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-4674729));
        mainPanel.add(panel6, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-65536)), "Xác nhận đặt trước", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer3 = new Spacer();
        panel6.add(spacer3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel6.add(spacer4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        btnXacNhanDat = new JButton();
        btnXacNhanDat.setHorizontalTextPosition(0);
        btnXacNhanDat.setText("Xác nhận đặt ");
        panel6.add(btnXacNhanDat, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(500, 40), null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel6.add(spacer5, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel6.add(spacer6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-4674729));
        mainPanel.add(panel7, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Phiếu đặt trước", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-14801221)));
        final JLabel label8 = new JLabel();
        label8.setText("Mã khách hàng đặt trước");
        panel7.add(label8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaKHSearch = new JTextField();
        panel7.add(txtMaKHSearch, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 40), null, 0, false));
        btnTimKH = new JButton();
        btnTimKH.setText("Tìm");
        panel7.add(btnTimKH, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        scrDatTruoc = new JScrollPane();
        scrDatTruoc.setBackground(new Color(-4674729));
        panel7.add(scrDatTruoc, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableDatTruoc = new JTable();
        scrDatTruoc.setViewportView(tableDatTruoc);
        btnLamMoi = new JButton();
        btnLamMoi.setText("Làm mới");
        panel7.add(btnLamMoi, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel8.setBackground(new Color(-4674729));
        mainPanel.add(panel8, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Chi tiết phiếu đặt trước", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-14801221)));
        scrCTDatTruoc = new JScrollPane();
        scrCTDatTruoc.setBackground(new Color(-4674729));
        panel8.add(scrCTDatTruoc, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableCTDatTruoc = new JTable();
        scrCTDatTruoc.setViewportView(tableCTDatTruoc);
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
