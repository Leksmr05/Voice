package com.boom.voice.ui.ui;
import static android.content.ContentValues.TAG;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.boom.voice.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.boom.voice.R;
import com.boom.voice.ui.data.profile_info;
import com.boom.voice.ui.data.user_info;
// Активность аутентификации
public class AuthActivity extends AppCompatActivity {
    // Элементы интерфейса
    EditText emailEditText, passwordEditText;
    ImageView nextImageView;
    TextView registrTextView, fogotPassTextView;
    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser aUser;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_layout); // Установка макета активности
        // Инициализация элементов интерфейса
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nextImageView = findViewById(R.id.nextImageView);
        registrTextView = findViewById(R.id.registrTextView);
        fogotPassTextView = findViewById(R.id.fogotPassTextView);
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_choose_user_layout, null);
        TextView user = dialogView.findViewById(R.id.user);
        TextView cansel = dialogView.findViewById(R.id.cansel);
        // Создание диалога выбора пользователя
        // Инициализация Firebase
        mAuth = FirebaseAuth.getInstance();
        aUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        // Проверка авторизации при запуске активности
        if (aUser != null) {
            // Пользователь вошел в систему
            String uid = aUser.getUid();
            // Проверка доступности сети
            if (isNetworkAvailable(this)) {
                // Получение информации о пользователе
                getUserInfo(uid);
                try {
                    Thread.sleep(2000); // Задержка для имитации загрузки данных
                    Intent i = new Intent(AuthActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i); // Переход к главному экрану
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            // Пользователь вышел из системы
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
        // Обработчик нажатия на кнопку "Отмена" в диалоговом окне
        cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        // Обработчик нажатия на текст "Пользователь" в диалоговом окне
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthActivity.this, RegistrUserActivity.class);
                startActivity(intent); // Переход к активности регистрации
                finish();
            }
        });
// Установка параметров для диалогового окна
        WindowManager.LayoutParams wlmp = dialogBuilder.getWindow().getAttributes();
        wlmp.gravity = Gravity.BOTTOM;
        dialogBuilder.setView(dialogView);
        // Обработчик нажатия на кнопку "Далее"
        nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (!email.equals("") || !password.equals("")) {
                    signIn(email , password); // Вызов метода входа в систему
                }
            }
        });
        // Обработчик нажатия на текст "Регистрация"
        registrTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AuthActivity.this, RegistrUserActivity.class);
                startActivity(i); // Переход к активности регистрации
            }
        });
    }
    // Метод для входа в систему по электронной почте и паролю
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Успешный вход в систему
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            String rr = mAuth.getUid();
                            getUserInfo(uid); // Получение информации о пользователе
                            Intent intent = new Intent(AuthActivity.this, SplashActivity.class);
                            startActivity(intent); // Переход к главному экрану
                            finish();
                        } else {
                            // Ошибка входа в систему
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    // Метод для отправки подтверждения по электронной почте
    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Письмо отправлено
                    }
                });
    }
    // Метод для получения информации о пользователе из базы данных Firestore
    public user_info getUserInfo (String UserToken) {
        user_info userInfo = new user_info();
        profile_info profileInfo = new profile_info();
        DocumentReference docRef = db.collection("users").document(UserToken);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String email= notNullStrForUsers(document , "email" );
                        String name= notNullStrForUsers(document , "name" );
                        userInfo.setEmail(email);
                        userInfo.setName(name);
                        profileInfo.getInstance().setMy_user_info(userInfo);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return userInfo;
    }
    // Метод для получения строки из документа Firestore
    public String notNullStr (QueryDocumentSnapshot document ,String tokenName) {
        String dd = "";
        try {
            dd = document.getData().get(tokenName).toString();
        } catch (Exception e) {

        }
        return dd;
    }
    // Метод для получения строки из документа Firestore для пользователей
    public String notNullStrForUsers (DocumentSnapshot document ,String tokenName) {
        String dd = "";
        try {
            dd = document.getData().get(tokenName).toString();
        } catch (Exception e) {

        }
        return dd;
    }
    // Проверка доступности сети
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}