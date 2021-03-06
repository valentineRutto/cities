package com.valentinerutto.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsFragment : Fragment() {
    private val viewModel by viewModel<CitiesViewModel>()


    private val callback = OnMapReadyCallback { googleMap ->

        viewModel.currentCityList.observe(
            this
        ) { citiesResponse ->

            citiesResponse?.map {
                googleMap.addMarker(
                    MarkerOptions().position(LatLng(it.latitude, it.longitude)).title(it.name)
                )
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLng(
                        LatLng(
                            it.latitude,
                            it.longitude
                        )
                    )
                )

            }

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val page = requireArguments().getInt("page")
        viewModel.init(page, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }


}