package com.nespichanl.is8n1sp.navigation

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nespichanl.is8n1sp.ui.about.AboutScreen
import com.nespichanl.is8n1sp.ui.grades.GradesScreen
import com.nespichanl.is8n1sp.ui.home.HomeScreen
import com.nespichanl.is8n1sp.ui.members.MembersScreen
import com.nespichanl.is8n1sp.ui.splash.SplashScreen
import com.nespichanl.is8n1sp.ui.transfer.ProductEntryActivity

@Composable
fun AppNavHost(startDestination: String = "splash") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {

        composable("splash") {
            SplashScreen(onTimeout = { navController.navigate("members") })
        }

        composable("members") {
            MembersScreen(onContinue = { navController.navigate("home") })
        }

        composable("home") {
            val context = LocalContext.current
            HomeScreen(
                onOpenMembers = { navController.navigate("members") },
                onOpenAbout   = { navController.navigate("about") },
                onOpenGrades  = { navController.navigate("grades") },
                onOpenDataPass = {
                    context.startActivity(Intent(context, ProductEntryActivity::class.java))
                },
                onExit        = {
                    (context as? Activity)?.finishAffinity()
                }
            )
        }

        composable("grades") {
            GradesScreen(onContinue = { navController.navigate("home") }) }
        composable("about")  {
            AboutScreen(onContinue = { navController.navigate("home") })
        }
    }
}
