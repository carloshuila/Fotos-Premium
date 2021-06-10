package app.fotoschicas.premium.personas;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

import app.fotoschicas.premium.R;

public class VerPersonaActivity extends AppCompatActivity {

    private TextView tvNombre;
    private PhotoView ivImagen;
    private Context micontext;
    private ImageButton btnDescargarImg;
    private ImageButton btnAtras;
    private String url_Imagen;

    private static final int REQUEST_CODE = 100;
    String nombreImagen = "nombreImagen";



    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Persona persona = null;
    public  String num;
    private AdView mAdView; //Google AdMob
    private AdView mAdView2; //Google AdMob


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_persona);
        isStoragePermissionGranted();

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

        ivImagen = (PhotoView) findViewById(R.id.id_imagen_persona_Act);
        //Recibir datos
        Bundle personaEnviado = getIntent().getExtras();
        if (personaEnviado != null){
            persona = (Persona) personaEnviado.getSerializable("persona");
            Glide.with(this).load(persona.getImagen()).into(ivImagen);
            url_Imagen = persona.getImagen();
            nombreImagen = persona.getNombre();

        }

        //boton descargar Imagen
        btnDescargarImg = (ImageButton) findViewById(R.id.btn_descargar_img);
        btnDescargarImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startDownload(url_Imagen);
              // downloadImage22(url_Imagen,imageName);
           }
       });

        //boton atras
        btnAtras = (ImageButton) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void startDownload(String url_img) {
        Toast makeText = Toast.makeText(this, "Iniciando Descarga", Toast.LENGTH_SHORT);
        makeText.setGravity(Gravity.CENTER, 0, 0);
        makeText.show();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url_img));
        request.setAllowedNetworkTypes(3);
        request.setNotificationVisibility(1);
        request.setTitle(nombreImagen+".png");
        request.setVisibleInDownloadsUi(true);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/FotosChicasPremium/" + nombreImagen+".png" );
        ((DownloadManager) this.getSystemService(DOWNLOAD_SERVICE)).enqueue(request);
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                MediaScannerConnection.scanFile(this, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + "/" + "/FotosChicasPremium/" +  nombreImagen+".png" ).getAbsolutePath()}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String url_img, Uri uri) {
                    }
                });
                return;
            }
            this.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + "/" + "/FotosChicasPremium/" + nombreImagen+".png" ))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

}
