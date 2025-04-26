package com.example.projetoextenso.Forms

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Usuario(
    val nome: String,
    val cpf: String,
    val endereco: String,
    val telefone: String,
    val senha: String,
    val email: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Perfil(navController: NavController, usuario: Usuario) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Perfil") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)
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

                    Spacer(modifier = Modifier.width(16.dp))

                    SmallFloatingActionButton(
                        onClick = { /* Função para atualizar lista */ },
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

                    Spacer(modifier = Modifier.width(16.dp))

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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)  // Inclui o innerPadding do Scaffold
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Perfil", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(24.dp))

            InfoRow(label = "Nome", value = usuario.nome) {
                navController.navigate("editarNome?nome=${usuario.nome}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(label = "CPF", value = usuario.cpf) {
                navController.navigate("editarCpf?cpf=${usuario.cpf}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(label = "Endereço", value = usuario.endereco) {
                navController.navigate("editarEndereco?endereco=${usuario.endereco}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(label = "Telefone", value = usuario.telefone) {
                navController.navigate("editarTelefone?telefone=${usuario.telefone}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(label = "Email", value = usuario.email) {
                navController.navigate("editarEmail?email=${usuario.email}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(label = "Senha", value = "******") {
                navController.navigate("editarSenha")
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, onEditClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$label: $value", modifier = Modifier.weight(1f))
        Button(onClick = onEditClick) {
            Text(text = "Editar")
        }
    }
}
