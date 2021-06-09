package app.fotoschicas.premium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

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

public class MainActivity extends AppCompatActivity {

    public List<Categoria> listaCategorias = new ArrayList<>();
    public List<Persona> listaRecomendados = new ArrayList<>();
    public List<Persona> listaPersonas = new ArrayList<>();
    private AdView mAdView; //Google AdMob
    private AdView mAdView2; //Google AdMob

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
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView = findViewById(R.id.ads_banner_home1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView adView2 = new AdView(this);
        adView2.setAdSize(AdSize.BANNER);
        adView2.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView2 = findViewById(R.id.ads_banner_home2);
        mAdView2.loadAd(adRequest);
        //Fin API Goolge AdmOB

        EnviarListarRecyclerView_CategoriasHome(listaCategorias);
        EnviarListarRecyclerViewRecomendados(listaRecomendados);

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

         db.collection("personas")
                .get()
                .addOnCompleteListener(task ->  {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Personas", document.getId() + " => " + document.getData());
                            Log.d("entro","entrooo Home Recomendados");
                            Persona persona = document.toObject(Persona.class);
                            listaRecomendados.add(persona);
                            listaPersonas.add(persona);
                            EnviarListarRecyclerViewRecomendados(listaRecomendados);
                        }
                    } else {
                        Log.w("Error", "Erroooooor getting documents.", task.getException());
                    }

                });

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

                }
                return false;

        });

    }

    public void vercategorias(View view) {
        Intent intent = new Intent(this, ListarPersonaActivity.class);
        startActivity(intent);
    }


    public void EnviarListarRecyclerView_CategoriasHome(List<Categoria> misCategorias){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView_Home_Categorias);
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setHasFixedSize(true);
        AdapterCategoria MyAdapter = new AdapterCategoria(this,misCategorias);
        myRecyclerView.setAdapter(MyAdapter);
    }

    public void  EnviarListarRecyclerViewRecomendados( List<Persona> misRecomendados){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView_producto_recomendado);
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setHasFixedSize(true);
        AdapterRecomendado MyAdapter = new AdapterRecomendado(this,misRecomendados);
        myRecyclerView.setAdapter(MyAdapter);
    }

}