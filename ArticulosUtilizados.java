package inventario;

import java.sql.*;
import javax.swing.JOptionPane;

public class ArticulosUtilizados 
{
    Conexion conexion;
    
    public ArticulosUtilizados()
    {
        conexion = new Conexion();
    }
    
    public void NuevoArticuloUtilizado(String folio_prestamo, String no_serie, String numControl, String fecha_p, String fecha_e, String descripcion)
    {
       try 
       {            
            String nuevo = "INSERT INTO prestamo(num_folio,no_serie,id_usuario,fecha_prestamo,fecha_entrega,descripcion) VALUES (?,?,?,?,?,?);";
            PreparedStatement p;            
            p = conexion.getConexion().prepareStatement(nuevo);
            p.setString(1, folio_prestamo);
            p.setString(2, no_serie);
            p.setString(3, numControl);
            p.setString(4, fecha_p);
            p.setString(5, fecha_e);
            p.setString(6, descripcion);
            p.execute();
            p.close();            
       }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
   }

      public Object [][] getDatos()
      {
        int registros = 0;
        try
        {         
             PreparedStatement pstm = conexion.getConexion().prepareStatement("SELECT count(num_folio) as total FROM prestamo");
             ResultSet res = pstm.executeQuery();
             res.next();
             registros = res.getInt("total");
             res.close();
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }    
       
        Object[][] data = new String[registros][6];  
        try
        {    
            String consulta = "SELECT * FROM prestamo ORDER BY num_folio;";
            PreparedStatement p = conexion.getConexion().prepareStatement(consulta);
            ResultSet r = p.executeQuery();
            int i = 0;
            while(r.next())
            {
                String folio_prestamo = r.getString("num_folio");
                String no_serie = r.getString("no_serie");
                String area_usado = r.getString("id_usuario");
                String fecha_p = r.getString("fecha_prestamo");
                String fecha_e = r.getString("fecha_entrega");
                String descripcion = r.getString("descripcion");
                data[i][0] = folio_prestamo;
                data[i][1] = no_serie;            
                data[i][2] = area_usado;            
                data[i][3] = fecha_p;            
                data[i][4] = fecha_e;            
                data[i][5] = descripcion;                     
                i++;
            }
            r.close();
        }catch(SQLException e)
          {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
          }
    return data;
 }

    public void borrarArticuloUtilizado(String codigo)
    {  
            try 
            {                
                String borrar = "DELETE FROM prestamo WHERE num_folio = ?;";
                PreparedStatement p = conexion.getConexion().prepareStatement(borrar);            
                p.setString(1, codigo);                   
                p.execute();
                p.close();            
            }catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }            
   }

    public void actualizarArticuloUtilizado(String folio_prestamo, String no_serie, String numControl, String fecha_p, String fecha_e, String descripcion)
    {
       try {
            String actualizar = "UPDATE prestamo set num_folio = ?, no_serie = ?, id_usuario = ?, fecha_prestamo = ?, fecha_entrega = ?, descripcion = ?" +
                                "where num_folio = ?;";
            PreparedStatement p = conexion.getConexion().prepareStatement(actualizar);
            p.setString(1, folio_prestamo);
            p.setString(2, no_serie);
            p.setString(3, numControl);
            p.setString(4, fecha_p);
            p.setString(5, fecha_e);
            p.setString(6, descripcion);
            p.setString(7, folio_prestamo);
            p.execute();
            p.close();            
         }catch(SQLException e)
         {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
         }
   }
}