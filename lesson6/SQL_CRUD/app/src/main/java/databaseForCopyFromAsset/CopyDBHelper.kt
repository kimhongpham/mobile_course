package databaseForCopyFromAsset

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import com.example.sql_crud.CmmVariable

class CopyDBHelper (private val context: Context) {

    // Sử dụng tên và version từ CmmVariable
    companion object {
        private const val DATABASE_NAME = CmmVariable.DB_NAME
        private const val DATABASE_VERSION = CmmVariable.DB_VERSION
    }

    fun openDatabase(): SQLiteDatabase {
        // Lấy đường dẫn DB mặc định
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        val file = File(dbFile.toString())

        // Kiểm tra xem file đã tồn tại trên thiết bị chưa
        if (file.exists()) {
            Log.i("CopyDBHelper", "File đã tồn tại. Không cần copy.")
        } else {
            // Nếu chưa tồn tại, thực hiện copy
            copyDatabase(dbFile)
        }

        // Mở database để đọc
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyDatabase(dbFile: File?) {
        try {
            // Mở file DB trong thư mục assets
            val openDB = context.assets.open(DATABASE_NAME)
            // Tạo output stream đến thư mục database của app
            val outputStream = FileOutputStream(dbFile)

            val buffer = ByteArray(1024)
            var length: Int
            while (openDB.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
                Log.d("CopyDBHelper", "Đang ghi...")
            }

            outputStream.flush()
            outputStream.close()
            openDB.close()
            Log.i("CopyDBHelper", "Đã copy database thành công.")
        } catch (e: Exception) {
            Log.e("CopyDBHelper", "Lỗi khi copy database: ${e.message}")
            throw RuntimeException("Error copying database", e)
        }
    }
}