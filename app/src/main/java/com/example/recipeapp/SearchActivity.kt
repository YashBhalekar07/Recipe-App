package com.example.recipeapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding
    private lateinit var rvAdapter: SearchAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var recipes : List<Recipe?>
    @SuppressLint("ServiceCast")
        override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
            binding = ActivitySearchBinding.inflate(layoutInflater)
//        enableEdgeToEdge()
        setContentView(binding.root)
            binding.search.requestFocus()

            var db = Room.databaseBuilder(this@SearchActivity, AppDatabase::class.java, "db_name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .createFromAsset("recipe.db")
                .build()

            var daoObject = db.getDao()

            recipes = daoObject.getAll()!!
            setUpRecyclerView()

            binding.goBackHome.setOnClickListener {
                finish()
            }

            binding.search.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s.toString()!=""){
                        filterData(s.toString())
                    }else{
                        setUpRecyclerView()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.go_up)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    private fun filterData(filterText:String){
        var filterData = ArrayList<Recipe>()
        for(i in recipes.indices){
            if(recipes[i]!!.tittle.lowercase().contains(filterText.lowercase())){
                filterData.add(recipes[i]!!)
            }
            rvAdapter.filterList(filterList =  filterData)
        }
    }
    private fun setUpRecyclerView() {
        dataList = ArrayList()

        binding.rvSearch.layoutManager =
            LinearLayoutManager(this)

        for(i in recipes!!.indices){
            if(recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)
            }
            rvAdapter=SearchAdapter(dataList,this)
            binding.rvSearch.adapter=rvAdapter
        }
    }
}