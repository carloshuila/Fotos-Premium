package app.fotoschicas.premium.categorias;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.fotoschicas.premium.R;

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.MyViewHolder> {

    public Context micontext;
    public List<Categoria> listaCategorias;

    public AdapterCategoria(Context micontext, List<Categoria> listaCategorias) {
        this.micontext = micontext;
        this.listaCategorias = listaCategorias;
    }

    @Override
    public AdapterCategoria.MyViewHolder onCreateViewHolder(@NonNull ViewGroup miparent, int viewType) {

        View miview;
        LayoutInflater minflater = LayoutInflater.from(micontext);
        miview = minflater.inflate(R.layout.cardview_item_home_categorias,miparent,false);
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
                Log.d("nombre categoriaaaa",listaCategorias.get(position).nombre );
                if(listaCategorias.get(position).nombre == "Latinas"){
                    Intent intent = new Intent(micontext, ListarCategoriasActivity .class);
                    Log.d("entroooo", "entrroooo");
                    //--------Iniciamos la Activity PostresActivity
                    micontext.startActivity(intent);
                }
                else{
                    Intent intent = new Intent(micontext, ListarCategoriasActivity .class);
                    //--------Iniciamos la Activity PostresActivity
                    micontext.startActivity(intent);

                }




            }
        });



    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nombreCategoria;
        ImageView imgCategoria;
        CardView cardViewCategoria;
        public MyViewHolder(View itemView){
            super(itemView);

            nombreCategoria = (TextView) itemView.findViewById(R.id.id_nombre_categoria_home);
            imgCategoria  = (ImageView) itemView.findViewById(R.id.id_img_categoria_home);
            cardViewCategoria= (CardView) itemView.findViewById(R.id.id_cardView_CategoriaHome);
        }
    }
}
