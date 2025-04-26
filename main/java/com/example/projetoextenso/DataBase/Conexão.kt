package com.example.projetoextenso.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

class DBHelper(context: Context?, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // Criação da tabela Perfil
    private fun onCreatePerfil(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME_PERFIL (
                $ID_PERFIL VARCHAR(1), 
                $NOME_PERFIL VARCHAR(50), 
                $USUARIO_PERFIL VARCHAR(50), 
                $SENHA_PERFIL VARCHAR(50), 
                $HOST_PERFIL VARCHAR(50), 
                $PORTA_PERFIL VARCHAR(5),
                PRIMARY KEY ($ID_PERFIL)
            )
        """
        db.execSQL(query)
    }

    // Criação da tabela Reclamacao
    private fun onCreateReclamacao(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME_RECLAMACAO (
                $ID_RECLAMACAO INTEGER PRIMARY KEY AUTOINCREMENT,
                $DESCRICAO_RECLAMACAO TEXT,
                $STATUS_RECLAMACAO INTEGER,
                $DTHR_RECLAMACAO VARCHAR(20)
            )
        """
        db.execSQL(query)
    }

    override fun onCreate(db: SQLiteDatabase) {
        onCreatePerfil(db)
        onCreateReclamacao(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Atualização de estrutura pode ser feita aqui
    }

    fun onUpgradePerfil(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_PERFIL")
        onCreatePerfil(db)
    }

    fun onUpgradeReclamacao(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_RECLAMACAO")
        onCreateReclamacao(db)
    }

    fun readRecordsPerfil(id: String): Cursor? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_PERFIL WHERE $ID_PERFIL = ?", arrayOf(id))
        return cursor
    }

    fun readRecordsTarefa(id: String): Cursor? {
        val whereClause = if (id.isNotBlank()) " WHERE $ID_RECLAMACAO = ?" else ""
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME_RECLAMACAO$whereClause", if (id.isNotBlank()) arrayOf(id) else null)
    }

    fun onInsertOrUpdatePerfil(id: String, nome: String, usuario: String, senha: String, host: String, porta: String) {
        val values = ContentValues().apply {
            put(ID_PERFIL, id)
            put(NOME_PERFIL, nome)
            put(USUARIO_PERFIL, usuario)
            put(SENHA_PERFIL, senha)
            put(HOST_PERFIL, host)
            put(PORTA_PERFIL, porta)
        }

        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT $ID_PERFIL FROM $TABLE_NAME_PERFIL WHERE $ID_PERFIL = ?", arrayOf(id))
        if (cursor.count == 0) {
            db.insert(TABLE_NAME_PERFIL, null, values)
        } else {
            db.update(TABLE_NAME_PERFIL, values, "$ID_PERFIL = ?", arrayOf(id))
        }
        cursor.close()
        db.close()
    }

    fun onInsertOrUpdateReclamacao(id: Int, descricao: String, status: Int, dtHrReclamacao: String) {
        val values = ContentValues().apply {
            put(DESCRICAO_RECLAMACAO, descricao)
            put(STATUS_RECLAMACAO, status)
            put(DTHR_RECLAMACAO, dtHrReclamacao)
        }

        val db = this.writableDatabase
        if (id == -1) {
            db.insert(TABLE_NAME_RECLAMACAO, null, values)
        } else {
            values.put(ID_RECLAMACAO, id)
            db.update(TABLE_NAME_RECLAMACAO, values, "$ID_RECLAMACAO = ?", arrayOf(id.toString()))
        }
        db.close()
    }

    fun onDeletePerfil(db: SQLiteDatabase, id: String) {
        val whereClause = if (id.isNotBlank()) "$ID_PERFIL = ?" else null
        db.delete(TABLE_NAME_PERFIL, whereClause, if (id.isNotBlank()) arrayOf(id.trim()) else null)
        db.close()
    }

    fun onDeleteReclamacao(db: SQLiteDatabase, id: String) {
        val whereClause = if (id.isNotBlank()) "$ID_RECLAMACAO = ?" else null
        db.delete(TABLE_NAME_RECLAMACAO, whereClause, if (id.isNotBlank()) arrayOf(id.trim()) else null)
        db.close()
    }

    fun writeRecordsFakeReclamacao(zIndex: Int): Cursor? {
        var wDb = this.writableDatabase
        onUpgradeReclamacao(wDb)

        repeat(zIndex) { index ->
            onInsertOrUpdateReclamacao(-1, "DESCRIÇÃO DA RECLAMAÇÃO --> $index", Random.nextInt(3), getCurrentDate())
        }

        wDb.close()
        val rDb = this.readableDatabase
        return rDb.rawQuery("SELECT * FROM $TABLE_NAME_RECLAMACAO", null)
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        return sdf.format(Date())
    }

    companion object {
        private const val DATABASE_NAME = "Aula_000.DB"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME_PERFIL = "PERFIL"
        const val ID_PERFIL = "ID"
        const val NOME_PERFIL = "NOME"
        const val USUARIO_PERFIL = "USUARIO"
        const val SENHA_PERFIL = "SENHA"
        const val HOST_PERFIL = "HOST"
        const val PORTA_PERFIL = "PORTA"

        const val TABLE_NAME_RECLAMACAO = "RECLAMACAO"
        const val ID_RECLAMACAO = "ID"
        const val DESCRICAO_RECLAMACAO = "DESCRICAO"
        const val STATUS_RECLAMACAO = "STATUS"
        const val DTHR_RECLAMACAO = "DTHRRECLAMACAO"
    }
}