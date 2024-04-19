package com.joao.mendes;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FireBaseApi {

    private static final String TABELA_NOME = "/listadereceitas";
    private Activity activity;
    private ListView listViewReceitas;
    private ArrayAdapter<Receita> adapter;
    private List<Receita> receitas;

    public FireBaseApi(Activity activity, ListView listViewReceitas, ArrayAdapter<Receita> adapter) {
        this.activity = activity;
        this.listViewReceitas = listViewReceitas;
        this.adapter = adapter;
    }
    public void criarReceita(Receita receita, String message){
        FirebaseFirestore.getInstance().collection(TABELA_NOME).add(receita).addOnSuccessListener(documentReference -> {
                    receita.setId(documentReference.getId());
                    atualizarId(receita);

                    Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    activity.finish();
                })
                .addOnFailureListener(e -> {Toast.makeText(activity.getApplicationContext(), "Erro ao criar a receita", Toast.LENGTH_LONG).show();});
    }

    public Receita getReceitas(int posicao){return receitas.get(posicao);}

    public FireBaseApi(Activity activity){
        this.activity = activity;
    }

    private void atualizarId(Receita receita){
        FirebaseFirestore.getInstance().collection(TABELA_NOME).document(receita.getId()).set(receita).addOnSuccessListener(aVoid -> {});
    }

    public void removerReceita(Receita receita, String message){
        FirebaseFirestore.getInstance().collection(TABELA_NOME).document(receita.getId()).delete().addOnSuccessListener(aVoid -> {
            Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }).addOnFailureListener(aVoid -> {
            Toast.makeText(activity.getApplicationContext(), "Erro ao remover a receita", Toast.LENGTH_LONG).show();
        });

    }

    public void buscaReceitas(){
        receitas = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(TABELA_NOME).addSnapshotListener((value, error) -> {
            List<DocumentChange> dcs = value.getDocumentChanges();

            for (DocumentChange doc: dcs){
                if (doc.getType() == DocumentChange.Type.ADDED){
                    Receita r = doc.getDocument().toObject(Receita.class);
                    receitas.add(r);
                }
            }
            adapter = new ArrayAdapter<>(activity.getApplicationContext(), android.R.layout.simple_list_item_1, receitas);
            listViewReceitas.setAdapter(adapter);
        });
    }

}
