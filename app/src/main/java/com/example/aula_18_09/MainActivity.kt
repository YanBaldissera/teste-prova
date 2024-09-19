package com.example.aula_18_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aula_18_09.ui.theme.Aula1809Theme
import com.google.gson.Gson


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LayoutPreview()
        }
    }
}

@Composable
fun LayoutMain(){

    val navController = rememberNavController()

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        NavHost(navController = navController,
            startDestination = "Tela1"){

            composable("Tela1"){
                Tela1(navController)
            }
            composable("DetalhesContatos/{contatoJson}"){

                backStackEntry ->
                //receber paramtreo passado via navegaçaõ
                val contatoJSON = backStackEntry.arguments?.getString("contatoJson")
                //desserializa o Json novamente num objeto do tipo contato.
                val contato = Gson().fromJson(contatoJSON, Contato::class.java)

                //passando parametro para o DatalhesContatos
                DetalhesContatos(navController, contato)
            }

        }
    }
}

@Composable
fun Tela1(navController: NavController){
    //lista de contatos imutável
    val listaContatos = listOf(
        Contato("Eliza", "99999999", "eliza@gmail.com", "Brigadeiro franco"),
        Contato("Yan", "8888888", "yan@gmail.com", "Brigadeiro franco"),
        Contato("Juninho", "7777777777777", "juninho@gmail.com", "Brigadeiro franco"),
        Contato("Toddy", "666666666", "toddy@gmail.com", "Brigadeiro franco")
    )

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(text = "Contatos Cadastrados",
            fontSize = 25.sp,
            modifier = Modifier.padding(10.dp))

        LazyColumn {
            items(listaContatos){

                contato ->
                    Text(text = "${ contato.nome } (${contato.fone})",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {

                                // serializar o objeto contato num JSON
                                val contatoJSON = Gson().toJson(contato)

                                //ao clicar, efetuamos a navegaçao, enviando o json como parâmetro
                                navController.navigate("DetalhesContatos/${contatoJSON}")

                            })
            }
        }
    }
}

@Composable
fun DetalhesContatos(navController: NavController, contato: Contato){

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(text = "Detalhes do Contato")

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "Nome: ${contato.nome}")

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "Telefone: ${contato.fone}")

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "E-mail: ${contato.email}")

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "Endereço: ${contato.endereco}")
        
        
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Voltar")
        }

    }


}

@Preview(showBackground = true)
@Composable
fun LayoutPreview(){
    LayoutMain()
}