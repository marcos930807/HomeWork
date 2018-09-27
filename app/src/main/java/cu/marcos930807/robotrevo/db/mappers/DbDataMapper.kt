package cu.marcos930807.robotrevo.db.mappers

import android.content.ContentValues
import android.database.Cursor
import cu.marcos930807.robotrevo.db.model.Client

import cu.marcos930807.robotrevo.db.model.Notice
import cu.marcos930807.robotrevo.db.sql.DBContract

fun convertFromDB(cursor:Cursor?) : ArrayList<Notice>{
    val notices = ArrayList<Notice>()
    var id: Long
    var category: String
    var title: String
    var body: String
    var price: String
    var mail:String
    var ownername: String
    var number: String
    var photoa: String
    var photob: String
    var photoc: String
    var clientid: Long?

    if (cursor!!.moveToFirst()) {
        while (cursor.isAfterLast == false) {
            id = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_ID)).toLong()
            category = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_CATEGORY))
            title = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_TITLE))
            body = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_BODY))
            price = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_PRICE))
            mail = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_MAIL))
            ownername = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_OWNERNAME))
            number = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_NUMBER))
            photoa = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_PHOTOA))
            photob = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_PHOTOB))
            photoc = cursor.getString(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_PHOTOC))
           clientid = cursor.getLong(cursor.getColumnIndex(DBContract.NoticeEntry.COLUMN_CLIENTID))
            notices.add(Notice(id,category, title,body,price,mail,ownername,number,photoa,photob,photoc,clientid))
            cursor.moveToNext()
        }
    }
    return notices
}

fun convertClientFromDB(cursor:Cursor?) : ArrayList<Client>{
    val clients = ArrayList<Client>()
    var id: Long
    var name: String
    var mail: String
    var phone: String

    if (cursor!!.moveToFirst()) {
        while (cursor.isAfterLast == false) {
            id = cursor.getString(cursor.getColumnIndex(DBContract.ClientEntry.COLUMN_ID)).toLong()

            mail = cursor.getString(cursor.getColumnIndex(DBContract.ClientEntry.COLUMN_MAIL))
            name = cursor.getString(cursor.getColumnIndex(DBContract.ClientEntry.COLUMN_NAME))
            phone = cursor.getString(cursor.getColumnIndex(DBContract.ClientEntry.COLUMN_PHONE))

            clients.add(Client(id,mail,name,phone))
            cursor.moveToNext()
        }
    }
    return clients
}

fun createValuesfromNotice(notice:Notice): ContentValues{

    val values = ContentValues()
    values.put(DBContract.NoticeEntry.COLUMN_CATEGORY, notice.category)
    values.put(DBContract.NoticeEntry.COLUMN_TITLE, notice.title)
    values.put(DBContract.NoticeEntry.COLUMN_BODY, notice.body)
    values.put(DBContract.NoticeEntry.COLUMN_PRICE, notice.price)
    values.put(DBContract.NoticeEntry.COLUMN_MAIL, notice.mail)
    values.put(DBContract.NoticeEntry.COLUMN_OWNERNAME, notice.ownername)
    values.put(DBContract.NoticeEntry.COLUMN_NUMBER, notice.number)
    values.put(DBContract.NoticeEntry.COLUMN_PHOTOA, notice.photoa)
    values.put(DBContract.NoticeEntry.COLUMN_PHOTOB, notice.photob)
    values.put(DBContract.NoticeEntry.COLUMN_PHOTOC, notice.photoc)
    values.put(DBContract.NoticeEntry.COLUMN_CLIENTID, notice.clientid)

    return values
}
fun createValuesfromClient(client: Client): ContentValues{

    val values = ContentValues()
    values.put(DBContract.ClientEntry.COLUMN_MAIL, client.mail)
    values.put(DBContract.ClientEntry.COLUMN_NAME, client.name)
    values.put(DBContract.ClientEntry.COLUMN_PHONE, client.phone)
    return values
}

