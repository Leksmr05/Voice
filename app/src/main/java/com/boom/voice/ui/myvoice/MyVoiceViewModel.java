package com.boom.voice.ui.myvoice;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
// ViewModel для фрагмента, отображающий список пользовательских голосований.
public class MyVoiceViewModel extends ViewModel {
    // данные для текста на фрагменте
    private final MutableLiveData<String> mText;
    // Конструктор класса MyVoiceViewModel
    public MyVoiceViewModel() {
        // Инициализация данных
        mText = new MutableLiveData<>();
        // Установка значения по умолчанию
        mText.setValue("This is dashboard fragment");
    }
    // Получение данных для текста
    public LiveData<String> getText() {
        return mText;
    }
}