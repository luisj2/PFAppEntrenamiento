package com.example.fithealth.View.Activitys.Social

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fithealth.R

class MensajesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensajes)

    }

    /*
    private fun actualizacionMensajesListener() {
        helper!!.mensajesListener(idEmisor!!, idNodoMensajes, object : RecepcionMensajes() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            fun mensajeRecibido(mensaje: Mensaje?) {
                cambiarDia()
                adapter!!.addElemento(mensaje)
                rvMensajes!!.adapter = adapter
                adapter!!.notifyItemChanged(adapter!!.elementos.size - 1)
            }

            fun mensajeEnviado(mensaje: Mensaje?) {
                cambiarDia()
                adapter!!.addElemento(mensaje)
                rvMensajes!!.adapter = adapter
                adapter!!.notifyItemChanged(adapter!!.elementos.size - 1)
            }
        })
    }


    private fun cambiarDia() {
        if (mensajeCambioDia) {
            val dateTime = ZonedDateTime.now(ZoneId.systemDefault())
            val localDate = dateTime.toLocalDate()
            adapter!!.addElemento(ElementoFecha(localDate, "Hoy"))
            adapter!!.notifyItemChanged(adapter!!.elementos.size - 1)
        }
    }

    private fun btnEnviarMensajeListener() {
        btnEnviarMensaje!!.setOnClickListener {
            enviarMensaje()
            etMensaje!!.setText("")
        }
    }

    private fun enviarMensaje() {
        val txtMensaje = etMensaje!!.text.toString().trim { it <= ' ' }
        if (!txtMensaje.isEmpty()) {
            helper!!.enviarMensaje(idEmisor!!, idNodoMensajes, idReceptor!!, txtMensaje)
        } else {
            Toast.makeText(this, "No puedes enviar el mensaje vacio", Toast.LENGTH_SHORT).show()
        }
    }
     */
}