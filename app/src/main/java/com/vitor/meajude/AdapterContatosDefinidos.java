package com.vitor.meajude;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class AdapterContatosDefinidos extends BaseAdapter {

    List<ContatoEmergencia> listaContatos;

    Context mContext;

    public AdapterContatosDefinidos(List<ContatoEmergencia> contatos, Context context){
        this.listaContatos = contatos;
        this.mContext = context;

    }

    @Override
    public int getCount() {

        return(listaContatos.size());
    }

    @Override
    public Object getItem(int i) {

        return (listaContatos.get(i));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext,R.layout.contactlist_android_item,null);

        TextView nome = (TextView) view.findViewById(R.id.textview_android_contact_name);
        TextView telefone = (TextView) view.findViewById(R.id.textview_android_contact_phoneNr);

        nome.setText(listaContatos.get(position).getNome());
        telefone.setText(listaContatos.get(position).getTelefone());
        view.setTag(listaContatos.get(position).getNome());

        return view;
    }
}
