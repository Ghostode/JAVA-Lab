import javafx.scene.control.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.Date;
import java.text.SimpleDateFormat;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.event.ActionEvent;

public class Control
{
	@FXML
	private RadioButton Doctor_sel;
	@FXML
	private RadioButton Patient_sel;
    @FXML
    private TextField User;
    @FXML
    private PasswordField Password;

    private static Stage stage = new Stage();
    private ConnectDB db = new ConnectDB();
    private Connection connection = db.getConnection();

	@FXML
    private void on_btn_Login_clicked(ActionEvent event) throws Exception
    {
        if(User.getText().equals("")||Password.getText().equals(""))
        {
            Alert message = new Alert(Alert.AlertType.ERROR,"输入不能为空");
            message.showAndWait();
            return;
        }
        if(Doctor_sel.isSelected())
        {
            Statement state = connection.createStatement();
            String sql = "select * from t_ksys where YSBH='"+User.getText()+"' and DLKL='"+Password.getText()+"'";
            ResultSet re = state.executeQuery(sql);
            if(re.next())
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String currentTime = df.format(new Date());
                System.out.println(re.getString(3)+" 登录成功！ 登录时间："+currentTime);
                sql = "update t_ksys set DLRQ='"+currentTime+"' where YSBH='"+re.getString(1)+"'";
                state.executeUpdate(sql);
                try
                {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("DoctorView.fxml"));
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Parent pane = fxmlLoader.load();
                    stage.setTitle("医生界面");
                    stage.setScene(new Scene(pane));
                    stage.setResizable(false);
                    DoctorView controller = fxmlLoader.getController();
                    controller.Init();
                    stage.show();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert message = new Alert(Alert.AlertType.ERROR,"用户名或密码不正确");
                message.showAndWait();
            }
            User.clear();
            Password.clear();
        }
        if(Patient_sel.isSelected())
        {
            Statement state = connection.createStatement();
            String sql = "select * from t_brxx where BRBH='"+User.getText()+"' and DLKL='"+Password.getText()+"'";
            ResultSet re = state.executeQuery(sql);
            if(re.next())
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String currentTime = df.format(new Date());
                String YCJE = re.getString("YCJE");
                String BRBH = re.getString("BRBH");
                System.out.println(re.getString(2)+" 登录成功！ 登录时间："+currentTime);
                sql = "update t_brxx set DLRQ='"+currentTime+"' where BRBH='"+BRBH+"'";
                state.executeUpdate(sql);
                try
                {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("PatientView.fxml"));
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Parent pane = fxmlLoader.load();
                    stage.setTitle("病人挂号界面"); 
                    stage.setScene(new Scene(pane));
                    stage.setResizable(false);
                    PatientView controller = fxmlLoader.getController();
                    controller.jkje.setText(YCJE);
                    controller.BRBH = BRBH;
                    controller.Init();
                    stage.show();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert message = new Alert(Alert.AlertType.ERROR,"用户名或密码不正确");
                message.showAndWait();
            }
            User.clear();
            Password.clear();
        }
    }
    @FXML
    private void on_btn_Exit_clicked(ActionEvent event)
    {
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }
}