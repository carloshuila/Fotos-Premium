package app.fotoschicas.premium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.fotoschicas.premium.categorias.AdapterCategoria;
import app.fotoschicas.premium.categorias.AdapterRecomendado;
import app.fotoschicas.premium.categorias.Categoria;
import app.fotoschicas.premium.personas.Persona;
import app.fotoschicas.premium.personas.PersonaActivity;

public class MainActivity extends AppCompatActivity {

    public List<Categoria> listaCategorias = new ArrayList<>();
    public List<Persona> listaRecomendados = new ArrayList<>();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       db.collection("categorias")
                .get()
                .addOnCompleteListener(task ->  {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Categorias", document.getId() + " => " + document.getData());
                                Log.d("entro","entrooo");
                                Categoria categoria = document.toObject(Categoria.class);
                                listaCategorias.add(categoria);
                              EnviarListarRecyclerView(listaCategorias);
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
                            Log.d("entro","entrooo");
                            Persona persona = document.toObject(Persona.class);
                            listaRecomendados.add(persona);
                            EnviarListarRecyclerViewRecomendados(listaRecomendados);
                        }
                    } else {
                        Log.w("Error", "Erroooooor getting documents.", task.getException());
                    }

                });






        //Barra superior
        //boton atras
      /* btnAtras = (ImageButton) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            onBackPressed();
                                        }
                                    }
        );
        ./
       */

        //Barra navegacion
      /*  BottomNavigationView navBar = findViewById(btnBarraNav);

        navBar.setSelectedItemId(R.id.MainActivity);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.MainActivity:
                        return true;
                    case R.id.PedidosActivity:
                        startActivity(new Intent(getApplicationContext(), PedidosActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.PerfilActivity:
                        startActivity(new Intent(getApplicationContext(), PerfilActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        */
    }

    public void vercategorias(View view) {
        Intent intent = new Intent(this, PersonaActivity.class);
        startActivity(intent);
    }


    public void  EnviarListarRecyclerView( List<Categoria> misCategorias){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView_Categorias);
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