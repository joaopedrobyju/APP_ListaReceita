package com.joao.mendes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listViewListaReceita;
    private ArrayAdapter<Receita> adapter;
    private FireBaseApi fireBaseApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarListaReceita);
        setSupportActionBar(toolbar);

        listViewListaReceita = findViewById(R.id.listViewListaReceita);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fireBaseApi = new FireBaseApi(this, listViewListaReceita, adapter);
        fireBaseApi.buscaReceitas();

        configurarClicks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_abrir_receita){
            Intent telaMain = new Intent(this, CriarReceitaActivity.class);
            startActivity(telaMain);
        }
        return super.onOptionsItemSelected(item);
    }

    private void configurarClicks(){
        listViewListaReceita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Receita r = fireBaseApi.getReceitas(position);
                verDetalhes(r);
            }
        });
        listViewListaReceita.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Receita r = fireBaseApi.getReceitas(position);
                removerReceita(r);
                return true;
            }
        });
    }

    private void removerReceita(Receita receita){
        String msg = String.format("A receita %s será removida! Deseja continuar?", receita.getNome());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover receita?");
        builder.setMessage(msg);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fireBaseApi.removerReceita(receita, "Receita removida com sucesso!");
                fireBaseApi.buscaReceitas();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void verDetalhes(Receita receita){
        String titulo = String.format("Nome %s", receita.getNome());
        String modoPreparo = String.format("Ingredientes: %s", receita.getIngredientes())
                .concat(String.format("\n\nModo de Preparo: %s", receita.getModoPreparo()));


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(modoPreparo);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}