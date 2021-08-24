package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import dto.LoaiDiaDto;
import dto.Page.DiaPage;
import dto.Page.TieuDePage;
import dto.Post.PostDia;
import dto.TieuDeDto;
import lombok.extern.log4j.Log4j2;
import retrofit.RetrofitClientCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.DiaService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;


public class FrmDia extends JPanel implements MouseListener, ActionListener {

    private JPanel mainPanel;
    private JTextField txtMaDia;
    private JTextField txtTrangThai;
    private JTextField txtMaTieuDe;
    private JTable table;
    private JScrollPane scrPanel;
    private JButton btnThemD;
    private JButton btnXoaD;
    private JComboBox cbMaTieuDe;
    private JComboBox cbTrangThai;
    private Retrofit retrofit;
    private DefaultTableModel tableModel;
    private DiaService diaService;
    private TieuDeService tieuDeService;

    public FrmDia() {
        $$$setupUI$$$();
        add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(1366, 710));
        createTable();
        scrPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        retrofit = RetrofitClientCreator.getClient();
        diaService = retrofit.create(DiaService.class);
        tieuDeService = retrofit.create(TieuDeService.class);
        getDanhSachDia();
        updateComBoBoxTieuDe();
        updateComBoBoxTrangThai();
        btnThemD.addActionListener(this);
        btnXoaD.addActionListener(this);
    }

    public void getDanhSachDia() {
        tableModel.getDataVector().removeAllElements();
        try {
            diaService.getDanhSachDia().execute().body().getDiaDtos().forEach(x -> {
                Vector vector = new Vector();
                vector.add(x.getMaDia());
                vector.add(x.getTrangThai());
                vector.add(x.getTenTieuDe());
                tableModel.addRow(vector);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateComBoBoxTieuDe() {
        cbMaTieuDe.removeAllItems();
        try {
            tieuDeService.getTieuDe().execute().body().getTieuDeDtos().forEach(x -> {
                cbMaTieuDe.addItem(x.getMaTieuDe() + "-" + x.getTenTieuDe());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateComBoBoxTrangThai() {
        cbTrangThai.removeAllItems();
        for (TrangThaiDia trangThaiDia : TrangThaiDia.values()) {
            cbTrangThai.addItem(trangThaiDia);
        }
    }

    public void xoaDia() {
        try {
            int maDia = Integer.parseInt(txtMaDia.getText().toString());
            diaService.deleteDia(maDia).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    getDanhSachDia();
                    JOptionPane.showMessageDialog(null, "Xóa Đĩa thành công !!!", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }

            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn đĩa cần xóa !!!", "Thông báo !",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
        }
    }

    public void addDia() {
        try {
            diaService.addDia(new PostDia(TrangThaiDia.sanSangChoThue, Long.parseLong(traMaTieuDeComboBox(cbMaTieuDe.getSelectedItem().toString())))).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    getDanhSachDia();
                    JOptionPane.showMessageDialog(null, "Thêm Đĩa thành công !!!", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm Đĩa thất bại !!!", "Thông báo !",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
            e.printStackTrace();
        }
    }

    private void createTable() {
        String[] header = "Mã đĩa; Trạng thái; Tiêu đề ".split(";");
        tableModel = new DefaultTableModel(header, 0);
        table.setModel(tableModel);
        table.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(btnThemD)) {
            addDia();
        }
        if (obj.equals(btnXoaD)) {
            xoaDia();
            getDanhSachDia();
        }
    }

    public String traTenTieuDeComboBox(String s) {
        String[] maTenTieuDe = s.split("-");
        return maTenTieuDe[1];
    }

    public String traMaTieuDeComboBox(String s) {
        String[] maTenTieuDe = s.split("-");
        return maTenTieuDe[0];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        String maDia = tableModel.getValueAt(row, 0).toString();
        String trangThai = tableModel.getValueAt(row, 1).toString();
        String maTieuDe = tableModel.getValueAt(row, 2).toString();

        txtMaDia.setText(maDia);
        for (int i = 0; i < cbTrangThai.getItemCount(); i++) {
            if (trangThai.equalsIgnoreCase(cbTrangThai.getItemAt(i).toString())) {
                cbTrangThai.setSelectedItem(cbTrangThai.getItemAt(i));
                return;
            }
        }

        for (int i = 0; i < cbMaTieuDe.getItemCount(); i++) {
            if (maTieuDe.equalsIgnoreCase(traTenTieuDeComboBox(cbMaTieuDe.getItemAt(i).toString()))) {
                cbMaTieuDe.setSelectedItem(cbMaTieuDe.getItemAt(i));
                return;
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(11, 5, new Insets(10, 20, 10, 20), -1, -1));
        mainPanel.setBackground(new Color(-4674729));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setBackground(new Color(-6643315));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-6214087));
        Font label1Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 24, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-14801221));
        label1.setHorizontalAlignment(0);
        label1.setText("QUẢN LÝ ĐĨA");
        panel1.add(label1, BorderLayout.NORTH);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-16185079));
        label2.setText("Mã đĩa");
        mainPanel.add(label2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaDia = new JTextField();
        txtMaDia.setEditable(false);
        mainPanel.add(txtMaDia, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-16185079));
        label3.setText("Trạng thái");
        mainPanel.add(label3, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-16185079));
        label4.setText("Mã tiêu đề");
        mainPanel.add(label4, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainPanel.add(spacer2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel2, new GridConstraints(10, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrPanel = new JScrollPane();
        panel2.add(scrPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Danh sách đĩa", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, scrPanel.getFont()), new Color(-14801221)));
        table = new JTable();
        scrPanel.setViewportView(table);
        final Spacer spacer3 = new Spacer();
        mainPanel.add(spacer3, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        mainPanel.add(spacer4, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        mainPanel.add(spacer5, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(10, 5, 10, 5), -1, -1));
        panel3.setBackground(new Color(-4674729));
        mainPanel.add(panel3, new GridConstraints(8, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Chọn chức năng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, panel3.getFont()), new Color(-14801221)));
        btnThemD = new JButton();
        Font btnThemDFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, btnThemD.getFont());
        if (btnThemDFont != null) btnThemD.setFont(btnThemDFont);
        btnThemD.setForeground(new Color(-16185079));
        btnThemD.setText("Thêm đĩa");
        panel3.add(btnThemD, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        btnXoaD = new JButton();
        Font btnXoaDFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, btnXoaD.getFont());
        if (btnXoaDFont != null) btnXoaD.setFont(btnXoaDFont);
        btnXoaD.setForeground(new Color(-16185079));
        btnXoaD.setText("Xóa đĩa");
        panel3.add(btnXoaD, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        final Spacer spacer6 = new Spacer();
        mainPanel.add(spacer6, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        mainPanel.add(spacer7, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        cbMaTieuDe = new JComboBox();
        mainPanel.add(cbMaTieuDe, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbTrangThai = new JComboBox();
        mainPanel.add(cbTrangThai, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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


}
