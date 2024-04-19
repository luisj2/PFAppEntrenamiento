package com.example.fithealth.PantallasPrincipales.principales.Social;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaAmigosAdapter extends RecyclerView.Adapter<AmigosViewHolder> {


    private List <Contacto> solicitudesAmistad;

    public ListaAmigosAdapter(List<Contacto> solicitudesAmistad) {
        this.solicitudesAmistad = solicitudesAmistad;
    }

    public void setSolicitudesAmistad(List<Contacto> solicitudesAmistad) {
        this.solicitudesAmistad = solicitudesAmistad;
    }

    @NonNull
    @Override
    public AmigosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_lista_amigos,parent,false);



        return new AmigosViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull AmigosViewHolder holder, int position) {

        Contacto contacto = solicitudesAmistad.get(position);

        holder.setTvNombreusuario(contacto.getNombre());




        if(contacto.getRutaImagen() != null && !contacto.getRutaImagen().toString().isEmpty()){
            holder.setIconoUsuario(contacto.getRutaImagen());
        }

        holder.funcionalidadRechazar(contacto.getId());

        holder.funcionalidadAceptar(contacto);



    }

    @Override
    public int getItemCount() {
        return solicitudesAmistad.size();
    }
}

class AmigosViewHolder extends RecyclerView.ViewHolder{

    private ImageButton btnAceptarSolicitud;
    private ImageButton btnRechazarSolicitud;

    private TextView tvNombreusuario;

    private CircleImageView cvImagenUsuario;

    private Context context;

    public AmigosViewHolder(@NonNull View itemView,Context context) {
        super(itemView);
        this.context = context;
        enlazarComponentes(itemView);
    }

    public void funcionalidadRechazar(String idEliminado) {
        btnRechazarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseHelper.eliminarSolicitudAmistad(idEliminado,context);
            }
        });
    }

    public void funcionalidadAceptar(Contacto contacto) {
        btnAceptarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseHelper.aniadirAmigoSolicitud(contacto);

            }
        });
    }

    public void enlazarComponentes(View view){

        btnAceptarSolicitud = view.findViewById(R.id.btnAceptarSolicitudAmistad);
        btnRechazarSolicitud = view.findViewById(R.id.btnRechazarSolicitudAmistad);
        tvNombreusuario = view.findViewById(R.id.tvNombreUsuarioSolicitud);
        cvImagenUsuario = view.findViewById(R.id.cvImagenSolicitudAmistad);

    }

    public void setTvNombreusuario(String nombre){
        tvNombreusuario.setText(nombre);
    }

    public void setIconoUsuario(Uri uri){
        Glide.with(context)
                .load(uri)
                .into(cvImagenUsuario);
    }



}
