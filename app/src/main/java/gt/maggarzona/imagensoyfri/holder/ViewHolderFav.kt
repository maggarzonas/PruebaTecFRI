package gt.maggarzona.imagensoyfri.holder

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gt.maggarzona.imagensoyfri.R
import gt.maggarzona.imagensoyfri.baseDatos.baseDatos
import gt.maggarzona.imagensoyfri.clases.clsInformacion
import gt.maggarzona.imagensoyfri.conexiones.conexionWeb


class  ViewHolderFav(view: View) : RecyclerView.ViewHolder(view) {
    val imagen = view.findViewById(R.id.iv_foto) as ImageView
    var usuario = view.findViewById(R.id.tv_usuario) as TextView
    var tv_favs  = view.findViewById(R.id.tv_favs) as TextView
    var iUsuario = view.findViewById(R.id.iv_usuario) as de.hdodenhof.circleimageview.CircleImageView
    var bFavorito = view.findViewById(R.id.ib_favorito) as ImageButton
    fun bind(campos: clsInformacion, context: Context, tipo: Int){
        usuario.text = campos.user
        tv_favs.text = campos.likes
        bFavorito.setOnClickListener(View.OnClickListener {
            if (buscarFavorito(context, campos.id)) {
                bFavorito.setImageResource(R.drawable.nofavorito)
                eliminarFavorito(context, campos.id)
                if (tipo == 1) {
                    conexionWeb.dFragmentoF.obtenerDatos(context, "")
                }
            }
        }
        )

        itemView.setOnClickListener(View.OnClickListener {
            conexionWeb.dFragmentoF.informacionUsuario(campos.portafolio, campos.twitterUser, campos.instagramuser, campos.colecciones, campos.bio, campos.favorito, campos.fotos, campos.descripcion, campos.id)
        }
        )

        if(buscarFavorito(context, campos.id)){
            bFavorito.setImageResource(R.drawable.fav)
        }else{
            bFavorito.setImageResource(R.drawable.nofavorito)
        }
        val bmp = BitmapFactory.decodeByteArray(campos.imagen, 0, campos.imagen.size)
        imagen.setImageBitmap(bmp)
        val bmp2 = BitmapFactory.decodeByteArray(campos.perfil, 0, campos.perfil.size)
        iUsuario.setImageBitmap(bmp2)
    }


    fun eliminarFavorito(context: Context, id: String){
        val db = baseDatos(context)
        db.eliminarFavorito(id)
    }

    fun buscarFavorito(context: Context, id: String) : Boolean{
        var existe = false
        val db = baseDatos(context)
        val dInfo: List<clsInformacion> = db.obtenerFavoritoID(id)
        if(dInfo.isNotEmpty()){
            existe = true
        }

        return existe
    }
}