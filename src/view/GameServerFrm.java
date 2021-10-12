package view;


import Controller.NguoiChoiDAO;
import Controller.PhongDAO;
import Controller.TinNhanDAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;
import model.Phong;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public class GameServerFrm extends javax.swing.JFrame {

    private JButton[][] button;
    private Phong phong;
    private boolean flat;
    private boolean winner;
    private int[][] matran;
    private int[][] matrandanh;
    private int[][] matranchoi;
    // Server Socket
    private ServerSocket serversocket;
    private Socket socket;
    private OutputStream os;
    private InputStream is;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Timer thoigian;
    private Integer second, minute;
    private String normalItem[];
    private String winItem[];
    private String iconItem[];
    private String preItem[];
    private JButton preButton;
    private int soVan;
    private boolean click;
    private Thread th;
    private boolean check_exit;
    private Thread checkThread;
    private boolean checkwin;
    /**
     * Creates new form GameFrm
     */
    public GameServerFrm(Phong phong) {
        this.phong = phong;
        check_exit = true;
        checkwin = false;
        flat = false;
        initComponents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Caro Game by Ju");
        this.setIconImage(new ImageIcon("image/caroicon.png").getImage());
//        this.setSize(706, 505);
        this.setResizable(false);
        this.getContentPane().setLayout(null);
//        jPanel1.setSize(484, 484);
        jPanel1.setLayout(new GridLayout(15, 15));
        button = new JButton[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                button[i][j] = new JButton("");
                button[i][j].setBackground(Color.white);
                button[i][j].setDisabledIcon(new ImageIcon("image/border.jpg"));
//                button[i][j].setSize(100,100);
                jPanel1.add(button[i][j]);
            }
        }
        //
        matran = new int[15][15];
        matrandanh = new int[15][15];
        matranchoi = new int[15][15];
        //
        jLabel1.setFont(new Font("Arial", Font.BOLD, 15));
        jLabel6.setFont(new Font("Arial", Font.BOLD, 15));
        jLabel18.setFont(new Font("Arial", Font.BOLD, 15));
//        timerjLabel19.setFont(new Font("Arial",Font.BOLD,15));
        jLabel18.setAlignmentX(JLabel.CENTER);
        jButton1.setBackground(Color.white);
        jButton1.setIcon(new ImageIcon("image/send2.png"));
//        jLabel15.setText(phong.getKhach().getNickName());
//        jLabel15.setText(Integer.toString(phong.getKhach().getSoVanChoi()));
//        jLabel17.setText(Integer.toString(phong.getKhach().getSoVanThang()));
        jLabel18.setText("Tên phòng: " + phong.getTenPhong());
        jLabel12.setText(phong.getChuPhong().getNickName());
        jLabel13.setText(Integer.toString(phong.getChuPhong().getSoVanChoi()));
        jLabel14.setText(Integer.toString(phong.getChuPhong().getSoVanThang()));
//        jLabel5.setIcon(new ImageIcon("image/x3.jpg"));
        denLuotBanjLabel3.setVisible(false);
        denLuotDoiThu.setVisible(false);
        timerjLabel19.setVisible(false);
        jLabel20.setVisible(false);
        jTextArea1.setEditable(false);
        jLabel15.setVisible(false);
        jLabel16.setVisible(false);
        jLabel17.setVisible(false);
        
        ////////Luu dia chi IP va tao 1 port trên server
//        String IP = "";
//        try {
//            Enumeration e = NetworkInterface.getNetworkInterfaces();
//            while (e.hasMoreElements()) {
//                NetworkInterface n = (NetworkInterface) e.nextElement();
//                Enumeration ee = n.getInetAddresses();
//                while (ee.hasMoreElements()) {
//                    InetAddress i = (InetAddress) ee.nextElement();
//                    if (i.getHostAddress().contains("192.168.1.")) {
//                        IP = i.getHostAddress();
//                    }
//                }
//            }
////                String IP = i.getHostAddress();
//            System.out.println("Set IP=" + IP);
//            phong.setIP_Server(IP);
//            new PhongDAO().thietLapSerVer(phong.getID(), IP);
//            phong.setPort(new PhongDAO().layPortPhong(phong.getID()));
//
////            InetAddress addr = InetAddress.getLocalHost();
////            String IP = addr.getHostAddress();      
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Lỗi xảy ra khi đặt IP cho phòng");
//        } catch (SocketException ex) {
//            ex.printStackTrace();
//        }

        /////Thiet lap timmer
        second = 20;
        minute = 0;
        thoigian = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = minute.toString();
                String temp1 = second.toString();
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                if (temp1.length() == 1) {
                    temp1 = "0" + temp1;
                }
                /*if (second == 59) {
                                demthoigian.setText("Thời Gian:" + temp + ":" + temp1);
                                minute++;
                                second = 0;
                        } else {
                                demthoigian.setText("Thời Gian:" + temp + ":" + temp1);
                                second++;
                        }*/

                if (second == 0) {
                    checkwin = true;
                    timerjLabel19.setText("Thời Gian:" + temp + ":" + temp1);
                    phong.getKhach().setSoVanThang(phong.getKhach().getSoVanThang() + 1);
                    jLabel17.setText(Integer.toString(phong.getKhach().getSoVanThang()));
                    jLabel20.setText("Tỉ số :" + phong.getChuPhongThang() + "-" + phong.getKhachThang());
                    new NguoiChoiDAO().themVanThang(phong.getKhach());
                    JOptionPane.showMessageDialog(rootPane, "Hết giờ, bạn đã thua, nhấn OK để chơi ván mới");
                    if(!check_exit) return;
                    second = 20;
                    minute = 0;
                    try {
                        oos.writeObject("newgame,timeout");
                    } catch (IOException ie) {
                        //
                    }

                } else {
                    timerjLabel19.setVisible(true);
                    timerjLabel19.setText("Thời Gian:" + temp + ":" + temp1);
                    second--;
                }

            }

        });
        setupButton();
        this.setVisible(true);
        thietLapSocket();
        click = true;
        // Thietlap X/O
        normalItem = new String[2];
        normalItem[1] = "image/o2.jpg";
        normalItem[0] = "image/x2.jpg";
        winItem = new String[2];
        winItem[1] = "image/owin.jpg";
        winItem[0] = "image/xwin.jpg";
        preItem = new String[2];
        preItem[1] = "image/o2_pre.jpg";
        preItem[0] = "image/x2_pre.jpg";
        iconItem = new String[2];
        iconItem[1] = "image/o3.jpg";
        iconItem[0] = "image/x3.jpg";
        jLabel5.setVisible(false);
        jLabel19.setVisible(false);
        /// block game
        blockgame();
        jButton2.setVisible(true);
        // khoi tao so van
        soVan = 0;
        phong.setChuPhongThang(0);
        phong.setKhachThang(0);
        String click = "sound/click.wav";
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (phong.getKhach() == (null)) {
                    new PhongDAO().thietLapKhach(phong, phong.getChuPhong());
                }
                if (!checkwin && soVan > 0) {
                    phong.getChuPhong().setSoVanChoi(phong.getKhach().getSoVanChoi() - 1);
                    phong.getKhach().setSoVanChoi(phong.getKhach().getSoVanChoi() - 1);
                    new NguoiChoiDAO().themVanChoi(phong.getChuPhong());
                    new NguoiChoiDAO().themVanChoi(phong.getKhach());
                }
            }
        });
        checkToEnd();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        jLabel2 = new javax.swing.JLabel();
        denLuotBanjLabel3 = new javax.swing.JLabel();
        denLuotDoiThu = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        timerjLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);

        jLabel2.setText("Số ván thắng");

        denLuotBanjLabel3.setForeground(new java.awt.Color(255, 0, 0));
        denLuotBanjLabel3.setText("Đến lượt bạn");

        denLuotDoiThu.setForeground(new java.awt.Color(0, 0, 255));
        denLuotDoiThu.setText("Đến lượt đối thủ");

        jLabel8.setText("Nickname");

        jLabel4.setText("Số ván chơi");

        jLabel9.setText("Số ván thắng");

        jLabel10.setText("Nickname");

        jLabel11.setText("Số ván chơi");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel12.setText("{nickname}");

        jLabel13.setText("{sovanthang}");

        jLabel14.setText("{tilethang}");

        jLabel15.setText("{nickname}");

        jLabel16.setText("{sovanthang}");

        jLabel17.setText("{tilethang}");

        timerjLabel19.setForeground(new java.awt.Color(255, 0, 51));
        timerjLabel19.setText("{timer}");

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 571, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bạn");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(102, 102, 102));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Đối thủ");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 204, 204));
        jLabel3.setText("Chờ đối thủ vào phòng");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addComponent(jLabel3))
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("{Tên Phòng}");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jLabel20.setText("Tỉ số:  0-0");

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Ván Mới");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel19.setText("x/o");

        jLabel5.setText("x/o");

        jMenu1.setText("Menu");
        jMenu1.setToolTipText("");

        jMenuItem1.setText("Game mới");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Thoát");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        jMenuItem3.setText("Trợ giúp");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(denLuotBanjLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(timerjLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(denLuotDoiThu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(90, 90, 90)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel15))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel17))
                        .addGap(8, 8, 8)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(21, 21, 21))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(timerjLabel19)
                            .addComponent(denLuotDoiThu)
                            .addComponent(denLuotBanjLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        //for(int i=0; i<5; i++){
            //    for(int j=0;j<5;j++){
                //        jPanel1.add(button[i][j]);
                //    }
            //}

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        exitGame();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (flat) {
                // TODO add your handling code here:
                String temp = jTextArea1.getText();
                temp += "Tôi: " + jTextField1.getText()+"\n";
                jTextArea1.setText(temp);
                new TinNhanDAO().themTinNhan(phong.getChuPhong(), phong.getKhach(), jTextField1.getText());
                oos.writeObject("chat," + jTextField1.getText());
                jTextField1.setText("");
                //temp = "";
                jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
            } else {
                throw new Exception("Chưa có kết nối tới Client");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, "Chưa có kết nối tới Client");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Chưa có kết nối tới Client");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!click) {
            try {
                int res = JOptionPane.showConfirmDialog(rootPane, "Bạn có thực sự muốn cầu hòa ván chơi này", "Thông báo", JOptionPane.YES_NO_OPTION);
                if(!check_exit) return;
                if (res == JOptionPane.YES_OPTION) {
                    oos.writeObject("draw");
                    thoigian.stop();
                    setEnableButton(false);
                    phong.getChuPhong().setSoVanChoi(phong.getKhach().getSoVanChoi() - 1);
                    phong.getKhach().setSoVanChoi(phong.getKhach().getSoVanChoi() - 1);
                }
            } catch (IOException ex) {

            }
        }
        if (click) {
            try {
                if (soVan % 2 == 1) {
                    throw new Exception("Trong ván tiếp theo, khách là người bắt đầu trước");
                }
                if (!flat) {
                    JOptionPane.showMessageDialog(rootPane, "Đang chờ người khác vào phòng...");
                } else {
                    try {
                        oos.writeObject("newgame,me");
                        newgame();
//                    JOptionPane.showMessageDialog(rootPane, "Đến lượt bạn đi trước");
                        second = 20;
                        minute = 0;
                        thoigian.start();
                        denLuotBanjLabel3.setVisible(true);
                        jLabel5.setVisible(true);
                        jButton2.setText("Cầu hòa");
                        click = false;
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Người chơi đã thoát khỏi phòng");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Luật chơi: luật quốc tế 5 nước chặn 2 đầu\n"
                + "Hai người chơi luân phiên nhau chơi trước\n"
                + "Người chơi trước đánh X, người chơi sau đánh O\n"
                + "Bạn có 20 giây cho mỗi lượt đánh, quá 20 giây bạn sẽ thua\n"
                + "Khi cầu hòa, nếu đối thủ đồng ý thì ván hiện tại được hủy kết quả\n"
                + "Chúc bạn chơi game vui vẻ");
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    void batDauDemNguoc() {
        Timer timer;
        timer = new Timer(1000, new ActionListener() {
            int count = 20;

            @Override
            public void actionPerformed(ActionEvent e) {
                count--;
                if (count >= 0) {
                    timerjLabel19.setText("Còn lại " + Integer.toString(count) + " giây");
                } else {
                    ((Timer) (e.getSource())).stop();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/click.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void playSound1() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/1click.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void playSound2() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/win.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    void showTinNhan() {
        List<String> list = new TinNhanDAO().layTinNhan(phong.getChuPhong(), phong.getKhach());
        String tmp = "";
        for (String string : list) {
            tmp = tmp + string + "\n";
        }
        jTextArea1.setText(tmp);
    }

    int dao(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 0) {
            return 1;
        }
        return 0;
    }

    void setupButton() {
        int x = 15;
        int y = 15;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                final int a = i, b = j;

                button[a][b].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (!flat) {
                                throw new Exception("Chưa kết nối client");
                            }
                            button[a][b].setDisabledIcon(new ImageIcon(normalItem[dao(soVan % 2)]));
                            button[a][b].setEnabled(false);
                            playSound();
                            thoigian.start();
                            second = 20;
                            minute = 0;
                            matrandanh[a][b] = 1;
                            matranchoi[a][b] = 1;
                            button[a][b].setEnabled(false);
                            try {
                                if (checkHang_Choi() == 1 || checkCot_Choi() == 1 || checkCheoPhai_Choi() == 1 || checkCheoTrai_Choi() == 1) {
                                    checkwin = true;
                                    phong.setChuPhongThang(phong.getChuPhongThang() + 1);
                                    phong.getChuPhong().setSoVanThang(phong.getChuPhong().getSoVanThang() + 1);
                                    jLabel20.setText("Tỉ số: " + phong.getChuPhongThang() + "-" + phong.getKhachThang());
                                    jLabel14.setText(Integer.toString(phong.getChuPhong().getSoVanThang()));
                                    new NguoiChoiDAO().themVanThang(phong.getChuPhong());
                                }
                                oos.writeObject("caro," + a + "," + b);
                                timerjLabel19.setVisible(false);
                                denLuotDoiThu.setVisible(true);
                                jLabel19.setVisible(true);
                                denLuotBanjLabel3.setVisible(false);
                                jButton2.setVisible(false);
                                jLabel5.setVisible(false);
                                setEnableButton(false);

                            } catch (Exception ie) {
                                ie.printStackTrace();
                            }
                            thoigian.stop();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                        }
                    }
                });
            }
        }
    }

    void thietLapTinNhan() {
        List<String> list = new ArrayList<>();
        list = new TinNhanDAO().layTinNhan(phong.getKhach(), phong.getChuPhong());
        String tmp = "";
        for (String string : list) {
            tmp += string+"\n";
        }
        jTextArea1.setText(tmp);
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }

    public void thietLapSocket() {
        try {
            serversocket = new ServerSocket(phong.getPort());
            System.out.println("Dang doi client...");
            System.out.println(phong.getIP_Server() + "," + phong.getPort());
            th = new Thread() {
                @Override
                public void run() {
                    System.out.println("running");
                    try {
                        socket = serversocket.accept();
                        phong.setKhach(new PhongDAO().layKhach(phong));
                        jLabel15.setVisible(true);
                        jLabel16.setVisible(true);
                        jLabel17.setVisible(true);
                        jLabel15.setText(phong.getKhach().getNickName());
                        jLabel16.setText(Integer.toString(phong.getKhach().getSoVanChoi()));
                        jLabel17.setText(Integer.toString(phong.getKhach().getSoVanThang()));
                        jLabel20.setVisible(true);
                        jLabel20.setText("Tỉ số :" + phong.getChuPhongThang() + "-" + phong.getKhachThang());
                        thietLapTinNhan();
                        jLabel3.setVisible(false);
                        os = socket.getOutputStream();
                        is = socket.getInputStream();
                        oos = new ObjectOutputStream(os);
                        ois = new ObjectInputStream(is);
                        flat = true;
                        JOptionPane.showMessageDialog(rootPane, phong.getKhach().getNickName() + " đã vào phòng, click Ván Mới để bắt đầu"); 
                        while (check_exit) {
                            //do something
                            String stream = ois.readObject().toString();
//                            System.out.println(stream);
                            String[] data = stream.split(",");
                            if (data[0].equals("chat")) {
                                String temp = jTextArea1.getText();
                                temp +=phong.getKhach().getNickName() + ": " + data[1]+"\n";
                                jTextArea1.setText(temp);
                                jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
                            } else if (data[0].equals("caro")) {
                                denLuotDoiThu.setVisible(false);
                                jLabel19.setVisible(false);
                                denLuotBanjLabel3.setVisible(true);
                                jButton2.setVisible(true);
                                jLabel5.setVisible(true);
                                thoigian.start();
                                second = 20;
                                minute = 0;
                                caro(data[1], data[2]);
                                setEnableButton(true);

                                if (winner == false) {
                                    setEnableButton(true);
                                }
                            } else if (data[0].equals("newgame")) {
                                second = 20;
                                minute = 0;
                                if (data[1].equals("me")) {
                                    newgame();
                                    blockgame();
                                } else if (data[1].equals("timeout")) {
                                    phong.setChuPhongThang(phong.getChuPhongThang() + 1);
                                    phong.getChuPhong().setSoVanThang(phong.getChuPhong().getSoVanThang() + 1);
                                    jLabel20.setText("Tỉ số: " + phong.getChuPhongThang() + "-" + phong.getKhachThang());
                                    jLabel14.setText(Integer.toString(phong.getChuPhong().getSoVanThang()));
                                    thoigian.stop();
                                    second = 20;
                                    minute = 0;
                                    playSound2();
                                    JOptionPane.showMessageDialog(rootPane, "Bạn đã thắng, làm ván nữa chứ!");
                                    if(!check_exit) break;
                                    newgame();
                                    if (soVan % 2 == 0) {
                                        oos.writeObject("newgame,you");
                                        blockgame();
                                    }
                                    if (soVan % 2 == 1) {
                                        oos.writeObject("newgame,me");
                                    }
                                } else {
                                    newgame();
                                    if (soVan % 2 == 0) {
                                        blockgame();
                                    } else {
                                        thoigian.start();
                                    }
                                }
                            } else if (data[0].equals("youwin")) {
                                thoigian.stop();
                                second = 20;
                                minute = 0;
                                playSound2();
                                JOptionPane.showMessageDialog(rootPane, "Bạn đã thắng. Mời bạn chơi ván mới!");
                                if(!check_exit) break;
                                newgame();
                                if (soVan % 2 == 0) {
                                    oos.writeObject("newgame,you");
                                    blockgame();
                                }
                                if (soVan % 2 == 1) {
                                    oos.writeObject("newgame,me");
                                }
                            } else if (data[0].equals("draw")) {
                                int res = JOptionPane.showConfirmDialog(rootPane,
                                        "Đối thủ muốn hòa ván này, bạn có đồng ý không?", "Thông báo: ",
                                        JOptionPane.YES_NO_OPTION);
                                if(!check_exit) break;
                                if (res == JOptionPane.YES_OPTION) {
                                    phong.getChuPhong().setSoVanChoi(phong.getKhach().getSoVanChoi() - 1);
                                    phong.getKhach().setSoVanChoi(phong.getKhach().getSoVanChoi() - 1);
                                    new NguoiChoiDAO().themVanChoi(phong.getChuPhong());
                                    new NguoiChoiDAO().themVanChoi(phong.getKhach());
                                    newgame();
                                    if (soVan % 2 == 0) {
                                        oos.writeObject("newgame,you");
                                        blockgame();
                                    }
                                    if (soVan % 2 == 1) {
                                        oos.writeObject("newgame,me");
                                    }
                                } else {
                                    oos.writeObject("disagree");
                                }
                            } else if (data[0].equals("disagree")) {
                                thoigian.start();
                                JOptionPane.showMessageDialog(rootPane, "Đối thủ không đồng ý hòa, mời bạn chơi tiếp!");
                                if(!check_exit) break;
                                phong.getChuPhong().setSoVanChoi(phong.getKhach().getSoVanChoi() + 1);
                                phong.getKhach().setSoVanChoi(phong.getKhach().getSoVanChoi() + 1);
                                setEnableButton(true);
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("End thread");
                }
                
            };
            th.start();
        } catch (Exception ie) {
            
        } 

    }
    void checkToEnd(){
        checkThread = new Thread() {
            @Override
            public void run() {
                while (check_exit) {
                    if (flat) {
                        try {
                            oos.writeObject("check");  
                        } catch (IOException ex) {
                            thoigian.stop();
                            JOptionPane.showMessageDialog(rootPane, "Đối thủ đã thoát khỏi phòng");
                            quitGame();
                        } 
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println("Kết thúc kiểm tra");
            }
        };
        checkThread.start();
    }
    void exitGame() {
        if (!checkwin && soVan > 0) {
            phong.getChuPhong().setSoVanChoi(phong.getKhach().getSoVanChoi() - 1);
            phong.getKhach().setSoVanChoi(phong.getKhach().getSoVanChoi() - 1);
            new NguoiChoiDAO().themVanChoi(phong.getChuPhong());
            new NguoiChoiDAO().themVanChoi(phong.getKhach());
        }
        thoigian.stop();
        check_exit = false;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {

            }
        }
        try {
            serversocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        os = OutputStream.nullOutputStream();
        is = InputStream.nullInputStream();
        oos = null;
        ois = null;
        this.setVisible(false);
        new GiaoDienChinhFrm(new NguoiChoiDAO().layNguoiChoi(phong.getChuPhong().getID())).setVisible(true);
    }
    void quitGame() {
//        if (flat) {
//            try {
//                oos.writeObject("bye");
//            } catch (IOException ex) {
//            }
//        }
//        if (!checkwin && soVan > 0) {
//            phong.getChuPhong().setSoVanChoi(phong.getKhach().getSoVanChoi() - 1);
//            phong.getKhach().setSoVanChoi(phong.getKhach().getSoVanChoi() - 1);
//            new NguoiChoiDAO().themVanChoi(phong.getChuPhong());
//            new NguoiChoiDAO().themVanChoi(phong.getKhach());
//        }
        thoigian.stop();
        if (socket != null) {
            try {
                socket.close();
                serversocket.close();
                os = OutputStream.nullOutputStream();
                is = InputStream.nullInputStream();
                oos = null;
                ois = null;
            } catch (IOException ex) {

            }
        }
        check_exit=false;
        this.setVisible(false);
        new GiaoDienChinhFrm(new NguoiChoiDAO().layNguoiChoi(phong.getChuPhong().getID())).setVisible(true);
    }

    public void newgame() {
        if(!check_exit) return;
        checkwin = false;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                button[i][j].setIcon(new ImageIcon("image/blank.jpg"));
                button[i][j].setDisabledIcon(new ImageIcon("image/border.jpg"));
                button[i][j].setText("");
                matran[i][j] = 0;
                matrandanh[i][j] = 0;
                matranchoi[i][j] = 0;
                jLabel5.setIcon(new ImageIcon(iconItem[soVan % 2]));
                jLabel19.setIcon(new ImageIcon(iconItem[dao(soVan % 2)]));
            }
        }
        setEnableButton(true);
        soVan++;
        if (soVan % 2 == 1) {
            JOptionPane.showMessageDialog(rootPane, "Đến lượt bạn đi trước");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Đối thủ đi trước");
        }
        preButton=null;
        phong.getKhach().setSoVanChoi(phong.getKhach().getSoVanChoi() + 1);
        phong.getChuPhong().setSoVanChoi(phong.getChuPhong().getSoVanChoi() + 1);
        jLabel16.setText(Integer.toString(phong.getKhach().getSoVanChoi()));
        jLabel13.setText(Integer.toString(phong.getChuPhong().getSoVanChoi()));
        new NguoiChoiDAO().themVanChoi(phong.getChuPhong());

    }

    public void blockgame() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                button[i][j].setBackground(Color.white);
                button[i][j].setDisabledIcon(new ImageIcon("image/border.jpg"));
                button[i][j].setText("");
                matran[i][j] = 0;
                matrandanh[i][j] = 0;
                matranchoi[i][j] = 0;
                jButton2.setVisible(false);
            }
        }
        setEnableButton(false);
    }

    public void setEnableButton(boolean b) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (matrandanh[i][j] == 0) {
                    button[i][j].setEnabled(b);
                }
            }
        }
    }
    //thuat toan tinh thang thua

    public int checkHang() {
        int win = 0, hang = 0, n = 0, k = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (check) {
                    if (matran[i][j] == 1) {
                        hang++;
                        list.add(button[i][j]);
                        if (hang > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[soVan % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        hang = 0;
                    }
                }
                if (matran[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    hang++;
                } else {
                    list = new ArrayList<>();
                    check = false;
                }
            }
            list = new ArrayList<>();
            hang = 0;
        }
        return win;
    }

    public int checkCot() {
        int win = 0, cot = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int j = 0; j < 15; j++) {
            for (int i = 0; i < 15; i++) {
                if (check) {
                    if (matran[i][j] == 1) {
                        cot++;
                        list.add(button[i][j]);
                        if (cot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[soVan % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        check = false;
                        cot = 0;
                        list = new ArrayList<>();
                    }
                }
                if (matran[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    cot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            cot = 0;
        }
        return win;
    }

    public int checkCheoPhai() {
        int win = 0, cheop = 0, n = 0, k = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 14; i >= 0; i--) {
            for (int j = 0; j < 15; j++) {
                if (check) {
                    if (matran[n - j][j] == 1) {
                        cheop++;
                        list.add(button[n - j][j]);
                        if (cheop > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[soVan % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheop = 0;
                    }
                }
                if (matran[i][j] == 1) {
                    n = i + j;
                    check = true;
                    list.add(button[i][j]);
                    cheop++;
                } else {
                    check = false;
                    list = new ArrayList<>();
                }
            }
            cheop = 0;
            check = false;
            list = new ArrayList<>();
        }
        return win;
    }

    public int checkCheoTrai() {
        int win = 0, cheot = 0, n = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            for (int j = 14; j >= 0; j--) {
                if (check) {
                    if (matran[n - j - 2 * cheot][j] == 1) {
                        list.add(button[n - j - 2 * cheot][j]);
                        cheot++;
                        System.out.print("+" + j);
                        if (cheot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[soVan % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheot = 0;
                    }
                }
                if (matran[i][j] == 1) {
                    list.add(button[i][j]);
                    n = i + j;
                    check = true;
                    cheot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            n = 0;
            cheot = 0;
            check = false;
        }
        return win;
    }

    public int checkHang_Choi() {
        int win = 0, hang = 0, n = 0, k = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (check) {
                    if (matranchoi[i][j] == 1) {
                        hang++;
                        list.add(button[i][j]);
                        if (hang > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[dao(soVan % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        hang = 0;
                    }
                }
                if (matranchoi[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    hang++;
                } else {
                    list = new ArrayList<>();
                    check = false;
                }
            }
            list = new ArrayList<>();
            hang = 0;
        }
        return win;
    }

    public int checkCot_Choi() {
        int win = 0, cot = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int j = 0; j < 15; j++) {
            for (int i = 0; i < 15; i++) {
                if (check) {
                    if (matranchoi[i][j] == 1) {
                        cot++;
                        list.add(button[i][j]);
                        if (cot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[dao(soVan % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        check = false;
                        cot = 0;
                        list = new ArrayList<>();
                    }
                }
                if (matranchoi[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    cot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            cot = 0;
        }
        return win;
    }

    public int checkCheoPhai_Choi() {
        int win = 0, cheop = 0, n = 0, k = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 14; i >= 0; i--) {
            for (int j = 0; j < 15; j++) {
                if (check) {
                    if (matranchoi[n - j][j] == 1) {
                        cheop++;
                        list.add(button[n - j][j]);
                        if (cheop > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[dao(soVan % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheop = 0;
                    }
                }
                if (matranchoi[i][j] == 1) {
                    n = i + j;
                    check = true;
                    list.add(button[i][j]);
                    cheop++;
                } else {
                    check = false;
                    list = new ArrayList<>();
                }
            }
            cheop = 0;
            check = false;
            list = new ArrayList<>();
        }
        return win;
    }

    public int checkCheoTrai_Choi() {
        int win = 0, cheot = 0, n = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            for (int j = 14; j >= 0; j--) {
                if (check) {
                    if (matranchoi[n - j - 2 * cheot][j] == 1) {
                        list.add(button[n - j - 2 * cheot][j]);
                        cheot++;
                        System.out.print("+" + j);
                        if (cheot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[dao(soVan % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheot = 0;
                    }
                }
                if (matranchoi[i][j] == 1) {
                    list.add(button[i][j]);
                    n = i + j;
                    check = true;
                    cheot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            n = 0;
            cheot = 0;
            check = false;
        }
        return win;
    }

    public void caro(String x, String y) {
        int xx, yy;
        xx = Integer.parseInt(x);
        yy = Integer.parseInt(y);
        // danh dau vi tri danh
        matran[xx][yy] = 1;
        matrandanh[xx][yy] = 1;
        button[xx][yy].setEnabled(false);
        playSound1();
        //bt[xx][yy].setIcon(new ImageIcon("x.png"));
        if(preButton!=null){
            preButton.setDisabledIcon(new ImageIcon(normalItem[soVan % 2]));
        }
        preButton = button[xx][yy];
        button[xx][yy].setDisabledIcon(new ImageIcon(preItem[soVan % 2]));
        // Kiem tra thang hay chua
//        System.out.println("CheckH:" + checkHang());
//        System.out.println("CheckC:" + checkCot());
//        System.out.println("CheckCp:" + checkCheoPhai());
//        System.out.println("CheckCt:" + checkCheoTrai());
        winner = (checkHang() == 1 || checkCot() == 1 || checkCheoPhai() == 1 || checkCheoTrai() == 1);
        if (checkHang() == 1 || checkCot() == 1 || checkCheoPhai() == 1
                || checkCheoTrai() == 1) {   
            checkwin = true;
            setEnableButton(false);
            thoigian.stop();
            phong.getKhach().setSoVanThang(phong.getKhach().getSoVanThang() + 1);
            phong.setKhachThang(phong.getKhachThang() + 1);
            jLabel17.setText(Integer.toString(phong.getKhach().getSoVanThang()));
            jLabel20.setText("Tỉ số: " + phong.getChuPhongThang() + "-" + phong.getKhachThang());
            JOptionPane.showMessageDialog(rootPane, "Bạn đã thua, nhấn OK để chơi ván mới");
            if(!check_exit) return;
            second = 20;
            minute = 0;
            try {
                oos.writeObject("youwin," + x + "," + y);
            } catch (IOException ie) {
                //
            }

        }

    }
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel denLuotBanjLabel3;
    private javax.swing.JLabel denLuotDoiThu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel timerjLabel19;
    // End of variables declaration//GEN-END:variables

}
