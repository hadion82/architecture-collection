package com.example.demo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.demo.databinding.ActivityDemoBinding
import com.example.demo.databinding.ListItemUserBinding
import com.example.toRelativeMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoActivity : BaseActivity<ActivityDemoBinding>() {

    private val adapter by lazy { UserAdapter() }

    override fun layout(): Int =
        R.layout.activity_demo

    override fun onBind(savedInstanceState: Bundle?, binding: ActivityDemoBinding) {

        binding.run {
            model?.let { viewModel ->
                viewModel.userIdLiveData.toRelativeMap()
                    .relateMap({ viewModel.getUser(it) }) {
                        Log.d("test", "user1 -> $it")
                    }.relateMap({ viewModel.getUser(it) }) {
                        Log.d("test", "user2 -> $it")
                    }.collect(this@DemoActivity) {
                        Log.d("test", "id -> $it")
                    }

                viewModel.init()
                var i = 0L
                find.setOnClickListener {
                    viewModel.setId(i++)
                }
            }
        }


//        binding.run {
//            model?.let { viewModel ->
//                viewModel.getUsers().sync(
//                    this@DemoActivity, userList, adapter
//                )
//                viewModel.userIdLiveData.switchMap {id ->
//                    viewModel.getUser(id)
//                }.collect(this@DemoActivity) {user ->
//                    viewModel.setUserName(user?.name)
//                }
//                find.setOnClickListener {
//                    viewModel.findUser(
//                        number.text.toSafeLong()
//                    )
//                }
//                viewModel.init()
//            }
//        }
    }

    inner class UserAdapter :
        ListAdapter<User,
                UserViewHolder>(
            UserCallback()
        ) {

        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ) = UserViewHolder(
            ListItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

        override fun onBindViewHolder(
            holder: UserViewHolder,
            position: Int
        ) = holder.onBindViewHolder(currentList[position], position)
    }

    inner class UserViewHolder(val binding: ListItemUserBinding) :
        BaseViewHolder<User>(binding.root) {

        override fun onBindViewHolder(item: User, position: Int) {
            binding.name = item.name
        }
    }

    class UserCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.name == newItem.name
    }
}
