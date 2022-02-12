package com.example.tcp_test

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerManger(var fm:FragmentManager):FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        
        return when(position){
            0 -> "원격 시동"
            1 -> "인증 Log"
            2 -> "유튜브"
            else -> "몰라유"
        }
    }
    
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> management()
            2 -> youtubeClient()
            else -> Fragment(R.layout.youtube_tcp)
        }
    }

    override fun getCount(): Int = 3

}