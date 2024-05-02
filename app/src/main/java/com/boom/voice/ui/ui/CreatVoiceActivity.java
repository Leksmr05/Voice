package com.boom.voice.ui.ui;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.boom.voice.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
// Активность для создания нового элемента для голосования
public class CreatVoiceActivity extends AppCompatActivity {
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    // Элементы интерфейса
    EditText nameEdit;
    TextView creatButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_voice); // Установка макета активности
        // Инициализация элементов интерфейса
        nameEdit = findViewById(R.id.idCreatVoice);
        creatButton = findViewById(R.id.creatButton);
        // Инициализация Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // Обработчик клика по кнопке "Создать"
        creatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString(); // Получение названия элемента
                if (!name.equals("")) { // Проверка наличия названия
                    try {
                        setVoiceFirebase(name); // Добавление нового элемента в базу данных
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    // Переход к главному экрану
                    Intent i = new Intent(CreatVoiceActivity.this, SplashActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    // Метод для добавления нового элемента в базу данных Firestore
    public void setVoiceFirebase(String name) throws Exception {
        CollectionReference cities = db.collection("voice"); // Ссылка на коллекцию "voice"
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", name); // Название элемента
        cities.document(createTransactionID()).set(data1); // Добавление документа с уникальным идентификатором
    }
    // Метод для создания уникального идентификатора
    public String createTransactionID() throws Exception {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(); // Генерация случайного UUID
    }
}