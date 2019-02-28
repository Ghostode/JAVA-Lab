import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class ConnectDB
{
	private static Connection connection;
	private String driver;
	private String url;
	private String user;
	private String password;
	public ConnectDB()
	{
		super();
		this.driver = "com.mysql.jdbc.Driver";
		this.url = "jdbc:mysql://localhost:3306/hospital?useUnicode=true&characterEncoding=utf-8&useSSL=false";
		this.user = "root";
		this.password = "zhdmysql";
	}
	public void newConnection() throws SQLException
	{
		try
		{
			Class.forName(this.driver);
			this.connection = DriverManager.getConnection(url,user,password);
			if(!connection.isClosed())
				System.out.println("Database connected!");
		}
		catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}
	public void closeConnection() throws SQLException
	{
		try
		{
			this.connection.close();
			if(connection.isClosed())
				System.out.println("Database disconnected!");
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}
	public Connection getConnection()
	{
		return this.connection;
	}
}