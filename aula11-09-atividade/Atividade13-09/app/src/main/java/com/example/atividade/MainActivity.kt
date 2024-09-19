package com.example.atividade

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atividade.ui.theme.AtividadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutExercicio()
        }
    }
}

@Composable
fun LayoutExercicio() {

    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        NavHost(navController = navController, startDestination = "login"){
            composable("login"){
                Login(navController = navController)
            }
            composable("perfil/{email}"){
                navBackStackEntry ->
                    Perfil(navController = navController,
                        email = navBackStackEntry.arguments?.getString("email"))
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController){

    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val emailCorreto = "calabreso@gmail.com"


    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(text = "ENTRAR NO SISTEMA", fontSize = 20.sp)

        TextField(value = email, onValueChange = {email = it}, label = { Text(text = "Email")})

        Button(onClick = {

            when{
                email.isBlank() -> Toast.makeText(context, "Preencha o campo Email!", Toast.LENGTH_LONG).show()
                email != emailCorreto -> Toast.makeText(context, "EMAIL INVALIDO!", Toast.LENGTH_LONG).show()
                else -> navController.navigate("perfil/$email")
            }

        }) {
            Text(text = "Logar")
        }
    }
}

@Composable
fun Perfil(navController: NavController, email: String?){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "Bem vindo $email", fontSize = 25.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutPreview() {

}