package com.boom.voice.ui.myvoice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.boom.voice.Adapter.AdapterMyVoice;
import com.boom.voice.databinding.FragmentMyvoiceBinding;
import com.boom.voice.ui.data.MyvoiceInfo;
import com.boom.voice.ui.data.profile_info;

import java.util.ArrayList;
// Фрагмент, отображающий список пользовательских голосований.
public class MyVoiceFragment extends Fragment {
    // Привязка для макета фрагмента
    private FragmentMyvoiceBinding binding;
    // Создание представления фрагмента
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Получение экземпляра ViewModel для фрагмента
        MyVoiceViewModel dashboardViewModel = new ViewModelProvider(this).get(MyVoiceViewModel.class);
        // Настройка привязки макета фрагмента
        binding = FragmentMyvoiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Получение списка информации о пользовательских голосованиях из профиля
        ArrayList<MyvoiceInfo> partnersInfos = profile_info.getInstance().getMyvoice();
        // Получение RecyclerView для отображения списка голосований
        RecyclerView recyclerView = binding.myVoiceList;
        // Логирование размера списка голосований
        Log.d("PARTNERS_SIZE", String.valueOf(partnersInfos.get(0).getName()));
        // Создание адаптера для списка пользовательских голосований
        AdapterMyVoice adapter = new AdapterMyVoice(getActivity(), partnersInfos);
        // Установка адаптера для RecyclerView, если список не пустой
        if (partnersInfos.size()!=0) {
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