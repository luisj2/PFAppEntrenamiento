package com.example.fithealth.View.Fragments.Social

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.FitHealthApp
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.Model.Utils.ExtensionUtils.dissmissLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.showLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.FriendRequest.FriendRequestAdapter
import com.example.fithealth.ViewModel.Auth.Firestore.User.UserFirestoreViewModel
import com.example.fithealth.ViewModel.Auth.Firestore.User.UserFirestoreViewModelBuilder
import com.example.fithealth.ViewModel.Auth.Realtime.User.UserRealtimeViewModel
import com.example.fithealth.ViewModel.Auth.Realtime.User.UserRealtimeViewModelBuilder
import com.example.fithealth.databinding.FragmentSolicitudesAmistadBinding

class SolicitudesAmistadFragment : Fragment() {

    private var _binding: FragmentSolicitudesAmistadBinding? = null
    private val binding get() = _binding!!

    private val userRealtimeViewModel: UserRealtimeViewModel by viewModels {
        UserRealtimeViewModelBuilder.getUserRealtimeViewModel()
    }


    private val userFirestoreViewModel: UserFirestoreViewModel by viewModels {
        UserFirestoreViewModelBuilder.getUserViewModelFactory()
    }

    private var loggedUser: SearchUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userFirestoreViewModel.getLoggedUser()
        syncRequests()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSolicitudesAmistadBinding.inflate(layoutInflater)


        setupUI()

        return binding.root
    }

    private fun setupUI() {
        setupRecyclers()
        setupObservers()
        setupOnRefresh()
    }

    private fun setupOnRefresh() {
        binding.refreshLayout.apply {
            setOnRefreshListener {
                refreshingTo(true)
                syncRequests()
            }
        }

    }

    private fun refreshingTo(refreshingStatus: Boolean) {
        binding.refreshLayout.isRefreshing = refreshingStatus
    }

    private fun isRefreshing(): Boolean {
        return binding.refreshLayout.isRefreshing
    }

    private fun setupObservers() {
        setupFirestoreObservers()
        setupRealtimeObservers()
    }

    private fun setupFirestoreObservers() {

        userFirestoreViewModel.loggedUser.observe(viewLifecycleOwner) {
            loggedUser = it
        }


    }

    private fun setupRealtimeObservers() {

        userRealtimeViewModel.apply {
            requestList.observe(viewLifecycleOwner) { userRequestList ->
                if (userRequestList.isEmpty()) updateToEmptyRequstList()
                else updateToItemsRequestList(userRequestList)

                if (isRefreshing()) refreshingTo(false)
            }

            isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) requireActivity().showLoadingScreen()
                else requireActivity().dissmissLoadingScreen()
            }

            removeRequestStatus.observe(viewLifecycleOwner) { removeRequestStatus ->
                if (removeRequestStatus) performRemoveRequestActions()
                else performRemoveRequestError()
            }

            agreeFriendStatus.observe(viewLifecycleOwner) { agreeFriendStatus ->
                if (agreeFriendStatus) toast("Amigo añadido correctamente")
                else toast("Error al aceptar la solicitud")
            }

            agreePending.observe(viewLifecycleOwner) { user ->
                acceptFriendRequest(user)
            }
        }


    }

    private fun performRemoveRequestError() {
        toast("Error al eliminar la solicitud")
    }

    private fun performRemoveRequestActions() {
        syncRequests()
        toast("Solicitud eliminada")
    }

    private fun syncRequests() {
        userRealtimeViewModel.getUserRequests()
    }

    private fun updateToItemsRequestList(userRequestList: List<SearchUser>) {
        noRequestsVisibility(false)
        updateRequestList(userRequestList)
    }


    private fun updateToEmptyRequstList() {
        noRequestsVisibility(true)
        updateRequestList(emptyList())
    }

    private fun updateRequestList(userRequestList: List<SearchUser>) {
        val adapter = binding.rvRequestList.adapter as FriendRequestAdapter
        adapter.updateList(userRequestList)
    }

    private fun noRequestsVisibility(visible: Boolean) {
        if (visible) binding.tvNoRequest.visibility = View.VISIBLE
        else binding.tvNoRequest.visibility = View.GONE
    }

    private fun setupRecyclers() {
        binding.apply {
            rvRequestList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter =
                    FriendRequestAdapter(emptyList(),
                        onAcceptRequest = { user ->

                            acceptFriendRequest(user)
                        },
                        onRejectRequst = { id -> rejectFriendRequest(id) })

            }
        }
    }

    private fun rejectFriendRequest(id: String) {
        userRealtimeViewModel.removeUserRequest(id)
    }

    private fun acceptFriendRequest(userToAgree: SearchUser) {
        if (loggedUser != null) userRealtimeViewModel.agreeFriend(userToAgree, loggedUser!!)
        else toast("Ocurrio un problema al añadir al usuario")
    }
}