package ru.appkode.clippingdemo.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import ru.appkode.clippingdemo.R

class DemoActivity : AppCompatActivity() {
  private var navDrawerToggle: ActionBarDrawerToggle? = null

  private var fadeInAnimation: Animator? = null
  private var fadeOutAnimation: Animator? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_demo)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)

    val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
    navDrawerToggle = ActionBarDrawerToggle(
      this,
      drawerLayout,
      toolbar,
      R.string.navigation_drawer_open,
      R.string.navigation_drawer_close
    )

    val navigationView = findViewById<NavigationView>(R.id.nav_view)
    setupNavSelectionListener(navigationView, drawerLayout)

    val item = navigationView.menu.findItem(R.id.nav_canvas_star)
    navigationView.post {
      item.isChecked = true
      onMenuItemSelected(item)
    }
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    navDrawerToggle?.syncState()
  }

  private fun setupNavSelectionListener(navigationView: NavigationView, drawerLayout: DrawerLayout) {
    navigationView.setNavigationItemSelectedListener { item ->
      drawerLayout.closeDrawers()
      onMenuItemSelected(item)
      true
    }
  }

  private fun onMenuItemSelected(item: MenuItem) {
    findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar).title = item.title

    val layout: Pair<Int, Int> = when (item.itemId) {
      R.id.nav_canvas_star -> Pair(
        R.layout.demo_clip_path_star,
        R.layout.demo_porter_duff_hardware_star
      )
      R.id.nav_canvas_clippath -> Pair(
              R.layout.demo_clip_path,
              R.layout.demo_clip_path2
      )
      R.id.nav_porter_duff_hardware -> Pair(
              R.layout.demo_porter_duff_hardware,
              R.layout.demo_porter_duff_hardware2
      )
      R.id.nav_porter_duff_software -> Pair(
              R.layout.demo_porter_duff_software,
              R.layout.demo_porter_duff_software2
              )
      R.id.nav_clip_to_outline -> Pair(
              R.layout.demo_clip_to_outline,
              R.layout.demo_clip_to_outline2
      )
      else -> throw IllegalArgumentException("Unknown menu id: ${item.itemId}")
    }

    val contentContainer = findViewById<ViewGroup>(R.id.content_container)
    contentContainer.removeAllViews()

    LayoutInflater.from(this).inflate(layout.first, contentContainer)
    LayoutInflater.from(this).inflate(layout.second, contentContainer)

    findViewById<View>(R.id.page_2).apply {
        alpha = 0f
        visibility = View.GONE
    }

    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation)
    bottomNavigationView.selectedItemId = R.id.menu_page_1
    bottomNavigationView.setOnNavigationItemSelectedListener {
      onBottomNavigationItemSelected(it.itemId)
      true
    }
  }

  private fun onBottomNavigationItemSelected(id: Int) {
    val page1 = findViewById<View>(R.id.page_1)
    val page2 = findViewById<View>(R.id.page_2)

    when (id) {
      R.id.menu_page_1 -> showPage(page1, page2)
      R.id.menu_page_2 -> showPage(page2, page1)
      else -> throw IllegalArgumentException("Unknown menu id: $id")
    }
  }

  private fun showPage(show: View, hide: View) {
    fadeInAnimation?.apply {
      end()
      cancel()
    }

    fadeOutAnimation?.apply {
      end()
      cancel()
    }

    fadeInAnimation = fadeIn(show).apply { start() }
    fadeOutAnimation = fadeOut(hide).apply { start() }
  }

  private fun fadeIn(view: View): Animator {
    view.visibility = View.VISIBLE
    return ObjectAnimator.ofFloat(view,
      "alpha",
      0f,
      1f)
      .apply {
        duration = 200
        interpolator = AccelerateInterpolator()
      }
  }

  private fun fadeOut(view: View): Animator {
    return ObjectAnimator.ofFloat(view,
      "alpha",
      1f,
      0f)
      .apply {
        duration = 200
        interpolator = AccelerateInterpolator()
        addListener(object : Animator.AnimatorListener {
          override fun onAnimationEnd(animation: Animator?) {
            view.visibility = View.GONE
          }
          override fun onAnimationRepeat(animation: Animator?) {}
          override fun onAnimationCancel(animation: Animator?) {}
          override fun onAnimationStart(animation: Animator?) {}
        })
      }
  }
}
