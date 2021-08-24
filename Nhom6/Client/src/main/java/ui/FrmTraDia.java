package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import dto.CTPDTByTieuDeId;
import dto.DiaDto;
import dto.KhachHangDto;
import dto.LoaiDiaDto;
import dto.Post.PostChiTietTreHen;
import dto.Post.PostPhiTreHen;
import retrofit.RetrofitClientCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.*;

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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class FrmTraDia extends JPanel implements ActionListener, MouseListener {
    private JButton btnTraDia;
    private JButton btnXoaRong;
    private JButton btnHuy;
    private JTextField txtMaDia;
    private JButton btnNhap;
    private JTextField txtMaKH;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    private JTable table;
    private JScrollPane scrPanel;
    private JPanel mainPanel;
    private JTextField txtTenKH;
    private JTextField txtTienDaTra;
    private JLabel lblTongPhiTreHen;
    private DefaultTableModel tableModel;
    private Retrofit retrofit;
    private DiaService diaService;
    private KhachHangService khachHangService;
    private LoaiDiaService loaiDiaService;
    private PhiTreHenService phiTreHenService;
    private ChiTietPhiTreHen chiTietPhiTreHen;
    private ChiTietPhieuDatTruoc chiTietPhieuDatTruoc;
    private double phiTreHen;
    private String role;
    private List<DiaDto> diaDtos;
    private boolean maKH;


    public FrmTraDia(String role) {
        add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(1366, 710));
        createTable();
        diaDtos = new ArrayList<>();
        this.role = role;
        phiTreHen = 0.0;
        maKH = true;
        scrPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        retrofit = RetrofitClientCreator.getClient();
        diaService = retrofit.create(DiaService.class);
        khachHangService = retrofit.create(KhachHangService.class);
        loaiDiaService = retrofit.create(LoaiDiaService.class);
        phiTreHenService = retrofit.create(PhiTreHenService.class);
        chiTietPhiTreHen = retrofit.create(ChiTietPhiTreHen.class);
        chiTietPhieuDatTruoc = retrofit.create(ChiTietPhieuDatTruoc.class);
        btnNhap.addActionListener(this);
        btnTraDia.addActionListener(this);
        btnXoaRong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtMaDia.setText("");
                txtDiaChi.setText("");
                txtTienDaTra.setText("");
                txtSDT.setText("");
                txtTenKH.setText("");
                txtMaKH.setText("");
                tableModel = new DefaultTableModel();
                table.setModel(tableModel);
            }
        });
    }

    private void createTable() {
        String[] header = "Mã đĩa; Tên tiêu đề; Mã tiêu đề; Ngày thuê; Ngày phái trả; Ngày trả; Số ngày trễ; Phí trễ hẹn; Mã khách hàng".split(";");
        tableModel = new DefaultTableModel(header, 0);
        table.setModel(tableModel);
        table.addMouseListener(this);
    }

    public LocalDate converStringToLocalDate(String s) {
        LocalDate localDate = LocalDate.parse(s);
        return localDate;
    }

    public void getPhiTreHen() {
        try {
            loaiDiaService.getLoaiDiaById(1).enqueue(new Callback<LoaiDiaDto>() {
                @Override
                public void onResponse(Call<LoaiDiaDto> call, Response<LoaiDiaDto> response) {
                    phiTreHen = response.body().getPhiTre();
                }

                @Override
                public void onFailure(Call<LoaiDiaDto> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDiaDtos(DiaDto dto) {
        chiTietPhieuDatTruoc.getAllChitietPhieuDatTruocByTieuDeId(dto.getMaTieuDe()).enqueue(new Callback<List<CTPDTByTieuDeId>>() {
            @Override
            public void onResponse(Call<List<CTPDTByTieuDeId>> call, Response<List<CTPDTByTieuDeId>> response) {
                if (response.body().isEmpty()) {
                    return;
                } else {
                    diaDtos.add(dto);
                }
            }

            @Override
            public void onFailure(Call<List<CTPDTByTieuDeId>> call, Throwable t) {
            }
        });
    }

    public boolean kiemTraDiaNhap(String maDia, String maKH) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).toString().equals(maDia)) {
                JOptionPane.showMessageDialog(null, "Trùng mã đĩa !", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                txtMaDia.selectAll();
                txtMaDia.requestFocus();
                return false;
            }
            if (!tableModel.getValueAt(i, 8).toString().equals(maKH)) {
                JOptionPane.showMessageDialog(null, "Đĩa không thuộc khách hàng đang trả !", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                txtMaDia.selectAll();
                txtMaDia.requestFocus();
                return false;
            }
        }
        return true;
    }

    public void getDiaTraByMaDia(int id) {
        getPhiTreHen();
        try {
            diaService.getDiaTra(id).enqueue(new Callback<DiaDto>() {
                @Override
                public void onResponse(Call<DiaDto> call, Response<DiaDto> response) {
                    if (response.body().getMaDia() == 0) {
                        JOptionPane.showMessageDialog(null, "Đĩa không phải đang Cho Thuê  !", "Thông báo !",
                                JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                        txtMaDia.selectAll();
                        txtMaDia.requestFocus();
                    } else {

                        if (kiemTraDiaNhap(response.body().getMaDia() + "", response.body().getMaKhachHang() + "") == true) {
                            saveDiaDtos(response.body());
                            Vector vector = new Vector();

                            vector.add(response.body().getMaDia());
                            vector.add(response.body().getTenTieuDe());
                            vector.add(response.body().getMaTieuDe());
                            vector.add(response.body().getNgayThue());
                            vector.add(response.body().getNgayPhaiTra());

                            vector.add(LocalDate.now());

//                            ChronoUnit.DAYS.between(converStringToLocalDate(response.body().getNgayPhaiTra()), LocalDate.now());
                            if (converStringToLocalDate(response.body().getNgayPhaiTra()).compareTo(LocalDate.now()) <= 0) {
                                vector.add(ChronoUnit.DAYS.between(converStringToLocalDate(response.body().getNgayPhaiTra()), LocalDate.now()));
                                vector.add(Math.round((phiTreHen * Double.valueOf(ChronoUnit.DAYS.between(converStringToLocalDate(response.body().getNgayPhaiTra()), LocalDate.now()))) * 100.0) / 100.0);
                            } else {
                                vector.add(0);
                                vector.add(0);
                            }
                            vector.add(response.body().getMaKhachHang());
                            khachHangService.getKhachHangById(response.body().getMaKhachHang()).enqueue(new Callback<KhachHangDto>() {
                                @Override
                                public void onResponse(Call<KhachHangDto> call, Response<KhachHangDto> response) {
                                    txtMaDia.selectAll();
                                    txtMaDia.requestFocus();

                                    txtMaKH.setText(String.valueOf(response.body().getMaKhachHang()));
                                    txtTenKH.setText(response.body().getHoTen());
                                    txtSDT.setText(response.body().getDiaChi());
                                    txtDiaChi.setText(response.body().getSoDienThoai());
                                }


                                @Override
                                public void onFailure(Call<KhachHangDto> call, Throwable t) {

                                }
                            });
                            tableModel.addRow(vector);
                            tongPhiTreHen();
                            txtMaDia.selectAll();
                            txtMaDia.requestFocus();
                        }


                    }
                }

                @Override
                public void onFailure(Call<DiaDto> call, Throwable t) {
                    JOptionPane.showMessageDialog(null, "Thông tin đĩa sai !", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thông tin đĩa sai !", "Thông báo !",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
        }
    }

    public void tongPhiTreHen() {
        Double tong = 0.0;
        for (int i = 0; i < table.getRowCount(); i++) {
            tong = tong + Double.parseDouble(tableModel.getValueAt(i, 7).toString());
        }
        lblTongPhiTreHen.setText(String.valueOf(tong));
        if (tong != 0)
            txtTienDaTra.setEditable(true);
    }

    public void addChiTietTreHen(Long maPhiTreHen) {
        try {
            for (int i = 0; i < table.getRowCount(); i++) {
                chiTietPhiTreHen.addPhiTreHen(new PostChiTietTreHen(Long.parseLong(tableModel.getValueAt(i, 0).toString()), maPhiTreHen, tableModel.getValueAt(i, 5).toString(), Double.parseDouble(tableModel.getValueAt(i, 7).toString()), Integer.parseInt(tableModel.getValueAt(i, 6).toString()))).enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {

                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {

                    }
                });
            }
        } catch (Exception e) {

        }
        xoaRong();
    }

    public void xoaRong() {
        txtTienDaTra.setText("0.0");
        tableModel.setRowCount(0);
        txtMaDia.setText("");
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        lblTongPhiTreHen.setText("0.0");
    }

    public void xacNhanTraDia() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            diaService.updateTrangThaiDia(Long.parseLong(tableModel.getValueAt(i, 0).toString()), "sanSangChoThue").enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {

                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {

                }
            });
        }
        try {
            if (txtTienDaTra.getText().equals("")) {
                phiTreHenService.addPhiTreHen(new PostPhiTreHen(Long.parseLong(txtMaKH.getText()), Double.parseDouble(lblTongPhiTreHen.getText()), 0)).enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        JOptionPane.showMessageDialog(null, "Trả đĩa thành công !", "Thông báo !",
                                JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                        Long maPhiTreHen = response.body().longValue();
                        addChiTietTreHen(maPhiTreHen);
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {

                    }
                });
            } else {
                double tongNo = Double.parseDouble(lblTongPhiTreHen.getText());
                phiTreHenService.addPhiTreHen(new PostPhiTreHen(Long.parseLong(txtMaKH.getText()), tongNo, Double.parseDouble(txtTienDaTra.getText()))).enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        JOptionPane.showMessageDialog(null, "Trả đĩa thành công !", "Thông báo !",
                                JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                        Long maPhiTreHen = response.body().longValue();
                        addChiTietTreHen(maPhiTreHen);
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(btnNhap)) {
            getDiaTraByMaDia(Integer.parseInt(txtMaDia.getText()));
        } else if (obj.equals(btnTraDia)) {
            xacNhanTraDia();
            if (diaDtos.isEmpty()) {
                return;
            } else {
                FrmXacNhanDatTruoc frmXacNhanDatTruoc = new FrmXacNhanDatTruoc(this.role, diaDtos);
                FrmTONG.contentPane.removeAll();
                FrmTONG.contentPane.add(frmXacNhanDatTruoc);
                FrmTONG.contentPane.revalidate();
                FrmTONG.contentPane.repaint();
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
        mainPanel.setLayout(new GridLayoutManager(9, 9, new Insets(5, 10, 5, 10), -1, -1));
        mainPanel.setBackground(new Color(-4674729));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-4674729));
        mainPanel.add(panel1, new GridConstraints(8, 0, 1, 9, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnTraDia = new JButton();
        btnTraDia.setText("Xác nhận trả");
        panel1.add(btnTraDia, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        btnXoaRong = new JButton();
        btnXoaRong.setText("Làm mới");
        panel1.add(btnXoaRong, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        btnHuy = new JButton();
        btnHuy.setText("Hủy");
        panel1.add(btnHuy, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setBackground(new Color(-8683131));
        mainPanel.add(panel2, new GridConstraints(0, 0, 1, 9, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-6643315));
        Font label1Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 24, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-14801221));
        label1.setHorizontalAlignment(0);
        label1.setText("TRẢ ĐĨA");
        label1.setVerticalAlignment(0);
        panel2.add(label1, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-4674729));
        mainPanel.add(panel3, new GridConstraints(2, 8, 6, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Thông tin khách hàng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-14801221)));
        final JLabel label2 = new JLabel();
        label2.setText("Mã khách hàng");
        panel3.add(label2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaKH = new JTextField();
        txtMaKH.setEditable(false);
        panel3.add(txtMaKH, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Tên khách hàng");
        panel3.add(label3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setForeground(new Color(-14801221));
        label4.setText("THÔNG TIN KHÁCH HÀNG TRẢ ĐĨA");
        panel3.add(label4, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTenKH = new JTextField();
        txtTenKH.setEditable(false);
        panel3.add(txtTenKH, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(250, 30), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Địa chỉ");
        panel3.add(label5, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDiaChi = new JTextField();
        txtDiaChi.setEditable(false);
        panel3.add(txtDiaChi, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(250, 30), null, 0, false));
        txtSDT = new JTextField();
        txtSDT.setEditable(false);
        panel3.add(txtSDT, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(250, 30), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Số điện thoại");
        panel3.add(label6, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-4674729));
        Font panel4Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, panel4.getFont());
        if (panel4Font != null) panel4.setFont(panel4Font);
        mainPanel.add(panel4, new GridConstraints(2, 0, 6, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14801221)), "Danh sách đĩa trả", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, panel4.getFont()), new Color(-14801221)));
        scrPanel = new JScrollPane();
        panel4.add(scrPanel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table = new JTable();
        scrPanel.setViewportView(table);
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setForeground(new Color(-16185079));
        label7.setText("Tổng phí trễ hẹn");
        panel4.add(label7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTongPhiTreHen = new JLabel();
        Font lblTongPhiTreHenFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, lblTongPhiTreHen.getFont());
        if (lblTongPhiTreHenFont != null) lblTongPhiTreHen.setFont(lblTongPhiTreHenFont);
        lblTongPhiTreHen.setForeground(new Color(-65536));
        lblTongPhiTreHen.setText("0.0");
        panel4.add(lblTongPhiTreHen, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setForeground(new Color(-16185079));
        label8.setText("Số tiền khách hàng muốn trả");
        panel4.add(label8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTienDaTra = new JTextField();
        txtTienDaTra.setEditable(false);
        Font txtTienDaTraFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, txtTienDaTra.getFont());
        if (txtTienDaTraFont != null) txtTienDaTra.setFont(txtTienDaTraFont);
        txtTienDaTra.setForeground(new Color(-65536));
        panel4.add(txtTienDaTra, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-4674729));
        mainPanel.add(panel5, new GridConstraints(1, 0, 1, 9, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Nhập mã đĩa trả");
        panel5.add(label9, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaDia = new JTextField();
        panel5.add(txtMaDia, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 40), null, 0, false));
        btnNhap = new JButton();
        btnNhap.setText("Nhập");
        panel5.add(btnNhap, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel5.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel5.add(spacer4, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel5.add(spacer5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
