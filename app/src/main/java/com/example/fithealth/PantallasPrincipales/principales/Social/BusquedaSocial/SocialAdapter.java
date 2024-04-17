package com.example.fithealth.PantallasPrincipales.principales.Social.BusquedaSocial;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.PantallaCarga;
import com.example.fithealth.R;
import com.example.fithealth.Usuario.Usuario;

import java.util.List;

public class SocialAdapter extends RecyclerView.Adapter<SocialViewHolder>{

    private List<Usuario> usuarios; //lista de usuarios que va a mostrar en la lista

    private FirebaseHelper helper;



    public SocialAdapter(List<Usuario> usuarios, FirebaseHelper helper) {
        this.usuarios = usuarios;
        this.helper = helper;

    }

    //Inflar la vista del adapter
    @NonNull
    @Override
    public SocialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.social_usuarios_item,parent,false);
        return new SocialViewHolder(view,helper);
    }

    //Funcionalidad del adapter
    @Override
    public void onBindViewHolder(@NonNull SocialViewHolder holder, int position) {

        Usuario usuario = usuarios.get(position);


        holder.setTvNombreUsuario(usuario.getNombreUsario());
        holder.setTvHashtag("# "+usuario.getHastagIdentificativo());
        //holder.adaptarImagenUsuario();
        holder.onClickAniadirAmigo(usuario);

    }

    //Items que se mostraran en el RecyclerView
    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}

class SocialViewHolder extends RecyclerView.ViewHolder{


    //Items de la vista
   private TextView tvNombreUsuario;

    private TextView tvHashtagUsuario;

    private ImageView ivIconoUsuario;

    private ImageButton btnAniadirAmigo;

    private FirebaseHelper helper;

    public SocialViewHolder(@NonNull View itemView,FirebaseHelper helper) {
        super(itemView);

        this.helper = helper;

        enlazarComponentes(itemView);

        funcionalidadSolicitudAmistad();

        Glide.with(itemView.getContext())
                .load(R.drawable.ic_agregaramigo)
                .fitCenter()
                .into(btnAniadirAmigo);



    }

    private void funcionalidadSolicitudAmistad() {

        btnAniadirAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void enlazarComponentes(View view) {
        tvNombreUsuario = view.findViewById(R.id.tvNombreUsuarioItem);
        ivIconoUsuario = view.findViewById(R.id.ivIconoUsuario);
        btnAniadirAmigo = view.findViewById(R.id.btnAniadirAmigo);
        tvHashtagUsuario = view.findViewById(R.id.tvHashtagUsuarioAmistad);
    }
    public void setTvNombreUsuario(String nombreUsuario){
        tvNombreUsuario.setText(nombreUsuario);
    }

    public void setTvHashtag(String hashtag){
        tvHashtagUsuario.setText(hashtag);
    }

    public void adaptarImagenUsuario (){
        Glide.with(itemView.getContext()).load(ivIconoUsuario.getDrawable()).into(ivIconoUsuario);
    }

    public void onClickAniadirAmigo(Usuario usuario){
        btnAniadirAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helper = new FirebaseHelper(v.getContext());


                helper.getIdUsuarioActual(new FirebaseHelper.IdUsuarioActual() {
                    @Override
                    public void getIdUsuario(String id) {

                         helper.aniadirSolicitudAmistad(id,usuario.getNombreUnico());
                        //helper.prueba();
                    }
                });




            }
        });
    }
}
