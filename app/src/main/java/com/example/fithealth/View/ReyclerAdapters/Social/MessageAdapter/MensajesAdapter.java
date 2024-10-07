package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.Model.Utils.FechasHelper;
import com.example.fithealth.Model.DataClass.ElementoFecha;
import com.example.fithealth.Model.DataClass.Fecha;
import com.example.fithealth.Model.DataClass.Mensaje;
import com.example.fithealth.R;

import java.util.ArrayList;
import java.util.List;

public class MensajesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Object> elementos;

    private static final int MENSAJE_ENVIADO = 0;
    private static final int MENSAJE_RECIBIDO = 1;
    private static final int ELEMENTO_DIA = 2;




    public MensajesAdapter() {
        this.elementos = new ArrayList<>();
    }

    public List<Object> getElementos() {
        return elementos;
    }

    public void addElemento(Object elemento) {
        elementos.add(elemento);
    }

    @Override
    public int getItemViewType(int position) {
        Object elemento = elementos.get(position);

        if (elemento instanceof Mensaje) {
            Mensaje mensaje = (Mensaje) elemento;

            if (mensaje.getTipoMensaje().equals("enviado")) {
                return MENSAJE_ENVIADO;
            } else {
                return MENSAJE_RECIBIDO;
            }
        } else {
            return ELEMENTO_DIA;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;

        switch (viewType) {
            case MENSAJE_ENVIADO:
                view = inflater.inflate(R.layout.item_mensaje_enviado, parent, false);
                viewHolder = new MensajeEnviadoHolder(view);
                break;
            case MENSAJE_RECIBIDO:

                view = inflater.inflate(R.layout.item_mensaje_recibido, parent, false);
                viewHolder = new MensajeRecibidoHolder(view);
                break;

            case ELEMENTO_DIA:

                view = inflater.inflate(R.layout.item_dia_mensajes,parent,false);
                viewHolder = new DiaHolder(view);
                break;
        }


        assert viewHolder != null;
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Object elemento = elementos.get(position);

        if(elemento instanceof Mensaje){

            Mensaje mensaje = (Mensaje) elemento;
            if (mensaje.getTipoMensaje().equals("enviado")) {
                ((MensajeEnviadoHolder) holder).setTvMensaje(mensaje.getMensaje());
                ((MensajeEnviadoHolder) holder).setTvHora(mensaje.getFechaMensaje());
            } else {
                ((MensajeRecibidoHolder) holder).setMensajeRecibido(mensaje.getMensaje());
                ((MensajeRecibidoHolder) holder).setTvHora(mensaje.getFechaMensaje());
            }
        }else{
            ElementoFecha elementoFecha = (ElementoFecha) elemento;
            ((DiaHolder)holder).setDia(elementoFecha.getTxtFecha());
        }

    }

    public void actualizarDias () {

        for (int i = 0; i < elementos.size(); i++) {
            Object elemento = elementos.get(i);

            if (elemento instanceof ElementoFecha) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    ElementoFecha elementoFecha = (ElementoFecha) elemento;
                    elementoFecha.setTxtFecha(FechasHelper.getTxtDia(elementoFecha.getFecha()));
                    notifyItemChanged(i);
                }

            }

        }
    }






    @Override
    public int getItemCount() {
        return elementos.size();
    }


}

class MensajeEnviadoHolder extends RecyclerView.ViewHolder {

    private TextView tvMensaje;

    private TextView tvHora;

    public MensajeEnviadoHolder(@NonNull View itemView) {

        super(itemView);
        tvMensaje = itemView.findViewById(R.id.tvMensajeEnviado);
        tvHora = itemView.findViewById(R.id.tvHoraEnviado);
    }

    public void setTvMensaje(String mensaje) {
        tvMensaje.setText(mensaje);
    }

    public void setTvHora(Fecha fecha) {
        tvHora.setText(fecha.getHora() + ":" + fecha.getMinuto());
    }
}

class MensajeRecibidoHolder extends RecyclerView.ViewHolder {

    private TextView tvMensajeRecibido;

    private TextView tvHora;

    public MensajeRecibidoHolder(@NonNull View itemView) {
        super(itemView);
        tvMensajeRecibido = itemView.findViewById(R.id.tvMensajeRecibido);
        tvHora = itemView.findViewById(R.id.tvHoraRecibido);
    }

    public void setMensajeRecibido(String mensaje) {
        tvMensajeRecibido.setText(mensaje);
    }

    public void setTvHora(Fecha fecha) {
        tvHora.setText(fecha.getHora() + ":" + fecha.getMinuto());
    }

}

class DiaHolder extends RecyclerView.ViewHolder{

    private TextView tvDia;
    public DiaHolder(@NonNull View itemView) {
        super(itemView);
        tvDia = itemView.findViewById(R.id.tvDia);
    }

    public void setDia (String dia){
        tvDia.setText(dia);
    }
}

