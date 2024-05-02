package com.boom.voice.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.boom.voice.R;
import com.boom.voice.ui.data.VoiceInfo;

import java.util.List;
public class AdapterVoice extends RecyclerView.Adapter<AdapterVoice.ViewHolder>{
    // Интерфейс для обработки нажатий на элементы RecyclerView
    public interface OnStateClickListener{
        void onStateClick(VoiceInfo partners , int position);
    }
    private final OnStateClickListener onClickListener; // Обработчик нажатий
    private final LayoutInflater inflater; // LayoutInflater для инфляции макета XML
    private final List<VoiceInfo> voiceInfoList; // Список для хранения объектов VoiceInfo
    // Конструктор класса AdapterVoice
    public AdapterVoice(OnStateClickListener onClickListener, Context context, List<VoiceInfo> voiceInfoList) {
        this.onClickListener = onClickListener; // Установка обработчика нажатий
        this.voiceInfoList = voiceInfoList; // Инициализация списка объектов VoiceInfo
        this.inflater = LayoutInflater.from(context); // Инициализация LayoutInflater
    }
    // Создание новых представлений (вызывается менеджером макета)
    @Override
    public AdapterVoice.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Надуть макет для каждого элемента в RecyclerView
        View view = inflater.inflate(R.layout.voice_list, parent, false);
        return new ViewHolder(view); // Возвращаем новый экземпляр ViewHolder
    }
    // Замена содержимого представления (вызывается менеджером макета)
    @Override
    public void onBindViewHolder(AdapterVoice.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        VoiceInfo partners = voiceInfoList.get(position); // Получить объект VoiceInfo по заданной позиции
        holder.nameView.setText(partners.getName()); // Установить имя в TextView
        // Установка обработчика нажатий на элемент списка
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickListener.onStateClick(partners, position); // Вызов метода из интерфейса для обработки нажатия
            }
        });
    }
    // Возвращает размер набора данных (вызывается менеджером макета)
    @Override
    public int getItemCount() {
        return voiceInfoList.size(); // Возвращаем размер списка VoiceInfo
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