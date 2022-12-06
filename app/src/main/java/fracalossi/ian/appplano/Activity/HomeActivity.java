package fracalossi.ian.appplano.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fracalossi.ian.appplano.Adapter.MyAdapter;
import fracalossi.ian.appplano.Adapter.ProductComparator;
import fracalossi.ian.appplano.Fragment.EditarPlanejamentoFragment;
import fracalossi.ian.appplano.Fragment.HomeFragment;
import fracalossi.ian.appplano.Fragment.PerfilFragment;
import fracalossi.ian.appplano.Model.HomeViewModel;
import fracalossi.ian.appplano.Model.MainViewModel;
import fracalossi.ian.appplano.Model.Product;
import fracalossi.ian.appplano.R;

public class HomeActivity extends AppCompatActivity {

    static int ADD_PRODUCT_ACTIVITY_RESULT = 1;

    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Aqui configuramos o RecycleView
        // no meu caso temos um fragContainer, onde dentro virá um fragment com um view pager, onde virá um fragment dos gastos e gráficos (que será a home)

        RecyclerView rvProduct = findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvProduct.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(this, new ProductComparator());
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
        LiveData<PagingData<Product>> productsLd = homeViewModel.getProductsLd();

        // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado contendo uma página
        // com 10 produtos será guardado dentro do LiveData. Neste momento o
        // LiveData avisa que uma nova página de produtos chegou chamando o método onChanged abaixo.
        productsLd.observe(this, new Observer<PagingData<Product>>() {
            /**
             * Esse método é chamado sempre que uma nova página de produtos é entregue à app pelo
             * servidor web.
             * @param productPagingData contém uma página de produtos
             */
            @Override
            public void onChanged(PagingData<Product> productPagingData) {

                // Adiciona a nova página de produtos ao Adapter do RecycleView. Isso faz com que
                // novos produtos apareçam no RecycleView.
                myAdapter.submitData(getLifecycle(),productPagingData);
            }
        });


        // Quando o usuário quer adicionar um novo produto, navegamos para a página de
        // adição de novos produtos
        Button btnNewProduct = findViewById(R.id.btnNewProduct);
        btnNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AddGastoActivity.class);
                startActivityForResult(i, ADD_PRODUCT_ACTIVITY_RESULT);
            }
        });

    }

    /**
     * Quando o usuário adiciona um novo produto com sucesso, ele volta para a tela HomeActivity.
     * O método abaixo é chamado quando a tela de adição de novo produto finaliza. Neste momento,
     * verificamos se o o produto foi adicionado com sucesso. Se sim, atualizamos o Adapter, que por
     * sua vez irá recarregar a lista de produtos do servidor.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Se estiver retornando da tela de adição de produtos
        if(requestCode == ADD_PRODUCT_ACTIVITY_RESULT) {
            // Se a adição de produtos foi realizada com sucesso
            if(resultCode == Activity.RESULT_OK) {
                // O adapter é atualizado. Isso faz com que os dados atuais sejam invalidados e
                // sejam pedidas novas páginas de produtos para o servidor web.
                myAdapter.refresh();
            }
        }
    }



}