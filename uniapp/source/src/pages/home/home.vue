<template>
    <view class="app-home">
        <view class="app-home-content">
            <!-- 页面容器 -->
            <MissionList v-if="currentTab == 'mission'" ref="missionList"/>
            <RewardList v-if="currentTab == 'reward'" ref="rewardList"/>
            <Profile v-if="currentTab == 'profile'" ref="profile" />
        </view>

        <!-- 底部导航栏 -->
        <view class="com-tabbar">
            <view class="com-tabbar-item" :class="currentTab == 'mission' ? '.com-tabbar-item-active' : ''" @click="toggleTabs('mission')">
                <uni-icons type="medal-filled" size="46.15rpx"></uni-icons>
                <text>打卡任务</text>
            </view>
    
            <view class="com-tabbar-item" :class="currentTab == 'reward' ? '.com-tabbar-item-active' : ''" @click="toggleTabs('reward')">
                <uni-icons type="gift-filled" size="46.15rpx"></uni-icons>
                <text>积分商城</text>
            </view>
    
            <view class="com-tabbar-item" :class="currentTab == 'profile' ? '.com-tabbar-item-active' : ''" @click="toggleTabs('profile')">
                <uni-icons type="person-filled" size="46.15rpx"></uni-icons>
                <text>我的</text>
            </view>
        </view>
    </view>
</template>

<script>
import MissionList from "@/components/mission_list/mission_list.vue";
import RewardList from "@/components/reward_list/reward_list.vue";
import Profile from "@/components/profile/profile.vue";

const app = getApp();

export default{
    components: {
        MissionList,
        RewardList,
        Profile
    },

    data(){
        return {
            currentTab: "profile", //mission reward profile
            checkInterval: null,
        }
    },
    
    onShow(){
        switch(this.currentTab){
            case "mission": {
                this.$refs.missionList.onNavigateBack();
                break;
            }
        }
    },

    methods: {
        toggleTabs(tab){
            this.currentTab = tab;
        }
    }
}
</script>

<style>
.app-home{
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #FBFDFF;
}

.app-home-content{
    width: 100vw;
    height: calc(100vh - 192.31rpx);
    display: flex;
    flex-direction: column;
}

.com-tabbar{
    width: 100vw;
    height: 192.31rpx;
    box-shadow: 0rpx -4rpx 10rpx -8rpx rgba(0,0,0,0.25);
    background-color: white;
    margin-top: auto;
    margin-bottom: 0;

    display: flex;
    justify-content: space-around;
}

.com-tabbar-item{
    width: 96.15rpx;
    height: 96.15rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 26.92rpx;
    color: #575757;
}

.com-tabbar-item > text{
    font-size: 23rpx;
    height: 23rpx;
    line-height: 23rpx;
    font-weight: 400;
    font-weight: bold;
    margin-top: 7.69rpx;
}

.com-tabbar-item-active{
    color: #3EAFC8;
}
</style>