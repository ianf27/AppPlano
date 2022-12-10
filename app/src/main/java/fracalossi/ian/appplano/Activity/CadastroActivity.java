package fracalossi.ian.appplano.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fracalossi.ian.appplano.Model.CadastroViewModel;
import fracalossi.ian.appplano.R;

public class CadastroActivity extends AppCompatActivity {

   CadastroViewModel registerViewModel;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_cadastro);

      // obtemos o ViewModel pois é nele que está o método que se conecta ao servior web.
      registerViewModel = new ViewModelProvider(this).get(CadastroViewModel.class);

      // Quando o usuário clicar no bptão cadastrar
      Button btnRegister =  findViewById(R.id.btSalvar);
      btnRegister.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            // Primeiro verificamos se o usuário digitou corretamente os dados de cadastro.
            // No nosso caso, apenas verificamos se o campo não está vazio no momento em que o
            // usuário clicou no botão cadastrar. Se o campo está vazio, exibimos uma mensagem para o
            // usuário indicando que ele não preencheu o campo e retornamos da função sem fazer
            // mais nada.

            EditText etNewNome =  findViewById(R.id.etNome);
            final String newNome = etNewNome.getText().toString();
            if(newNome.isEmpty()) {
               Toast.makeText(CadastroActivity.this, "Campo NOME não preenchido", Toast.LENGTH_LONG).show();
               return;
            }

            EditText etNewSobrenome =  findViewById(R.id.etSobrenome);
            final String newSobrenome = etNewSobrenome.getText().toString();
            if(newSobrenome.isEmpty()) {
               Toast.makeText(CadastroActivity.this, "Campo SOBRENOME não preenchido", Toast.LENGTH_LONG).show();
               return;
            }

            EditText etNewLogin =  findViewById(R.id.etNewCPF);
            final String newLogin = etNewLogin.getText().toString();
            if(newLogin.isEmpty()) {
               Toast.makeText(CadastroActivity.this, "Campo de CPF não preenchido", Toast.LENGTH_LONG).show();
               return;
            }

            EditText etNewNascimento =  findViewById(R.id.etNascimento);
            final String newNascimento = etNewNascimento.getText().toString();
            if(newNascimento.isEmpty()) {
               Toast.makeText(CadastroActivity.this, "Campo de DATA DE NASCIMENTO não preenchido", Toast.LENGTH_LONG).show();
               return;
            }

            EditText etNewSexo =  findViewById(R.id.etSexo);
            final String newSexo = etNewSexo.getText().toString();
            if(newSexo.isEmpty()) {
               Toast.makeText(CadastroActivity.this, "Campo SEXO não preenchido", Toast.LENGTH_LONG).show();
               return;
            }

            EditText etNewCelular =  findViewById(R.id.etCelular);
            final String newCelular = etNewCelular.getText().toString();
            if(newCelular.isEmpty()) {
               Toast.makeText(CadastroActivity.this, "Campo de CELULAR não preenchido", Toast.LENGTH_LONG).show();
               return;
            }

            EditText etNewEmail =  findViewById(R.id.etEmailCadastro);
            final String newEmail = etNewEmail.getText().toString();
            if(newEmail.isEmpty()) {
               Toast.makeText(CadastroActivity.this, "Campo de EMAIL não preenchido", Toast.LENGTH_LONG).show();
               return;
            }


            EditText etNewPassword =  findViewById(R.id.etSenhaCadastro);
            final String newPassword = etNewPassword.getText().toString();
            if(newPassword.isEmpty()) {
               Toast.makeText(CadastroActivity.this, "Campo de SENHA não preenchido", Toast.LENGTH_LONG).show();
               return;
            }

            EditText etNewPasswordCheck =  findViewById(R.id.etConfirmSenha);
            String newPasswordCheck = etNewPasswordCheck.getText().toString();
            if(newPasswordCheck.isEmpty()) {
               Toast.makeText(CadastroActivity.this, "Campo de CHECAGEM DE SENHA não preenchido", Toast.LENGTH_LONG).show();
               return;
            }

            if(!newPassword.equals(newPasswordCheck)) {
               Toast.makeText(CadastroActivity.this, "Senha não confere", Toast.LENGTH_LONG).show();
               return;
            }

            // O ViewModel possui o método register, que envia as informações para o servidor web.
            // O servidor web recebe as infos e cadastra um novo usuário. Se o usuário foi cadastrado
            // com sucesso, a app recebe o valor true. Se não o servidor retorna o valor false.
            //
            // O método de register retorna um LiveData, que na prática é um container que avisa
            // quando o resultado do servidor chegou.
            LiveData<Boolean> resultLD = registerViewModel.register(newNome, newSobrenome, newLogin, newNascimento, newSexo, newCelular, newEmail, newPassword);

            // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado indicando
            // se o cadastro deu certo ou não será guardado dentro do LiveData. Neste momento o
            // LiveData avisa que o resultado chegou chamando o método onChanged abaixo.
            resultLD.observe(CadastroActivity.this, new Observer<Boolean>() {
               @Override
               public void onChanged(Boolean aBoolean) {
                  // aBoolean contém o resultado do cadastro. Se aBoolean for true, significa
                  // que o cadastro do usuário foi feito corretamente. Indicamos isso ao usuário
                  // através de uma mensagem do tipo toast e finalizamos a Activity. Quando
                  // finalizamos a Activity, voltamos para a tela de login.
                  if(aBoolean) {
                     Toast.makeText(CadastroActivity.this, "Novo usuario registrado com sucesso", Toast.LENGTH_LONG).show();
                     finish();
                  }
                  else {
                     // Se o cadastro não deu certo, apenas continuamos na tela de cadastro e
                     // indicamos com uma mensagem ao usuário que o cadastro não deu certo.
                     Toast.makeText(CadastroActivity.this, "Erro ao registrar novo usuário", Toast.LENGTH_LONG).show();
                  }
               }
            });
         }
      });
   }
}