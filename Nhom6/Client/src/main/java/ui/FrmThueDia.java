package ui;

import com.google.gson.JsonIOException;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import dto.DiaDto;
import dto.KhachHangDto;
import dto.LoaiDiaDto;
import dto.PhiTreHenDto;
import dto.Post.PostChiTietHoaDon;
import dto.Post.PostDia;
import dto.Post.PostHoaDon;
import retrofit.RetrofitClientCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class FrmThueDia extends JPanel implements ActionListener, MouseListener {
    public JPanel mainPanel;
    private JTextField txtMaDia;
    private JLabel lblMaDia;
    private JPanel toplPanel;
    private JPanel toprPanel;
    private JPanel belowlPanel;
    private JPanel belowrPanel;
    private JButton btnNhap;
    private JTextField txtMaKHKT;
    private JLabel lblMaKH;
    private JTable table;
    private JScrollPane scrPanelL;
    private JButton btnCheck;
    private JTextField txtMaKH;
    private JTextField txtTenKH;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    private JButton btnThue;
    private JPanel panel;
    private JLabel lblSoLuong;
    private JLabel lblGiaThue;
    private JLabel lblTongTien;
    private JLabel lblPhiTreHen;
    private DefaultTableModel tableModel;
    private Retrofit retrofit;
    private DiaService diaService;
    private KhachHangService khachHangService;
    private LoaiDiaService loaiDiaService;
    private HoaDonService hoaDonService;
    private ChiTietHoaDonService chiTietHoaDonService;
    private PhiTreHenService phiTreHenService;
    private List<Long> maDia;
    private double tongPhiTre;
    private long maPhiTreHen;
    private double tienTra;


    public FrmThueDia() {
        add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(1366, 710));
        createTable();
        retrofit = RetrofitClientCreator.getClient();
        diaService = retrofit.create(DiaService.class);
        khachHangService = retrofit.create(KhachHangService.class);
        loaiDiaService = retrofit.create(LoaiDiaService.class);
        hoaDonService = retrofit.create(HoaDonService.class);
        chiTietHoaDonService = retrofit.create(ChiTietHoaDonService.class);
        phiTreHenService = retrofit.create(PhiTreHenService.class);
        maDia = new ArrayList<>();
        btnNhap.addActionListener(this);
        btnCheck.addActionListener(this);
        btnThue.addActionListener(this);
    }

    private void createTable() {
        String[] header = "Mã đĩa; Tên tiêu đề; Trạng thái ".split(";");
        tableModel = new DefaultTableModel(header, 0);
        table.setModel(tableModel);
        table.addMouseListener(this);
    }

    public void updatePhiTre() {
        tienTra = Double.valueOf(lblPhiTreHen.getText());
        if (tienTra == 0.0) {
            return;
        } else {
            try {
                phiTreHenService.getPhiTreHenByIdKH(Long.parseLong(txtMaKH.getText())).execute().body().getPhiTreHenDtos().forEach(x -> {
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

    public void setGiaThueSoLuongDia() {
        lblSoLuong.setText(String.valueOf(tableModel.getRowCount()));
        loaiDiaService.getLoaiDiaById(1).enqueue(new Callback<LoaiDiaDto>() {
            @Override
            public void onResponse(Call<LoaiDiaDto> call, Response<LoaiDiaDto> response) {
                lblGiaThue.setText(String.valueOf(response.body().getGiaThue()));
                lblTongTien.setText(String.valueOf(Double.valueOf(lblPhiTreHen.getText()) + Double.valueOf(lblSoLuong.getText().toString()) * response.body().getGiaThue()));
            }

            @Override
            public void onFailure(Call<LoaiDiaDto> call, Throwable t) {

            }
        });

    }

    public boolean kiemTraDiaNhap(String maDia) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).toString().equals(maDia)) {
                JOptionPane.showMessageDialog(null, "Trùng mã đĩa !", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                txtMaDia.selectAll();
                txtMaDia.requestFocus();
                return false;
            }
        }
        return true;
    }

    public void xoaRong() {
        tableModel.setRowCount(0);
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        txtMaKHKT.setText("");
        txtMaDia.setText("");
        lblTongTien.setText("0.0");
        lblGiaThue.setText("0.0");
        lblSoLuong.setText("0");
        lblPhiTreHen.setText("0.0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(btnNhap)) {
            if (kiemTraDiaNhap(txtMaDia.getText())) {
                try {
                    diaService.getDiaById(Integer.parseInt(txtMaDia.getText())).enqueue(new Callback<DiaDto>() {
                        @Override
                        public void onResponse(Call<DiaDto> call, Response<DiaDto> response) {
                            if (response.body() == null) {
                                JOptionPane.showMessageDialog(null, "Đĩa không sẵn sàng cho thuê hoặc không tồn tại Đĩa", "Thông báo !",
                                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                                txtMaDia.selectAll();
                                txtMaDia.requestFocus();
                            } else {
                                Vector vector = new Vector();

                                vector.add(response.body().getMaDia());
                                vector.add(response.body().getTenTieuDe());
                                vector.add(response.body().getTrangThai());
                                tableModel.addRow(vector);
                                maDia.add(Long.parseLong(txtMaDia.getText()));
                                txtMaDia.selectAll();
                                txtMaDia.requestFocus();
                                setGiaThueSoLuongDia();
                            }
                        }

                        @Override
                        public void onFailure(Call<DiaDto> call, Throwable t) {
                            JOptionPane.showMessageDialog(null, "Vui lòng nhập  !!!", "Thông báo !",
                                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                        }
                    });

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập ID đĩa cần thuê !!!", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                }
            }
        } else if (obj.equals(btnCheck)) {
            tongPhiTre = 0.0;
            try {
                khachHangService.getKhachHangById(Long.parseLong(txtMaKHKT.getText())).enqueue(new Callback<KhachHangDto>() {
                    @Override
                    public void onResponse(Call<KhachHangDto> call, Response<KhachHangDto> response) {
                        if (response.body() == null) {
                            JOptionPane.showMessageDialog(null, "Khách hàng không tồn tại !", "Thông báo !",
                                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                            txtMaKHKT.selectAll();
                            txtMaKHKT.requestFocus();
                        } else {
                            txtMaKH.setText(response.body().getMaKhachHang() + "");
                            txtTenKH.setText(response.body().getHoTen());
                            txtDiaChi.setText(response.body().getDiaChi());
                            txtSDT.setText(response.body().getSoDienThoai());
                            try {

                                phiTreHenService.getPhiTreHenByIdKH(Long.parseLong(txtMaKH.getText())).execute().body().getPhiTreHenDtos().forEach(x -> {
                                    if (response.body() == null) {
                                        return;
                                    } else {
                                        tongPhiTre += x.getTongPhiTreHen() - x.getTienDaTra();
                                        maPhiTreHen = x.getMaPhiTreHen();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (tongPhiTre != 0) {
                                String value = "0.0";
                                value = JOptionPane.showInputDialog(null, "Phí trễ hẹn là: " + tongPhiTre + "\nNhập số tiền khách muốn thanh toán !",
                                        "Thông báo phí trễ hẹn", JOptionPane.INFORMATION_MESSAGE);
                                if (value == null)
                                    lblPhiTreHen.setText("0.0");
                                else
                                    lblPhiTreHen.setText(value);
                                setGiaThueSoLuongDia();
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<KhachHangDto> call, Throwable t) {

                    }
                });

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập mã khách hàng là chữ số !", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
                txtMaKHKT.selectAll();
                txtMaKHKT.requestFocus();
            }
        } else if (obj.equals(btnThue)) {
            if (tableModel.getRowCount() == 0 || txtMaKHKT.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin bao gồm Đĩa thuê và Thông tin Khách Hàng!", "Thông báo !",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
            } else {
                updatePhiTre();
                addHoaDon();
            }
        }
    }

    public void addHoaDon() {
        LocalDate now = LocalDate.now();
        LocalDate returnDia = now.plusDays(7);
        try {
            hoaDonService.addHoaDon(new PostHoaDon(now.toString(), returnDia.toString(), Double.valueOf(lblTongTien.getText().toString()), Long.parseLong(txtMaKH.getText().toString()))).enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    JOptionPane.showMessageDialog(null, "Thuê Đĩa Thành Công !!!", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                    Long maHoaDon = response.body().longValue();
                    for (int i = 0; i < maDia.size(); i++) {
                        addChiTietHoaDon(maDia.get(i), maHoaDon);
                    }
                    xoaRong();
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    JOptionPane.showMessageDialog(null, "Thuê Đĩa!!!", "Thông báo !",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("image/yes.png"));
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thuê Đĩa thất bại !!!", "Thông báo !",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("image/warning.png"));
            e.printStackTrace();
        }
    }

    public void addChiTietHoaDon(Long maDia, Long maHoaDon) {
        try {
            chiTietHoaDonService.addChiTietHoaDon(new PostChiTietHoaDon(maDia, maHoaDon)).enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {

                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {

                }
            });
        } catch (Exception e) {

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
        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(5, 10, 5, 10), -1, -1));
        mainPanel.setBackground(new Color(-4474531));
        mainPanel.setForeground(new Color(-4474531));
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel.setBackground(new Color(-4474531));
        mainPanel.add(panel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        toplPanel = new JPanel();
        toplPanel.setLayout(new GridLayoutManager(1, 3, new Insets(5, 10, 5, 10), -1, -1));
        toplPanel.setBackground(new Color(-4474531));
        panel.add(toplPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        toplPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Nhập mã đĩa", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, toplPanel.getFont()), new Color(-15132997)));
        lblMaDia = new JLabel();
        lblMaDia.setText("Mã đĩa");
        toplPanel.add(lblMaDia, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaDia = new JTextField();
        toplPanel.add(txtMaDia, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(20, 20), new Dimension(-1, 40), null, 0, false));
        btnNhap = new JButton();
        btnNhap.setText("Nhập");
        toplPanel.add(btnNhap, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        toprPanel = new JPanel();
        toprPanel.setLayout(new GridLayoutManager(1, 3, new Insets(5, 10, 10, 5), -1, -1));
        toprPanel.setBackground(new Color(-4474531));
        panel.add(toprPanel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        toprPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Nhập mã khách hàng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, toprPanel.getFont()), new Color(-15132997)));
        lblMaKH = new JLabel();
        lblMaKH.setText("Mã khách hàng");
        toprPanel.add(lblMaKH, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaKHKT = new JTextField();
        toprPanel.add(txtMaKHKT, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 40), null, 0, false));
        btnCheck = new JButton();
        btnCheck.setText("Nhập");
        toprPanel.add(btnCheck, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 40), null, 0, false));
        belowlPanel = new JPanel();
        belowlPanel.setLayout(new GridLayoutManager(5, 3, new Insets(5, 10, 5, 10), -1, -1));
        belowlPanel.setBackground(new Color(-4474531));
        panel.add(belowlPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        belowlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Danh sách đĩa thuê", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, belowlPanel.getFont()), new Color(-15132997)));
        scrPanelL = new JScrollPane();
        belowlPanel.add(scrPanelL, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table = new JTable();
        table.setEnabled(true);
        scrPanelL.setViewportView(table);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16448251));
        label1.setText("Tổng số lượng :");
        belowlPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblSoLuong = new JLabel();
        Font lblSoLuongFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, lblSoLuong.getFont());
        if (lblSoLuongFont != null) lblSoLuong.setFont(lblSoLuongFont);
        lblSoLuong.setForeground(new Color(-65536));
        lblSoLuong.setText("0");
        belowlPanel.add(lblSoLuong, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-16448251));
        label2.setText("Giá thuê :");
        belowlPanel.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblGiaThue = new JLabel();
        lblGiaThue.setFocusable(false);
        Font lblGiaThueFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, lblGiaThue.getFont());
        if (lblGiaThueFont != null) lblGiaThue.setFont(lblGiaThueFont);
        lblGiaThue.setForeground(new Color(-65536));
        lblGiaThue.setText("0.0");
        belowlPanel.add(lblGiaThue, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-16448251));
        label3.setText("Tổng tiền thanh toán :");
        belowlPanel.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTongTien = new JLabel();
        lblTongTien.setAutoscrolls(false);
        Font lblTongTienFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, lblTongTien.getFont());
        if (lblTongTienFont != null) lblTongTien.setFont(lblTongTienFont);
        lblTongTien.setForeground(new Color(-65536));
        lblTongTien.setText("0.0");
        belowlPanel.add(lblTongTien, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-16185079));
        label4.setText("Phí trễ hẹn thanh toán");
        belowlPanel.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblPhiTreHen = new JLabel();
        Font lblPhiTreHenFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 14, lblPhiTreHen.getFont());
        if (lblPhiTreHenFont != null) lblPhiTreHen.setFont(lblPhiTreHenFont);
        lblPhiTreHen.setForeground(new Color(-65536));
        lblPhiTreHen.setText("0.0");
        belowlPanel.add(lblPhiTreHen, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        belowrPanel = new JPanel();
        belowrPanel.setLayout(new GridLayoutManager(4, 2, new Insets(5, 10, 5, 10), -1, -1));
        belowrPanel.setBackground(new Color(-4474531));
        panel.add(belowrPanel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        belowrPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Thông tin khách hàng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Wingdings 3", Font.BOLD, 12, belowrPanel.getFont()), new Color(-15132997)));
        final JLabel label5 = new JLabel();
        label5.setText("Mã khách hàng");
        belowrPanel.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMaKH = new JTextField();
        txtMaKH.setEditable(false);
        belowrPanel.add(txtMaKH, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Họ tên");
        belowrPanel.add(label6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTenKH = new JTextField();
        txtTenKH.setEditable(false);
        belowrPanel.add(txtTenKH, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Địa chỉ");
        belowrPanel.add(label7, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDiaChi = new JTextField();
        txtDiaChi.setEditable(false);
        belowrPanel.add(txtDiaChi, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        txtSDT = new JTextField();
        txtSDT.setEditable(false);
        belowrPanel.add(txtSDT, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Số điện thoại");
        belowrPanel.add(label8, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnThue = new JButton();
        btnThue.setBackground(new Color(-13849378));
        Font btnThueFont = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 20, btnThue.getFont());
        if (btnThueFont != null) btnThue.setFont(btnThueFont);
        btnThue.setForeground(new Color(-1));
        btnThue.setLabel("THUÊ ĐĨA");
        btnThue.setMargin(new Insets(5, 10, 5, 10));
        btnThue.setMultiClickThreshhold(0L);
        btnThue.setText("THUÊ ĐĨA");
        panel.add(btnThue, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-6643315));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$("Wingdings 3", Font.BOLD, 24, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setForeground(new Color(-14801221));
        label9.setText("QUẢN LÝ THUÊ ĐĨA");
        panel1.add(label9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
