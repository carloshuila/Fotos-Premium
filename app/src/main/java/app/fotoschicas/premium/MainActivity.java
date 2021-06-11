package app.fotoschicas.premium;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.fotoschicas.premium.categorias.AdapterCategoria;
import app.fotoschicas.premium.categorias.ListarCategoriasActivity;
import app.fotoschicas.premium.recomendado.AdapterRecomendado;
import app.fotoschicas.premium.categorias.Categoria;
import app.fotoschicas.premium.personas.Persona;
import app.fotoschicas.premium.personas.ListarPersonaActivity;
//API Goolge AdmOB
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Categoria> listaCategorias = new ArrayList<>();
    public ArrayList<Persona> listaRecomendados = new ArrayList<>();
    public ArrayList<Persona> listaPersonas = new ArrayList<>();
    private AdView mAdView; //Google AdMob
    private AdView mAdView2; //Google AdMob
    private int numeroAleatorio;

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageButton btnAtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //API Goolge AdmOB
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getResources().getString(R.string.admob_banner_ad1));
        mAdView = findViewById(R.id.ads_banner_home1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView adView2 = new AdView(this);
        adView2.setAdSize(AdSize.BANNER);
        adView2.setAdUnitId(getResources().getString(R.string.admob_banner_ad2));
        mAdView2 = findViewById(R.id.ads_banner_home2);
        mAdView2.loadAd(adRequest);
        //Fin API Goolge AdmOB

        EnviarListarRecyclerView_CategoriasHome(listaCategorias);
        EnviarListarRecyclerViewRecomendados(listaRecomendados);
        mostrarCategorias();
        numeroAleatorio = (int) (Math.random()*6+1);
        Log.w("aleatorioooo", Integer.toString(numeroAleatorio));
        switch (numeroAleatorio){
            case 1:
                verPersonasCategoria(getResources().getString(R.string.categoria_1));
                break;
            case 2:
                verPersonasCategoria(getResources().getString(R.string.categoria_2));
                break;
            case 3:
                verPersonasCategoria(getResources().getString(R.string.categoria_3));
                break;
            case 4:
                verPersonasCategoria(getResources().getString(R.string.categoria_4));
                break;
            case 5:
                verPersonasCategoria(getResources().getString(R.string.categoria_5));
                break;
            case 6:
                verPersonasCategoria(getResources().getString(R.string.categoria_6));
                break;
            case 7:
                verPersonasTodas();
                break;
            default:
                verPersonasTodas();
                break;
        }

        //Barra navegacion
        
        BottomNavigationView navBar = findViewById(R.id.btnBarraNav);
        navBar.setSelectedItemId(R.id.MainActivity);

        navBar.setOnNavigationItemSelectedListener((item) -> {
                switch (item.getItemId()){
                    case R.id.MainActivity:
                        return true;
                    case R.id.CategoriasActivity:
                        startActivity(new Intent(getApplicationContext(), ListarCategoriasActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.CompartirActivity:
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.putExtra("android.intent.extra.SUBJECT", this.getString(R.string.app_name));
                        intent.putExtra("android.intent.extra.TEXT", this.getString(R.string.share_app_message) + ("\nhttps://play.google.com/store/apps/details?id=" + this.getPackageName()));
                        intent.setType("text/plain");
                        this.startActivity(Intent.createChooser(intent, getResources().getString(R.string.menu_compartir)));
                        return true;
                    }
                return false;

        });

    }

    public void vercategorias(View view) {
        Intent intent = new Intent(this, ListarPersonaActivity.class);
        startActivity(intent);
    }


    public void EnviarListarRecyclerView_CategoriasHome(ArrayList<Categoria> misCategorias){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView_Home_Categorias);
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setHasFixedSize(true);
        AdapterCategoria MyAdapter = new AdapterCategoria(this,misCategorias);
        myRecyclerView.setAdapter(MyAdapter);
    }

    public void  EnviarListarRecyclerViewRecomendados( ArrayList<Persona> misRecomendados){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView_producto_recomendado);
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setHasFixedSize(true);
        AdapterRecomendado MyAdapter = new AdapterRecomendado(this,misRecomendados);
        myRecyclerView.setAdapter(MyAdapter);
    }

    public void mostrarCategorias(){
        db.collection("categorias")
                .get()
                .addOnCompleteListener(task ->  {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Categorias", document.getId() + " => " + document.getData());
                            Log.d("entro","entrooo  Homme Categorias");
                            Categoria categoria = document.toObject(Categoria.class);
                            listaCategorias.add(categoria);
                            EnviarListarRecyclerView_CategoriasHome(listaCategorias);
                        }
                    } else {
                        Log.w("Error", "Erroooooor getting documents.", task.getException());
                    }

                });
    }

    public void verPersonasTodas(){
        db.collection("personas").limit(30)
                .get()
                .addOnCompleteListener(task ->  {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Personas", document.getId() + " => " + document.getData());
                            Log.d("entro","entrooo Home Recomendados");
                            Persona persona = document.toObject(Persona.class);
                            listaRecomendados.add(persona);
                            EnviarListarRecyclerViewRecomendados(listaRecomendados);
                        }
                    } else {
                        Log.w("Error", "Erroooooor getting documents.", task.getException());
                    }

                });
    }

    public void verPersonasCategoria(String nombre_categoria){
        db.collection("personas").whereEqualTo("categoria", nombre_categoria).limit(30)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //  Log.d("Personas", document.getId() + " => " + document.getData());
                                Persona persona = document.toObject(Persona.class);
                                listaRecomendados.add(persona);
                                EnviarListarRecyclerViewRecomendados(listaRecomendados);
                            }
                        } else {
                            Log.w("Erorrrrr", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}