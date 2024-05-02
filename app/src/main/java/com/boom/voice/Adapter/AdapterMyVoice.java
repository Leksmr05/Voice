package com.boom.voice.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.boom.voice.R;
import com.boom.voice.ui.data.MyvoiceInfo;
import java.util.List;
public class AdapterMyVoice extends RecyclerView.Adapter<AdapterMyVoice.ViewHolder>{
    private final LayoutInflater inflater; // LayoutInflater для инфляции макета XML
    private final List<MyvoiceInfo> myvoiceInfoList; // Список для хранения объектов MyvoiceInfo
    // Конструктор класса AdapterMyVoice
    public AdapterMyVoice(Context context, List<MyvoiceInfo> myvoiceInfoList) {
        this.myvoiceInfoList = myvoiceInfoList; // Инициализация списка объектов MyvoiceInfo
        this.inflater = LayoutInflater.from(context); // Инициализация LayoutInflater
    }
    // Создание новых представлений (вызывается менеджером макета)
    @Override
    public AdapterMyVoice.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Надуть макет для каждого элемента в RecyclerView
        View view = inflater.inflate(R.layout.my_voice_list, parent, false);
        return new ViewHolder(view); // Возвращаем новый экземпляр ViewHolder
    }
    // Замена содержимого представления (вызывается менеджером макета)
    @Override
    public void onBindViewHolder(AdapterMyVoice.ViewHolder holder, int position) {
        MyvoiceInfo partners = myvoiceInfoList.get(position); // Получить объект MyvoiceInfo по заданной позиции
        holder.nameView.setText(partners.getName()); // Установить имя в TextView
        holder.descriptionView.setText(partners.getVoice()); // Установить описание голоса в TextView
    }
    // Возвращает размер набора данных (вызывается менеджером макета)
    @Override
    public int getItemCount() {
        return myvoiceInfoList.size(); // Возвращаем размер списка MyvoiceInfo
    }
    // Класс ViewHolder для хранения ссылок на представления для каждого элемента в RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView,  descriptionView; // TextView для отображения имени и описания
        ViewHolder(View view){
            super(view); // Вызов конструктора суперкласса
            nameView = view.findViewById(R.id.nameList); // Инициализация TextView для имени
            descriptionView = view.findViewById(R.id.List); // Инициализация TextView для описания
        }
    }
}