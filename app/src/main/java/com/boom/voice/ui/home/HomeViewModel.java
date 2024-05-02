package com.boom.voice.ui.home;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
// ViewModel, отображающий список голосований
public class HomeViewModel extends ViewModel {
    // данные для текста на домашнем фрагменте
    private final MutableLiveData<String> mText;
    // Конструктор класса HomeViewModel
    public HomeViewModel() {
        // Инициализация живых данных
        mText = new MutableLiveData<>();
        // Установка значения по умолчанию
        mText.setValue("This is home fragment");
    }
    // Получение данных для текста
    public LiveData<String> getText() {
        return mText;
    }
}