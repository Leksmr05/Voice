package com.boom.voice.ui.ui;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.boom.voice.R;
import com.boom.voice.ui.data.VoiceInfo;
import com.boom.voice.ui.data.profile_info;
import com.boom.voice.ui.data.user_info;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
// Активность для голосования
public class GivingVoiceActivity extends AppCompatActivity {
    // Элементы интерфейса
    TextView giving, butZa, butProtiv, usersVoice, usersZa, usersProtiv;
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    user_info userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giving_voice); // Установка макета активности
        // Инициализация элементов интерфейса
        giving = findViewById(R.id.giving);
        butZa = findViewById(R.id.butZa);
        butProtiv = findViewById(R.id.butProtiv);
        usersVoice = findViewById(R.id.usersVoice);
        usersZa = findViewById(R.id.usersZa);
        usersProtiv = findViewById(R.id.usersProtiv);
        // Инициализация Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        // Получение информации о текущем пользователе
        userInfo = profile_info.getInstance().getMy_user_info();
        // Получение информации о выбранном элементе
        Bundle arguments = getIntent().getExtras();
        VoiceInfo voiceInfo;
        if (arguments != null) {
            voiceInfo = (VoiceInfo) arguments.getSerializable(VoiceInfo.class.getSimpleName());
            // Установка названия элемента в заголовок
            giving.setText(voiceInfo.getName());
            // Получение информации о голосах
            List<String> item = voiceInfo.getItem();
            String text = "Проголосовали: \n \n";
            for (int i = 0; i < item.size(); i++) {
                text = text + " " + item.get(i);
            }
            usersVoice.setText(text);
            usersProtiv.setText(String.valueOf(voiceInfo.getProtiv().size()));
            if (voiceInfo.getZa().size() == 0 || voiceInfo.getZa().get(0).equals("")) {
                usersZa.setText("0");
            } else {
                usersZa.setText(String.valueOf(voiceInfo.getZa().size()));
            }
            if (voiceInfo.getProtiv().size() == 0 || voiceInfo.getProtiv().get(0).equals("")) {
                usersProtiv.setText("0");
            } else {
                usersProtiv.setText(String.valueOf(voiceInfo.getProtiv().size()));
            }
            // Обработчик клика по кнопке "За"
            butZa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Добавление голоса "За" и обновление базы данных
                    setVoiceInfo(voiceInfo.getId(), userInfo.getName(), "za");
                    try {
                        setMyVoiceFirebase(voiceInfo.getName(), "За");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    // Переход к главному экрану
                    Intent intent = new Intent(GivingVoiceActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            // Обработчик клика по кнопке "Против"
            butProtiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Добавление голоса "Против" и обновление базы данных
                    setVoiceInfo(voiceInfo.getId(), userInfo.getName(), "protiv");
                    try {
                        setMyVoiceFirebase(voiceInfo.getName(), "Против");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    // Переход к главному экрану
                    Intent intent = new Intent(GivingVoiceActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
    // Метод для добавления информации о голосе в базу данных Firestore
    public void setVoiceInfo(String uid, String name, String field) {
        CollectionReference cities = db.collection("voice");
        final Map<String, Object> addUserToArrayMap = new HashMap<>();
        addUserToArrayMap.put(field, FieldValue.arrayUnion(name));
        final Map<String, Object> addUserToItem = new HashMap<>();
        addUserToItem.put("item", FieldValue.arrayUnion(name));
        cities.document(uid).update(addUserToArrayMap);
        cities.document(uid).update(addUserToItem);
    }
    // Метод для добавления информации о голосе пользователя в базу данных Firestore
    public void setMyVoiceFirebase(String name, String voice) throws Exception {
        String uid = user.getUid();
        CollectionReference cities = db.collection("myvoice");
        Map<String, Object> data1 = new HashMap<>();
        data1.put("id", uid);
        data1.put("name", name);
        data1.put("myvoice", voice);
        cities.document(createTransactionID()).set(data1);
    }
    // Метод для создания уникального идентификатора
    public String createTransactionID() throws Exception {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}
