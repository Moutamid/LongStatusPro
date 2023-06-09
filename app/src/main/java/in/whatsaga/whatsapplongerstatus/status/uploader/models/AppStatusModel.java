package in.whatsaga.whatsapplongerstatus.status.uploader.models;

public class AppStatusModel
{
    public String app_link;
    public String image_url;
    public String is_live;
    public String m_des;
    public String m_title;

    public AppStatusModel() {
    }

    public AppStatusModel(String app_link, String image_url, String is_live, String m_des, String m_title) {
        this.app_link = app_link;
        this.image_url = image_url;
        this.is_live = is_live;
        this.m_des = m_des;
        this.m_title = m_title;
    }

    public String getApp_link() {
        return app_link;
    }

    public void setApp_link(String app_link) {
        this.app_link = app_link;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getIs_live() {
        return is_live;
    }

    public void setIs_live(String is_live) {
        this.is_live = is_live;
    }

    public String getM_des() {
        return m_des;
    }

    public void setM_des(String m_des) {
        this.m_des = m_des;
    }

    public String getM_title() {
        return m_title;
    }

    public void setM_title(String m_title) {
        this.m_title = m_title;
    }
}
