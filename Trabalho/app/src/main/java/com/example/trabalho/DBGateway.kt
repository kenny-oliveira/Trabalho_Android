import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.trabalho.AgendaDBHelper

class DBGateway private constructor(ctx: Context) {
    val database: SQLiteDatabase
    val database2: SQLiteDatabase
    companion object {
        private var gw: DBGateway? = null
        fun getInstance(ctx: Context): DBGateway? {
            if (gw == null) {
                gw = DBGateway(ctx)
            }
            return gw
        }
    }
    init {
        val helper = AgendaDBHelper(ctx)
        database = helper.writableDatabase
        database2 = helper.readableDatabase
    }
}