package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.BusquedaSocial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fithealth.Model.Firebase.FirebaseHelper;
import com.example.fithealth.View.Activitys.Social.MensajesActivity;
import com.example.fithealth.Model.DataClass.Contacto;
import com.example.fithealth.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosHolder> {

    List<Contacto> contactos;

    private Context context;

    private Contacto contacto;

    public ContactosAdapter(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    @NonNull
    @Override
    public ContactosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_contactos,parent,false);

        return new ContactosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactosHolder holder, int position) {

        contacto = contactos.get(position);

        //holder.setTxtNombreContacto(contacto.nombre);

        /*
        if(contacto.rutaImagen != null){
           holder.setCircleViewPerfil(contacto.rutaImagen,context);
        }

         */


    /*
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
         */


    }

    /*
    private void clickViewListener(View view,Contacto contacto) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUsuarioAuth = FirebaseHelper.getIdUsuario();
                String nodoMensajeContactos = idUsuarioAuth+ contacto.id;
                Intent intent = new Intent(context, MensajesActivity.class);
                intent.putExtra("NombreContacto", contacto.nombre);
                intent.putExtra("ImagenContacto", contacto.rutaImagen);
                intent.putExtra("IdUsuario", contacto.id);
                intent.putExtra("idNodo",nodoMensajeContactos);
                context.startActivity(intent);
            }
        });
    }

     */

    @Override
    public int getItemCount() {
        return contactos.size();
    }
}

class ContactosHolder extends RecyclerView.ViewHolder{

    private TextView tvNombreContacto;
    private View itemView;
    private CircleImageView cvImagenPerfil;
    public ContactosHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        enlazarComponentes(itemView);

    }

    private void enlazarComponentes(View view) {
        tvNombreContacto = view.findViewById(R.id.tvNombreContacto);
        cvImagenPerfil = view.findViewById(R.id.cvImagenPerfil);
    }

    public void setTxtNombreContacto(String nombre) {
        tvNombreContacto.setText(nombre);
    }

    public void setCircleViewPerfil(Uri uri,Context context){
        Glide.with(context)
                .load(uri)
                .into(cvImagenPerfil);
    }

    public View getItemView() {
        return itemView;
    }


}
