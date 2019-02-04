package com.example.paco.convertidordivisas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MiVista> {

    CustomItemClickListener listener;
    private List<Elemento> listaMonedas;


    public class MiVista extends RecyclerView.ViewHolder {
        public TextView moneda, cantidad;

        public MiVista(View view) {
            super(view);
            moneda = (TextView) view.findViewById(R.id.moneda);
            cantidad = (TextView) view.findViewById(R.id.cantidad);

        }
    }

    public Adaptador(List<Elemento> listaPeliculas, CustomItemClickListener listener) {

        this.listaMonedas = listaPeliculas;
        this.listener = listener;
    }

    @Override

    public MiVista onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila, parent, false);
        final MiVista pvh = new MiVista(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                listener.onItemClick(itemView, pvh.getPosition());
            }
        });
        return pvh;
    }


    @Override
    public void onBindViewHolder(MiVista holder, int position) {
        Elemento moneda = listaMonedas.get(position);
        holder.moneda.setText(moneda.getMoneda());
        holder.cantidad.setText(moneda.getCantidad());
    }

    @Override
    public int getItemCount() {
        return listaMonedas.size();
    }
}