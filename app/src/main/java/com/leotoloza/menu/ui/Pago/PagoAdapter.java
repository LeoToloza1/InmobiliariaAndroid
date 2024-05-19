package com.leotoloza.menu.ui.Pago;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leotoloza.menu.R;

import com.leotoloza.menu.modelo.Pago;
import com.leotoloza.menu.request.ApiClient;


import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolder>{

    private List<Pago> listaPagos;
    private Context context;
    private PagoAdapter.ClickListener clickListener;

    public PagoAdapter(List<Pago> listaPagos, Context context) {
        this.listaPagos = listaPagos;
        this.context = context;
    }

    @NonNull
    @Override
    public PagoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pago, parent, false);
        return new ViewHolder(view);
    }
    public void setClickListener(ClickListener listener) {
        clickListener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Pago pago= listaPagos.get(position);
        holder.direccionPago.setText("Direccion del inmueble:"+pago.getContrato().getInmueble().getDireccion());
        holder.precioPago.setText("Importe abonado :$"+pago.getImporte()+"");
        holder.num_pago.setText("Número de pago: "+pago.getNumero_pago());
        String urlBase = ApiClient.URLBASE + "img/uploads/";
        String urlFoto =urlBase +pago.getContrato().getInmueble().getAvatarUrl();
        Glide.with(context)
                .load(urlFoto)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imagenPago);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.clickDetalle(pago);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView direccionPago;
        TextView precioPago;
        TextView num_pago;
        ImageView imagenPago;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccionPago=itemView.findViewById(R.id.DireccionPago);
            precioPago = itemView.findViewById(R.id.PrecioPago);
            num_pago=itemView.findViewById(R.id.numero_pago);
            imagenPago=itemView.findViewById(R.id.imagenPago);
        }
    }
    public interface ClickListener {
        void clickDetalle(Pago pago);
    }
}