package inventario;

import java.sql.*;
import javax.swing.JOptionPane;

public class Articulo 
{
    Conexion conexion;
    
    public Articulo()
    {
        conexion = new Conexion();
    }
    
   public void NuevaArticulo(String no_serie, String nombre, String marca, String descripcion,String area,String capacidad)
    {
       try {            
             String insertar = "INSERT INTO articulo (no_serie,nombre,marca,descripcion,area,capacidad)" +
                               "VALUES(?,?,?,?,?,?)";            
             PreparedStatement p = conexion.getConexion().prepareStatement(insertar);
             p.setString(1, no_serie);
             p.setString(2, nombre);
             p.setString(3, marca);
             p.setString(4, descripcion);
             p.setString(5, area);
             p.setString(6, capacidad);
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
            PreparedStatement p = conexion.getConexion().prepareStatement("SELECT count(no_serie) as total FROM articulo");
            ResultSet r = p.executeQuery();
            r.next();
            registros = r.getInt("total");
            r.close();
        }catch(SQLException e)
        {
             JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }    
       
        Object[][] data = new String[registros][6];  
        try
        {    
            String consulta = "SELECT * FROM articulo ORDER BY no_serie;" ;
            PreparedStatement p = conexion.getConexion().prepareStatement(consulta);
            ResultSet res = p.executeQuery();
            int i = 0;
            while(res.next())
            {
                String no_serie = res.getString("no_serie");
                String nombre = res.getString("nombre");
                String marca = res.getString("marca");
                String descripcion = res.getString("descripcion");
                String area=res.getString("area");
                String capacidad=res.getString("capacidad");
                data[i][0] = no_serie;            
                data[i][1] = nombre;            
                data[i][2] = marca;  
                data[i][3] = descripcion;
                data[i][4] = area;
                data[i][5] = capacidad;
                i++;
            }
            res.close();
         }catch(SQLException e)
         {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
         }
            return data;
        }

    public void borrarArticulo(String codigo)
    {  
            try 
            {                
                String borrar = "DELETE FROM articulo WHERE no_serie = ?;";
                PreparedStatement p = conexion.getConexion().prepareStatement(borrar);            
                p.setString(1, codigo);                   
                p.execute();
                p.close();            
            }catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }            
    }

    public void actualizarArticulo(String no_serie, String nombre, String marca, String descripcion,String area,String capacidad)
    {
       try 
       {            
            String actualizar = "UPDATE articulo set no_serie = ?, nombre = ?, marca = ?, descripcion = ?, area = ?, capacidad = ?"+
                                "WHERE no_serie = ?";
            PreparedStatement p = conexion.getConexion().prepareStatement(actualizar);
            p.setString(1, no_serie);                   
            p.setString(2, nombre);
            p.setString(3, marca);
            p.setString(4, descripcion);
            p.setString(5, area);
            p.setString(6, capacidad);
            p.setString(7, no_serie);
            p.execute();
            p.close();            
         }catch(SQLException e)
         {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
         }
   }      
}