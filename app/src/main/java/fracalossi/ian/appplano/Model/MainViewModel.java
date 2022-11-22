package fracalossi.ian.appplano.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import fracalossi.ian.appplano.R;

public class MainViewModel extends AndroidViewModel {

    int navigationOpSelected = R.id.HomeViewOp;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }

    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;
    }
}
