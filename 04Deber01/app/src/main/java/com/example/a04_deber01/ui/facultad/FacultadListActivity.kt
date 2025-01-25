package com.example.a04_deber01.ui.facultad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a04_deber01.data.model.Facultad
import com.example.a04_deber01.databinding.ActivityFacultadListBinding
import com.example.a04_deber01.repository.UniversidadRepository
import com.example.a04_deber01.ui.carrera.CarreraListActivity
import com.example.a04_deber01.ui.facultad.adapter.FacultadAdapter
import com.example.a04_deber01.utils.showFacultadDialog
import com.example.a04_deber01.utils.showUpdateFacultadDialog
import kotlinx.coroutines.launch

class FacultadListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFacultadListBinding
    private val repository = UniversidadRepository()
    private val viewModel = FacultadViewModel(repository)
    private lateinit var adapter: FacultadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFacultadListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = FacultadAdapter(
            onItemClick = { facultad ->
                startActivity(
                    CarreraListActivity.newIntent(this, facultad.id)
                )
            },
            onDeleteClick = { facultadId ->
                viewModel.deleteFacultad(facultadId)
                refreshData()
            },
            onEditClick = { facultad ->
                showUpdateFacultadDialog(
                    context = this,
                    facultad = facultad
                ) { nombre, fechaCreacion, activa, numeroDepartamentos, presupuestoAnual ->
                    viewModel.updateFacultad(
                        facultad.copy(
                            nombre = nombre,
                            fechaCreacion = fechaCreacion,
                            activa = activa,
                            numeroDepartamentos = numeroDepartamentos,
                            presupuestoAnual = presupuestoAnual
                        )
                    )
                    refreshData()
                }
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FacultadListActivity)
            adapter = this@FacultadListActivity.adapter
        }
        refreshData()
    }

    private fun refreshData() {
        lifecycleScope.launch {
            val facultades = repository.getAllFacultades()
            adapter.submitList(facultades)
        }
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            showFacultadDialog(
                context = this
            ) { nombre, fechaCreacion, activa, numeroDepartamentos, presupuestoAnual ->
                viewModel.insertFacultad(
                    Facultad(
                        id = 0,
                        nombre = nombre,
                        fechaCreacion = fechaCreacion,
                        activa = activa,
                        numeroDepartamentos = numeroDepartamentos,
                        presupuestoAnual = presupuestoAnual
                    )
                )
                refreshData()
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FacultadListActivity::class.java)
        }
    }
}
