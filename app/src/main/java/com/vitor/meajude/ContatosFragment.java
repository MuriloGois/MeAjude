package com.vitor.meajude;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ContatosFragment extends Fragment implements AdapterView.OnItemClickListener {

    //public List<ContatoEmergencia> listaContatosAdicionais = new ArrayList<>();
    public List<ContatoEmergencia> listaTotalContatos = new ArrayList<>(5);
    //1 CONTATO PREFERENCIAL
    //+
    //4 CONTATOS ADICIONAIS
    public ContatoEmergencia CONTATO_PREFERENCIAL;
    View view;
    BaseAdapter adapter;
    private ListView listaContatosListView;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contatos, container, false);
        listaContatosListView = (ListView) view.findViewById(R.id.listviewContatos);

        listaContatosListView.setOnItemClickListener(this); //SETO O LISTENER NO CLICK EM ALGUM CONTATO


        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) { //QUANDO USUÁRIO CLICAR EM ALGUM CONTATO DA LISTA

        String value = listaContatosListView.getItemAtPosition(position).toString();
       // Toast.makeText(getContext(), "REMOVENDO CONTATO", Toast.LENGTH_SHORT).show();

        if(position == 0){
            criarDialogoExcluirContatoPreferencial();
        }else{
            criarDialogoExcluirContatoAdicional(position);
        }

    }

    public void criarDialogoExcluirContatoAdicional(final int pos){
        AlertDialog.Builder criador = new AlertDialog.Builder(getContext());

        criador.setMessage("DESEJA EXCLUIR ESSE CONTATO ?");
        criador.setCancelable(true);

        criador.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removerContato(pos);
                salvarEstadoDosContatos();
            }
        });
        criador.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("1","NÃO");
            }
        });

        AlertDialog msg = criador.create();
        msg.show();
    }

    public void criarDialogoExcluirContatoPreferencial(){
        AlertDialog.Builder criador = new AlertDialog.Builder(getContext());

        criador.setMessage("NÃO É POSSÍVEL EXCLUIR O CONTATO PREFERENCIAL");
        criador.setCancelable(true);

        criador.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog msg = criador.create();
        msg.show();
    }

    public void removerContato(int pos){

        listaTotalContatos.remove(pos);

        onResume();

    }

    public void salvarEstadoDosContatos(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String json = gson.toJson(getListaTotalContatos());

        editor.putString("contatosAdicionais",json);

        editor.apply();
    }


    public void addContatoAdicional(ContatoEmergencia contato){

        listaTotalContatos.add(contato);

    }

    public boolean listaCheia(){
        return (listaTotalContatos.size() == 5);
    }


    public void setContatoPreferencial(ContatoEmergencia contatoE){
        CONTATO_PREFERENCIAL = contatoE;

        listaTotalContatos.add(CONTATO_PREFERENCIAL);
    }

    public List<ContatoEmergencia> getListaTotalContatos(){
        return listaTotalContatos;
    }

    public ContatoEmergencia getContatoPreferencial(){
        return CONTATO_PREFERENCIAL;
    }

    public boolean vazioListaTotal(){

        return listaTotalContatos.isEmpty();
    }


    public void onResume() {
        super.onResume();

        System.out.println("me abriu");

        imprimirContatos();

    }

    public void imprimirContatos(){

        adapter = new AdapterContatosDefinidos(listaTotalContatos, getContext());

        listaContatosListView.setAdapter(adapter);


    }

}
