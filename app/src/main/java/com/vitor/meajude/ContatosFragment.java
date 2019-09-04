package com.vitor.meajude;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class ContatosFragment extends Fragment {

    //public List<ContatoEmergencia> listaContatosAdicionais = new ArrayList<>();
    public List<ContatoEmergencia> listaTotalContatos = new ArrayList<>();
    public ContatoEmergencia CONTATO_PREFERENCIAL;
    View view;
    BaseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contatos, container, false);
        return view;

    }

    public void addContatoAdicional(ContatoEmergencia contato){

       // listaContatosAdicionais.add(contato);

        listaTotalContatos.add(contato);

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

   /* public boolean vazioContatosAdicionais(){
        return listaContatosAdicionais.isEmpty();
    }*/


    public void onResume() {
        super.onResume();

        System.out.println("me abriu");

        imprimirContatos();

    }

    public void imprimirContatos(){

        ListView listaContatosListView = (ListView) view.findViewById(R.id.listviewContatos);

        adapter = new AdapterContatosDefinidos(listaTotalContatos, getContext());

        listaContatosListView.setAdapter(adapter);


    }

}
