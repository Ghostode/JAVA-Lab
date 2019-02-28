import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class PatientInfo
{
    private final StringProperty GHBH;
    private final StringProperty BRMC;
    private final StringProperty RQSJ;
    private final StringProperty HZLB;

    public PatientInfo(String ghbh, String brmc, String rqsj, String hzlb)
    {
        this.GHBH = new SimpleStringProperty(ghbh);
        this.BRMC = new SimpleStringProperty(brmc);
        this.RQSJ = new SimpleStringProperty(rqsj);
        this.HZLB = new SimpleStringProperty(hzlb);
    }
    public String getGHBH()
    {
        return GHBH.get();
    }
    public void setGHBH(String ghbh)
    {
        GHBH.set(ghbh);
    }
    public StringProperty GHBHProperty()
    {
        return GHBH;
    }
    public String getBRMC()
    {
        return BRMC.get();
    }
    public void setBRMC(String brmc)
    {
        BRMC.set(brmc);
    }
    public StringProperty BRMCProperty()
    {
        return BRMC;
    }
    public String getRQSJ()
    {
        return RQSJ.get();
    }
    public void setRQSJ(String rqsj)
    {
        RQSJ.set(rqsj);
    }
    public StringProperty RQSJProperty()
    {
        return RQSJ;
    }
    public String getHZLB()
    {
        return HZLB.get();
    }
    public void setHZLB(String hzlb)
    {
        HZLB.set(hzlb);
    }
    public StringProperty HZLBProperty()
    {
        return HZLB;
    }
}