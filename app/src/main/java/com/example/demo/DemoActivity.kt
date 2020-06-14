package com.example.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.switchMap
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.database.entity.User
import com.example.demo.databinding.ActivityDemoBinding
import com.example.demo.databinding.ListItemUserBinding
import com.example.toSafeLong

class DemoActivity : BaseActivity<ActivityDemoBinding>() {

    private val adapter by lazy { UserAdapter() }

    override fun layout(): Int =
        R.layout.activity_demo

    override fun onBind(savedInstanceState: Bundle?, binding: ActivityDemoBinding) {
        binding.run {
            model?.let { viewModel ->
                viewModel.getUsers().sync(userList, adapter)
                viewModel.userIdLiveData.switchMap {id ->
                    viewModel.getUser(id)
                }.collect {user ->
                    viewModel.setUserName(user?.name)
                }
                find.setOnClickListener {
                    viewModel.findUser(
                        number.text.toSafeLong()
                    )
                }
                viewModel.init()
            }
        }
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
