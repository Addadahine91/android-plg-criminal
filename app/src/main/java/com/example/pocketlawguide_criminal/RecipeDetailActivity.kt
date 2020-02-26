package com.example.pocketlawguide_criminal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.navigation.NavigationView
import com.yuyakaido.android.cardstackview.*
import java.util.*
import android.text.Spanned
import android.text.SpannableString
import android.text.style.TypefaceSpan
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.android.synthetic.main.activity_recipe_detail.*

class RecipeDetailActivity : AppCompatActivity(), CardStackListener {

  private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
  private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
  private val manager by lazy { CardStackLayoutManager(this, this) }
  private val adapter by lazy { CardStackAdapter(createSpots()) }

  companion object {
      private const val EXTRA_TITLE = "name"
      private const val EXTRA_FACTS = "facts"
      private const val EXTRA_CATEGORY = "category"
      private const val EXTRA_JUDGMENT = "judgment"
      private const val EXTRA_ATTRIBUTE = "attribute"

    fun newIntent(context: Context, recipe: Case): Intent {
      val detailIntent = Intent(context, RecipeDetailActivity::class.java)

        detailIntent.putExtra(EXTRA_TITLE, recipe.name)
        detailIntent.putExtra(EXTRA_FACTS, recipe.facts)
        detailIntent.putExtra(EXTRA_CATEGORY, recipe.category)
        detailIntent.putExtra(EXTRA_JUDGMENT, recipe.judgment)
        detailIntent.putExtra(EXTRA_ATTRIBUTE, recipe.attribute)

      return detailIntent
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recipe_detail)

//    val title = intent.extras?.getString(EXTRA_TITLE)
//    setTitle(title)


    setContentView(R.layout.activity_recipe_detail)
    setupCardStackView()

    case_title.text = intent.extras?.getString(EXTRA_TITLE)
    case_attribute.text = intent.extras?.getString(EXTRA_ATTRIBUTE)



  }

  override fun onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawers()
    } else {
      super.onBackPressed()
    }
  }

  override fun onCardDragging(direction: Direction, ratio: Float) {
    //Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
  }

  override fun onCardSwiped(direction: Direction) {
    Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
    if (manager.topPosition == adapter.itemCount - 5) {
      paginate()
    }

    if(manager.topPosition == 99) {

      Log.d("First","Card")
      reload()

    } else {

      Log.d("Second","Card")

    }

  }

  override fun onCardRewound() {
    Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
  }

  override fun onCardCanceled() {
    Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
  }

  override fun onCardAppeared(view: View, position: Int) {
    val textView = view.findViewById<TextView>(R.id.item_facts)
    Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")

  }

  override fun onCardDisappeared(view: View, position: Int) {
    val textView = view.findViewById<TextView>(R.id.item_facts)
    Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
  }

  private fun syncState() {

  }

  private fun setupCardStackView() {
    initialize()
  }

