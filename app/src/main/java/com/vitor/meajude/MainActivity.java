package com.vitor.meajude;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.*;
import androidx.preference.PreferenceFragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Fragment selectedFragment = null;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";


    Fragment FRAGMENTO_EMERGENCIA;
    Fragment FRAGMENTO_CONTATOS;
    Fragment FRAGMENTO_OPCOES;

    BaseAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        FRAGMENTO_CONTATOS = new ContatosFragment();
        FRAGMENTO_EMERGENCIA = new EmergenciaFragment();
        FRAGMENTO_OPCOES = new OpcoesFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FRAGMENTO_EMERGENCIA).commit();

        /*SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();*/


        if(getSharedPreferences(SHARED_PREFS,MODE_PRIVATE).contains("contatoPreferencial") &&
                !getSharedPreferences(SHARED_PREFS,MODE_PRIVATE).contains("contatosAdicionais")){

            Log.e("2","ACHOU UM CONTATO PREFERENCIAL");
            carregarContatoPreferencial();
        }else{
            Log.e("3","NENHUM CONTATO PREFERENCIAL");
        }

        if(getSharedPreferences(SHARED_PREFS,MODE_PRIVATE).contains("contatosAdicionais")){

            Log.e("4","CONTATOS ADICIONAIS ENCONTRADOS");
            carregarContatosAdicionais();
        }else{
            Log.e("5","NADA");
        }


    }

    public void salvarContato(ContatoEmergencia contatoE){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();



        ((ContatosFragment) FRAGMENTO_CONTATOS).addContatoAdicional(contatoE);

        List<ContatoEmergencia> lista = ((ContatosFragment) FRAGMENTO_CONTATOS).getListaTotalContatos();

        String json = gson.toJson(lista);
        editor.putString("contatosAdicionais",json);
        editor.apply();
        Log.e("1","ADICIONAL ADDED");


    }

    public void carregarContatosAdicionais(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("contatosAdicionais","");
        Type type = new TypeToken<List<ContatoEmergencia>>() {}.getType();
        List<ContatoEmergencia> listaTotalContatos = gson.fromJson(json,type);

        for (int i = 0;i<listaTotalContatos.size();i++){
            ((ContatosFragment) FRAGMENTO_CONTATOS).addContatoAdicional(listaTotalContatos.get(i));
        }

    }

    public void salvarContatoPreferencial(ContatoEmergencia contatoE){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        /*SALVA OBJETO NO SHARED PREFERENCES*/
        Gson gson = new Gson();

        String json = gson.toJson(contatoE);
        editor.putString("contatoPreferencial",json);

        /**/
        ((ContatosFragment) FRAGMENTO_CONTATOS).setContatoPreferencial(contatoE);
        editor.apply();

        Log.e("1","SALVOU CONTATO PREFERENCIAL");


    }

    public void carregarContatoPreferencial(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("contatoPreferencial","");
        ContatoEmergencia meuContato = gson.fromJson(json,ContatoEmergencia.class);

        ((ContatosFragment) FRAGMENTO_CONTATOS).setContatoPreferencial(meuContato);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Cursor cursor = null;
                    try {
                        String phoneNo = null;
                        String name = null;

                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        phoneNo = cursor.getString(phoneIndex);
                        name = cursor.getString(nameIndex);

                        ContatoEmergencia contato = new ContatoEmergencia();

                        contato.setNome(name);
                        contato.setTelefone(phoneNo);

                        if(!getSharedPreferences(SHARED_PREFS,MODE_PRIVATE).contains("contatoPreferencial")){
                            salvarContatoPreferencial(contato);
                        }else{
                            salvarContato(contato);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Log.e("Failed", "NÃO FOI POSSÍVEL PUXAR OS DADOS DO CONTATO");
        }
    }

    public void teste(View v){
        System.out.println("APERTOU GOSTOSO !!! ");
    }

    public void vaitomarnocu(View v){
        System.out.println("TOMAR NO CU ");
    }

    public void getContatos(View v){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent,1);


    }




    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.navigation_emergencia:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    FRAGMENTO_EMERGENCIA).commit();
                            selectedFragment = FRAGMENTO_EMERGENCIA;
                            break;
                        case R.id.navigation_contatos:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    FRAGMENTO_CONTATOS).commit();
                            selectedFragment = FRAGMENTO_CONTATOS;
                            break;
                        case R.id.navigation_opcoes:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    FRAGMENTO_OPCOES).commit();
                            selectedFragment = FRAGMENTO_OPCOES;
                            break;
                    }

                    return true;
                }
            };

}
