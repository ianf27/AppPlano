package fracalossi.ian.appplano.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fracalossi.ian.appplano.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String N_MESES = "nMeses";

    // TODO: Rename and change types of parameters
    private int nMeses;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(int nMeses) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(N_MESES, nMeses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nMeses = getArguments().getInt(N_MESES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(
                R.layout.fragment_home, container, false);
    }

    // FAZER ON VIEW CREATED
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager2 viewPager2 = view.findViewById(R.id.vpHome);

        SampleAdapter sampleAdapter = new SampleAdapter(this,nMeses);

        viewPager2.setAdapter(sampleAdapter);
    }

    class SampleAdapter extends FragmentStateAdapter{

        int totalMeses;
        public SampleAdapter(@NonNull Fragment fragment, int nMeses) {
            super(fragment);
            this.totalMeses = nMeses;
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return MonthExpensesFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return totalMeses;
        }
    }

}
