import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

@SuppressWarnings("unchecked")

public class DoctorView
{
	@FXML
	private Label ModeLabel;
    @FXML
    private Label LabelBegin;
    @FXML
    private Label LabelEnd;
    @FXML
    private DatePicker BeginDatePicker;
    @FXML
    private DatePicker EndDatePicker;
    @FXML
    private TableView PatientTable;
    @FXML
    private TableColumn ghbhCol;
    @FXML
    private TableColumn brmcCol;
    @FXML
    private TableColumn rqsjCol;
    @FXML
    private TableColumn hzlbCol;
    @FXML
    private TableView IncomeTable;
    @FXML
    private TableColumn ksmcCol;
    @FXML
    private TableColumn ysbhCol;
    @FXML
    private TableColumn ysmcCol;
    @FXML
    private TableColumn hzlcCol;
    @FXML
    private TableColumn ghrcCol;
    @FXML
    private TableColumn srhjCol;


	private ConnectDB db = new ConnectDB();
    private Connection connection = db.getConnection();
    private ObservableList<PatientInfo> patientItems = FXCollections.observableArrayList();
    private ObservableList<IncomeInfo> incomeItems = FXCollections.observableArrayList();

    private String BeginDate, EndDate;
    private boolean BeginReady = false, EndReady = false;

    public void Init()
    {
        BeginDatePicker.valueProperty().addListener((ov,oldv,newv)->{
            if (newv != null && BeginDatePicker.getValue() != null)
            {
                BeginDate = newv.toString() + " 00:00:00";
                BeginReady = true;
            }
        });
        EndDatePicker.valueProperty().addListener((ov,oldv,newv)->{
            if (newv != null && BeginDatePicker.getValue() != null)
            {
                EndDate = newv.toString() + " 23:59:59";
                EndReady = true;
            }
        });
    }

	@FXML
    private void on_menu_Patient_clicked() throws Exception
    {
        patientItems.clear();

        LabelBegin.setVisible(true);
        LabelEnd.setVisible(true);
        BeginDatePicker.setVisible(true);
        EndDatePicker.setVisible(true);
        IncomeTable.setVisible(false);
        PatientTable.setVisible(true);
    	ModeLabel.setText("病人列表");

        ghbhCol.setCellValueFactory(new PropertyValueFactory<PatientInfo, String>("GHBH"));
        brmcCol.setCellValueFactory(new PropertyValueFactory<PatientInfo, String>("BRMC"));
        rqsjCol.setCellValueFactory(new PropertyValueFactory<PatientInfo, String>("RQSJ"));
        hzlbCol.setCellValueFactory(new PropertyValueFactory<PatientInfo, String>("HZLB"));

        Statement state = connection.createStatement();
        String sql = "select GHBH,BRMC,RQSJ,SFZJ "+
                    "from t_brxx,t_hzxx,t_ghxx "+
                    "where t_brxx.BRBH=t_ghxx.BRBH and t_ghxx.HZBH=t_hzxx.HZBH and t_ghxx.THBZ='0'";
        if(BeginReady && EndReady)
            sql += "and t_ghxx.RQSJ >= '" + BeginDate + "' "+
                    "and t_ghxx.RQSJ <= '" + EndDate + "'";
        ResultSet rs = state.executeQuery(sql);
        
        while(rs.next())
        {
            String GHBH = rs.getString("GHBH");
            String BRMC = rs.getString("BRMC");
            String RQ = rs.getDate("RQSJ").toString();
            String SJ = rs.getTime("RQSJ").toString();
            String HZLB = rs.getBoolean("SFZJ") ? "专家号" : "普通号";

            patientItems.add(new PatientInfo(GHBH, BRMC, RQ + "  " + SJ, HZLB));
        }
        PatientTable.setItems(patientItems);
    }
    @FXML
    private void on_menu_Income_clicked() throws Exception
    {
        incomeItems.clear();

        LabelBegin.setVisible(true);
        LabelEnd.setVisible(true);
        BeginDatePicker.setVisible(true);
        EndDatePicker.setVisible(true);
        PatientTable.setVisible(false);
        IncomeTable.setVisible(true);
    	ModeLabel.setText("收入列表");

        ksmcCol.setCellValueFactory(new PropertyValueFactory<IncomeInfo, String>("KSMC"));
        ysbhCol.setCellValueFactory(new PropertyValueFactory<IncomeInfo, String>("YSBH"));
        ysmcCol.setCellValueFactory(new PropertyValueFactory<IncomeInfo, String>("YSMC"));
        hzlcCol.setCellValueFactory(new PropertyValueFactory<IncomeInfo, String>("HZLC"));
        ghrcCol.setCellValueFactory(new PropertyValueFactory<IncomeInfo, String>("GHRC"));
        srhjCol.setCellValueFactory(new PropertyValueFactory<IncomeInfo, String>("SRHJ"));

        Statement state = connection.createStatement();
        String sql = "select HZBH,KSMC,t_ghxx.YSBH,YSMC,SFZJ,COUNT(GHRC),SUM(GHFY) "+
                    "from t_ksxx,t_ksys,t_ghxx "+
                    "where t_ksxx.KSBH = t_ksys.KSBH and t_ksys.YSBH = t_ghxx.YSBH and t_ghxx.THBZ='0' ";
        if(BeginReady && EndReady)
            sql += "and t_ghxx.RQSJ >= '" + BeginDate + "' "+
                    "and t_ghxx.RQSJ <= '" + EndDate + "'";
            sql += "group by HZBH, KSMC, YSBH, YSMC, SFZJ";
        ResultSet rs = state.executeQuery(sql);
        
        while(rs.next())
        {
            String KSMC = rs.getString("KSMC");
            String YSBH = rs.getString("YSBH");
            String YSMC = rs.getString("YSMC");
            String HZLC = rs.getBoolean("SFZJ") ? "专家号" : "普通号";
            int GHRC = rs.getInt(6);
            Double SRHJ = rs.getDouble(7);

            incomeItems.add(new IncomeInfo(KSMC, YSBH, YSMC, HZLC, String.valueOf(GHRC), String.valueOf(SRHJ)));
        }
        IncomeTable.setItems(incomeItems);
    }
	@FXML
    private void on_menu_Exit_clicked()
    {
    	ModeLabel.getScene().getWindow().hide();
    }
}