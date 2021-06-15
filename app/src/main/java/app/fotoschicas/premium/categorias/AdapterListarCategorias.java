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

import java.util.ArrayList;
import java.util.List;

import app.fotoschicas.premium.R;
import app.fotoschicas.premium.personas.ListarPersonaActivity;

public class AdapterListarCategorias  extends RecyclerView.Adapter<AdapterListarCategorias.MyViewHolder> {

        public Context micontext;
        public ArrayList<Categoria> listaCategorias;

        public AdapterListarCategorias(Context micontext, ArrayList<Categoria> listaCategorias) {
            this.micontext = micontext;
            this.listaCategorias = listaCategorias;
        }

        @Override
        public AdapterListarCategorias.MyViewHolder onCreateViewHolder(@NonNull ViewGroup miparent, int viewType) {

            View miview;
            LayoutInflater minflater = LayoutInflater.from(micontext);
            miview = minflater.inflate(R.layout.cardview_item_listar_categoria,miparent,false);
            return new AdapterListarCategorias.MyViewHolder(miview);
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterListarCategorias.MyViewHolder holder, final int position) {
            holder.nombreCategoria.setText((listaCategorias.get(position).getNombre()));
            Glide.with(micontext).load(listaCategorias.get(position).getImagen()).into(holder.imgCategoria);

            holder.cardViewCategoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final  String nombreCategoria = listaCategorias.get(position).nombre;

                    Intent intent = new Intent(micontext, ListarPersonaActivity.class);
                    intent.putExtra("nombreCategoria", nombreCategoria);
                    micontext.startActivity(intent);
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

                nombreCategoria = (TextView) itemView.findViewById(R.id.id_nombre_listar_categoria);
                imgCategoria  = (ImageView) itemView.findViewById(R.id.id_img_listar_categoria);
                cardViewCategoria= (CardView) itemView.findViewById(R.id.id_cardView_ListarCategoria);
            }
        }


}
