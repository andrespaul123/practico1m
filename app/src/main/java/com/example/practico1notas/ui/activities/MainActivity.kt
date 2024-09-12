package com.example.practico1notas.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practico1notas.R
import com.example.practico1notas.models.Nota
import com.example.practico1notas.ui.adapter.NotaAdapter

import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NotaAdapter.OnNotaClickListener {
    private val datalist = arrayListOf(
        Nota("practico 1", Color.WHITE),
        Nota("Tarea 2", Color.WHITE),
        Nota("Nota 3", Color.WHITE),
        Nota("Nota 4", Color.WHITE),
        Nota("Nota 5", Color.WHITE),

    )

    private lateinit var rcvNotas: RecyclerView
    private lateinit var btnAgregarN: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rcvNotas = findViewById(R.id.rcvNotas)
        btnAgregarN = findViewById(R.id.btnAgregarN)
        setupRecyclerView()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        btnAgregarN.setOnClickListener {
            buildAlertDialog()
        }
    }

    private fun setupRecyclerView() {
        rcvNotas.adapter = NotaAdapter(datalist, this)
        rcvNotas.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

    }

    private fun buildAlertDialog(nota: Nota? = null) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Agregar Nota")

        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.item_view, null, false)

        val lblDescripcion: EditText = viewInflated.findViewById(R.id.lblDescripcion)
        lblDescripcion.setText(nota?.descripcion)
        builder.setView(viewInflated)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
            val descripcion = lblDescripcion.text.toString()

            if (nota != null) {
                nota.descripcion = descripcion
                editNotaFromList(nota)
            } else {

                addNotaTolist(descripcion)

            }
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }


    private fun editNotaFromList(nota: Nota) {
        val adapter = rcvNotas.adapter as NotaAdapter
        adapter.itemUpdated(nota)
    }

    private fun addNotaTolist(descripcion: String) {
        val nota = Nota(descripcion, Color.WHITE)
        val adapter = rcvNotas.adapter as NotaAdapter
        adapter.itemAdded(nota)
    }

    override fun onEditClickListener(nota: Nota) {
        buildAlertDialog(nota)
    }

    override fun onDeleteClickListener(nota: Nota) {
        val adapter = rcvNotas.adapter as NotaAdapter
        adapter.itemDeleted(nota)
    }



}