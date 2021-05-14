package com.example.tcp_test

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerManger(var fm:FragmentManager):FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        
        return when(position){
            0 -> "유튜브"
            1 -> "인증 Log"
            2 -> "관리"
            else -> "몰라유"
        }
    }
    
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> youtubeClient()
            2 -> management()
            else -> Fragment(R.layout.youtube_tcp)
        }
    }

    override fun getCount(): Int = 3

}