package com.boom.voice.ui.home;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.boom.voice.Adapter.AdapterVoice;
import com.boom.voice.databinding.FragmentHomeBinding;
import com.boom.voice.ui.data.VoiceInfo;
import com.boom.voice.ui.data.profile_info;
import com.boom.voice.ui.ui.GivingVoiceActivity;
import java.util.ArrayList;
import java.util.List;
// Фрагмент домашней страницы, отображающий список голосований.
public class HomeFragment extends Fragment {
    // Привязка для макета фрагмента
    private FragmentHomeBinding binding;
    // Создание представления фрагмента
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Получение экземпляра ViewModel для фрагмента
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // Настройка привязки макета фрагмента
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Получение списка информации о голосовании из профиля
        ArrayList<VoiceInfo> listInfos = profile_info.getInstance().getVoiceInfos();
        // Получение RecyclerView для отображения списка голосований
        RecyclerView recyclerView = binding.VoiceList;
        // Получение списка элементов первого голосования
        List<String> item = new ArrayList();
        item = listInfos.get(0).getItem();
        // Логирование размера списка элементов первого голосования
        Log.d("PARTNERS_SIZE", String.valueOf(item.get(0).length()));
        // Обработчик клика на элемент списка
        AdapterVoice.OnStateClickListener stateClickListener = new AdapterVoice.OnStateClickListener() {
            @Override
            public void onStateClick(VoiceInfo partners, int position) {
                // Переход к активности для участия в голосовании
                Intent i = new Intent(getActivity(), GivingVoiceActivity.class);
                i.putExtra(VoiceInfo.class.getSimpleName(),  partners);
                startActivity(i);
            }
        };
        // Создание адаптера для списка голосований
        AdapterVoice adapter = new AdapterVoice(stateClickListener, getActivity(), listInfos);
        // Установка адаптера для RecyclerView, если список голосований не пустой
        if (listInfos.size()!=0) {
            recyclerView.setAdapter(adapter);
        }
        return root;
    }
    // Уничтожение привязки макета фрагмента при уничтожении фрагмента
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}