package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import dto.DiaDto;
import dto.KhachHangDto;
import retrofit.RetrofitClientCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ChiTietPhieuDatTruoc;
import service.DiaService;
import service.KhachHangService;
import status.TrangThaiDia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class FrmXacNhanDatTruoc extends JPanel implements ActionListener {
    private JButton btnXacNhan;
    private JTextField txtMaKH;
    private JTextField txtTenKH;
    private JTextField txtSDT;
    private JTextField txtTenTD;
    private JButton btnHuy;
    private JTable tableDT;
    private JTable tableDia;
    private JTextField txtMaPDT;
    private JScrollPane scrPanelDia;
    private JScrollPane scrPanelDT;
    private JPanel mainPanel;
    private JTextField txtMaDia;
    private DefaultTableModel tableModelDia, tableModelDT;
    private String role;
    private List<DiaDto> diaDtoList;
    private ChiTietPhieuDatTruoc chiTietPhieuDatTruoc;
    private KhachHangService khachHangService;
    private DiaService diaService;
    private int i = 0;

    public FrmXacNhanDatTruoc(String role, List<DiaDto> diaDtoList) {
        add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(1366, 710));
        this.setDebugGraphicsOptions(JFrame.HIDE_ON_CLOSE);
        createTable();
        scrPanelDia.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPanelDia.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrPanelDT.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPanelDT.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.role = role;
        this.diaDtoList = diaDtoList;
        chiTietPhieuDatTruoc = RetrofitClientCreator.getClient().create(ChiTietPhieuDatTruoc.class);
        khachHangService = RetrofitClientCreator.getClient().create(KhachHangService.class);
        diaService = RetrofitClientCreator.getClient().create(DiaService.class);
        getAllDiaDtos();
        getAllChiTietDatTruoc();

        btnXacNhan.addActionListener(this);
        btnHuy.addActionListener(this);

    }

    private void createTable() {
        String[] header = "Mã đĩa; Mã tiêu đề; Tên tiêu đề ".split(";");
        tableModelDia = new DefaultTableModel(header, 0);
        tableDia.setModel(tableModelDia);

        String[] headerdt = "Mã phiếu đặt trước; Mã tiêu đề; Tên tiêu đề; Mã khách hàng; Ngày đặt; Trạng thái".split(";");
        tableModelDT = new DefaultTableModel(headerdt, 0);
        tableDT.setModel(tableModelDT);
    }

    public void setThongTinPhieuDatTruoc() {
        khachHangService.getKhachHangById(Long.parseLong(tableModelDT.getValueAt(0, 3) + "")).enqueue(new Callback<KhachHangDto>() {
            @Override
            public void onResponse(Call<KhachHangDto> call, Response<KhachHangDto> response) {
                txtMaKH.setText(response.body().getMaKhachHang() + "");
                txtTenKH.setText(response.body().getHoTen());
                txtSDT.setText(response.body().getSoDienThoai());
                txtTenTD.setText(tableModelDT.getValueAt(0, 2) + "");
                txtMaDia.setText(tableModelDia.getValueAt(i, 0) + "");
                txtMaPDT.setText(tableModelDT.getValueAt(0, 0) + "");
            }

            @Override
            public void onFailure(Call<KhachHangDto> call, Throwable t) {

            }
        });


    }

    public void xoaTieuDeHet(long maTieuDe) {
        for (DiaDto dto : diaDtoList) {
            if (dto.getMaTieuDe() == maTieuDe) {
                diaService.updateTrangThaiDia(dto.getMaDia(), "sanSangChoThue").enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {

                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {

                    }
                });
                diaDtoList.remove(dto);
            }
        }
        kiemTraDiaGan();
    }

    public void xoaDiaXacNhan(long maDia) {
        for (DiaDto dto : diaDtoList) {
            if (dto.getMaDia() == maDia) {
                diaDtoList.remove(dto);
            }
        }
        kiemTraDiaGan();
    }

    public void kiemTraDiaGan() {
        if (diaDtoList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Đĩa đã gán hết !", "Thông báo !",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
            FrmTONG.contentPane.removeAll();
            FrmTONG.contentPane.add(new FrmTraDia(role));
            FrmTONG.contentPane.revalidate();
            FrmTONG.contentPane.repaint();
        } else {
            getAllDiaDtos();
            getAllChiTietDatTruoc();
            setThongTinPhieuDatTruoc();
        }
    }

    public void getAllDiaDtos() {
        tableModelDia.getDataVector().removeAllElements();
        for (DiaDto diaDto : diaDtoList) {
            Vector<String> vector = new Vector<>();
            vector.add(diaDto.getMaDia() + "");
            vector.add(diaDto.getMaTieuDe() + "");
            vector.add(diaDto.getTenTieuDe());
            tableModelDia.addRow(vector);
        }
    }

    public void getAllChiTietDatTruoc() {
        tableModelDT.getDataVector().removeAllElements();
        try {
            if (chiTietPhieuDatTruoc.getAllChitietPhieuDatTruocByTieuDeId(Long.parseLong(tableModelDia.getValueAt(i, 1).toString())).execute().body().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không còn khách hàng đặt tiêu đề này !", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                xoaTieuDeHet(Long.parseLong(tableModelDia.getValueAt(i, 1) + ""));

            } else {
                chiTietPhieuDatTruoc.getAllChitietPhieuDatTruocByTieuDeId(Long.parseLong(tableModelDia.getValueAt(i, 1).toString())).execute().body().forEach(x -> {
                    Vector<String> vector = new Vector<>();
                    vector.add(x.getMaPhieuDatTruoc() + "");
                    vector.add(x.getMaTieuDe() + "");
                    vector.add(x.getTenTieuDe());
                    vector.add(x.getMaKhachHang() + "");
                    vector.add(x.getNgayDat());
                    vector.add(x.getTrangThaiDatTruoc());
                    tableModelDT.addRow(vector);

                });
                setThongTinPhieuDatTruoc();
            }
        } catch (IOException e) {

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(btnXacNhan)) {
            chiTietPhieuDatTruoc.thayDoiTrangThai(Long.parseLong(txtMaPDT.getText() + ""), Long.parseLong(tableModelDT.getValueAt(0, 1) + ""), Long.parseLong(txtMaDia.getText()), "xacNhan").enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    JOptionPane.showMessageDialog(null, "Xác nhận thành công !", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));

                    diaService.updateTrangThaiDia(Long.parseLong(txtMaDia.getText()), "daGan").enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {

                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable t) {

                        }
                    });
                    xoaDiaXacNhan(Long.parseLong(txtMaDia.getText()));

                }


                @Override
                public void onFailure(Call<Long> call, Throwable t) {

                }
            });

        } else if (obj.equals(btnHuy)) {
            chiTietPhieuDatTruoc.thayDoiTrangThai(Long.parseLong(txtMaPDT.getText() + ""), Long.parseLong(tableModelDT.getValueAt(0, 1) + ""), Long.parseLong(txtMaDia.getText()), "huy").enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    JOptionPane.showMessageDialog(null, "Hủy thành công !", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                    getAllChiTietDatTruoc();
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {

                }
            });
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
        mainPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(5, 10, 5, 10), -1, -1));
        panel1.setBackground(new Color(-6643315));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-14801221));
        label1.setText("XÁC NHẬN ĐẶT TRƯỚC");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(5, 10, 5, 10), -1, -1));
        mainPanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrPanelDia = new JScrollPane();
        panel2.add(scrPanelDia, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableDia = new JTable();
        tableDia.setEnabled(false);
        scrPanelDia.setViewportView(tableDia);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(5, 10, 5, 10), -1, -1));
        mainPanel.add(panel3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(8, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Mã khách hàng");
        panel4.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaKH = new JTextField();
        txtMaKH.setEditable(false);
        panel4.add(txtMaKH, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Tên khách hàng");
        panel4.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTenKH = new JTextField();
        txtTenKH.setEditable(false);
        panel4.add(txtTenKH, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Số điện thoại");
        panel4.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtSDT = new JTextField();
        txtSDT.setEditable(false);
        panel4.add(txtSDT, new GridConstraints(3, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Tên tiêu đề");
        panel4.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTenTD = new JTextField();
        txtTenTD.setEditable(false);
        panel4.add(txtTenTD, new GridConstraints(4, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btnHuy = new JButton();
        btnHuy.setText("Hủy");
        panel4.add(btnHuy, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Phiếu Đặt Trước");
        panel4.add(label6, new GridConstraints(0, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Mã phiếu đặt trước");
        panel4.add(label7, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaPDT = new JTextField();
        txtMaPDT.setEditable(false);
        panel4.add(txtMaPDT, new GridConstraints(6, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btnXacNhan = new JButton();
        btnXacNhan.setText("Xác Nhận");
        panel4.add(btnXacNhan, new GridConstraints(7, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Mã đĩa");
        panel4.add(label8, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaDia = new JTextField();
        panel4.add(txtMaDia, new GridConstraints(5, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(5, 10, 5, 10), -1, -1));
        mainPanel.add(panel5, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrPanelDT = new JScrollPane();
        panel5.add(scrPanelDT, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableDT = new JTable();
        tableDT.setEnabled(false);
        scrPanelDT.setViewportView(tableDT);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
