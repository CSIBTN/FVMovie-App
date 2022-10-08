package com.csibtn.fvmovies.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.csibtn.fvmovies.api.Contributor
import com.csibtn.fvmovies.databinding.ContributorItemBinding

class CastListAdapter(private val castList: List<Contributor>) :
    RecyclerView.Adapter<CastListAdapter.CastHolder>() {
    inner class CastHolder(private val binding: ContributorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contributor: Contributor) {
            binding.tvContributorName.text = contributor.name
            if (contributor.profile_path == null)
                binding.ivActor.load("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/300px-No_image_available.svg.png")
            else
                binding.ivActor.load(contributor.contributorPicture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContributorItemBinding.inflate(inflater, parent, false)
        return CastHolder(binding)
    }

    override fun onBindViewHolder(holder: CastHolder, position: Int) {

        holder.bind(castList[position])
    }

    override fun getItemCount(): Int = castList.size

}