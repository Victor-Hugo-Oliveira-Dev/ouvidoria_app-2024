package com.example.projetoextenso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetoextenso.Forms.Cadastro
import com.example.projetoextenso.Forms.ListaReclamacao
import com.example.projetoextenso.Forms.Login
import com.example.projetoextenso.Forms.SalvarReclamacao


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppReclamacao() // Chama o composable que organiza a navegação
        }
    }

    @Composable
    fun AppReclamacao() {
        val navController = rememberNavController() as NavHostController

        NavHost(navController = navController, startDestination = "Login") {
            composable(
                route = "Login"
            ) {
                Login(navController)
            }
            composable(
                route = "Cadastro"
            ) {
                Cadastro(navController)
            }
            composable(
                route = "ListaReclamacao"
            ) {
                ListaReclamacao(navController, navController)
            }
            composable(
                route = "SalvarReclamacao"
            ) {
                SalvarReclamacao(navController)
            }
        }
    }
}