package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import dto.KhachHangDto;
import dto.Post.PostKhachHang;
import retrofit.RetrofitClientCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.KhachHangService;
import status.TrangThaiKhachHang;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;

public class FrmKhachHang extends JPanel implements ActionListener, MouseListener {
    private JPanel mainPanel;
    private JPanel panelTopL;
    private JPanel panelTopR;
    private JScrollPane scrPanel;
    private JTable table;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    private JRadioButton rdTrue;
    private JRadioButton rdFalse;
    private JTextField txtMaKH;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXoaRong;
    private JPanel panelBotR;
    private JTextField txtTenKH;
    private DefaultTableModel tableModel;
    private KhachHangService khachHangService;
    private Retrofit retrofit;
    private String role;

    public FrmKhachHang(String role) {
        $$$setupUI$$$();
        add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(1366, 710));
        createTable();
        createButton();
        scrPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        retrofit = RetrofitClientCreator.getClient();
        khachHangService = retrofit.create(KhachHangService.class);
        getDanhSachKhachHang();
        rdTrue.setSelected(true);
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoaRong.addActionListener(this);
        this.role = role;
        setAuth();
    }

    public void getDanhSachKhachHang() {
        tableModel.getDataVector().removeAllElements();
        try {
            khachHangService.getDanhSachKhachHang().execute().body().getKhachHangDtos().forEach(x -> {
                Vector vector = new Vector();
                vector.add(x.getMaKhachHang());
                vector.add(x.getHoTen());
                vector.add(x.getDiaChi());
                vector.add(x.getSoDienThoai());
                if (x.isTrangThai() == true)
                    vector.add("Đang hoat động");
                else
                    vector.add("Ngưng hoạt động");
                tableModel.addRow(vector);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void addKhachHang() {
        try {
            khachHangService.addKhacHang(new PostKhachHang(txtTenKH.getText(), txtDiaChi.getText(), txtSDT.getText())).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    getDanhSachKhachHang();
                    JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công !!!", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm khách hàng thất bại !!!", "Thông báo !",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
        }
    }

    public void xoaKhachHang() {
        try {
            int maKH = Integer.parseInt(txtMaKH.getText().toString());
            khachHangService.deleteKhachHang(maKH).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    getDanhSachKhachHang();
                    JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công !!!", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } catch (Exception e) {
        }
    }

    public void suaKhachHang() {
        try {
            khachHangService.editKhachHang(Integer.parseInt(txtMaKH.getText()), new KhachHangDto(Long.parseLong(txtMaKH.getText()), txtTenKH.getText(), txtDiaChi.getText(), txtSDT.getText(), true)).enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    getDanhSachKhachHang();
                    JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công !!!", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sửa khách hàng thất bại !!!", "Thông báo !",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
        }
    }

    private boolean kiemTraData() {
        if (txtTenKH.getText().matches("[a-zA-Z ]+") != true) {
            JOptionPane.showMessageDialog(null,
                    "Tên có chữ cái và khoảng trắng !\n" + "Ví dụ: Nguyễn Văn Trung",
                    "Lỗi dữ liệu nhập !!!",
                    JOptionPane.ERROR_MESSAGE);
            txtTenKH.requestFocus();
            txtTenKH.selectAll();
            return false;
        } else if (txtDiaChi.getText().equals("") || txtDiaChi.getText().length() < 20) {
            JOptionPane.showMessageDialog(null,
                    "Địa chỉ không được bỏ trống và ít hơn 20 kí tự !\n" + "Ví dụ: Gò Vấp, Hồ Chí Minh",
                    "Lỗi dữ liệu nhập !!!",
                    JOptionPane.ERROR_MESSAGE);
            txtDiaChi.requestFocus();
            txtDiaChi.selectAll();
            return false;
        } else if (txtSDT.getText().equals("") || txtSDT.getText().matches("^0[0-9]{9}") != true) {
            JOptionPane.showMessageDialog(null,
                    "Số điện thoại bắt đầu bằng chữ số '0' và có 10 chữ số !\n" + "Ví dụ: 0911982090",
                    "Lỗi dữ liệu nhập !!!",
                    JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            txtSDT.selectAll();
            return false;
        }
        return true;
    }

    public void xoaRong() {
        txtMaKH.setEditable(true);
        txtMaKH.requestFocus();
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
    }

    private void createTable() {
        String[] header = "Mã khách hàng; Họ tên; Địa chỉ ; Số điện thoại; Trạng thái hoạt động".split(";");
        tableModel = new DefaultTableModel(header, 0);
        table.setModel(tableModel);
        table.addMouseListener(this);
    }

    private void createButton() {
        ButtonGroup btnTrangThai = new ButtonGroup();
        btnTrangThai.add(rdTrue);
        btnTrangThai.add(rdFalse);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj.equals(btnThem)) {
            if (kiemTraData())
                addKhachHang();
        } else if (obj.equals(btnXoa)) {
            if (txtMaKH.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần xóa !!!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
            }
            xoaKhachHang();
        } else if (obj.equals(btnSua)) {
            if (kiemTraData())
                suaKhachHang();
        } else if (obj.equals(btnXoaRong)) {
            xoaRong();
        }
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
        mainPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBackground(new Color(-4674729));
        panelTopL = new JPanel();
        panelTopL.setLayout(new GridLayoutManager(1, 1, new Insets(5, 10, 5, 10), -1, -1));
        panelTopL.setBackground(new Color(-4674729));
        mainPanel.add(panelTopL, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelTopL.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Danh sách khách hàng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, panelTopL.getFont()), new Color(-14801221)));
        scrPanel = new JScrollPane();
        panelTopL.add(scrPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table = new JTable();
        scrPanel.setViewportView(table);
        panelTopR = new JPanel();
        panelTopR.setLayout(new GridLayoutManager(11, 3, new Insets(5, 10, 5, 10), -1, -1));
        panelTopR.setBackground(new Color(-4674729));
        mainPanel.add(panelTopR, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 24, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-14801221));
        label1.setText("THÔNG TIN KHÁCH HÀNG");
        panelTopR.add(label1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-16185079));
        label2.setText("Họ tên");
        panelTopR.add(label2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-16185079));
        label3.setText("Địa chỉ");
        panelTopR.add(label3, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDiaChi = new JTextField();
        panelTopR.add(txtDiaChi, new GridConstraints(6, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-16185079));
        label4.setText("Số điện thoại");
        panelTopR.add(label4, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtSDT = new JTextField();
        panelTopR.add(txtSDT, new GridConstraints(8, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-16185079));
        label5.setText("Trạng thái");
        panelTopR.add(label5, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rdTrue = new JRadioButton();
        Font rdTrueFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 13, rdTrue.getFont());
        if (rdTrueFont != null) rdTrue.setFont(rdTrueFont);
        rdTrue.setForeground(new Color(-16185079));
        rdTrue.setText("Đang hoạt động");
        panelTopR.add(rdTrue, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rdFalse = new JRadioButton();
        Font rdFalseFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 13, rdFalse.getFont());
        if (rdFalseFont != null) rdFalse.setFont(rdFalseFont);
        rdFalse.setForeground(new Color(-16185079));
        rdFalse.setText("Ngưng hoạt động");
        panelTopR.add(rdFalse, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelTopR.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelTopR.add(spacer2, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panelTopR.add(spacer3, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panelTopR.add(spacer4, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txtTenKH = new JTextField();
        panelTopR.add(txtTenKH, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setForeground(new Color(-16185079));
        label6.setText("Mã khách hàng");
        panelTopR.add(label6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaKH = new JTextField();
        txtMaKH.setEditable(false);
        panelTopR.add(txtMaKH, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final Spacer spacer5 = new Spacer();
        panelTopR.add(spacer5, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelBotR = new JPanel();
        panelBotR.setLayout(new GridLayoutManager(2, 2, new Insets(5, 10, 5, 10), -1, -1));
        panelBotR.setBackground(new Color(-4674729));
        mainPanel.add(panelBotR, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelBotR.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Thao tác", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, panelBotR.getFont()), new Color(-14801221)));
        btnThem = new JButton();
        Font btnThemFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, btnThem.getFont());
        if (btnThemFont != null) btnThem.setFont(btnThemFont);
        btnThem.setForeground(new Color(-16185079));
        btnThem.setText("Thêm");
        panelBotR.add(btnThem, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        btnSua = new JButton();
        Font btnSuaFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, btnSua.getFont());
        if (btnSuaFont != null) btnSua.setFont(btnSuaFont);
        btnSua.setForeground(new Color(-16185079));
        btnSua.setText("Cập nhật");
        panelBotR.add(btnSua, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        btnXoa = new JButton();
        Font btnXoaFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, btnXoa.getFont());
        if (btnXoaFont != null) btnXoa.setFont(btnXoaFont);
        btnXoa.setForeground(new Color(-16185079));
        btnXoa.setText("Xóa");
        panelBotR.add(btnXoa, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        btnXoaRong = new JButton();
        Font btnXoaRongFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, btnXoaRong.getFont());
        if (btnXoaRongFont != null) btnXoaRong.setFont(btnXoaRongFont);
        btnXoaRong.setForeground(new Color(-16185079));
        btnXoaRong.setText("Xóa rỗng");
        panelBotR.add(btnXoaRong, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        String maKH = tableModel.getValueAt(row, 0).toString();
        String tenKH = tableModel.getValueAt(row, 1).toString();
        String diaChiKH = tableModel.getValueAt(row, 2).toString();
        String sdtKH = tableModel.getValueAt(row, 3).toString();
        String trangThai = tableModel.getValueAt(row, 4).toString();

        txtMaKH.setText(maKH);
        txtTenKH.setText(tenKH);
        txtDiaChi.setText(diaChiKH);
        txtSDT.setText(sdtKH);

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

    public void setAuth() {
        if (role.equalsIgnoreCase("nv")) {
            btnXoa.setVisible(false);
        }
        if (role.equalsIgnoreCase("am")) {
            btnThem.setVisible(false);
            btnSua.setVisible(false);
        }
    }
}
