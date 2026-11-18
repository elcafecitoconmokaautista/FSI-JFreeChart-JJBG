import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Formulario principal: panel para la gráfica (PGrafica) y tabla (TQuery).
 */
public class FConsultas extends javax.swing.JFrame {

    // Paso 9: variables globales para los datos de la gráfica
    //Datos de la consulta a graficar
    //El primer campo debe de ser los valores del eje X
    ArrayList<ArrayList<String>> datos = new ArrayList();

    //Nombres de cada una de las series
    ArrayList<String> series = new ArrayList();

    //Objeto para crear una gráfica
    GraficaXY chart;

    // Paso 6: declarar el objeto de la clase Conexion
    Conexion cnx;

    public FConsultas() {
        initComponents();
        setLocationRelativeTo(null);

        // Paso 6: crear el objeto Conexion
        cnx = new Conexion();

        // Paso 7: abrir la conexión a la base de datos "insulina"
        if (cnx.conectar("localhost", "insulina", "root", null) == 0) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar a la BD");
            System.exit(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PNorte = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        BGlucosa = new javax.swing.JButton();
        PCentro = new javax.swing.JPanel();
        PGrafica = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TQuery = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PNorte.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(0, 51, 102));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Graficación de Valores de una BD");
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(800, 45));
        PNorte.add(jLabel1, java.awt.BorderLayout.NORTH);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        BGlucosa.setText("Glucosa promedio");
        BGlucosa.setFocusable(false);
        BGlucosa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BGlucosa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BGlucosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BGlucosaActionPerformed(evt);
            }
        });
        jToolBar1.add(BGlucosa);

        PNorte.add(jToolBar1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PNorte, java.awt.BorderLayout.NORTH);

        PCentro.setLayout(new java.awt.BorderLayout());

        PGrafica.setBackground(new java.awt.Color(153, 153, 153));
        PGrafica.setPreferredSize(new java.awt.Dimension(800, 330));
        PGrafica.setLayout(new java.awt.BorderLayout());
        PCentro.add(PGrafica, java.awt.BorderLayout.NORTH);

        jScrollPane1.setViewportView(TQuery);

        PCentro.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(PCentro, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Pasos 10 y 11: consulta, gráfica y tabla
    private void BGlucosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BGlucosaActionPerformed
        // Paso 8: consulta probada previamente en MySQL
        // Nivel de glucosa promedio de cada paciente
        String query = "SELECT nombrep, AVG(nvl_glucosa) AS promedio "
                + "FROM mediciones, pacientes "
                + "WHERE pacientes_curp = curp "
                + "GROUP BY curp";

        datos = cnx.consultar(query);

        series.clear();
        series.add("promedio");

        PGrafica.removeAll();
        chart = new GraficaXY("Nivel de glucosa promedio por paciente",
                "Paciente", "Glucosa (mg/dL)", series, datos);
        PGrafica.add(chart.chartPanel);
        PGrafica.updateUI();

        cnx.entablar(query, TQuery, 60, 40);
    }//GEN-LAST:event_BGlucosaActionPerformed

    // Paso 12: ejecutar el programa
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FConsultas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BGlucosa;
    private javax.swing.JPanel PCentro;
    private javax.swing.JPanel PGrafica;
    private javax.swing.JPanel PNorte;
    private javax.swing.JTable TQuery;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
