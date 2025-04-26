package com.example.projetoextenso.Shows

import android.app.AlertDialog
import android.content.Context
import androidx.navigation.NavHostController

// Mensagem de alerta
fun dialogMsg(context: Context, msg: String) {
    // Constroi a mensagem
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder
        .setMessage(msg)
        .setTitle("Informação")
        .setNegativeButton("OK") { dialog, which -> dialog.dismiss() }
    // Constroi o alerta
    val dialog: AlertDialog = builder.create()
    // Exibe a caixa de alerta
    dialog.show()
}