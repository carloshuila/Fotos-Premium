package app.fotoschicas.premium.personas;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.fotoschicas.premium.R;

public class PersonaActivity extends AppCompatActivity {

    List<Persona> listaPersonas = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageButton btnAtras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_personas);

        //Barra superior
        //boton atras
        btnAtras = (ImageButton) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            onBackPressed();
                                        }
                                    }
        );

   /*     //Barra navegacion
        BottomNavigationView navBar = findViewById(btnBarraNav);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.MainActivity:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
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
        //Fin barra navegacion
*/
        db.collection("personas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Personas", document.getId() + " => " + document.getData());
                                Persona persona = document.toObject(Persona.class);
                                listaPersonas.add(persona);
                                EnviarListarRecyclerView(listaPersonas);
                            }
                        } else {
                            Log.w("Erorrrrr", "Error getting documents.", task.getException());
                        }
                    }
                });


    }
    public void  EnviarListarRecyclerView( List<Persona> mispersonas){
        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView_persona);
        AdapterPersona MyAdapter = new AdapterPersona(this,mispersonas);
        myRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        myRecyclerView.setAdapter(MyAdapter);
    }
}
