package com.example.fithealth.View.Activitys.Social

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.Model.Database.Tables.Entitys.Message
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.MessageAdapter
import com.example.fithealth.ViewModel.Auth.Realtime.User.UserRealtimeViewModel
import com.example.fithealth.ViewModel.Auth.Realtime.User.UserRealtimeViewModelBuilder
import com.example.fithealth.ViewModel.Local_Database.User.UserDatabaseViewModel
import com.example.fithealth.ViewModel.Local_Database.User.UserDatabaseViewModelBuilder
import com.example.fithealth.databinding.ActivityMensajesBinding
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMensajesBinding

    private val userRealtimeViewModel: UserRealtimeViewModel by viewModels {
        UserRealtimeViewModelBuilder.getUserRealtimeViewModel()
    }
    private val userDatabaseViewModel: UserDatabaseViewModel by viewModels {
        UserDatabaseViewModelBuilder.getUserDatabaseViewModelFactory(this)
    }

    private var contact: Contact? = null


    private companion object {
        const val CONTACT_ID_KEY = "contactId"
        const val MESSAGE_IN_PROCESS = 1
        const val MESSAGE_SUCCESS = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMensajesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        loadContactFromIntent()
        setupRecyclerView()
        setupOnClicks()
        setupObservers()
    }

    private fun getMessageList(contactId: String) {
        userDatabaseViewModel.getMessageList(contactId)
    }

    private fun setupRecyclerView() {
        binding.rvMensajes.apply {
            val manager = LinearLayoutManager(this@MessageActivity).apply {
                stackFromEnd = true
            }
            this.layoutManager = manager
        }
    }

    private fun loadContactFromIntent() {
        val contactId = intent.getStringExtra(CONTACT_ID_KEY)

        if (!contactId.isNullOrEmpty()) {
            userRealtimeViewModel.getContactById(contactId)
        }
    }

    private fun setupOnClicks() {
        binding.apply {
            btnEnviarMensaje.setOnClickListener {
                val messageText: String = etMensaje.text.toString()
                if (messageText.isNotBlank()) {

                    contact?.let { contact ->
                        val message = Message(
                            messageContent = messageText,
                            messageMine = true,
                            contactOwnerId = contact.contactId
                        )
                        putMessageInView(message)
                        sendMessageToContactId(message)
                        saveMessageDatabase(message)

                    } ?: toast("Ha ocurrido un error intentalo mas tarde")
                } else {
                    toast("Mensaje vacio")
                }
            }
        }
    }

    private fun putMessageInView(message: Message) {
        binding.rvMensajes.adapter?.let { adapter ->
            noMessageVisibility(false)
            val messageAdapter = adapter as MessageAdapter
            messageAdapter.addMessage(message)
        } ?: toast("Ha ocurrido un error")
    }

    private fun updateMessages(contactId: String) {
        userDatabaseViewModel.getMessageList(contactId)
    }

    private fun saveMessageDatabase(message: Message) {
        userDatabaseViewModel.insertMessageToContact(message)
    }


    //tenog que manegar la logica de mandar el mensje al servidor y posteriormente recibirlo desde el servicio
    private fun sendMessageToContactId(message: Message) {
        userRealtimeViewModel.sendMessage(message)

    }

    private fun setupObservers() {
        setupRealtimeObservers()
        setupDatabaseObservers()
    }

    private fun setupDatabaseObservers() {
        userDatabaseViewModel.apply {
            lifecycleScope.launch {
                messageList.collect { messageList ->

                    Log.i("infoMessages", messageList.toString())
                    noMessageVisibility(messageList.isEmpty())
                    if (messageList.isEmpty()) binding.rvMensajes.adapter = MessageAdapter()
                    else binding.rvMensajes.adapter =
                        MessageAdapter(putDaysInMessageList(messageList))
                }

                messageStatus.collect { status ->
                    status?.let {
                        if (it) {
                            Log.i("messageStatus", "El mensaje fue guardado en la base de datos")
                        } else {
                            Log.i("messageStatus", "El mensaje no fue guardado en la base de datos")
                        }
                    }
                }


            }


        }
    }

    private fun putDaysInMessageList(messageList: List<Message>): List<Any> {
        return if (messageList.isEmpty()) emptyList()
        else {
            val result: MutableList<Any> = mutableListOf()

            var lastDateLabel: String? = null

            val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM")

            for (message in messageList) {
                val currentMessageDate = message.messageDate.toLocalDate()

                val currentDateLabel: String = when {
                    currentMessageDate.isEqual(LocalDateTime.now().toLocalDate()) -> "Hoy"
                    currentMessageDate.isEqual(
                        LocalDateTime.now().toLocalDate().minusDays(1)
                    ) -> "Ayer"

                    else -> currentMessageDate.format(formatter)
                }

                if (lastDateLabel != currentDateLabel) {
                    result.add(currentDateLabel)
                    lastDateLabel = currentDateLabel
                }

                result.add(message)
            }

            return result.toList()
        }
    }

    private fun setupContactInfo(contact: Contact) {
        binding.apply {
            tvNombreUsuarioMensaje.text = contact.userName
        }
    }

    private fun setupNullUserInformation() {
        binding.apply {
            tvNombreUsuarioMensaje.text = "???"
        }
    }

    private fun noMessageVisibility(visible: Boolean) {
        Log.i("pruebaBoolean", "hola soy $visible")
        binding.tvNoMessage.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun getAdapterFromRecyclerAsMessageAdapter(): MessageAdapter =
        binding.rvMensajes.adapter as MessageAdapter

    private fun setupRealtimeObservers() {
        userRealtimeViewModel.apply {
            contactById.observe(this@MessageActivity) { contactToMessage ->
                contactToMessage?.let {
                    setupContactInfo(it)
                    getMessageList(it.contactId)
                    contact = it
                } ?: setupNullUserInformation()
            }

            isMessageSend.observe(this@MessageActivity) { isMessageSend ->
                if (isMessageSend) Log.i("messageStatus", "mensaje enviado realtime")
                else toast("Mensaje no enviado")
            }

            lifecycleScope.launch {
                loadingMessage.collect { loadingMessage ->
                    loadingMessage?.let {
                        //if(it)
                    }
                }
                messageSent.collect { messageSent ->
                    messageSent?.let { message ->
                        message.messageMine = false
                        getAdapterFromRecyclerAsMessageAdapter().addMessage(message)
                    }
                }
            }

        }
    }
}