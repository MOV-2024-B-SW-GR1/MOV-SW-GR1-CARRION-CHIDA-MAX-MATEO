package com.example.a04_deber01.ui.carrera

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a04_deber01.data.local.UniversidadDatabase
import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.databinding.ActivityCarreraListBinding
import com.example.a04_deber01.repository.UniversidadRepository
import com.example.a04_deber01.ui.carrera.adapter.CarreraAdapter
import com.example.a04_deber01.utils.showCarreraDialog
import com.example.a04_deber01.utils.showUpdateCarreraDialog
import kotlinx.coroutines.launch

class CarreraListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarreraListBinding
    private lateinit var adapter: CarreraAdapter
    private lateinit var viewModel: CarreraViewModel
    private var facultadId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarreraListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        facultadId = intent.getIntExtra(EXTRA_FACULTAD_ID, 0)


        val database = UniversidadDatabase.getDatabase(this)
        val repository = UniversidadRepository(
            database.facultadDao(),
            database.carreraDao()
        )
        viewModel = CarreraViewModel(repository, facultadId)

        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = CarreraAdapter(
            onDeleteClick = { carreraId ->
                viewModel.deleteCarrera(carreraId)
            },
            onEditClick = { carrera ->
                showUpdateCarreraDialog(
                    context = this,
                    carrera = carrera
                ) { nombre, anioInicio, acreditada, creditosTotales, mensualidad ->
                    viewModel.updateCarrera(
                        carrera.copy(
                            nombre = nombre,
                            anioInicio = anioInicio,
                            acreditada = acreditada,
                            creditosTotales = creditosTotales,
                            mensualidad = mensualidad
                        )
                    )
                }
            }
        )

        binding.recyclerViewCarrera.apply {
            layoutManager = LinearLayoutManager(this@CarreraListActivity)
            adapter = this@CarreraListActivity.adapter
        }


        lifecycleScope.launch {
            viewModel.getCarreras().collect { listaCarreras ->
                adapter.submitList(listaCarreras)
            }
        }
    }

    private fun setupFab() {
        binding.fabCarrera.setOnClickListener {
            showCarreraDialog(
                context = this
            ) { nombre, anioInicio, acreditada, creditosTotales, mensualidad ->
                viewModel.insertCarrera(
                    Carrera(
                        id = 0,
                        nombre = nombre,
                        anioInicio = anioInicio,
                        acreditada = acreditada,
                        creditosTotales = creditosTotales,
                        mensualidad = mensualidad,
                        facultadId = facultadId
                    )
                )

            }
        }
    }



    companion object {
        private const val EXTRA_FACULTAD_ID = "extra_facultad_id"

        fun newIntent(context: Context, facultadId: Int): Intent {
            return Intent(context, CarreraListActivity::class.java).apply {
                putExtra(EXTRA_FACULTAD_ID, facultadId)
            }
        }
    }
}
