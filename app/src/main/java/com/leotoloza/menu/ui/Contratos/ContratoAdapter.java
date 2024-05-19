package com.leotoloza.menu.ui.Contratos;

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
import com.leotoloza.menu.modelo.Contrato;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.request.ApiClient;
import com.leotoloza.menu.ui.Inmuebles.InmuebleAdapter;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder>{

    private List<Contrato> listaContratos;
    private Context context;
    private ContratoAdapter.ClickListener clickListener;

    public ContratoAdapter(List<Contrato> listaContratos, Context context) {
        this.listaContratos = listaContratos;
        this.context = context;
    }

    @NonNull
    @Override
    public ContratoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contrato, parent, false);
        return new ViewHolder(view);
    }
    public void setClickListener(ContratoAdapter.ClickListener listener) {
        clickListener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ViewHolder holder, int position) {

        Contrato contrato= listaContratos.get(position);
        holder.direccionContrato.setText("Direccion :"+contrato.getInmueble().getDireccion());
        holder.precioContrato.setText("Abono mensual :$"+contrato.getMonto()+"");
        String urlBase = ApiClient.URLBASE + "img/uploads/";
        String urlFoto =urlBase +  contrato.getInmueble().getAvatarUrl();
        Glide.with(context)
                .load(urlFoto)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imagenContrato);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.clickDetalle(contrato);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaContratos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView direccionContrato;
        TextView precioContrato;
        ImageView imagenContrato;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccionContrato=itemView.findViewById(R.id.DireccionContrato);
            precioContrato = itemView.findViewById(R.id.PrecioContrato);
            imagenContrato=itemView.findViewById(R.id.imagenContrato);
        }
    }
    public interface ClickListener {
        void clickDetalle(Contrato contrato);
    }
}