package com.sebade.relasiroom.ui.homepage.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.FragmentCheckoutBinding
import com.sebade.relasiroom.model.seat.SeatDetail
import com.sebade.relasiroom.utils.ModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CheckoutFragment : Fragment() {

    private lateinit var viewModel: CheckoutViewModel
    private var rvAdapter: CheckoutAdapater? = null
    private lateinit var binding: FragmentCheckoutBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false)

        val argListOrder = CheckoutFragmentArgs.fromBundle(requireArguments()).listFilm.map {
            with(it) {
                SeatDetail(isBooked, row, seatNumber)
            }
        }

        val argsFilm = CheckoutFragmentArgs.fromBundle(requireArguments()).idFilm

        viewModel = ViewModelProvider(
            requireActivity(), ModelFactory(
                requireActivity().application
            )
        )[CheckoutViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.setListOrder(argListOrder, argsFilm)
        rvAdapter = CheckoutAdapater()

        binding.rvSeat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listOrder.observe(viewLifecycleOwner) {
            rvAdapter?.setList(it)
        }
        viewModel.navigatedToFragment.observe(viewLifecycleOwner) {
            if (it) {

                viewModel.postFilmResponse.observe(viewLifecycleOwner) { historyFilm ->
                    showNotif()
                    requireView().findNavController()
                        .navigate(
                            CheckoutFragmentDirections
                                .actionCheckoutFragmentToFragmentCheckoutSuccess(historyFilm)
                        )
                    viewModel.navigatedDone()
                }

            }
        }

        /** Test */
        viewModel.suck.observe(viewLifecycleOwner) {
            if (it != null) {
                it.username?.let { it1 -> viewModel.getUserTransaction(it1) }
            }
        }
    }

    private fun showNotif() {
        val notifcationId = 1
        val channelId = "CheckoutFragment"
        val channelName = "ini Channel Fragment "
        var notificationChannel: NotificationChannel
        var builder: Notification.Builder
        val notifManager: NotificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat
            .Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_baseline_10k_24)
            .setContentTitle("Set Context Title")
            .setContentText("setContextText")
            .setSubText("setSubText")
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = channelName
            channel.enableVibration(true)
            mBuilder.setChannelId(channelId)
            notifManager.createNotificationChannel(channel)
            /*notificationChannel = NotificationChannel(
                channelId,
                descripttion,
                NotificationManager.IMPORTANCE_HIGH)
                .apply {
                    enableLights(true)
                    enableVibration(true)
                }
            notifManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(requireContext(), channelId)*/
        }
        val notification = mBuilder.build()
        notifManager.notify(1, notification)
    }
}