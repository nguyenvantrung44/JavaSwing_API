package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class FrmTONG extends JFrame implements ActionListener {
    public static final long serialVersionUID = 1L;
    public static JPanel contentPane;
    public static JMenuBar menuBar;
    public static JMenu mnXuLy, mnDanhMuc, mnThongKe, mnHeThong, mnCaiDat;
    public static JMenuItem mntmDia, mntmDatTruoc, mntmBaoCaoTieuDe,
            mntmBaoCaoKhachHang, mntmTieuDe, mntmThueDia, mntmTraDia, mntmDoiMatKhau, mntmDangXuat,
            mntmThoat,
            mntmKhachHang,
            mntmPhiTreHen, mntmCaiDatThue, mntmTimPhiTreHen;
    private static FrmTONG frmTONG;
    private String role;
    private JLabel lblNewLabel;

    public FrmTONG(String role) {
        setIconImage(Toolkit.getDefaultToolkit().getImage("image/gioithieu.jpg"));
        setTitle("Quản Lý Cửa Hàng");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setResizable(false);

        MainGUI();
        this.role = role;
        setAuth();
    }

    public static void main(String[] args) {
        frmTONG = new FrmTONG("admin");
        frmTONG.pack();
        frmTONG.setLocationRelativeTo(null);
        frmTONG.setVisible(true);
    }

    public void MainGUI() {

        contentPane = new JPanel();
        setContentPane(contentPane);
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        mnHeThong = new JMenu("Hệ thống");
        mnHeThong.setIcon(new ImageIcon("image/system.png"));
        mnHeThong.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(mnHeThong);

        mntmDoiMatKhau = new JMenuItem("Đổi mật khẩu");
        mntmDoiMatKhau.setIcon(new ImageIcon("image/change-password-icon.png"));
        mntmDoiMatKhau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_MASK));
        mntmDoiMatKhau.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnHeThong.add(mntmDoiMatKhau);

        mntmDangXuat = new JMenuItem("Đăng xuất");
        mntmDangXuat.setIcon(new ImageIcon("image/gnome_logout.png"));
        mntmDangXuat.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.ALT_MASK));
        mntmDangXuat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnHeThong.add(mntmDangXuat);

        mntmThoat = new JMenuItem("Thoát");
        mntmThoat.setIcon(new ImageIcon("image/exit2.png"));
        mntmThoat.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        mntmThoat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnHeThong.add(mntmThoat);

        mnDanhMuc = new JMenu("Danh Mục");
        mnDanhMuc.setIcon(new ImageIcon("image/danhmuc.png"));
        mnDanhMuc.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(mnDanhMuc);

        mntmDia = new JMenuItem("Đĩa");
        mntmDia.setIcon(new ImageIcon("image/disk.png"));
        mntmDia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_MASK));
        mntmDia.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnDanhMuc.add(mntmDia);

        mntmTieuDe = new JMenuItem("Tiêu đề");
        mntmTieuDe.setIcon(new ImageIcon("image/title.png"));
        mntmTieuDe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.ALT_MASK));
        mntmTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnDanhMuc.add(mntmTieuDe);

        mntmKhachHang = new JMenuItem("Khách hàng");
        mntmKhachHang.setIcon(new ImageIcon("image/Users-icon.png"));
        mntmKhachHang.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.ALT_MASK));
        mntmKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnDanhMuc.add(mntmKhachHang);

        mntmPhiTreHen = new JMenuItem("Phí trễ hẹn");
        mntmPhiTreHen.setIcon(new ImageIcon("image/fee.png"));
        mntmPhiTreHen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, InputEvent.ALT_MASK));
        mntmPhiTreHen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnDanhMuc.add(mntmPhiTreHen);


        mnXuLy = new JMenu("Xử lý");
        mnXuLy.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnXuLy.setIcon(new ImageIcon("image/xuli.png"));
        menuBar.add(mnXuLy);

        mntmThueDia = new JMenuItem("Thuê đĩa ");
        mntmThueDia.setIcon(new ImageIcon("image/Actions-contact-new-icon.png"));
        mntmThueDia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
        mntmThueDia.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnXuLy.add(mntmThueDia);

        mntmTraDia = new JMenuItem("Trả đĩa ");
        mntmTraDia.setIcon(new ImageIcon("image/tradia.png"));
        mntmTraDia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
        mntmTraDia.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnXuLy.add(mntmTraDia);

        mntmDatTruoc = new JMenuItem("Đặt trước");
        mntmDatTruoc.setIcon(new ImageIcon("image/dattruoc.png"));
        mntmDatTruoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_MASK));
        mntmDatTruoc.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnXuLy.add(mntmDatTruoc);

        mntmTimPhiTreHen = new JMenuItem("Tìm kiếm phí trễ hẹn ");
        mntmTimPhiTreHen.setIcon(new ImageIcon("image/tradia.png"));
        mntmTimPhiTreHen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, InputEvent.CTRL_MASK));
        mntmTimPhiTreHen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnXuLy.add(mntmTimPhiTreHen);


        mnThongKe = new JMenu("Thống Kê/Báo cáo");
        mnThongKe.setIcon(new ImageIcon("image/SEO-icon.png"));
        mnThongKe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(mnThongKe);

        mntmBaoCaoKhachHang = new JMenuItem("Báo cáo khách hàng");
        mntmBaoCaoKhachHang.setIcon(new ImageIcon("image/reportkh.png"));
        mntmBaoCaoKhachHang.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.ALT_MASK));
        mntmBaoCaoKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnThongKe.add(mntmBaoCaoKhachHang);

        mntmBaoCaoTieuDe = new JMenuItem("Báo cáo tiêu đề");
        mntmBaoCaoTieuDe.setIcon(new ImageIcon("image/reporttieude.png"));
        mntmBaoCaoTieuDe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.ALT_MASK));
        mntmBaoCaoTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnThongKe.add(mntmBaoCaoTieuDe);

        mnCaiDat = new JMenu("Cài đặt thuê đĩa");
        mnCaiDat.setIcon(new ImageIcon("image/settingg.png"));
        mnCaiDat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(mnCaiDat);

        mntmCaiDatThue = new JMenuItem("Giá và Thời gian");
        mntmCaiDatThue.setIcon(new ImageIcon("image/setting.png"));
        mntmCaiDatThue.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.ALT_MASK));
        mntmCaiDatThue.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mnCaiDat.add(mntmCaiDatThue);


        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        ImageIcon img = new ImageIcon("image/background.jpg");
        lblNewLabel = new JLabel("", img, JLabel.CENTER);
        lblNewLabel.setBounds(0, 0, 1366, 700);
        contentPane.add(lblNewLabel, BorderLayout.CENTER);

        mntmDia.addActionListener(this);
        mntmDatTruoc.addActionListener(this);
        mntmBaoCaoKhachHang.addActionListener(this);
        mntmDia.addActionListener(this);
        mntmKhachHang.addActionListener(this);
        mntmPhiTreHen.addActionListener(this);
        mntmDangXuat.addActionListener(this);
        mntmThoat.addActionListener(this);
        mntmTieuDe.addActionListener(this);
        mntmDoiMatKhau.addActionListener(this);
        mntmThueDia.addActionListener(this);
        mntmTraDia.addActionListener(this);
        mntmBaoCaoTieuDe.addActionListener(this);
        mntmTimPhiTreHen.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj.equals(mntmThoat)) {
            int diaRS = JOptionPane.YES_OPTION;
            int diaLogMess = JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không ?", "Warning", diaRS);
            if (diaLogMess == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else
                return;
        } else if (obj.equals(mntmThueDia)) {
            FrmThueDia FrmThueDia = new FrmThueDia();
            contentPane.removeAll();
            contentPane.add(FrmThueDia);
            contentPane.revalidate();
            contentPane.repaint();
        } else if (obj.equals(mntmTieuDe)) {
            FrmTieuDe frmTieuDe = new FrmTieuDe();
            contentPane.removeAll();
            contentPane.add(frmTieuDe);
            contentPane.revalidate();
            contentPane.repaint();
        } else if (obj.equals(mntmDia)) {
            FrmDia frmDia = new FrmDia();
            contentPane.removeAll();
            contentPane.add(frmDia);
            contentPane.revalidate();
            contentPane.repaint();
        } else if (obj.equals(mntmKhachHang)) {
            FrmKhachHang frmKhachHang = new FrmKhachHang(this.role);
            contentPane.removeAll();
            contentPane.add(frmKhachHang);
            contentPane.revalidate();
            contentPane.repaint();
        } else if (obj.equals(mntmTraDia)) {
            FrmTraDia frmTraDia = new FrmTraDia(this.role);
            contentPane.removeAll();
            contentPane.add(frmTraDia);
            contentPane.revalidate();
            contentPane.repaint();
        } else if (obj.equals(mntmDatTruoc)) {
            FrmDatTruoc frmDatTruoc = new FrmDatTruoc();
            contentPane.removeAll();
            contentPane.add(frmDatTruoc);
            contentPane.revalidate();
            contentPane.repaint();
        } else if (obj.equals(mntmPhiTreHen)) {
            FrmPhiTreHen treHen = new FrmPhiTreHen(this.role);
            contentPane.removeAll();
            contentPane.add(treHen);
            contentPane.revalidate();
            contentPane.repaint();
        } else if (obj.equals(mntmDangXuat)) {
            FrmDangNhap frmDangNhap = new FrmDangNhap();
            frmDangNhap.setVisible(true);
            this.dispose();
        }
    }

    public void setAuth() {
        mntmDia.setVisible(false);
        mntmDatTruoc.setVisible(false);
        mntmBaoCaoTieuDe.setVisible(false);
        mntmBaoCaoKhachHang.setVisible(false);
        mntmTieuDe.setVisible(false);
        mntmThueDia.setVisible(false);
        mntmTraDia.setVisible(false);
        mntmDoiMatKhau.setVisible(false);
        mntmDangXuat.setVisible(false);
        mntmThoat.setVisible(false);
        mntmKhachHang.setVisible(false);
        mntmPhiTreHen.setVisible(false);
        mntmCaiDatThue.setVisible(false);
        mntmTimPhiTreHen.setVisible(false);

        if (role.equalsIgnoreCase("nv")) {
            mntmDangXuat.setVisible(true);
            mntmKhachHang.setVisible(true);
            mntmThueDia.setVisible(true);
            mntmTraDia.setVisible(true);
            mntmDatTruoc.setVisible(true);
            mntmPhiTreHen.setVisible(true);

        }

        if (role.equalsIgnoreCase("am")){
            mntmDia.setVisible(true);
            mntmTieuDe.setVisible(true);
            mntmBaoCaoTieuDe.setVisible(true);
            mntmBaoCaoKhachHang.setVisible(true);
            mntmCaiDatThue.setVisible(true);
            mntmKhachHang.setVisible(true);
            mntmDangXuat.setVisible(true);
            mntmPhiTreHen.setVisible(true);

        }
    }

    //mntmTraDia
    //    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                FrmTieuDe frmTieuDe = new FrmTieuDe();
//                frmTieuDe.setVisible(true);
//            }
//        });
//    }
}
