package com.joao.mendes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class CriarReceitaActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextIngrediente;
    private EditText editTextModoDePreparo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_receita);

        editTextNome = findViewById(R.id.editTextNome);
        editTextIngrediente = findViewById(R.id.editTextIngrediente);
        editTextModoDePreparo = findViewById(R.id.editTextModoDePreparo);

        Toolbar toolbar = findViewById(R.id.toolbarCriar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_criar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_criar_receita) {
            Receita r = new Receita(editTextNome.getText().toString(), editTextIngrediente.getText().toString(), editTextModoDePreparo.getText().toString());

            FireBaseApi fireBaseApi = new FireBaseApi(this);
            fireBaseApi.criarReceita(r, "Receita criada com sucesso");

        }

        return super.onOptionsItemSelected(item);
    }


}