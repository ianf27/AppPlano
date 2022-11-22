package fracalossi.ian.appplano.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import fracalossi.ian.appplano.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarPlanejamentoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarPlanejamentoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarPlanejamentoFragment() {
        // Required empty public constructor
    }


    public static EditarPlanejamentoFragment newInstance() {
        new EditarPlanejamentoFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_planejamento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //looping para escrever cada slider de definir porcentagem de categoria
        // esse 3 será substituído pela quantidade de categorias q a pessoa tiver
        for (int i=0; i<3; i++) {
            View v = getLayoutInflater().inflate(R.layout.categoria_sliders_item, null);

            //será necesário usar um findviewbyid para alterar os text view do item

            ScrollView sv = view.findViewById(R.id.svCategorias);
            sv.addView(v);
        }
    }
}