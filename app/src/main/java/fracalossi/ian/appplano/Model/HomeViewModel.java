package fracalossi.ian.appplano.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import fracalossi.ian.appplano.R;
import kotlinx.coroutines.CoroutineScope;

/**
 * ViewModel referente a HomeActivity
 */
public class HomeViewModel extends AndroidViewModel {

    LiveData<PagingData<Gasto>> gastosLd;

    int navigationOpSelected = R.id.HomeViewOp;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        // Abaixo configuramos o uso da biblioteca de paginação Paging 3, assim como foi feito na
        // atividade Galeria Pública
        GastosRepository gastosRepository = new GastosRepository(getApplication());
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, Gasto> pager = new Pager(new PagingConfig(10), () -> new GastosPagingSource(gastosRepository));
        gastosLd = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);

    }

    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }

    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;
    }

    public LiveData<PagingData<Gasto>> getGastosLd() {
        return gastosLd;
    }

}
