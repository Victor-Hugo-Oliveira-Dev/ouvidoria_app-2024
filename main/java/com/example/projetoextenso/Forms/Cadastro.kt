package com.example.projetoextenso.Forms

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projetoextenso.Componente.CaixaDeTexto

@Composable
fun Cadastro(navController: NavController) {
    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Título da tela
        Text(
            text = "Cadastro",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Campos de entrada
        CaixaDeTexto(
            value = nome,
            onValueChange = { nome = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Nome",
            maxLines = 1,
            keyboardType = KeyboardType.Text,
            enabled = true
        )
        CaixaDeTexto(
            value = cpf,
            onValueChange = { cpf = it },
            modifier = Modifier.fillMaxWidth(),
            label = "CPF",
            maxLines = 1,
            keyboardType = KeyboardType.Number,
            enabled = true
        )
        CaixaDeTexto(
            value = endereco,
            onValueChange = { endereco = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Endereço",
            maxLines = 1,
            keyboardType = KeyboardType.Text,
            enabled = true
        )
        CaixaDeTexto(
            value = telefone,
            onValueChange = { telefone = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Telefone",
            maxLines = 1,
            keyboardType = KeyboardType.Phone,
            enabled = true
        )
        CaixaDeTexto(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Email",
            maxLines = 1,
            keyboardType = KeyboardType.Email,
            enabled = true
        )
        CaixaDeTexto(
            value = senha,
            onValueChange = { senha = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Senha",
            maxLines = 1,
            keyboardType = KeyboardType.Password,
            enabled = true
        )
        CaixaDeTexto(
            value = confirmarSenha,
            onValueChange = { confirmarSenha = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Confirmar Senha",
            maxLines = 1,
            keyboardType = KeyboardType.Password,
            enabled = true
        )

        // Exibir mensagem de erro se necessário
        if (showError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Botão de Cadastro
        Button(
            onClick = {
                // Lógica de validação
                if (nome.isBlank() || cpf.isBlank() || endereco.isBlank() ||
                    telefone.isBlank() || email.isBlank() || senha.isBlank() ||
                    confirmarSenha.isBlank()) {
                    errorMessage = "Todos os campos devem ser preenchidos."
                    showError = true
                } else if (senha != confirmarSenha) {
                    errorMessage = "As senhas não coincidem."
                    showError = true
                } else {
                    showError = false
                    // Aqui você pode adicionar a lógica para realizar o cadastro
                    // Por exemplo, salvar no banco de dados
                    navController.navigate("Login") // Navegar para Login após cadastro
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        ) {
            Text(text = "Realizar Cadastro")
        }

        // Texto de redirecionamento
        TextButton(onClick = { navController.navigate("Login") }) {
            Text(text = "Já sou cadastrado")
        }
    }
}