//  private fun setupButton() {
//    val skip = findViewById<View>(R.id.skip_button)
//    skip.setOnClickListener {
//      val setting = SwipeAnimationSetting.Builder()
//        .setDirection(Direction.Left)
//        .setDuration(Duration.Normal.duration)
//        .setInterpolator(AccelerateInterpolator())
//        .build()
//      manager.setSwipeAnimationSetting(setting)
//      cardStackView.swipe()
//    }
//
//    val rewind = findViewById<View>(R.id.rewind_button)
//    rewind.setOnClickListener {
//      val setting = RewindAnimationSetting.Builder()
//        .setDirection(Direction.Bottom)
//        .setDuration(Duration.Normal.duration)
//        .setInterpolator(DecelerateInterpolator())
//        .build()
//      manager.setRewindAnimationSetting(setting)
//      cardStackView.rewind()
//    }
//
//    val like = findViewById<View>(R.id.like_button)
//    like.setOnClickListener {
//      val setting = SwipeAnimationSetting.Builder()
//        .setDirection(Direction.Right)
//        .setDuration(Duration.Normal.duration)
//        .setInterpolator(AccelerateInterpolator())
//        .build()
//      manager.setSwipeAnimationSetting(setting)
//      cardStackView.swipe()
//    }
//  }

  private fun initialize() {
    manager.setStackFrom(StackFrom.Top)
    manager.setVisibleCount(2)
    manager.setTranslationInterval(8.0f)
    manager.setScaleInterval(0.95f)
    manager.setSwipeThreshold(0.3f)
    manager.setMaxDegree(20.0f)
    manager.setDirections(Direction.FREEDOM)
    manager.setCanScrollHorizontal(true)
    manager.setCanScrollVertical(true)
    manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
    //manager.setOverlayInterpolator(LinearInterpolator())
    cardStackView.layoutManager = manager
    cardStackView.adapter = adapter
    cardStackView.itemAnimator.apply {
      if (this is DefaultItemAnimator) {
        supportsChangeAnimations = false
      }
    }


  }

  private fun paginate() {
    val old = adapter.getSpots()
    val new = old.plus(createSpots())
    val callback = SpotDiffCallback(old, new)
    val result = DiffUtil.calculateDiff(callback)
    adapter.setSpots(new)
    result.dispatchUpdatesTo(adapter)
  }

  private fun reload() {
    val old = adapter.getSpots()
    val new = createSpots()
    val callback = SpotDiffCallback(old, new)
    val result = DiffUtil.calculateDiff(callback)
    adapter.setSpots(new)
    result.dispatchUpdatesTo(adapter)
  }

  private fun addFirst(size: Int) {
    val old = adapter.getSpots()
    val new = mutableListOf<Spot>().apply {
      addAll(old)
      for (i in 0 until size) {
        add(manager.topPosition, createSpot())
      }
    }
    val callback = SpotDiffCallback(old, new)
    val result = DiffUtil.calculateDiff(callback)
    adapter.setSpots(new)
    result.dispatchUpdatesTo(adapter)
  }

  private fun addLast(size: Int) {
    val old = adapter.getSpots()
    val new = mutableListOf<Spot>().apply {
      addAll(old)
      addAll(List(size) { createSpot() })
    }
    val callback = SpotDiffCallback(old, new)
    val result = DiffUtil.calculateDiff(callback)
    adapter.setSpots(new)
    result.dispatchUpdatesTo(adapter)
  }

  private fun removeFirst(size: Int) {
    if (adapter.getSpots().isEmpty()) {
      return
    }

    val old = adapter.getSpots()
    val new = mutableListOf<Spot>().apply {
      addAll(old)
      for (i in 0 until size) {
        removeAt(manager.topPosition)
      }
    }
    val callback = SpotDiffCallback(old, new)
    val result = DiffUtil.calculateDiff(callback)
    adapter.setSpots(new)
    result.dispatchUpdatesTo(adapter)
  }

  private fun removeLast(size: Int) {
    if (adapter.getSpots().isEmpty()) {
      return
    }

    val old = adapter.getSpots()
    val new = mutableListOf<Spot>().apply {
      addAll(old)
      for (i in 0 until size) {
        removeAt(this.size - 1)
      }
    }
    val callback = SpotDiffCallback(old, new)
    val result = DiffUtil.calculateDiff(callback)
    adapter.setSpots(new)
    result.dispatchUpdatesTo(adapter)
  }

  private fun replace() {
    val old = adapter.getSpots()
    val new = mutableListOf<Spot>().apply {
      addAll(old)
      removeAt(manager.topPosition)
      add(manager.topPosition, createSpot())
    }
    adapter.setSpots(new)
    adapter.notifyItemChanged(manager.topPosition)
  }

  private fun swap() {
    val old = adapter.getSpots()
    val new = mutableListOf<Spot>().apply {
      addAll(old)
      val first = removeAt(manager.topPosition)
      val last = removeAt(this.size - 1)
      add(manager.topPosition, last)
      add(first)
    }
    val callback = SpotDiffCallback(old, new)
    val result = DiffUtil.calculateDiff(callback)
    adapter.setSpots(new)
    result.dispatchUpdatesTo(adapter)
  }

  private fun createSpot(): Spot {
    return Spot(
        facts = "Yasaka Shrine",
        judgment = "Kyoto",
        title = "Facts",
        category = "Nuisance"
    )
  }

  private fun createSpots(): List<Spot> {
    val spots = ArrayList<Spot>()

        val facts: String = intent.extras?.getString(EXTRA_FACTS)!!
        val category: String = intent.extras?.getString(EXTRA_CATEGORY)!!
        val judgment: String = intent.extras?.getString(EXTRA_JUDGMENT)!!


    for (i in 0..100) {

      spots.add(Spot(facts = facts, judgment = "", title = "Facts", category = category))
      spots.add(Spot(facts = judgment, judgment = "", title = "Judgment", category = category))

    }


    return spots
  }
 }
