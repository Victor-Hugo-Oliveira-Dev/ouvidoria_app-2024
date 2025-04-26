package com.example.projetoextenso.DataClass

import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projetoextenso.DataBase.DBHelper
import com.example.projetoextenso.Shows.dialogMsg
import com.example.projetoextenso.ui.theme.ShapesCard

// Dados da reclamação
data class Reclamacao(
    val id: Int? = 0,
    val titulo: String? = null,
    val departamento: String? = null,
    val horario: String? = "",
    val status: Int? = 0 // 0 = aberto, 1 = em andamento, 2 = resolvido
)

// Função para excluir uma reclamação pelo ID
fun deleteReclamacao(thisContext: Context, navController: NavController, reclamacao: Reclamacao) {
    if (reclamacao.id != null) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(thisContext)
        builder
            .setMessage("Por favor, confirme a exclusão da reclamação.")
            .setTitle("Confirme")
            .setPositiveButton("Sim") { _, _ ->
                val dataBase = DBHelper(thisContext.applicationContext, null)
                val wDb = dataBase.writableDatabase
                dataBase.onDeleteReclamacao(wDb, "${reclamacao.id}")
                wDb.close()
                dataBase.close()
                dialogMsg(thisContext, "Reclamação excluída com sucesso.")
                navController.navigate("showReclamacao")
            }
            .setNegativeButton("Não", null)
        builder.create().show()
    }
}

// Exibição do item de lista de reclamação
@Composable
fun ReclamacaoItens(
    thisContext: Context, navController: NavController,
    position: Int,
    listaReclamacoes: MutableList<Reclamacao>
) {
    val reclamacao = listaReclamacoes[position]

    val corStatus = when (reclamacao.status) {
        0 -> Color.Red // aberto
        1 -> Color.Yellow // em andamento
        else -> Color.Green // resolvido
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC7E5F6))
    ) {
        Column(Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reclamação: ${reclamacao.titulo}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Departamento: ${reclamacao.departamento}",
                    fontSize = 18.sp,
                    maxLines = 1
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reclamacao.horario ?: "",
                    fontSize = 16.sp
                )
                Canvas(modifier = Modifier.size(20.dp).padding(start = 8.dp)) {
                    drawCircle(color = corStatus, radius = 10.dp.toPx())
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                SmallFloatingActionButton(
                    onClick = {
                        navController.navigate("formReclamacao?id=${reclamacao.id}")
                    },
                    containerColor = Color.Unspecified,
                    shape = ShapesCard.small,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                SmallFloatingActionButton(
                    onClick = {
                        deleteReclamacao(thisContext, navController, reclamacao)
                    },
                    containerColor = Color.DarkGray,
                    shape = ShapesCard.large
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}