import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * Clase Conexion (Paso 3 del PowerPoint).
 * Usa el controlador mysql-connector-java-5.1.23-bin.jar (com.mysql.jdbc.Driver).
 */
public class Conexion {

    Connection con = null;

    // Devuelve 1 si se conectó, 0 si falló (el pptx compara "== 0" para mostrar el error)
    public int conectar(String servidor, String bd, String usuario, String clave) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + servidor + "/" + bd;
            con = DriverManager.getConnection(url, usuario, clave == null ? "" : clave);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            con = null;
            return 0;
        }
    }

    // Ejecuta el SELECT y regresa todas las filas como lista de listas de String
    public ArrayList<ArrayList<String>> consultar(String query) {
        ArrayList<ArrayList<String>> datos = new ArrayList<ArrayList<String>>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            int columnas = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                ArrayList<String> fila = new ArrayList<String>();
                for (int i = 1; i <= columnas; i++) {
                    fila.add(rs.getString(i));
                }
                datos.add(fila);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datos;
    }

    // Muestra el resultado de la consulta en una JTable.
    // anchoCol = ancho preferido de cada columna, altoFila = alto de cada fila
    public void entablar(String query, JTable tabla, int anchoCol, int altoFila) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();
            int columnas = md.getColumnCount();

            DefaultTableModel modelo = new DefaultTableModel();
            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(md.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getString(i + 1);
                }
                modelo.addRow(fila);
            }
            rs.close();
            st.close();

            tabla.setModel(modelo);
            tabla.setRowHeight(altoFila);
            for (int i = 0; i < columnas; i++) {
                TableColumn c = tabla.getColumnModel().getColumn(i);
                c.setPreferredWidth(anchoCol);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
