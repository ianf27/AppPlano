package fracalossi.ian.appplano.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fracalossi.ian.appplano.Fragment.EditarPlanejamentoFragment;
import fracalossi.ian.appplano.Fragment.HomeFragment;
import fracalossi.ian.appplano.Fragment.PerfilFragment;
import fracalossi.ian.appplano.Model.HomeViewModel;
import fracalossi.ian.appplano.R;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final HomeViewModel vm = new ViewModelProvider(this).get(HomeViewModel.class);

        bottomNavigationView = findViewById(R.id.btNav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                vm.setNavigationOpSelected(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.PersonViewOp:
                        PerfilFragment perfilFragment = PerfilFragment.newInstance();
                        setFragment(perfilFragment);
                        break;
                    case R.id.PlanoViewOp:
                        EditarPlanejamentoFragment editarPlanejamentoFragment = EditarPlanejamentoFragment.newInstance();
                        setFragment(editarPlanejamentoFragment);
                        break;
                    case R.id.HomeViewOp:
                        HomeFragment homeFragment = HomeFragment.newInstance();
                        setFragment(homeFragment);
                        break;
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.HomeViewOp);

    }

    void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}