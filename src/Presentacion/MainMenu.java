package Presentacion;




/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import Entidades.Registro;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author laboratorio
 */
public class MainMenu extends javax.swing.JFrame {

        private Logica.Logica service;

    private JTextField txtPlacaEntrada, txtHoraEntrada;
    private JTextField txtPlacaSalida, txtHoraSalida;
    private JComboBox<String> cbTipo;
    private JLabel lblMensaje;

    private JTable tablaActivos, tablaHistorial;
    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
      service = new Logica.Logica();

        setTitle("Sistema de Parqueo");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // SOLO JFrame
        setLocationRelativeTo(null);

        // ======== INGRESO ========
        JLabel lblPlacaE = new JLabel("Placa:");
        lblPlacaE.setBounds(20, 20, 60, 25);
        add(lblPlacaE);

        txtPlacaEntrada = new JTextField();
        txtPlacaEntrada.setBounds(80, 20, 100, 25);
        add(txtPlacaEntrada);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(200, 20, 50, 25);
        add(lblTipo);

        cbTipo = new JComboBox<>(new String[]{"carro", "moto"});
        cbTipo.setBounds(250, 20, 100, 25);
        add(cbTipo);

        JLabel lblHoraE = new JLabel("Hora:");
        lblHoraE.setBounds(370, 20, 50, 25);
        add(lblHoraE);

        txtHoraEntrada = new JTextField();
        txtHoraEntrada.setBounds(420, 20, 80, 25);
        add(txtHoraEntrada);

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(520, 20, 120, 25);
        add(btnIngresar);

        // ======== SALIDA ========
        JLabel lblPlacaS = new JLabel("Placa:");
        lblPlacaS.setBounds(20, 60, 60, 25);
        add(lblPlacaS);

        txtPlacaSalida = new JTextField();
        txtPlacaSalida.setBounds(80, 60, 100, 25);
        add(txtPlacaSalida);

        JLabel lblHoraS = new JLabel("Hora:");
        lblHoraS.setBounds(200, 60, 50, 25);
        add(lblHoraS);

        txtHoraSalida = new JTextField();
        txtHoraSalida.setBounds(250, 60, 100, 25);
        add(txtHoraSalida);

        JButton btnSalida = new JButton("Registrar Salida");
        btnSalida.setBounds(370, 60, 150, 25);
        add(btnSalida);

        // ======== TABLA ACTIVOS ========
        tablaActivos = new JTable();
        JScrollPane scrollActivos = new JScrollPane(tablaActivos);
        scrollActivos.setBounds(20, 120, 400, 300);
        add(scrollActivos);

        // ======== TABLA HISTORIAL ========
        tablaHistorial = new JTable();
        JScrollPane scrollHistorial = new JScrollPane(tablaHistorial);
        scrollHistorial.setBounds(450, 120, 400, 300);
        add(scrollHistorial);

        // ======== MENSAJES ========
        lblMensaje = new JLabel("");
        lblMensaje.setBounds(20, 450, 800, 30);
        add(lblMensaje);

        // ======== EVENTOS ========
        btnIngresar.addActionListener(e -> manejarEntrada());
        btnSalida.addActionListener(e -> manejarSalida());

        actualizarTablas();
    }

    // ================= EVENTOS =================
    private void manejarEntrada() {
        try {
            String msg = service.registrarEntrada(
                    txtPlacaEntrada.getText(),
                    cbTipo.getSelectedItem().toString(),
                    txtHoraEntrada.getText()
            );
            lblMensaje.setText(msg);
            limpiarEntrada();
            actualizarTablas();
        } catch (RuntimeException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    private void manejarSalida() {
        try {
            String msg = service.registrarSalida(
                    txtPlacaSalida.getText(),
                    txtHoraSalida.getText()
            );
            lblMensaje.setText(msg);
            limpiarSalida();
            actualizarTablas();
        } catch (RuntimeException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    // ================= LIMPIAR =================
    private void limpiarEntrada() {
        txtPlacaEntrada.setText("");
        txtHoraEntrada.setText("");
    }

    private void limpiarSalida() {
        txtPlacaSalida.setText("");
        txtHoraSalida.setText("");
    }

    // ================= TABLAS =================
    private void actualizarTablas() {

        // Activos
        DefaultTableModel modeloActivos = new DefaultTableModel();
        modeloActivos.addColumn("Placa");
        modeloActivos.addColumn("Tipo");
        modeloActivos.addColumn("Entrada");

        List<Registro> activos = service.getActivos();

        for (Registro r : activos) {
            modeloActivos.addRow(new Object[]{
                    r.getVehiculo().getPlaca(),
                    r.getVehiculo().getTipo(),
                    r.getHoraEntrada().toString()
            });
        }

        tablaActivos.setModel(modeloActivos);

        // Historial
        DefaultTableModel modeloHist = new DefaultTableModel();
        modeloHist.addColumn("Placa");
        modeloHist.addColumn("Entrada");
        modeloHist.addColumn("Salida");
        modeloHist.addColumn("Monto");

        List<Registro> historial = service.getHistorial();

        for (Registro r : historial) {
            modeloHist.addRow(new Object[]{
                    r.getVehiculo().getPlaca(),
                    r.getHoraEntrada().toString(),
                    (r.getHoraSalida() != null ? r.getHoraSalida().toString() : ""),
                    r.getMonto()
            });
        }

        tablaHistorial.setModel(modeloHist);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

