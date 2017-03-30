package team.unicorn.vsb.twoclicks.activity.model;

/**
 * Created by lenovo on 27/1/17.
 */

public class Job {
    public String name;
    public String jobcard;
    public String panchayath;
    public String district;
    public String mobileno;
    public String demandStatus;
    public String poStatus;
    public String gpStatus;
    public String duration;

    public Job()
    {}

    public String getName()
    {
        return name;
    }

    public String getJobcard()
    {
        return jobcard;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPanchayath()
    {
        return panchayath;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setPanchayath(String panchayath)
    {
        this.panchayath = panchayath;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }

    public String getMobileno()
    {
        return mobileno;
    }

    public String getPoStatus()
    {
        return poStatus;
    }

    public String getGpStatus()
    {
        return gpStatus;
    }

    public String getDemandStatus()
    {
        return demandStatus;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setMobileno(String mobileno)
    {
        this.mobileno = mobileno;
    }

    public void setDemandStatus(String demandStatus)
    {
        this.demandStatus = demandStatus;
    }

    public void setPoStatus(String poStatus)
    {
        this.poStatus = poStatus;
    }

    public void setGpStatus(String gpStatus)
    {
        this.gpStatus = gpStatus;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public void setJobcard(String jobcard)
    {
        this.jobcard = jobcard;
    }

}

