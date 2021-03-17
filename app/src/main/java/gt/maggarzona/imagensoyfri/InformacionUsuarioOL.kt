package gt.maggarzona.imagensoyfri

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import gt.maggarzona.imagensoyfri.baseDatos.baseDatos
import gt.maggarzona.imagensoyfri.clases.clsInformacion

class InformacionUsuarioOL : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_usuario)

        (this as? AppCompatActivity)?.supportActionBar?.title = ""

        val imgPerfil: de.hdodenhof.circleimageview.CircleImageView = findViewById(R.id.iv_usuario)

        val tv_coleccion: TextView = findViewById(R.id.tv_dcolecciones)
        val tv_likes: TextView = findViewById(R.id.tv_dlikes)
        val tv_fotos: TextView = findViewById(R.id.tv_dtotalFotos)

        val iv_twitter: ImageView = findViewById(R.id.iv_twitter)
        val iv_instagram: ImageView = findViewById(R.id.iv_instagram)
        val iv_portafolio: ImageView = findViewById(R.id.iv_portafolio)

        val iv_imagen: ImageView = findViewById(R.id.iv_imagen)

        val tv_twitter: TextView = findViewById(R.id.tv_twitter)
        val tv_instagram: TextView = findViewById(R.id.tv_instagram)
        val tv_bio: TextView = findViewById(R.id.tv_bio)
        val tv_descripcion: TextView = findViewById(R.id.tv_descripcion)

        val intent = intent
        lateinit var iPerfil: ByteArray

        val id = intent.getStringExtra("id")

        val dColeccion = intent.getStringExtra("colecciones")
        val dLikes = intent.getStringExtra("likes")
        val dFotos = intent.getStringExtra("totalFotos")

        val dTwitter = intent.getStringExtra("twitterUser")
        val dInstagram = intent.getStringExtra("instagramuser")
        val dPortafolio = intent.getStringExtra("url")

        val dBio = intent.getStringExtra("bio")
        lateinit var dActual: ByteArray
        val ddescripcion = intent.getStringExtra("descripcion")

        iv_twitter.visibility = View.INVISIBLE
        iv_instagram.visibility = View.INVISIBLE
        iv_portafolio.visibility = View.INVISIBLE

        if(id != null){
            val db = baseDatos(this)
            val listado: List<clsInformacion> =  db.obtenerFavoritoID(id)
            if(listado.isNotEmpty()){
                iPerfil = listado[0].perfil
                dActual = listado[0].imagen
            }
        }

        if (dBio != null) {
            tv_bio.text = dBio
        }
        val bmp2 = BitmapFactory.decodeByteArray(dActual, 0, dActual.size)
        iv_imagen.setImageBitmap(bmp2)
        if (ddescripcion != null) {
            tv_descripcion.text = ddescripcion
        }

        val bmp = BitmapFactory.decodeByteArray(iPerfil, 0, iPerfil.size)
        imgPerfil.setImageBitmap(bmp)

        if (dColeccion != null) {
            tv_coleccion.text = dColeccion
        }
        if (dLikes != null) {
            tv_likes.text = dLikes
        }
        if (dFotos != null) {
            tv_fotos.text = dFotos
        }
        if (dTwitter != null) {
            iv_twitter.visibility = View.VISIBLE
            tv_twitter.text = dTwitter
        }
        if (dInstagram != null) {
            iv_instagram.visibility = View.VISIBLE
            tv_instagram.text = dInstagram
        }
        if (dPortafolio != null) {
            iv_portafolio.visibility = View.VISIBLE
            iv_portafolio.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(dPortafolio)
                startActivity(openURL)
            }
        }
    }
}