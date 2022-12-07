package fracalossi.ian.appplano.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fracalossi.ian.appplano.Adapter.MyAdapter;
import fracalossi.ian.appplano.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthExpensesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthExpensesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MES = "mes";

    int mes = 0;

    MyAdapter myAdapter;

    public MonthExpensesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthExpensesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthExpensesFragment newInstance(int posicao) {
        MonthExpensesFragment fragment = new MonthExpensesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MES, posicao);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mes = getArguments().getInt(ARG_MES);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_month_expenses, container, false);
    }

}