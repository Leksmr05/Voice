package com.boom.voice.ui.ui;
import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import java.util.HashMap;
import java.util.Map;
import com.boom.voice.R;
// Активность для регистрации нового пользователя
public class RegistrUserActivity extends AppCompatActivity {
    // Поля для ввода данных
    EditText emailEditText, nameEditText, passwordEditText;
    // Кнопки и текстовое поле
    TextView registrTextView;
    ImageView nextImageView;
    // Firebase
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr_user_layout); // Установка макета активности
        // Инициализация элементов интерфейса
        emailEditText = findViewById(R.id.emailEditText);
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registrTextView = findViewById(R.id.registrTextView);
        nextImageView = findViewById(R.id.nextImageView);
        // Инициализация Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        // Обработчик клика по тексту для перехода к активности аутентификации
        registrTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrUserActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Обработчик клика по кнопке для регистрации пользователя
        nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получение введенных пользователем данных
                String email = emailEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // Проверка на заполненность всех полей
                if (!email.equals("") && !name.equals("") && !password.equals("")) {
                    // Создание аккаунта пользователя
                    createAccount(email, password, name);
                    Intent intent = new Intent(RegistrUserActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    // Метод для создания аккаунта пользователя
    private void createAccount(String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Успешная регистрация пользователя
                            Log.d(TAG, "createUserWithEmail:success");
                            // Получение идентификатора пользователя
                            String uid = user.getUid();
                            Log.d("UID", uid);
                            // Установка информации о пользователе
                            setUserInfo(uid, email, name);
                            Intent intent = new Intent(RegistrUserActivity.this, SplashActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Ошибка при регистрации пользователя
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrUserActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Toast.makeText(RegistrUserActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    // Метод для установки информации о пользователе в базу данных Firestore
    public void setUserInfo(String uid, String email, String name) {
        CollectionReference users = db.collection("users");
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("name", name);
        users.document(uid).set(data);
    }
}