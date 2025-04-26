package com.example.projetoextenso.Forms

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.projetoextenso.DataBase.DBHelper
import com.example.projetoextenso.DataClass.Reclamacao
import com.example.projetoextenso.Shows.dialogMsg

@SuppressLint("Range", "MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaReclamacao(thisContext: NavHostController, navController: NavController) {
    var listaReclamacao by remember { mutableStateOf(mutableListOf<Reclamacao>()) }

    // Função para carregar dados do banco de dados
    fun loadReclamacoes() {
        listaReclamacao.clear()
        try {
            val dataBase = DBHelper(thisContext.context, null)
            val cursor = dataBase.writeRecordsFakeReclamacao(15)
            cursor?.let {
                it.moveToFirst()
                while (it.moveToNext()) {
                    listaReclamacao.add(
                        Reclamacao(
                            id = it.getInt(it.getColumnIndex("ID")),
                            titulo = it.getString(it.getColumnIndex("TITULO")),
                            departamento = it.getString(it.getColumnIndex("DEPARTAMENTO")),
                            horario = it.getString(it.getColumnIndex("DTHRRECLAMACAO")),
                            status = it.getInt(it.getColumnIndex("STATUS"))
                        )
                    )
                }
                it.close()
            }
        } catch (ex: Exception) {
            dialogMsg(thisContext.context, "${ex.message}")
        }
    }

    LaunchedEffect(Unit) { loadReclamacoes() }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.Blue),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left,
                        text = "Lista de Reclamações",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                contentColor = Color.White,
                containerColor = Color.Blue
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    SmallFloatingActionButton(
                        onClick = { navController.navigate("Perfil") },
                        modifier = Modifier.size(50.dp),
                        containerColor = Color.DarkGray,
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AccountCircle,
                            contentDescription = "Perfil",
                            tint = Color.White,
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Espaço entre os ícones

                    SmallFloatingActionButton(
                        onClick = { navController.navigate("ListaReclamacao") },
                        modifier = Modifier.size(50.dp),
                        containerColor = Color.DarkGray,
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Build,
                            contentDescription = "Lista de reclamações",
                            tint = Color.White,
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Espaço entre os ícones

                    SmallFloatingActionButton(
                        onClick = { loadReclamacoes() },
                        modifier = Modifier.size(50.dp),
                        containerColor = Color.DarkGray,
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = "Atualizar",
                            tint = Color.White,
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Espaço entre os ícones

                    SmallFloatingActionButton(
                        onClick = { navController.navigate("Login") },
                        modifier = Modifier.size(50.dp),
                        containerColor = Color.DarkGray,
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ExitToApp,
                            contentDescription = "Sair",
                            tint = Color.White,
                        )
                    }
                }
            }
        },
            floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("SalvarReclamacao?name=" + "-1") },
                shape = RoundedCornerShape(30.dp),
                containerColor = Color.Blue
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Adicionar reclamação",
                    tint = Color.White,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LazyColumn {
                itemsIndexed(listaReclamacao) { position, reclamacao ->
                    ReclamacaoItens(thisContext.context, navController, position, reclamacao)
                }
            }
        }
    }
}

@Composable
fun ReclamacaoItens(context: Context, navController: NavController, position: Int, reclamacao: Reclamacao) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = reclamacao.titulo.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = reclamacao.departamento.toString(), fontSize = 14.sp, color = Color.Gray)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = reclamacao.horario.toString(), fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(color = getStatusColor(reclamacao.status), shape = CircleShape)
                )
            }
        }
    }
}

fun getStatusColor(status: Int?): Color {
    return when (status) {
        1 -> Color.Green
        2 -> Color.Yellow
        3 -> Color.Red
        else -> Color.Gray
    }
}