package com.example.a04_deber01.ui.facultad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a04_deber01.data.local.UniversidadDatabase
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
    private lateinit var viewModel: FacultadViewModel
    private lateinit var adapter: FacultadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultadListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val database = UniversidadDatabase.getDatabase(this)
        val repository = UniversidadRepository(
            database.facultadDao(),
            database.carreraDao()
        )
        viewModel = FacultadViewModel(repository)

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
                }
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FacultadListActivity)
            adapter = this@FacultadListActivity.adapter
        }


        lifecycleScope.launch {
            viewModel.getAllFacultades().collect { listFacultades ->
                adapter.submitList(listFacultades)
            }
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

            }
        }
    }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FacultadListActivity::class.java)
        }
    }
}
