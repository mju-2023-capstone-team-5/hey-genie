package org.sopar.presentation.main

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kakao.sdk.user.UserApiClient
import org.sopar.R
import org.sopar.databinding.ActivityMainBinding
import org.sopar.presentation.entry.EntryActivity
import org.sopar.presentation.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController
    private lateinit var appBar: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        setJetpackNavigation()
        init()

    }

    private fun setJetpackNavigation() {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        appBar = AppBarConfiguration(
            setOf(R.id.fragment_map, R.id.fragment_my_page, R.id.fragment_setting),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBar)

        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBar) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun init() {
        binding.navView.menu.findItem(R.id.btn_home).setOnMenuItemClickListener {
            val intent = Intent(this, EntryActivity::class.java)
            startActivity(intent)
            true
        }

        binding.navView.menu.findItem(R.id.btn_logout).setOnMenuItemClickListener {
            UserApiClient.instance.logout { error ->
              if (error != null) {
                  Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
              } else {
                  Toast.makeText(this, "성공적으로 로그아웃되었습니다!🙂", Toast.LENGTH_SHORT).show()
                  val intent = Intent(this, LoginActivity::class.java)
                  //로그아웃 시 스택 제거
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                  startActivity(intent)
              }
            }
            true
        }
    }

}