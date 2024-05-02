package com.boom.voice.ui.data;
import static android.content.ContentValues.TAG;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class FireBaseLoad {
    Context context;
    user_info user = new user_info();
    ArrayList<VoiceInfo>  voiceInfos;
    ArrayList<MyvoiceInfo> myInfos;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser userDB= mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    profile_info profileInfo = new profile_info();
    // Конструктор класса FireBaseLoad
    public FireBaseLoad(Context context){
        this.context=context;
    }
    // Метод для получения информации о пользователе
    public user_info getUserInfo(String UserToken) {
        user_info userInfo = new user_info();
        DocumentReference docRef = db.collection("users").document(UserToken);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String email = notNullStrForUsers(document, "email");
                        String name = notNullStrForUsers(document, "name");
                        String year = notNullStrForUsers(document, "year");
                        // Установка полученных данных о пользователе
                        userInfo.setEmail(email);
                        userInfo.setName(name);
                        userInfo.setYear(year);
                        userInfo.setId(document.getId());
                        profileInfo.getInstance().setMy_user_info(userInfo);
                        Log.d("map", "setMy_user_info");
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
    // Метод для получения информации о голосах
    public void getVoiceInfo () {
        db.collection("voice")
                .get()
                .addOnCompleteListener((Activity) context, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        voiceInfos = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                VoiceInfo voiceInfo = new VoiceInfo();
                                voiceInfo.setId(document.getId());  // 5ADD4A60-236B-471A-9CCF-CD7325E53462
                                Map<String, Object> responseObj = new HashMap<String, Object>();
                                Object arr = new String[10];
                                String name =  notNullStr (document , "name" );
                                String users =  notNullStr (document , "users" );
                                String item = (notNullStr (document , "item" ));
                                String protiv = (notNullStr (document , "protiv" ));
                                String za= (notNullStr (document , "za" ));
                                // Установка полученных данных о голосах
                                voiceInfo.setName(name);
                                voiceInfo.setItem(stringToArrList(item));
                                voiceInfo.setUsers(users);
                                voiceInfo.setProtiv(stringToArrList(protiv));
                                voiceInfo.setZa(stringToArrList(za));
                                Log.d("RESP",  arr.toString());
                                voiceInfos.add(voiceInfo);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            profileInfo.getInstance().
                                    setVoiceInfos(voiceInfos);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    // Метод для получения информации о голосах пользователя
    public void getMyVoiceInfo() {
        myInfos = new ArrayList<>();
        String uid = userDB.getUid();
        db.collection("myvoice")
                .whereEqualTo("id", uid)
                .get()
                .addOnCompleteListener((Activity) context, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MyvoiceInfo pInfo = new MyvoiceInfo();
                                Map<String, Object> responseObj = new HashMap<String, Object>();
                                String name = notNullStr(document, "name");
                                String voice = notNullStr(document, "myvoice");
                                Log.d("PART", name);
                                // Установка полученных данных о голосах пользователя
                                pInfo.setName(name);
                                pInfo.setVoice(voice);
                                myInfos.add(pInfo);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            profileInfo.getInstance().setMyvoice(myInfos);
                            Log.d("map", "setPartnerInfos");
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Log.d("map", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    // Преобразование строки в список строк
    public List<String> stringToArrList(String s){
        s = s.replaceAll("[\\[\\]]", "");
        List<String> arr = new ArrayList<String>(Arrays.asList(s.split(",")));
        return arr;
    }
    // Получение значения из поля документа, обработка исключений
    public String notNullStr(QueryDocumentSnapshot document, String tokenName) {
        String dd = "";
        try {
            dd = document.getData().get(tokenName).toString();
        } catch (Exception e) {
        }
        return dd;
    }
    public String notNullStrForUsers(DocumentSnapshot document, String tokenName) {
        String dd = "";
        try {
            dd = document.getData().get(tokenName).toString();
        } catch (Exception e) {
        }
        return dd;
    }
    public void removeVoice (String s) {
        db.collection("voice").document(s).delete();
    }
}
