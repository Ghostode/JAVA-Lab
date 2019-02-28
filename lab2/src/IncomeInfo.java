import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class IncomeInfo
{
    private final StringProperty KSMC;
    private final StringProperty YSBH;
    private final StringProperty YSMC;
    private final StringProperty HZLC;
    private final StringProperty GHRC;
    private final StringProperty SRHJ;

    public IncomeInfo(String ksmc, String ysbh, String ysmc, String hzlc, String ghrc, String srhj)
    {
        this.KSMC = new SimpleStringProperty(ksmc);
        this.YSBH = new SimpleStringProperty(ysbh);
        this.YSMC = new SimpleStringProperty(ysmc);
        this.HZLC = new SimpleStringProperty(hzlc);
        this.GHRC = new SimpleStringProperty(ghrc);
        this.SRHJ = new SimpleStringProperty(srhj);
    }
    public String getKSMC()
    {
        return KSMC.get();
    }
    public void setKSMC(String ksmc)
    {
        KSMC.set(ksmc);
    }
    public StringProperty KSMCProperty()
    {
        return KSMC;
    }
    public String getYSBH()
    {
        return YSBH.get();
    }
    public void setYSBH(String ysbh)
    {
        YSBH.set(ysbh);
    }
    public StringProperty YSBHProperty()
    {
        return YSBH;
    }
    public String getYSMC()
    {
        return YSMC.get();
    }
    public void setYSMC(String ysmc)
    {
        YSMC.set(ysmc);
    }
    public StringProperty YSMCProperty()
    {
        return YSMC;
    }
    public String getHZLC()
    {
        return HZLC.get();
    }
    public void setHZLC(String hzlc)
    {
        HZLC.set(hzlc);
    }
    public StringProperty HZLCProperty()
    {
        return HZLC;
    }
    public String getGHRC()
    {
        return GHRC.get();
    }
    public void setGHRC(String ghrc)
    {
        GHRC.set(ghrc);
    }
    public StringProperty GHRCProperty()
    {
        return GHRC;
    }
    public String getSRHJ()
    {
        return SRHJ.get();
    }
    public void setSRHJ(String srhj)
    {
        SRHJ.set(srhj);
    }
    public StringProperty SRHJProperty()
    {
        return SRHJ;
    }
}