fun getCategoryInfofromDropdown(id: Int): ArrayList<String>{
    val result = ArrayList<String>()
    when(id) {
    0->{ result.add("Celulares/Líneas/Accesorios")
        result.add("31")}
    1->{ result.add("Reproductor MP3/MP4/IPOD")
        result.add("32")}
    2->{ result.add("Reproductor DVD/VCD/DVR")
        result.add("33")}
    3->{ result.add("Televisor")
        result.add("214")}
    4->{ result.add("Cámara Foto/Video")
        result.add("34")}
    5->{ result.add("Aire Acondicionado")
        result.add("213")}
    6->{ result.add("Consola Videojuego/Juegos")
        result.add("39")}
    7->{ result.add("Satélite")
        result.add("43") }
    8->{ result.add("Electrodomésticos")
        result.add("35")}
    9->{ result.add("Muebles/Decoración")
        result.add("36") }
    10->{ result.add("Ropa/Zapato/Accesorios")
        result.add("37") }
    11->{ result.add("Intercambio/Regalo")
        result.add("40") }
    12->{ result.add("Mascotas/Animales")
        result.add("41")}
    13->{ result.add("Divisas")
        result.add("42")}
    14->{ result.add("Libros/Revistas")
        result.add("38") }
    15->{ result.add("Joyas/Relojes")
        result.add("211")}
    16->{ result.add("Antiguedades/Colección")
        result.add("217")}
    17->{ result.add("Implementos Deportivos")
        result.add("219") }
    18->{ result.add("Arte")
        result.add("221")}
    19->{ result.add("Otros")
        result.add("44")}
    20->{ result.add("Carros")
        result.add("121")}
    21->{ result.add("Motos")
        result.add("122")}
    22->{ result.add("Bicicletas")
        result.add("215") }
    23->{ result.add("Piezas/Accesorios")
            result.add("125") }
    24->{ result.add("Alquiler")
        result.add("124")}
    25->{ result.add("Mecánico")
        result.add("123")}
    26->{ result.add("Otros")
        result.add("204")}
    27->{ result.add("Compra/Venta")
        result.add("101")}
    28->{ result.add("Permuta")
        result.add("102")}
    29->{ result.add("Alquiler a cubanos")
        result.add("103") }
    30->{ result.add("Alquiler a extranjeros")
        result.add("104") }
    31->{ result.add("Casa en la playa")
        result.add("105")}
    32->{ result.add("Ofertas de empleo")
        result.add("161") }
    33->{ result.add("Busco empleo")
        result.add("162")}
    34->{ result.add("Clases/Cursos")
        result.add("83")}
    35->{ result.add("Informática/Programación")
        result.add("71")}
    36->{ result.add("Películas/Series/Videos")
        result.add("72") }
    37->{ result.add("Limpieza/Doméstico")
        result.add("73")}
    38->{ result.add("Foto/Video")
        result.add("74")}
    39->{ result.add("Construcción/Mantenimiento")
        result.add("75") }
    40->{ result.add("Reparación Electrónica")
        result.add("76")}
    41->{ result.add("Peluquería/Barbería/Belleza")
        result.add("77") }
    42->{ result.add("Restaurantes/Gastronomía")
        result.add("78")}
    43->{ result.add("Diseño/Decoración")
        result.add("79")}
    44->{ result.add("Música/Animación/Shows")
        result.add("80")}
    45->{ result.add("Relojero/Joyero")
        result.add("81")}
    46->{ result.add("Gimnasio/Masaje/Entrenador")
        result.add("220")}
    47->{ result.add("Otros")
        result.add("82")}
    48->{ result.add("PC de Escritorio")
        result.add("2")}
    49->{ result.add("Laptop")
        result.add("3")}
    50->{ result.add("Microprocesador")
        result.add("5")}
    51->{ result.add("Monitor")
        result.add("4") }
    52->{ result.add("Motherboard")
        result.add("6")}
    53->{ result.add("Memoria RAM/FLASH")
        result.add("7")}
    54->{ result.add("Disco Duro Interno/Externo")
        result.add("8") }
    55->{ result.add("Chasis/Fuente")
        result.add("9")}
    56->{ result.add("Tarjeta de Video")
        result.add("11")}
    57->{ result.add("Tarjeta de Sonido/Bocinas")
        result.add("12") }
    58->{ result.add("Quemador/Lector DVD/CD")
        result.add("13")}
    59->{ result.add("Backup/UPS")
        result.add("14") }
    60->{ result.add("Impresora/Cartuchos")
        result.add("15")}
    61->{ result.add("Modem/Wifi/Red")
        result.add("16") }
    62->{ result.add("Webcam/Microf/Audífono")
        result.add("18") }
    63->{ result.add("Teclado/Mouse")
        result.add("19")}
    64->{ result.add("Internet/Email")
        result.add("216")}
    65->{ result.add("CD/DVD Virgen")
        result.add("218") }
    66->{ result.add("Otros")
        result.add("20")
    }

         }




    return result
}


