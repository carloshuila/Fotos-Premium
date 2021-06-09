package app.fotoschicas.premium.personas;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;

import app.fotoschicas.premium.R;

public class VerPersonaActivity extends AppCompatActivity {

    private TextView tvNombre;
    private ImageView ivImagen;
    private Context micontext;

    private ImageButton btnAtras;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Persona persona = null;
    public  String num;
    private AdView mAdView; //Google AdMob
    private AdView mAdView2; //Google AdMob


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_persona);

        //API Goolge AdmOB
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView = findViewById(R.id.ads_banner_verPersona1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        AdView adView2 = new AdView(this);
        adView2.setAdSize(AdSize.BANNER);
        adView2.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView2 = findViewById(R.id.ads_banner_verPersona2);
        mAdView2.loadAd(adRequest);
        //Fin API Goolge AdmOB

        ivImagen = (ImageView) findViewById(R.id.id_imagen_persona_Act);
        //Recibir datos
        Bundle personaEnviado = getIntent().getExtras();
        if (personaEnviado != null){
            persona = (Persona) personaEnviado.getSerializable("persona");
            Glide.with(this).load(persona.getImagen()).into(ivImagen);


        }
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
