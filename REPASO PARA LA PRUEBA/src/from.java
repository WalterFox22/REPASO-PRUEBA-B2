import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class from {
    public JTextPane titulo;
    public JTextField ingresoPlaca;
    public JTextField ingresoMarca;
    public JTextField ingresoCilindraje;
    public JTextField ingresoCombustible;
    public JTextField ingresarColor;
    public JTextField ingresoPropietario;
    public JTextPane placa;
    public JTextPane marca;
    public JTextPane cilindraje;
    public JTextPane combistible;
    public JTextPane colores;
    public JTextPane propietario;
    public JButton registrar;
    public JPanel panel1;
    public JButton limpiar;
    public JTextArea tituloBuscar;
    public JButton buscar;
    public JTextField ingresoBusqueda;
    public JLabel imprimir;


    public from() {
        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placastr=ingresoPlaca.getText();
                String marcastr=ingresoMarca.getText();
                double cilindrajeDb=Double.parseDouble(ingresoCilindraje.getText());
                String combustiblestr=ingresoCombustible.getText();
                String colorstr=ingresarColor.getText();
                String propietariostr=ingresoPropietario.getText();

                Vehiculo coche= new Vehiculo(placastr,marcastr,cilindrajeDb,combustiblestr,colorstr,propietariostr);

                String url = "jdbc:mysql://localhost:3306/VEHICULOS";
                String user = "root";
                String password = "123456";

                String sql = "INSERT INTO vehiculos (Placa, Marca, Cilindraje,Tipo_de_combustible, Color, Propietario ) VALUES(?,?,?,?,?,?)";

                try (Connection connection = DriverManager.getConnection(url, user, password)) {
                    PreparedStatement cadenaPreparada = connection.prepareStatement(sql);

                    cadenaPreparada.setString(1, coche.getPlaca());
                    cadenaPreparada.setString(2, coche.getMarca());
                    cadenaPreparada.setDouble(3, coche.getCilindraje());
                    cadenaPreparada.setString(4, coche.getTipo_combustible());
                    cadenaPreparada.setString(5, coche.getColor());
                    cadenaPreparada.setString(6, coche.getPropietario());
                    cadenaPreparada.executeUpdate();

                    JOptionPane.showMessageDialog(panel1, "Se ha registrado el coche");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel1, "Error al registrar el coche", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresoPlaca.setText("");
                ingresoMarca.setText("");
                ingresoCilindraje.setText("");
                ingresoCombustible.setText("");
                ingresarColor.setText("");
                ingresoPropietario.setText("");
                ingresoBusqueda.setText("");
                imprimir.setText("");

            }
        });
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String busqueda = ingresoBusqueda.getText().trim();
                String url = "jdbc:mysql://localhost:3306/VEHICULOS";
                String user = "root";
                String password = "123456";

                String sql = "SELECT * FROM vehiculos WHERE Placa = ?";

                try (Connection connection = DriverManager.getConnection(url, user, password);
                     PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setString(1, busqueda);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        String placa = resultSet.getString("Placa");
                        String marca = resultSet.getString("Marca");
                        double cilindraje = resultSet.getDouble("Cilindraje");
                        String combustible = resultSet.getString("Tipo_de_combustible");
                        String color = resultSet.getString("Color");
                        String propietario = resultSet.getString("Propietario");

                        imprimir.setText("<html>Placa: " + placa + "<br>Marca: " + marca +
                                "<br>Cilindraje: " + cilindraje + "<br>Combustible: " + combustible +
                                "<br>Color: " + color + "<br>Propietario: " + propietario + "</html>");
                    } else {
                        imprimir.setText("No se encontró ningún registro con la placa proporcionada.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel1, "Error al buscar el registro", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        }

    }