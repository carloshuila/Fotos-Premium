package app.fotoschicas.premium.categorias;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import app.fotoschicas.premium.MainActivity;
import app.fotoschicas.premium.R;
import app.fotoschicas.premium.personas.ListarPersonaActivity;
import app.fotoschicas.premium.personas.Persona;
import app.fotoschicas.premium.personas.PersonaConectActivity;

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.MyViewHolder> {

    public Context micontext;
    private List<Categoria> listaCategorias = new ArrayList<>();
    private List<Persona> listaPersonas = new ArrayList<>();
    private List<Persona> listaPersonasCategoria = new ArrayList<>();


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdapterCategoria(Context micontext, List<Categoria> listaCategorias) {
        this.micontext = micontext;
        this.listaCategorias = listaCategorias;
       // this.listaPersonasCategoria = listaPersonasCategoria;


        Persona persona1 = new Persona("Karen","Latinas","https://scontent.fclo1-3.fna.fbcdn.net/v/t1.6435-9/130482530_205747194414108_7035182440163167374_n.jpg?_nc_cat=101&ccb=1-3&_nc_sid=730e14&_nc_eui2=AeFUMob9HC0WMJ9ebWX_GHY2hjl9K0hmXxOGOX0rSGZfE458jkqu5HsIwGvTRiepipOZCm8MeLJPbihxdh0ZfUho&_nc_ohc=YZEjygFdf6wAX83cQBL&_nc_ht=scontent.fclo1-3.fna&oh=915dd6745129e70c654bba463db6e34a&oe=60DFDF38" );
        Persona persona2 = new Persona("Andrea","Latinas", "https://scontent.fclo1-4.fna.fbcdn.net/v/t1.6435-9/128374091_199296088392552_7805493767974819460_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=730e14&_nc_eui2=AeFTJta7XnUzS1LX2xmWh_6iy8USFEJdYwTLxRIUQl1jBOoIAst3QQ4cGvZOJGZoJFvrE4GuZ8ByiJdcB3Bpt5F1&_nc_ohc=w7JyA2gRKmEAX8yyXWF&_nc_ht=scontent.fclo1-4.fna&oh=0d1ee58035bc8513a9ccc64c3e7894e7&oe=60E064A3");
        Persona persona3 = new Persona("Astrid","Latinas","https://scontent.fclo1-3.fna.fbcdn.net/v/t1.6435-9/130482530_205747194414108_7035182440163167374_n.jpg?_nc_cat=101&ccb=1-3&_nc_sid=730e14&_nc_eui2=AeFUMob9HC0WMJ9ebWX_GHY2hjl9K0hmXxOGOX0rSGZfE458jkqu5HsIwGvTRiepipOZCm8MeLJPbihxdh0ZfUho&_nc_ohc=YZEjygFdf6wAX83cQBL&_nc_ht=scontent.fclo1-3.fna&oh=915dd6745129e70c654bba463db6e34a&oe=60DFDF38" );
        Persona persona4 = new Persona("Mercedes","Colombianas","https://scontent.fclo1-3.fna.fbcdn.net/v/t1.6435-9/130482530_205747194414108_7035182440163167374_n.jpg?_nc_cat=101&ccb=1-3&_nc_sid=730e14&_nc_eui2=AeFUMob9HC0WMJ9ebWX_GHY2hjl9K0hmXxOGOX0rSGZfE458jkqu5HsIwGvTRiepipOZCm8MeLJPbihxdh0ZfUho&_nc_ohc=YZEjygFdf6wAX83cQBL&_nc_ht=scontent.fclo1-3.fna&oh=915dd6745129e70c654bba463db6e34a&oe=60DFDF38" );

        listaPersonas.add(persona1);
        listaPersonas.add(persona2);
        listaPersonas.add(persona3);
        listaPersonas.add(persona4);
    }



    @Override
    public AdapterCategoria.MyViewHolder onCreateViewHolder(@NonNull ViewGroup miparent, int viewType) {

        View miview;
        LayoutInflater minflater = LayoutInflater.from(micontext);
        miview = minflater.inflate(R.layout.cardview_item_home_categorias, miparent, false);
        return new AdapterCategoria.MyViewHolder(miview);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategoria.MyViewHolder holder, final int position) {

        holder.nombreCategoria.setText((listaCategorias.get(position).getNombre()));

        Glide.with(micontext).load(listaCategorias.get(position).getImagen()).into(holder.imgCategoria);

        //Agregar click Listener
        holder.cardViewCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("entroooo", "entrroooo  desde el Home Latinas");

                Log.d("nombre categoriaaaa",listaCategorias.get(position).nombre );
                Log.d("tamaño Array ", Integer.toString( listaPersonas.size()) );

                String nombreCategoria = listaCategorias.get(position).nombre;
                Log.d("nombre categoriaaaa 222",nombreCategoria );

                for (int i = 0; i < listaPersonas.size(); i++){
                    if (listaPersonas.get(i).getCategoria().equals(nombreCategoria)){
                        listaPersonasCategoria.add(listaPersonas.get(i));
                        Log.d("personaCategorIA", listaPersonasCategoria.get(i).getNombre() );
                    }
                    else{  Log.d("entroooo", "No soy igual");
                   }
                }

               /* Intent intent = new Intent(micontext, ListarPersonaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listaPersonasCategoria",(Serializable) listaPersonasCategoria);
                intent.putExtras(bundle);
                //Iniciamos la Activity
                micontext.startActivity(intent);*/


               /* if(listaCategorias.get(position).nombre == "Latinas"){
                    Intent intent = new Intent(micontext, ListarPersonaActivity.class);

                    //--------Iniciamos la Activity PostresActivity
                    micontext.startActivity(intent);
                }
                else{
                    Intent intent = new Intent(micontext, ListarCategoriasActivity .class);
                    Log.d("entroooo", "entrroooo  desde el Home Latinas");
                    //--------Iniciamos la Activity PostresActivity
                    micontext.startActivity(intent);

                }*/


            }
        });


    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombreCategoria;
        ImageView imgCategoria;
        CardView cardViewCategoria;

        public MyViewHolder(View itemView) {
            super(itemView);

            nombreCategoria = (TextView) itemView.findViewById(R.id.id_nombre_categoria_home);
            imgCategoria = (ImageView) itemView.findViewById(R.id.id_img_categoria_home);
            cardViewCategoria = (CardView) itemView.findViewById(R.id.id_cardView_CategoriaHome);
        }
    }


}
