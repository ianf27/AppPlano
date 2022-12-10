package fracalossi.ian.appplano.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fracalossi.ian.appplano.Activity.HomeActivity;
import fracalossi.ian.appplano.Adapter.GastoComparator;
import fracalossi.ian.appplano.Adapter.MyAdapter;
import fracalossi.ian.appplano.Model.Gasto;
import fracalossi.ian.appplano.Model.HomeViewModel;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Aqui configuramos o RecycleView
        RecyclerView rvProduct = view.findViewById(R.id.rvListGasto);
        rvProduct.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((HomeActivity) getActivity());
        rvProduct.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter((HomeActivity) getActivity(), new GastoComparator());
        rvProduct.setAdapter(myAdapter);

        // obtemos o ViewModel pois é nele que está o método que se conecta ao servior web.
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // O ViewModel possui o método getProductsLD, que obtém páginas/blocos de produtos do servidor
        // web. Cada página contém 10 produtos. Quando o usuário rola a tela, novas páginas de produtos
        // são obtidas do servidor.
        //
        // O método de getProductsLD retorna um LiveData, que na prática é um container que avisa
        // quando o resultado do servidor chegou. Ele guarda a página de produtos que o servidor
        // entregou para a app.
        LiveData<PagingData<Gasto>> productsLd = homeViewModel.getGastosLd(mes);

        // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado contendo uma página
        // com 10 produtos será guardado dentro do LiveData. Neste momento o
        // LiveData avisa que uma nova página de produtos chegou chamando o método onChanged abaixo.
        productsLd.observe((HomeActivity) getActivity(), new Observer<PagingData<Gasto>>() {
            /**
             * Esse método é chamado sempre que uma nova página de produtos é entregue à app pelo
             * servidor web.
             * @param productPagingData contém uma página de produtos
             */
            @Override
            public void onChanged(PagingData<Gasto> productPagingData) {

                // Adiciona a nova página de produtos ao Adapter do RecycleView. Isso faz com que
                // novos produtos apareçam no RecycleView.
                myAdapter.submitData(getLifecycle(),productPagingData);
            }
        });
    }
}