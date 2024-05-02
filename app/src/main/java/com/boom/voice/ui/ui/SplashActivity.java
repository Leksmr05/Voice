package com.boom.voice.ui.ui;
import static android.content.ContentValues.TAG;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.boom.voice.MainActivity;
import com.boom.voice.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.boom.voice.ui.data.FireBaseLoad;
import com.boom.voice.ui.data.profile_info;
import com.boom.voice.ui.data.user_info;
// Активность для отображения заставки при запуске приложения
public class SplashActivity extends AppCompatActivity {
    // Пользователь Firebase
    private FirebaseUser aUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout); // Установка макета заставки
        // Получение текущего пользователя Firebase
        aUser = FirebaseAuth.getInstance().getCurrentUser();
        // Проверка, авторизован ли пользователь
        if (aUser != null) {
            // Если пользователь авторизован
            String uid = aUser.getUid(); // Получение идентификатора пользователя
            FireBaseLoad data = new FireBaseLoad(this); // Инициализация загрузки данных из Firebase
            data.getUserInfo(uid); // Получение информации о пользователе
            data.getVoiceInfo(); // Получение информации о голосованиях
            data.getMyVoiceInfo(); // Получение информации о пользовательских голосованиях
            // Проверка доступности сети
            if (isNetworkAvailable(this)) {
                // Если сеть доступна, переход к главной активности через некоторое время
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Получение информации о пользователе
                        user_info userInfo = profile_info.getInstance().getMy_user_info();
                        Intent i = new Intent(SplashActivity.this, MainActivity.class); // Создание намерения для перехода к главной активности
                        startActivity(i); // Запуск главной активности
                    }
                }, 4000); // Задержка перед переходом (в миллисекундах)
            }
        } else {
            // Если пользователь не авторизован, переход к активности авторизации
            Intent i = new Intent(SplashActivity.this, AuthActivity.class);
            startActivity(i);
            Log.d(TAG, "onAuthStateChanged:signed_out"); // Логирование выхода пользователя
        }
    }
    // Метод для проверки доступности сети
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}




