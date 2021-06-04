package app.fotoschicas.premium.personas;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import app.fotoschicas.premium.R;

public class PersonaConectActivity extends AppCompatActivity {

    private TextView tvNombre;
    private ImageView ivImagen;
    private Context micontext;

    private ImageButton btnAtras;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Persona persona = null;
    public  String num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_persona);

        //tvNombre = (TextView) findViewById(R.id.id_nombre_persona_Act);
        ivImagen = (ImageView) findViewById(R.id.id_imagen_persona_Act);

        //Recibir datos
        Bundle personaEnviado = getIntent().getExtras();
        if (personaEnviado != null){
           // Persona persona2 = new Persona("carlos", "https://fotosperros.es/wp-content/uploads/2021/05/Pomerania-2.jpg");

            persona = (Persona) personaEnviado.getSerializable("persona");
          //  Log.d("Nombreeeeee",persona.getNombre().toString() );
            //Pasa los valores para mostrar
         //   tvNombre.setText(persona.getNombre().toString());


            Glide.with(this).load(persona.getImagen()).into(ivImagen);

        }
     /*   // Obteber cantidad seleccionada
        final ElegantNumberButton btnCantidad = (ElegantNumberButton) findViewById(R.id.btn_cantidad);
        btnCantidad.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                num= btnCantidad.getNumber();
                Log.d("Cantidaddd", num);
                cantidadTotal = (Integer.parseInt(num))*yogurt.getPrecio();
                Log.d("PRECIOO",String.valueOf (cantidadTotal));
                tvTotal.setText(String.valueOf(cantidadTotal));
            }
        });*/


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
        /*
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
        */

    }


  /*  public void agregarCarrito(View view) {
        Toast.makeText(this, "Se agrego un producto a tu pedido", LENGTH_SHORT).show();
        // Create a new user with a first, middle, and last name
        Map<String, Object> Pedido = new HashMap<>();
        Pedido.put("id", yogurt.getId());
        Pedido.put("nombre", yogurt.getNombre());
        Pedido.put("precioTotal", cantidadTotal);
        Pedido.put("cantidad", Integer.parseInt(num));
        Pedido.put("imagen", yogurt.getImagen());

        // Add a new document with a generated ID
        db.collection("pedidos")
                .add(Pedido)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Pedidos", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERRORRR", "Error adding document", e);
                    }
                });
    }*/
}
