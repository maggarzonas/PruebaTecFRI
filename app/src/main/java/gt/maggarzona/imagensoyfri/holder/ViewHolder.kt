package gt.maggarzona.imagensoyfri.holder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import gt.maggarzona.imagensoyfri.R
import gt.maggarzona.imagensoyfri.baseDatos.baseDatos
import gt.maggarzona.imagensoyfri.clases.clsInformacion
import gt.maggarzona.imagensoyfri.conexiones.conexionWeb
import quicktype.CamposFoto
import java.io.ByteArrayOutputStream


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imagen = view.findViewById(R.id.iv_foto) as ImageView
    var usuario = view.findViewById(R.id.tv_usuario) as TextView
    var tv_favs  = view.findViewById(R.id.tv_favs) as TextView
    var iUsuario = view.findViewById(R.id.iv_usuario) as de.hdodenhof.circleimageview.CircleImageView
    var bFavorito = view.findViewById(R.id.ib_favorito) as ImageButton
    fun bind(campos: CamposFoto, context: Context, tipo: Int){
        usuario.text = campos.user.username
        tv_favs.text = campos.likes.toString()
        itemView.setOnClickListener(View.OnClickListener {
            informacion(campos, tipo)
        }
        )
        bFavorito.setOnClickListener(View.OnClickListener {
            if (buscarFavorito(context, campos.id)) {
                bFavorito.setImageResource(R.drawable.nofavorito)
                eliminarFavorito(context, campos.id)
                if (tipo == 1) {
                    conexionWeb.dFragmentoF.obtenerDatos(context, "")
                }
            } else {
                bFavorito.setImageResource(R.drawable.fav)
                agregarFavorito(context, campos)
            }
        }
        )

        if(buscarFavorito(context, campos.id)){
            bFavorito.setImageResource(R.drawable.fav)
        }else{
            bFavorito.setImageResource(R.drawable.nofavorito)
        }
        Picasso.get()
            .load(campos.urls.full)
            .placeholder(R.drawable.cargando)
            .error(R.drawable.cargando)
            .fit()
            .into(imagen)
        if(tipo == 0){
            Picasso.get()
                .load(campos.user.profile_image.medium)
                .placeholder(R.drawable.cargando)
                .error(R.drawable.cargando)
                .fit()
                .into(iUsuario)
        }
    }

    fun agregarFavorito(context: Context, campos: CamposFoto){
        val db = baseDatos(context)

        try {

            val bitmap = (iUsuario.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()

            val bitmap2 = (imagen.drawable as BitmapDrawable).bitmap
            val stream2 = ByteArrayOutputStream()
            bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream2)
            val image2 = stream2.toByteArray()

            val twitterUsuario: String? = campos.user.twitter_username
            val instagramUsuario: String? = campos.user.instagram_username
            val bio: String? = campos.user.bio
            val cInformacion = clsInformacion(campos.id, image, campos.user.username, campos.user.name, image2, campos.links.html, twitterUsuario, instagramUsuario, campos.user.total_collections.toString(), bio, campos.likes.toString(), campos.user.total_photos.toString(), campos.alt_description, campos.user.total_likes.toString())
            db.agregarFavorito(cInformacion)

        } catch (e: OutOfMemoryError) {
            Toast.makeText(context, "$e POr favor intente de nuevo", Toast.LENGTH_LONG).show()

        }
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

    fun informacion(campos: CamposFoto, tipo: Int){
        val imagenPerfil: String?
        val urlPortafolio: String?
        val colecciones: String?
        val likes: String?
        val fotos: String?
        val urlActual: String?
        val descripcion: String?

        imagenPerfil = campos.user.profile_image.medium
        urlPortafolio = campos.links.html
        val twitterUsuario: String? = campos.user.twitter_username
        val instagramUsuario: String? = campos.user.instagram_username
        colecciones = campos.user.total_collections.toString()
        val bio: String? = campos.user.bio
        likes = campos.user.total_likes.toString()
        fotos = campos.user.total_photos.toString()
        urlActual = campos.urls.full
        descripcion = campos.alt_description

        if (tipo == 0) {
            conexionWeb.dFragmentoH.informacionUsuario(imagenPerfil, urlPortafolio, twitterUsuario, instagramUsuario, colecciones, bio, likes, fotos, urlActual, descripcion)
        }
    }
}