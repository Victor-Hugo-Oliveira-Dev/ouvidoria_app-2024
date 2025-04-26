package com.example.projetoextenso.Forms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetoextenso.Componente.CaixaDeTexto
import com.example.projetoextenso.R
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(24.dp))

        CaixaDeTexto(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Email",
            maxLines = 1,
            keyboardType = KeyboardType.Email,
            enabled = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de senha com ícone para alternar visibilidade
        CaixaDeTexto(
            value = senha,
            onValueChange = { senha = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Senha",
            maxLines = 1,
            keyboardType = KeyboardType.Password,
            enabled = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon: Painter = if (passwordVisibility) {
                    painterResource(id = R.drawable.baseline_visibility_24)
                } else {
                    painterResource(id = R.drawable.baseline_visibility_off_24)
                }
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(painter = icon, contentDescription = "Alternar Visibilidade da Senha")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isEmpty() || senha.isEmpty()) {
                    showSnackbar = true
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Preencha todos os campos!")
                    }
                } else {
                    navController.navigate("ListaReclamacao")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Realizar cadastro",
            modifier = Modifier.clickable {
                navController.navigate("Cadastro")
            }
        )
    }

    // SnackbarHost
    SnackbarHost(hostState = snackbarHostState) { snackbarData ->
        Snackbar(snackbarData)
    }
}
