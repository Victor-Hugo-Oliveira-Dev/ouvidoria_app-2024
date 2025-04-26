package com.example.projetoextenso.Forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projetoextenso.Componente.CaixaDeTexto
import com.example.projetoextenso.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.platform.LocalContext
import com.example.projetoextenso.DataBase.DBHelper
import com.example.projetoextenso.DataClass.Reclamacao
import com.example.projetoextenso.Shows.dialogMsg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalvarReclamacao(navController: NavController) {
    val radioOptions = listOf("Infraestrutura", "Cultura", "Artes", "Saúde")
    var selectedOption by remember { mutableStateOf(radioOptions[0]) }
    var descricao by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val listaReclamacao = remember { mutableStateListOf<Reclamacao>() }
    val thisContext = LocalContext.current

    fun loadReclamacoes() {
        listaReclamacao.clear()
        try {
            val dataBase = DBHelper(thisContext, null)
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
            dialogMsg(thisContext, "${ex.message}")
        }
    }

    LaunchedEffect(Unit) { loadReclamacoes() }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(Color.Blue),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left,
                        text = "Formulário [ Reclamação ]",
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize(),
        ) {
            CaixaDeTexto(
                value = titulo,
                onValueChange = { titulo = it },
                modifier = Modifier
                    .heightIn(50.dp)
                    .fillMaxWidth()
                    .padding(10.dp),
                label = "Título da Reclamação",
                maxLines = 1,
                keyboardType = KeyboardType.Text,
                enabled = true
            )

            if (showError && titulo.isEmpty()) {
                Text(
                    text = "O título é obrigatório.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CaixaDeTexto(
                value = descricao,
                onValueChange = { descricao = it },
                modifier = Modifier
                    .heightIn(200.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
                label = "Descrição",
                maxLines = 6,
                keyboardType = KeyboardType.Text,
                enabled = true
            )

            if (showError && descricao.isEmpty()) {
                Text(
                    text = "A descrição é obrigatória.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(10.dp),
                text = "Selecionar Departamento",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Column {
                radioOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { selectedOption = text }
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null
                        )
                        Text(text = text)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* TODO: handle anexo */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_attach_file_24),
                        contentDescription = "Anexar Arquivo",
                        tint = Color.Black
                    )
                }
                Button(
                    onClick = {
                        showError = titulo.isEmpty() || descricao.isEmpty()
                        if (!showError) {
                            // Implementar a lógica de salvar a reclamação
                            navController.navigate("rotaDestino")
                        }
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                ) {
                    Text("Salvar")
                }
                Button(
                    onClick = {
                        titulo = ""
                        descricao = ""
                        selectedOption = radioOptions[0]
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                ) {
                    Text("Limpar")
                }
            }
        }
    }
}
