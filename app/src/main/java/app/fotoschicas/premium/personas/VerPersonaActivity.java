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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

import app.fotoschicas.premium.R;

public class VerPersonaActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;//Google AdMob

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
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_persona);
        toolbar_title=(TextView)findViewById(R.id.toolbar_title);
        isStoragePermissionGranted();


       //API Goolge AdmOB
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        //Banner Superios
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getResources().getString(R.string.admob_banner_ad1));
        mAdView = findViewById(R.id.ads_banner_verPersona1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        ///Banner Inferior
        AdView adView2 = new AdView(this);
        adView2.setAdSize(AdSize.BANNER);
        adView2.setAdUnitId(getResources().getString(R.string.admob_banner_ad2));
        mAdView2 = findViewById(R.id.ads_banner_verPersona2);
        mAdView2.loadAd(adRequest);

        anuncioIntersticial(adRequest);

        //Fin API Goolge AdmOB

        ivImagen = (PhotoView) findViewById(R.id.id_imagen_persona_Act);
        //Recibir datos
        Bundle personaEnviado = getIntent().getExtras();
        if (personaEnviado != null){
            persona = (Persona) personaEnviado.getSerializable("persona");
            Glide.with(this).load(persona.getImagen()).into(ivImagen);
            url_Imagen = persona.getImagen();
            nombreImagen = persona.getNombre();
            if(nombreImagen == null){
                toolbar_title.setText(getResources().getString(R.string.titulo_barra_ver_persona));
                Log.d("nombreNulooo", "nombre nuloooo");
            }else {
                toolbar_title.setText(nombreImagen);
                Log.d("no nombreNulooo", "nooo nombre nuloooo");
            }

        }
        FloatingActionButton btnDescargar = findViewById(R.id.floating_btn_descargar);
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(VerPersonaActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
                startDownload(url_Imagen);
            }
        });

        //boton descargar Imagen
      /*  btnDescargarImg = (ImageButton) findViewById(R.id.btn_descargar_img);
        btnDescargarImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (mInterstitialAd != null) {
                   mInterstitialAd.show(VerPersonaActivity.this);
               } else {
                   Log.d("TAG", "The interstitial ad wasn't ready yet.");
               }
               startDownload(url_Imagen);
           }
       });
*/
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
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Fotos Chicas Premium/" + nombreImagen+".png" );
        ((DownloadManager) this.getSystemService(DOWNLOAD_SERVICE)).enqueue(request);
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                MediaScannerConnection.scanFile(this, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + "/" + "/Fotos Chicas Premium/" +  nombreImagen+".png" ).getAbsolutePath()}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String url_img, Uri uri) {
                    }
                });
                return;
            }
            this.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + "/" + "/Fotos Chicas Premium/" + nombreImagen+".png" ))));
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

    public void anuncioIntersticial(AdRequest adRequest){
        InterstitialAd.load(this,getResources().getString(R.string.admob_interstitial_ad), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("AdMob", "onAdLoaded");
                //FullScreenContentCallback
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.d("TAG", "The ad was dismissed.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("AdMob", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

}
