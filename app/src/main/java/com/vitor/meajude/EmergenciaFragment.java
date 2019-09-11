package com.vitor.meajude;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Locale;


public class EmergenciaFragment extends Fragment {

    SmsManager smsManager;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        smsManager = SmsManager.getDefault();
        View v;
        v = inflater.inflate(R.layout.fragment_emergencia, container, false);


        return v;

    }

    public void rotinaEmergencia(List<ContatoEmergencia> listaContatos){
        //AQUI EU RODO A ROTINA DE EMERGÊNCIA.
        //MANDO SMS
        //FAÇO LIGAÇÃO

        System.out.println("ENTROU ");

        Log.e("10","lista de contatos para alertar");

        for (int i = 0;i<listaContatos.size();i++){
            System.out.println(listaContatos.get(i));
        }

        Log.e("20","MANDANDO SMS");

        for (int i = 0;i<listaContatos.size();i++){
            smsManager.sendTextMessage(listaContatos.get(i).getTelefone(), null, "SOCORRO", null, null);
        }

        //smsManager.sendTextMessage(listaContatos.get(0).getTelefone(), null, "SOCORRO", null, null);


    }



}
