package com.example.fithealth.Model.Database.Repositorys.Contacts

import android.util.Log
import com.example.fithealth.Model.Database.Dao.ContactsDao
import com.example.fithealth.Model.Database.Dao.MessageDao
import com.example.fithealth.Model.Database.Dao.UserDao
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.Model.Database.Tables.Entitys.Message
import com.example.fithealth.Model.Database.Tables.Entitys.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDatabaseRepositoryImpl(
    private val contactDao: ContactsDao,
    private val userDao: UserDao,
    private val messageDao: MessageDao
) : UserDatabaseRepository {

    private val ERROR = "user_database_error"


    override suspend fun insertLoggedUser(user: User): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                userDao.insertUser(user) > 0
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun insertContact(contact: Contact): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                contactDao.insertContact(contact) > 0
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun insertMessageToContact(
        message : Message
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                messageDao.insertMessage(message)
                true
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun getAllContactsFromLoggedUser(): List<Contact> {
        return withContext(Dispatchers.IO) {
            try {
                contactDao.getAllContactsFromUserId(FirebaseAuth.getInstance().currentUser!!.uid)
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                emptyList()
            }
        }
    }

    override suspend fun getContactMessages(contactId: String): List<Message> {
        return withContext(Dispatchers.IO) {
            try {
                messageDao.getMessagesByContactId(contactId)
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                emptyList()
            }
        }
    }
}