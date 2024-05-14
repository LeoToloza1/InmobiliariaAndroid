package com.leotoloza.menu.ui.Inmuebles;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leotoloza.menu.R;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.request.ApiClient;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder>{

    private List<Inmueble> listaInmuebles;
    private Context context;
    private ClickListener clickListener;

    public InmuebleAdapter(List<Inmueble> listaInmuebles, Context context) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
    }

    @NonNull
    @Override
    public InmuebleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }
    public void setClickListener(ClickListener listener) {
        clickListener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.ViewHolder holder, int position) {
        String urlBase = ApiClient.URLBASE + "img/uploads/";
        Inmueble inmueble= listaInmuebles.get(position);
    holder.direccion.setText(inmueble.getDireccion());
    holder._precio.setText(inmueble.getPrecio()+"");
    String urlFoto =urlBase +  inmueble.getAvatarUrl();
        Glide.with(context)
                .load(urlFoto)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imagen);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("salida", "onClickADAPTER: llega");
                if (clickListener != null) {
                    clickListener.clickDetalle(inmueble);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView direccion;
        TextView _precio;
        ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccion=itemView.findViewById(R.id.Direccion);
            _precio = itemView.findViewById(R.id.Precio);
            imagen=itemView.findViewById(R.id.imagen);
        }
    }
    public interface ClickListener {
        void clickDetalle(Inmueble inmueble);
    }
}

