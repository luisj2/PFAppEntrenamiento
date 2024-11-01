package com.example.fithealth.View.Fragments.Social

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.Model.Utils.ExtensionUtils.dissmissLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.showLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.BusquedaSocial.UserSearchAdapter
import com.example.fithealth.ViewModel.Auth.Firestore.User.UserFirestoreViewModel
import com.example.fithealth.ViewModel.Auth.Firestore.User.UserFirestoreViewModelBuilder
import com.example.fithealth.ViewModel.Auth.Realtime.User.UserRealtimeViewModel
import com.example.fithealth.ViewModel.Auth.Realtime.User.UserRealtimeViewModelBuilder
import com.example.fithealth.databinding.FragmentSearchUsersBinding

class SearchUsersFragment : Fragment() {


    private var _binding: FragmentSearchUsersBinding? = null
    private val binding get() = _binding!!

    private val userFirestoreViewModel: UserFirestoreViewModel by viewModels {
        UserFirestoreViewModelBuilder.getUserViewModelFactory()
    }

    private val userRealtimeViewModel: UserRealtimeViewModel by viewModels {
        UserRealtimeViewModelBuilder.getUserRealtimeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchUsersBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        setupRecyclerView()
        setupOnClicks()
        setupObversers()
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvListaUsuarios.let { recycler ->
                recycler.layoutManager = LinearLayoutManager(context)

                recycler.adapter = UserSearchAdapter(emptyList()) { user ->
                    sendUserRequest(user)
                }
            }

        }
    }

    private fun sendUserRequest(user: UserSearch) {
        userRealtimeViewModel.sendRequest(user)
    }


    private fun setupObversers() {
        userFirestoreObservers()
        userRealtimeObservers()
    }

    private fun userRealtimeObservers() {
        userRealtimeViewModel.apply {
            sendRequestStatus.observe(viewLifecycleOwner) { sendSuccess ->
                if (sendSuccess) toast("Solicitud enviada correctamente")
                else toast("Error al enviar la solicitud")
            }
            isLoading.observe(viewLifecycleOwner) { isLoading ->
                handleLoadingScreen(isLoading)
            }
        }
    }

    private fun userFirestoreObservers() {
        userFirestoreViewModel.apply {

            isLoading.observe(viewLifecycleOwner) { isLoading ->
                handleLoadingScreen(isLoading)
            }


            userListByName.observe(viewLifecycleOwner) { userList ->
                userList?.let { list ->
                    if (list.isEmpty()) toast("No existen usuarios")
                    else {
                        Log.i("userList", userList.toString())
                        updateSearchUserList(list)
                    }
                }
            }


        }
    }

    private fun handleLoadingScreen(loading: Boolean) {
        if (loading) requireActivity().showLoadingScreen()
        else requireActivity().dissmissLoadingScreen()
    }

    private fun updateSearchUserList(userList: List<UserSearch>) {
        val adapter = binding.rvListaUsuarios.adapter as UserSearchAdapter
        adapter.updateList(userList)
    }


    private fun setupOnClicks() {
        binding.apply {
            btnSearchUsers.setOnClickListener {
                searchUsersByName(etSearchUsers.text.toString())
            }
        }
    }

    private fun searchUsersByName(userName: String) {
        userFirestoreViewModel.getUserByName(userName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.etSearchUsers.setText("")
        userFirestoreViewModel.clearSearchResults()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}