package com.example.fithealth.View.Fragments.Social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.View.ReyclerAdapters.Social.ContactsAdapter.ContactsAdapter
import com.example.fithealth.ViewModel.Auth.Realtime.User.UserRealtimeViewModel
import com.example.fithealth.ViewModel.Auth.Realtime.User.UserRealtimeViewModelBuilder
import com.example.fithealth.ViewModel.Local_Database.User.UserDatabaseViewModel
import com.example.fithealth.ViewModel.Local_Database.User.UserDatabaseViewModelBuilder
import com.example.fithealth.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding: FragmentContactsBinding get() = _binding!!

    private val userRealtimeViewModel: UserRealtimeViewModel by viewModels {
        UserRealtimeViewModelBuilder.getUserRealtimeViewModel()
    }

    private val userDatabaseViewModel: UserDatabaseViewModel by viewModels {
        UserDatabaseViewModelBuilder.getUserDatabaseViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)

        setupUI()
        return binding.root
    }

    private fun setupUI() {
        //loadContacts()
        userRealtimeViewModel.getAllUserContacts()
        setupObservers()
    }

    private fun setupObservers() {
        setupRealtimeObservers()
        setupDatabaseObservers()
    }

    private fun setupDatabaseObservers() {
        userDatabaseViewModel.apply {
            contactList.observe(viewLifecycleOwner){contactList->
                changeNoElementMessageVisibility(contactList.isEmpty())
                setupRecycler(contactList)
            }
        }
    }

    private fun setupRealtimeObservers() {
        userRealtimeViewModel.apply {
            contactList.observe(viewLifecycleOwner){contactList->
                changeNoElementMessageVisibility(contactList.isEmpty())
                setupRecycler(contactList)
            }
        }
    }

    private fun loadContacts() {
        userDatabaseViewModel.getAllContactsFromLoggedUser()
    }

    private fun setupRecycler(contacts: List<Contact>) {
        binding.rvContactos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ContactsAdapter(contacts)
        }
    }

    private fun changeNoElementMessageVisibility(visible: Boolean) {
        binding.tvNoElementMessage.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}