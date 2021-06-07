package app.fotoschicas.premium.categorias;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.fotoschicas.premium.MainActivity;
import app.fotoschicas.premium.R;

public class ListarCategoriasActivity extends AppCompatActivity {
    List<Categoria> listaCategorias = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_categorias);

        //boton atras
        btnAtras = (ImageButton) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            onBackPressed();
                                        }
                                    }
        );

       //Barra navegacion
        BottomNavigationView navBar = findViewById(R.id.btnBarraNav);
        navBar.setSelectedItemId(R.id.MainActivity);

        navBar.setOnNavigationItemSelectedListener((item) -> {
                switch (item.getItemId()){
                    case R.id.MainActivity:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.CategoriasActivity:
                        return true;

                }
                return false;

        });
        //Fin barra navegacion

        db.collection("categorias")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // Log.d("Categorias", document.getId() + " => " + document.getData());
                                Categoria categoria = document.toObject(Categoria.class);
                                listaCategorias.add(categoria);
                                EnviarListarRecyclerView(listaCategorias);
                            }
                        } else {
                            Log.w("Erorrrrr", "Error getting documents.", task.getException());
                        }
                    }
                });


        }
        public void  EnviarListarRecyclerView( List<Categoria> misCategorias){
            RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView_listaCategorias);
            AdapterListarCategorias  MyAdapter = new AdapterListarCategorias (this,misCategorias);
            myRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
            myRecyclerView.setAdapter(MyAdapter);
        }


}