fun getCategoryNamefromRevoid( id:Int) : String{
    var reslt = ""

    when(id) {
        31 -> {
            reslt = "Celulares/Líneas/Accesorios"
        }
        32 -> {
            reslt = "Reproductor MP3/MP4/IPOD"
        }
        33 -> {
            reslt = "Reproductor DVD/VCD/DVR"
        }
        214 -> {
            reslt = "Televisor"
        }
        34 -> {
            reslt = "Cámara Foto/Video"
        }
        213 -> {
            reslt = "Aire Acondicionado"
        }
        39 -> {
            reslt = "Consola Videojuego/Juegos"
        }
        43 -> {
            reslt = "Satélite"
        }
        35 -> {
            reslt = "Electrodomésticos"
        }
        36 -> {
            reslt = "Muebles/Decoración"
        }
        37 -> {
            reslt = "Ropa/Zapato/Accesorios"
        }
        40 -> {
            reslt = "Intercambio/Regalo"
        }
        41 -> {
            reslt = "Mascotas/Animales"
        }
        42 -> {
            reslt = "Divisas"
        }
        38 -> {
            reslt = "Libros/Revistas"
        }
        211 -> {
            reslt = "Joyas/Relojes"
        }
        217 -> {
            reslt = "Antiguedades/Colección"
        }
        219 -> {
            reslt = "Implementos Deportivos"
        }
        221 -> {
            reslt = "Arte"
        }
        44 -> {
            reslt = "Otros"
        }
        121 -> {
            reslt = "Carros"
        }
        122 -> {
            reslt = "Motos"
        }
        215 -> {
            reslt = "Bicicletas"
        }
        125 -> {
            reslt = "Piezas/Accesorios"
        }
        124 -> {
            reslt = "Alquiler"
        }
        123 -> {
            reslt = "Mecánico"
        }
        204 -> {
            reslt = "Otros"
        }
        101 -> {
            reslt = "Compra/Venta"
        }
        102 -> {
            reslt = "Permuta"
        }
        103 -> {
            reslt = "Alquiler a cubanos"
        }
        104 -> {
            reslt = "Alquiler a extranjeros"
        }
        105 -> {
            reslt = "Casa en la playa"
        }
        161 -> {
            reslt = "Ofertas de empleo"
        }
        162 -> {
            reslt = "Busco empleo"
        }
        83 -> {
            reslt = "Clases/Cursos"
        }
        71 -> {
            reslt = "Informática/Programación"
        }
        72 -> {
            reslt = "Películas/Series/Videos"
        }
        73 -> {
            reslt = "Limpieza/Doméstico"
        }
        74 -> {
            reslt = "Foto/Video"
        }
        75 -> {
            reslt = "Construcción/Mantenimiento"
        }
        76 -> {
            reslt = "Reparación Electrónica"
        }
        77 -> {
            reslt = "Peluquería/Barbería/Belleza"
        }
        78 -> {
            reslt = "Restaurantes/Gastronomía"
        }
        79 -> {
            reslt = "Diseño/Decoración"
        }
        80 -> {
            reslt = "Música/Animación/Shows"
        }
        81 -> {
            reslt = "Relojero/Joyero"
        }
        220 -> {
            reslt = "Gimnasio/Masaje/Entrenador"
        }
        82 -> {
            reslt = "Otros"
        }
        2 -> {
            reslt = "PC de Escritorio"
        }
        3 -> {
            reslt = "Laptop"
        }
        5 -> {
            reslt = "Microprocesador"
        }
        4 -> {
            reslt = "Monitor"
        }
        6 -> {
            reslt = "Motherboard"
        }
        7 -> {
            reslt = "Memoria RAM/FLASH"
        }
        8 -> {
            reslt = "Disco Duro Interno/Externo"
        }
        9 -> {
            reslt = "Chasis/Fuente"
        }
        11 -> {
            reslt = "Tarjeta de Video"
        }
        12 -> {
            reslt = "Tarjeta de Sonido/Bocinas"
        }
        13 -> {
            reslt = "Quemador/Lector DVD/CD"
        }
        14 -> {
            reslt = "Backup/UPS"
        }
        15 -> {
            reslt = "Impresora/Cartuchos"
        }
        16 -> {
            reslt = "Modem/Wifi/Red"
        }
        18 -> {
            reslt = "Webcam/Microf/Audífono"
        }
        19 -> {
            reslt = "Teclado/Mouse"
        }
        216 -> {
            reslt = "Internet/Email"
        }
        218 -> {
            reslt = "CD/DVD Virgen"
        }
        20 -> {
            reslt = "Otros"

        }
    }
        return reslt
    }


