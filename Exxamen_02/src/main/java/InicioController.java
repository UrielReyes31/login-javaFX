import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InicioController {
    @FXML
    public TextField txt_username;
    @FXML
    public PasswordField txt_password;
    @FXML
    public Button btn_login;
    @FXML
    public TextField txt_username_UP;
    @FXML
    public TextField txt_password_UP;
    @FXML
    public TextField txt_Email;
    @FXML
    private AnchorPane panel_login;
    @FXML
    private AnchorPane panel_SignUp;
    private static String LIMPIAR = "";

    //Recuerda hacer esto porque siempre es bueno tenerlos fuera de los metodos y con atributos privados
    private Connection connection = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public void LoginpaneShow(){//cambia la vista depende si queremos iniciar session o registrarnos
        panel_login.setVisible(true);
        panel_SignUp.setVisible(false);
    }
    public void Loginpane_up_Show(){
        panel_login.setVisible(false);
        panel_SignUp.setVisible(true);
    }
    @FXML
    private void Login(ActionEvent actionEvent) throws SQLException {
          try {
          connection = myconection.connection();// se inicia la coneccion a base de datos
          //como buena practica, siempre intenta no poner caracteres especiales en los nombres de tablas y columnas
          //por ejemplo la letra ñ no es bien vista
          String query = "select Nombre_usuario, Contraseña from usuario where Nombre_usuario = ?";
          pst = connection.prepareStatement(query);
          pst.setString(1, txt_username.getText());
          rs = pst.executeQuery();
          if (rs.next()) {
              if(BCrypt.checkpw(txt_password.getText(), rs.getString(2))) {// se hace la comparacion de la contraseña de la base de datos y la que se utilizo
                  System.out.println("usuario y contraseña correctos");//si son iguales manda este mensaje
                 LIMPIAR();//limpia los txtfiel
                 btn_login.getScene().getWindow().hide();
                 Parent root = FXMLLoader.load(getClass().getResource("/Bienvenido.fxml"));
                 Stage mainStage = new Stage();
                 Scene scene = new Scene(root);
                 mainStage.setScene(scene);
                 mainStage.show();
              } else {
                  System.out.println("Contraseña incorrecta");
              }
          } else
              System.out.println("usuario incorrecto");

          }catch (SQLException | IOException e){
                JOptionPane.showMessageDialog(null, e);
                System.out.println(e);
          } finally {
              //al final se cierra la coneccion de la base de datos
              connection.close();
              LIMPIAR();
          }
    }
    @FXML
    public void add_usuario(ActionEvent actionEvent) throws SQLException{
        connection = myconection.connection();

        String query = "insert into usuario(Nombre_usuario, Contraseña,Correo)values (?,?,?)";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, txt_username_UP.getText());
            String hashed = BCrypt.hashpw(txt_password_UP.getText(),BCrypt.gensalt(10));
            pst.setString(2, hashed);
            pst.setString(3, txt_Email.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null,"Usuario Registrado");
            txt_username_UP.setText(LIMPIAR);
            txt_password_UP.setText(LIMPIAR);
            txt_Email.setText(LIMPIAR);

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
        } finally {
            connection.close();
        }
    }
    void LIMPIAR(){
        txt_username.setText(LIMPIAR);
        txt_password.setText(LIMPIAR);
    }
}
