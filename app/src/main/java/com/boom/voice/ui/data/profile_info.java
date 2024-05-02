package com.boom.voice.ui.data;
import java.util.ArrayList;
// Этот класс представляет информацию о профиле, включая информацию о голосе, информацию о пользователе и др.
public class profile_info {
    private static profile_info dataObject = null;
    ArrayList<VoiceInfo> mInfos = new ArrayList<>();
    ArrayList<MyvoiceInfo> myvoiceInfos = new ArrayList<>();
    user_info my_user_info = new user_info();
    public ArrayList<VoiceInfo> getVoiceInfos() {
        return mInfos;
    }
    public void setVoiceInfos(ArrayList<VoiceInfo> mapMarketInfos) {
        this.mInfos = mapMarketInfos;
    }
    public ArrayList<MyvoiceInfo> getMyvoice() {
        return myvoiceInfos;
    }
    public void setMyvoice(ArrayList<MyvoiceInfo> Infos) {
        this.myvoiceInfos = Infos;
    }
    public user_info getMy_user_info() {
        return my_user_info;
    }
    public void setMy_user_info(user_info my_user_info) {
        this.my_user_info = my_user_info;
    }
    public static profile_info getInstance() {
        if (dataObject == null)
            dataObject = new profile_info();
        return dataObject;
    }
}

