package gt.maggarzona.imagensoyfri.baseDatos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import gt.maggarzona.imagensoyfri.clases.clsInformacion

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "imagenSOYFRI.db"

private val tipoAutoincrementable = " INTEGER PRIMARY KEY AUTOINCREMENT"
private val crearTabla = " CREATE TABLE IF NOT EXISTS "
private val tipoTexto = " TEXT"
private val tipoEntero = " INTEGER"
private val tipoDecimal = " DOUBLE"
private val tipoBooleano = " BOOLEAN"
private val tipoImagen = " BLOB"
private val tipoFecha = " DATETIME"
private val coma = " ,"
private val abreParentesis = "( "
private val cierraParentesis = " )"
private val puntoYComa = ";"

const val tblImagenes = " tblImagenes "
private val tblImagenes_id = " id "
private val tblImagenes_perfil = " perfilI "
private val tblImagenes_username = " username "
private val tblImagenes_user = " user "
private val tblImagenes_imagen = " imagen "
private val tblImagenes_portafolio = " portaf "
private val tblImagenes_usuarioTwitter = " twitterU "
private val tblImagenes_usuarioInstagram = " instagram "
private val tblImagenes_colecciones = " coleccion "
private val tblImagenes_bio = " bio "
private val tblImagenes_likes = " likes "
private val tblImagenes_fotos = " foto "
private val tblImagenes_descripcion = " descFoto "
private val tblImagenes_favoritos = " fav "

class baseDatos(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val tabla_imagenes = crearTabla + tblImagenes + abreParentesis + tblImagenes_id + tipoTexto + coma + tblImagenes_perfil + tipoImagen + coma + tblImagenes_username + tipoTexto + coma +
                tblImagenes_user + tipoTexto + coma + tblImagenes_imagen + tipoImagen + coma + tblImagenes_portafolio + tipoTexto + coma + tblImagenes_usuarioTwitter + tipoTexto + coma +
                tblImagenes_usuarioInstagram + tipoTexto + coma + tblImagenes_colecciones + tipoTexto + coma + tblImagenes_bio + tipoTexto + coma + tblImagenes_likes + tipoTexto + coma +
                tblImagenes_fotos + tipoTexto + coma + tblImagenes_descripcion + tipoTexto + coma + tblImagenes_favoritos + tipoTexto + cierraParentesis + puntoYComa
        db?.execSQL(tabla_imagenes)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //db?.execSQL("Drop table tblImagenes")
        //onCreate(db);
    }

    fun agregarFavorito(informacion: clsInformacion) {
        val values = ContentValues()
        values.put("id", informacion.id)
        values.put("perfilI", informacion.perfil)
        values.put("username", informacion.username)
        values.put("user", informacion.user)
        values.put("imagen", informacion.imagen)
        values.put("portaf", informacion.portafolio)
        values.put("twitterU", informacion.twitterUser)
        values.put("instagram", informacion.instagramuser)
        values.put("coleccion", informacion.colecciones)
        values.put("bio", informacion.bio)
        values.put("likes", informacion.likes)
        values.put("foto", informacion.fotos)
        values.put("descFoto", informacion.descripcion)
        values.put("fav", informacion.favorito)

        val db = this.writableDatabase
        val result = db.insert(tblImagenes, null, values)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Error al agregar favorito", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Favorito agregado satisfactoriamente", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun obtenerFavorito(): List<clsInformacion> {
        var listado: List<clsInformacion> = emptyList()
        val db = this.readableDatabase
        val info: Cursor = db.rawQuery(
            "SELECT * FROM $tblImagenes",
            null
        )

        if (info.moveToFirst()) {
            do {
                val dInfo = clsInformacion(info.getString(0), info.getBlob(1), info.getString(2), info.getString(3), info.getBlob(4), info.getString(5), info.getString(6), info.getString(7), info.getString(8), info.getString(9), info.getString(10), info.getString(11), info.getString(12), info.getString(13))
                listado = listado + dInfo
            }
            while (info.moveToNext())
        }
        db.close()
        return listado
    }

    fun obtenerFavoritoUser(user: String): List<clsInformacion> {
        var listado: List<clsInformacion> = emptyList()
        val db = this.readableDatabase
        val info: Cursor = db.rawQuery(
            "SELECT * FROM $tblImagenes where user like '%$user%' or username like '%$user%'",
            null
        )

        if (info.moveToFirst()) {
            do {
                val dInfo = clsInformacion(info.getString(0), info.getBlob(1), info.getString(2), info.getString(3), info.getBlob(4), info.getString(5), info.getString(6), info.getString(7), info.getString(8), info.getString(9), info.getString(10), info.getString(11), info.getString(12), info.getString(13))
                listado = listado + dInfo
            }
            while (info.moveToNext())
        }
        db.close()
        return listado
    }

    fun obtenerFavoritoID(id: String): List<clsInformacion> {
        var listado: List<clsInformacion> = emptyList()
        val db = this.readableDatabase
        val info: Cursor = db.rawQuery(
            "SELECT * FROM $tblImagenes where id = '$id'",
            null
        )

        if (info.moveToFirst()) {
            do {
                val dInfo = clsInformacion(info.getString(0), info.getBlob(1), info.getString(2), info.getString(3), info.getBlob(4), info.getString(5), info.getString(6), info.getString(7), info.getString(8), info.getString(9), info.getString(10), info.getString(11), info.getString(12), info.getString(13))
                listado = listado + dInfo
            }
            while (info.moveToNext())
        }
        db.close()
        return listado
    }

    fun eliminarFavorito(id: String){
        val db = this.writableDatabase
        db.execSQL("delete from tblImagenes where id = '$id'")

        db.close()
    }
}