import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.sql.SQLException;

public class MainWindow extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		try
		{
			Parent pane = FXMLLoader.load(getClass().getResource("Main.fxml"));
			primaryStage.setTitle("医院挂号系统");
			primaryStage.setScene(new Scene(pane));
			primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args) 
	{
		try
		{
			ConnectDB db = new ConnectDB();
			db.newConnection();
			launch(args);
			db.closeConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}