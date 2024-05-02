package com.boom.voice.ui.profile;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.boom.voice.R;
import com.boom.voice.databinding.FragmentProfileBinding;
import com.boom.voice.ui.data.profile_info;
import com.boom.voice.ui.data.user_info;
import com.boom.voice.ui.ui.AuthActivity;
import com.boom.voice.ui.ui.CreatVoiceActivity;
import com.boom.voice.ui.ui.ProfileRenameActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
// Фрагмент профиля пользователя, отображающий информацию о пользователе и предоставляющий возможность выхода из учетной записи.
public class ProfileFragment extends Fragment {
    // Привязка для макета фрагмента
    private FragmentProfileBinding binding;
    // Поля для работы с Firebase
    TextView mText;
    private FirebaseUser aUser;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    // Создание представления фрагмента
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Получение экземпляра ViewModel для фрагмента
        ProfileViewModel notificationsViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        // Настройка привязки макета фрагмента
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Получение элементов макета
        TextView nameText = binding.nameText;
        TextView emailText = binding.emailText;
        TextView year = binding.yearProfile;
        TextView exit = binding.exitText;
        TextView creat = binding.creatVoice;
        ImageView button = (ImageView)root.findViewById(R.id.profileRename);
        // Инициализация Firebase
        mAuth = FirebaseAuth.getInstance();
        aUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        // Получение информации о пользователе из профиля и установка значений в соответствующие текстовые поля
        user_info userInfo = profile_info.getInstance().getMy_user_info();
        nameText.setText(userInfo.getName());
        emailText.setText(userInfo.getEmail());
        year.setText(userInfo.getYear());
        // Обработчик нажатия на кнопку выхода из учетной записи
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Отображение диалогового окна подтверждения выхода
                DialogFragmentExit myDialogFragment = new DialogFragmentExit();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                myDialogFragment.show(transaction, "dialog");
            }
        });
        // Обработчик нажатия на кнопку создания нового голосования
        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CreatVoiceActivity.class);
                startActivity(i);
            }
        });
        // Обработчик нажатия на кнопку редактирования профиля
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProfileRenameActivity.class);
                startActivity(i);
            }
        });
        return root;
    }
    // Уничтожение привязки макета фрагмента при уничтожении фрагмента
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    // Вложенный класс для диалогового окна подтверждения выхода из учетной записи
    public static class DialogFragmentExit extends DialogFragment {

        // Создание диалогового окна
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Заголовок и кнопки диалогового окна
            String title = "Вы уверены, что хотите завершить сеанс? ";
            String button1String = "Да, я уверен(а)";
            String button2String = "Отмена";
            // Создание диалогового окна
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title);  // заголовок
            // Установка обработчика для кнопки "Да"
            builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Выход из учетной записи пользователя
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), AuthActivity.class);
                    getActivity().startActivity(intent);
                }
            });
            // Установка обработчика для кнопки "Отмена"
            builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Закрытие диалогового окна
                }
            });
            builder.setCancelable(true);

            return builder.create();
        }
    }
}