package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import dto.LoaiDiaDto;
import dto.Post.PostDia;
import dto.Post.PostTieuDe;
import retrofit.RetrofitClientCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.LoaiDiaService;
import service.TieuDeService;
import status.TrangThaiDia;

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
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class FrmTieuDe extends JPanel implements MouseListener, ActionListener {
    private JPanel mainPanel;
    private JTable table;
    private JTextField txtMaTD;
    private JTextField txtTenTD;
    private JTextField txtMaLoaiDia;
    private JButton btnThemTD;
    private JButton btnXoaTD;
    private JScrollPane scrPanel;
    private JTextArea area;
    private JScrollPane scrarea;
    private JComboBox cbMaLoaiDia;
    private JButton btnXoaRong;
    private TieuDeService tieuDeService;
    private DefaultTableModel tableModel;
    private Retrofit retrofit;
    private LoaiDiaService loaiDiaService;

    public FrmTieuDe() {
        add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(1366, 710));
        createTable();
        scrarea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrarea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        retrofit = RetrofitClientCreator.getClient();
        tieuDeService = retrofit.create(TieuDeService.class);
        loaiDiaService = retrofit.create(LoaiDiaService.class);
        getDanhSachTieuDe();
        updateComBoBoxMaLoaiDia();
        btnThemTD.addActionListener(this);
        btnXoaTD.addActionListener(this);
        btnXoaRong.addActionListener(this);
    }

    public void getDanhSachTieuDe() {
        tableModel.getDataVector().removeAllElements();
        try {
            tieuDeService.getTieuDe().execute().body().getTieuDeDtos().forEach(x -> {
                Vector vector = new Vector();
                vector.add(x.getMaTieuDe());
                vector.add(x.getTenTieuDe());
                vector.add(x.getMaLoaiDia());
                vector.add(x.getTomTat());
                tableModel.addRow(vector);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean kiemTraDuLieu() {
        if (txtTenTD.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên tiêu đề không được bỏ trống !!!", "Thông báo !",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
            txtTenTD.selectAll();
            txtTenTD.requestFocus();
            return false;
        } else if (area.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Tóm tắt không được bỏ trống !!!", "Thông báo !",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
            area.selectAll();
            area.requestFocus();
            return false;
        }
        return true;
    }

    public void addTieuDe() {
        try {
            tieuDeService.addTieuDe(new PostTieuDe(txtTenTD.getText(), area.getText(), Long.parseLong(traMaLoaiDiaComboBox(cbMaLoaiDia.getSelectedItem().toString())))).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    getDanhSachTieuDe();
                    JOptionPane.showMessageDialog(null, "Thêm tiêu đề thành công !!!", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm tiêu đề thất bại !!!", "Thông báo !",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
        }

    }

    public boolean xoaTieuDe() {
        try {
            int maTD = Integer.parseInt(txtMaTD.getText().toString());
            tieuDeService.deleteTieuDe(maTD).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn đĩa cần xóa !");
        }
        return false;
    }

    public void updateComBoBoxMaLoaiDia() {
        cbMaLoaiDia.removeAllItems();
        Call<List<LoaiDiaDto>> call = loaiDiaService.getDanhSachLoaiDia();
        try {
            Response<List<LoaiDiaDto>> response;
            response = call.execute();
            response.body().forEach(x -> {
                cbMaLoaiDia.addItem(x.getMaLoaiDia() + "-" + x.getTenLoaiDia());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        String[] header = "Mã tiêu đề; Tên tiêu đề; Mã loại đĩa; Tóm tắt".split(";");
        tableModel = new DefaultTableModel(header, 0);
        table.setModel(tableModel);
        table.addMouseListener(this);
    }

    public String traMaLoaiDiaComboBox(String s) {
        String[] maLoaiDia = s.split("-");
        return maLoaiDia[0];
    }

    public void xoaRong() {
        txtMaTD.setText("");
        txtTenTD.setText("");
        area.setText("");
        cbMaLoaiDia.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj.equals(btnThemTD)) {
            if (kiemTraDuLieu())
                addTieuDe();
            xoaRong();
        } else if (obj.equals(btnXoaTD)) {
            if (xoaTieuDe() == true) {
                xoaRong();
                JOptionPane.showMessageDialog(null, "Xóa tiêu đề thành công !!!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                getDanhSachTieuDe();
            }
        } else if (obj.equals(btnXoaRong)) {
            xoaRong();
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
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(-4674729));
        mainPanel.setEnabled(true);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(8, 14, new Insets(10, 20, 10, 20), -1, -1));
        panel1.setBackground(new Color(-4674729));
        mainPanel.add(panel1, BorderLayout.CENTER);
        scrPanel = new JScrollPane();
        scrPanel.setBackground(new Color(-4674729));
        panel1.add(scrPanel, new GridConstraints(6, 0, 1, 13, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Danh sách tiêu đề", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, scrPanel.getFont()), new Color(-14801221)));
        table = new JTable();
        scrPanel.setViewportView(table);
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(7, 0, 1, 14, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(5, 0, 1, 14, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16185079));
        label1.setText("Mã tiêu đề");
        panel1.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaTD = new JTextField();
        txtMaTD.setEditable(false);
        panel1.add(txtMaTD, new GridConstraints(1, 1, 1, 5, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-16185079));
        label2.setText("Tên tiêu đề");
        panel1.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTenTD = new JTextField();
        panel1.add(txtTenTD, new GridConstraints(2, 1, 1, 5, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-16185079));
        label3.setText("Mã loại đĩa");
        panel1.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(10, 5, 10, 5), -1, -1));
        panel2.setBackground(new Color(-4674729));
        panel2.setForeground(new Color(-14801221));
        panel1.add(panel2, new GridConstraints(4, 0, 1, 13, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Chọn chức năng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, panel2.getFont()), new Color(-14801221)));
        btnThemTD = new JButton();
        Font btnThemTDFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, btnThemTD.getFont());
        if (btnThemTDFont != null) btnThemTD.setFont(btnThemTDFont);
        btnThemTD.setForeground(new Color(-16185079));
        btnThemTD.setText("Thêm tiêu đề");
        panel2.add(btnThemTD, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        btnXoaTD = new JButton();
        Font btnXoaTDFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, btnXoaTD.getFont());
        if (btnXoaTDFont != null) btnXoaTD.setFont(btnXoaTDFont);
        btnXoaTD.setForeground(new Color(-16185079));
        btnXoaTD.setText("Xóa tiêu đề");
        panel2.add(btnXoaTD, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        btnXoaRong = new JButton();
        Font btnXoaRongFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, btnXoaRong.getFont());
        if (btnXoaRongFont != null) btnXoaRong.setFont(btnXoaRongFont);
        btnXoaRong.setForeground(new Color(-16185079));
        btnXoaRong.setText("Xóa rỗng");
        panel2.add(btnXoaRong, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel3.setBackground(new Color(-6643315));
        panel1.add(panel3, new GridConstraints(0, 0, 1, 13, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setBackground(new Color(-14801221));
        Font label4Font = this.$$$getFont$$$("Yu Gothic UI Semibold", Font.BOLD, 24, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-14801221));
        label4.setHorizontalAlignment(0);
        label4.setHorizontalTextPosition(0);
        label4.setText("QUẢN LÝ TIÊU ĐỀ");
        label4.setVerticalAlignment(3);
        panel3.add(label4, BorderLayout.CENTER);
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-16185079));
        label5.setText("Tóm tắt");
        panel1.add(label5, new GridConstraints(1, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrarea = new JScrollPane();
        panel1.add(scrarea, new GridConstraints(1, 7, 3, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        area = new JTextArea();
        Font areaFont = this.$$$getFont$$$(null, -1, 14, area.getFont());
        if (areaFont != null) area.setFont(areaFont);
        scrarea.setViewportView(area);
        cbMaLoaiDia = new JComboBox();
        panel1.add(cbMaLoaiDia, new GridConstraints(3, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 30), null, 0, false));
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
        int rowSelect = table.getSelectedRow();
        String maTD = tableModel.getValueAt(rowSelect, 0).toString();
        String tenTD = tableModel.getValueAt(rowSelect, 1).toString();
        String maLoaiDia = tableModel.getValueAt(rowSelect, 2).toString();
        String tomTat = tableModel.getValueAt(rowSelect, 3).toString();

        txtMaTD.setText(maTD);
        txtTenTD.setText(tenTD);
        area.setText(tomTat);

        for (int i = 0; i < cbMaLoaiDia.getItemCount(); i++) {
            if (maLoaiDia.equalsIgnoreCase(traMaLoaiDiaComboBox(cbMaLoaiDia.getItemAt(i).toString()))) {
                cbMaLoaiDia.setSelectedItem(cbMaLoaiDia.getItemAt(i));
            }
        }
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
