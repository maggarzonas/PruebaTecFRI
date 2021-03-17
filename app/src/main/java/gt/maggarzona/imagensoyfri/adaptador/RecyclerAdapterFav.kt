package gt.maggarzona.imagensoyfri.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gt.maggarzona.imagensoyfri.R
import gt.maggarzona.imagensoyfri.clases.clsInformacion
import gt.maggarzona.imagensoyfri.holder.ViewHolderFav

class RecyclerAdapterFav : RecyclerView.Adapter<ViewHolderFav>() {
    var campos: List<clsInformacion>  = ArrayList()
    lateinit var context: Context
    var tipo: Int = 0

    fun RecyclerAdapterFav(lista: List<clsInformacion>, context: Context, tipo: Int){
        this.campos = lista
        this.context = context
        this.tipo = tipo
    }
    override fun onBindViewHolder(holder: ViewHolderFav, position: Int) {
        val item = campos.get(position)
        holder.bind(item, context, tipo)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFav {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderFav(layoutInflater.inflate(R.layout.item_imagenes, parent, false))
    }

    override fun getItemCount(): Int {
        return campos.size
    }
}