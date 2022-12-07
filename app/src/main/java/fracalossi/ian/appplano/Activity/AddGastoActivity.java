package fracalossi.ian.appplano.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import fracalossi.ian.appplano.Model.AddGastoViewModel;
import fracalossi.ian.appplano.R;

public class AddGastoActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gasto);

        // obtenção do ViewModel
        AddGastoViewModel addGastoViewModel = new ViewModelProvider(this).get(AddGastoViewModel.class);

        // Quando o usuário clicar no botão adicionar...
        Button btnAddGasto = findViewById(R.id.btnAddGasto);
        btnAddGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Aqui nós desabilitamos o botão adicionar. Fazemos isso para impedir o usuário de
                // apertar esse botão várias vezes e, assim, cadastrar o mesmo produto de forma
                // repetida.
                v.setEnabled(false);

                // Abaixo, verificamos se o usuário preencheu todos os campos necessários. Caso não
                // exibimos uma mensagem toast para o usuário indicando qual campo ele precisa
                // preencher, habilitamos novamente o botão de adicionar e retornamos.
                EditText etName = findViewById(R.id.etName);
                String name = etName.getText().toString();
                if(name.isEmpty()) {
                    Toast.makeText(AddGastoActivity.this, "O campo Nome do Gasto não foi preenchido", Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                EditText etPrice = findViewById(R.id.etPrice);
                String price = etPrice.getText().toString();
                if(price.isEmpty()) {
                    Toast.makeText(AddGastoActivity.this, "O campo Preço do Gasto não foi preenchido", Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                EditText etDescription = findViewById(R.id.etDate);
                String data = etDescription.getText().toString();
                if(data.isEmpty()) {
                    Toast.makeText(AddGastoActivity.this, "O campo Data do Gasto não foi preenchido", Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                // PRECISO QUE AQUI EU VERIFIQUE SE A CATEGORIA FOI ESCOLHIDA AO INVÉS DA FOTO, QUE NÃO VAI TER

                RadioGroup rg = findViewById(R.id.rbgCategoria);
                int radioButtonID = rg.getCheckedRadioButtonId();
                String categoria = null;

                if (radioButtonID == R.id.rbFixo){
                    categoria = "111";
                }
                else {
                    if (radioButtonID == R.id.rbInvestimento){
                        categoria = "333";
                    }else {
                        if (radioButtonID == R.id.rbLazer){
                            categoria = "444";
                        }
                        else {
                            if (radioButtonID == R.id.rbVariavel){
                                categoria = "222";
                            }
                        }
                    }
                }


                // O ViewModel possui o método addGasto, que envia os dados do novo produto para o
                // servidor web.O servidor web recebe esses dados e cadastra um novo produto. Se o
                // produto foi cadastrado com sucesso, a app recebe o valor true. Se não o servidor
                // retorna o valor false.
                //
                // O método de addGasto retorna um LiveData, que na prática é um container que avisa
                // quando o resultado do servidor chegou.
                LiveData<Boolean> resultLD = addGastoViewModel.addGasto(name, price, data, categoria);

                // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado indicando
                // se o cadastro do produto deu certo ou não será guardado dentro do LiveData. Neste momento o
                // LiveData avisa que o resultado chegou chamando o método onChanged abaixo.
                resultLD.observe(AddGastoActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        // aBoolean contém o resultado do cadastro do produto. Se aBoolean for true, significa
                        // que o cadastro do produto foi feito corretamente. Indicamos isso ao usuário
                        // através de uma mensagem do tipo toast e finalizamos a Activity. Quando
                        // finalizamos a Activity, voltamos para a tela home, que mostra a lista de
                        // produtos.
                        if(aBoolean == true) {
                            Toast.makeText(AddGastoActivity.this, "Gasto adicionado com sucesso", Toast.LENGTH_LONG).show();
                            // indica que a Activity terminou com resultado positivo e a finaliza
                            setResult(RESULT_OK);
                            finish();
                        }
                        else {
                            // Se o cadastro não deu certo, apenas continuamos na tela de cadastro e
                            // indicamos com uma mensagem ao usuário que o cadastro não deu certo.
                            // Reabilitamos também o botão de adicionar, para permitir que o usuário
                            // tente realizar uma nova adição de produto.
                            v.setEnabled(true);
                            Toast.makeText(AddGastoActivity.this, "Ocorreu um erro ao adicionar o produto", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

    }
}