package com.boom.voice.ui.ui;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.boom.voice.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
// Активность для изменения профиля пользователя
public class ProfileRenameActivity extends AppCompatActivity {
    // Элементы интерфейса
    TextView butSave;
    EditText nameEdit, yearEdit, emailEdit;
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_rename); // Установка макета активности
        // Инициализация элементов интерфейса
        butSave = findViewById(R.id.saveName);
        nameEdit = findViewById(R.id.nameEdit);
        yearEdit = findViewById(R.id.yearEdit);
        emailEdit = findViewById(R.id.emailEdit);
        // Инициализация Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        // Обработчик клика по кнопке для сохранения изменений профиля
        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получение введенных пользователем данных
                String name = nameEdit.getText().toString();
                String year = yearEdit.getText().toString();
                String email = emailEdit.getText().toString();
                // Проверка на заполненность всех полей
                if (!name.equals("") && !year.equals("") && !email.equals("")) {
                    // Получение идентификатора пользователя
                    String uid = user.getUid();
                    // Установка информации о пользователе
                    setUserInfo(uid, year, name, email);
                    Intent intent = new Intent(ProfileRenameActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    // Метод для установки информации о пользователе в базу данных Firestore
    public void setUserInfo(String uid, String year, String name, String email) {
        CollectionReference users = db.collection("users");
        Map<String, Object> data = new HashMap<>();
        data.put("year", year);
        data.put("name", name);
        data.put("email", email);
        users.document(uid).set(data);
    }
}