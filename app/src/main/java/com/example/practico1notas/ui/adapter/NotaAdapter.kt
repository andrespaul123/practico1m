package com.example.practico1notas.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.practico1notas.R
import com.example.practico1notas.models.Nota
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NotaAdapter(
    private val notaList: ArrayList<Nota>,
    private val listener: OnNotaClickListener
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_blognota, parent, false)
        return NotaViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return notaList.size
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        holder.bind(notaList[position], listener)
    }

    fun itemAdded(nota: Nota) {
        notaList.add(1, nota)
        notifyItemInserted(1)
    }

    fun itemDeleted(nota: Nota) {
        val index = notaList.indexOf(nota)
        notaList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun itemUpdated(nota: Nota) {
        val index = notaList.indexOf(nota)
        notaList[index] = nota
        notifyItemChanged(index)
    }

    class NotaViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
        private val lblDescripcion: TextView = itemView.findViewById(R.id.lblEditarN)
        private val btnColor: ImageButton = itemView.findViewById(R.id.btnColor)
        private val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
        private val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminar)
        private val notaLayout: ConstraintLayout = itemView.findViewById(R.id.notaLayout)

        // Array de colores disponibles para seleccionar
        private val colorArray = arrayOf(
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN,
            Color.MAGENTA, Color.LTGRAY,  Color.WHITE,
            Color.rgb(255,165,0),Color.rgb(0, 255, 191)
        )

        fun bind(nota: Nota, listener: OnNotaClickListener) {
            lblDescripcion.text = nota.descripcion

            // Establecer el color de fondo de acuerdo al color de la nota
            notaLayout.setBackgroundColor(nota.color)

            btnEditar.setOnClickListener {
                listener.onEditClickListener(nota)
            }

            btnEliminar.setOnClickListener {
                listener.onDeleteClickListener(nota)
            }

            // Listener para el botón de seleccionar color
            btnColor.setOnClickListener {
                showColorPickerDialog(nota)
            }
        }

        // Muestra un diálogo para seleccionar el color
        private fun showColorPickerDialog(nota: Nota) {
            val colorNames = arrayOf("Rojo", "Azul", "Verde", "Amarillo", "Celeste", "Morado", "Gris", "Blanco", "Naranja", "Turquesa")

            MaterialAlertDialogBuilder(context)
                .setTitle(" Selecciona un color")
                .setItems(colorNames) { dialog, which ->
                    // Cambiar el color del layout al seleccionar un color
                    val selectedColor = colorArray[which]
                    notaLayout.setBackgroundColor(selectedColor)

                    // Almacenar el color en el objeto nota
                    nota.color = selectedColor
                }
                .show()
        }
    }

    interface OnNotaClickListener {
        fun onEditClickListener(nota: Nota)
        fun onDeleteClickListener(nota: Nota)
    }
}
