package app.fotoschicas.premium.recomendado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import app.fotoschicas.premium.R;
import app.fotoschicas.premium.personas.Persona;
import app.fotoschicas.premium.personas.PersonaConectActivity;

public class AdapterRecomendado extends RecyclerView.Adapter<AdapterRecomendado.MyViewHolder> {

    private Context micontext;
    private List<Persona> listapersonas;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public AdapterRecomendado(Context micontext, List<Persona> listaPersonas) {
        this.micontext = micontext;
        listapersonas = listaPersonas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup miparent, int viewType) {

        View miview;
        LayoutInflater minflater = LayoutInflater.from(micontext);
        miview = minflater.inflate(R.layout.cardview_item_recomendado,miparent,false);
        return new MyViewHolder(miview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

       // holder.nombrePersona.setText((listapersonas.get(position).getNombre()));
        Glide.with(micontext).load(listapersonas.get(position).getImagen()).into(holder.imgPersona);

        final Persona persona = listapersonas.get(position);

        //Agregar click Listener
        holder.cardViewRecomendado.setOnClickListener(v -> {

            Intent intent = new Intent(micontext, PersonaConectActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("persona", persona);
            Log.d("NombreeeeeeRecoendado",persona.getNombre().toString() );

            //pasamos el objeto a la activity
            intent.putExtras(bundle);

            //Iniciamos la Activity
            micontext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listapersonas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //TextView nombrePersona;
        ImageView imgPersona;
        CardView cardViewRecomendado;


        public MyViewHolder(View itemView){
            super(itemView);

           // nombrePersona = (TextView) itemView.findViewById(R.id.id_recomendado_nombre);
            imgPersona  = (ImageView) itemView.findViewById(R.id.id_recomendado_img);
            cardViewRecomendado = (CardView) itemView.findViewById(R.id.id_cardViewRecomendado);
        }
    }
}
