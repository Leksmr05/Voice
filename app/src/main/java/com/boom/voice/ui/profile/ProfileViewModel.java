package com.boom.voice.ui.profile;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
// ViewModel для фрагмента профиля, отображающий информацию о пользователе и предоставляющий возможность управления профилем.
public class ProfileViewModel extends ViewModel {
    // данные для текста на фрагменте
    private final MutableLiveData<String> mText;
    // Конструктор класса ProfileViewModel
    public ProfileViewModel() {
        // Инициализация живых данных
        mText = new MutableLiveData<>();
        // Установка значения по умолчанию
        mText.setValue("This is notifications fragment");
    }
    // Получение данных для текста
    public LiveData<String> getText() {
        return mText;
    }
}