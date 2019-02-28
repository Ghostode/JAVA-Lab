import javafx.scene.control.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.lang.Integer;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("unchecked")

public class PatientView
{
    @FXML
    private ChoiceBox ksmc;
    @FXML
    private ChoiceBox ysxm;
	@FXML
	private ChoiceBox hzlb;
	@FXML
	private ChoiceBox hzmc;
	@FXML
	private TextField yjje;
	@FXML
	public TextField jkje;
	@FXML
	private TextField zlje;
	@FXML
	private TextField ghhm;

    private ConnectDB db = new ConnectDB();
    private Connection connection = db.getConnection();

    private ObservableList ysxmItems = FXCollections.observableArrayList();
    private ObservableList hzmcItems = FXCollections.observableArrayList();
    private ObservableList hzlbItems = FXCollections.observableArrayList();

    private static int ghrc = 0;
    public String KSBH;
    public String HZBH;
    public String YSBH;
    public String BRBH;

    public void Init()
    {
        zlje.setEditable(false);
        ghhm.setEditable(false);

        ksmc.setItems(FXCollections.observableArrayList("神经科","感染科","保健科","皮肤科","口腔科","中医科"));

        ksmc.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{
            try
            {
                hzlbItems.clear();
                hzmcItems.clear();
                ysxmItems.clear();
                yjje.clear();
                zlje.clear();
                ghhm.clear();
                Statement state = connection.createStatement();
                String sql = new String();
                switch(newv.intValue())
                {
                    case 0:
                        sql = "select * from t_ksys where KSBH='000001'";
                        break;
                    case 1:
                        sql = "select * from t_ksys where KSBH='000002'";
                        break;
                    case 2:
                        sql = "select * from t_ksys where KSBH='000003'";
                        break;
                    case 3:
                        sql = "select * from t_ksys where KSBH='000004'";
                        break;
                    case 4:
                        sql = "select * from t_ksys where KSBH='000005'";
                        break;
                    case 5:
                        sql = "select * from t_ksys where KSBH='000006'";
                        break;
                }
                ResultSet rs = state.executeQuery(sql);
                while(rs.next())
                {
                    String YSMC = rs.getString("YSMC");
                    ysxmItems.add(new String(YSMC));
                    this.KSBH = rs.getString("KSBH");
                    this.YSBH = rs.getString("YSBH");
                }
                ysxm.setItems(ysxmItems);
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        });
        ysxm.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{
            try
            {
                hzlbItems.clear();
                hzmcItems.clear();
                yjje.clear();
                zlje.clear();
                ghhm.clear();
                Statement state = connection.createStatement();
                String sql = "select HZBH,HZMC from t_hzxx where KSBH='"+this.KSBH+"'";
                ResultSet rs = state.executeQuery(sql);
                while(rs.next())
                {
                    String HZMC = rs.getString("HZMC");
                    hzmcItems.add(new String(HZMC));
                    this.HZBH = rs.getString("HZBH");
                }
                hzmc.setItems(hzmcItems);
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        });
        hzmc.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{
            try
            {
                hzlbItems.clear();
                yjje.clear();
                zlje.clear();
                ghhm.clear();
                Statement state = connection.createStatement();
                String sql = "select SFZJ from t_hzxx where HZMC='"+ hzmc.getItems().get(0) +"'";
                ResultSet rs = state.executeQuery(sql);
                while(rs.next())
                {
                    String HZLB = rs.getBoolean("SFZJ") ? "专家号" : "普通号";
                    hzlbItems.add(new String(HZLB));
                }
                hzlb.setItems(hzlbItems);
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        });
        hzlb.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{
            try
            {
                ghrc += 1;
                Statement state = connection.createStatement();
                ResultSet re = state.executeQuery("select GHRS from t_hzxx where HZMC='"+ hzmc.getItems().get(0) +"'");
                re.next();
                if(ghrc > re.getInt("GHRS"))
                {
                    Alert message = new Alert(Alert.AlertType.ERROR,"挂号人次已达上限");
                    message.showAndWait();
                }
                else
                {
                    yjje.clear();
                    zlje.clear();
                    ghhm.clear();
                    String sql = "select GHFY from t_hzxx where HZMC='"+ hzmc.getItems().get(0) +"'";
                    ResultSet rs = state.executeQuery(sql);
                    if(rs.next())
                    {
                        yjje.setText(rs.getBigDecimal("GHFY").toString());
                    }
                    BigDecimal jkjed = new BigDecimal(jkje.getText());
                    BigDecimal yjjed = new BigDecimal(yjje.getText());
                    BigDecimal zl = jkjed.subtract(yjjed);

                    if(zl.compareTo(BigDecimal.ZERO) == -1)
                    {
                        Alert message = new Alert(Alert.AlertType.ERROR,"余额不足");
                        message.showAndWait();
                        zlje.setText("余额不足");
                    }
                    else
                    {
                        zlje.setText(zl.toString());
                        SimpleDateFormat df = new SimpleDateFormat("MMdd");//设置日期格式
                        String date = df.format(new Date());
                        Integer i = new Integer(ghrc);
                        String GHBH = date + "0" + i.toString();
                        ghhm.setText(GHBH);

                        SimpleDateFormat cf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        String currentTime = cf.format(new Date());
                        state.execute("insert into t_ghxx(GHBH,HZBH,YSBH,BRBH,GHRC,THBZ,GHFY,RQSJ) values('"+ghhm.getText()+"','"+this.HZBH+"','"+this.YSBH+"','"+this.BRBH+"','"+new Integer(ghrc).toString()+"','1','"+yjje.getText()+"','"+currentTime+"')");
                    }
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        });
    }
	@FXML
    private void on_btn_OK_clicked(ActionEvent event)
    {
        try
        {
            Statement state = connection.createStatement();
            state.executeUpdate("update t_ghxx set THBZ='0' where GHBH='"+ghhm.getText()+"'");
            state.executeUpdate("update t_brxx set YCJE='"+zlje.getText()+"' where BRBH='"+this.BRBH+"'");
            Alert message = new Alert(Alert.AlertType.INFORMATION,"挂号成功");
            message.showAndWait();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
	@FXML
    private void on_btn_Clr_clicked(ActionEvent event)
    {
        ysxmItems.clear();
        hzmcItems.clear();
        hzlbItems.clear();
    	yjje.clear();
    	zlje.clear();
    	ghhm.clear();
    }
    @FXML
    private void on_btn_Exit_clicked(ActionEvent event)
    {
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }
}