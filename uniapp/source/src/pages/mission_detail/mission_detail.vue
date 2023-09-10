<template>
    <view class="app-md">
        <view class="app-md-header">
            <text>拉布拉卡</text>
            <view @click="back">
                <uni-icons type="back" size="26.92rpx" color="#8E8E8E"></uni-icons>
                <text>返回</text>
            </view>
        </view>

        <img class="app-md-img" :src="mission.img" />
        <view class="app-md-button" @click="finishMission">
            <uni-icons type="checkmarkempty" size="60rpx" color="white"></uni-icons>
            <text>点击打卡</text>
        </view>
        <text class="app-md-title">{{ mission.name }}</text>
        <text class="app-md-desc">{{ mission.description }}</text>
    </view>    
</template>

<script>
const app = getApp();
export default{

    onLoad(options){
        this.loadMissionDetail(options.missionId);
    },

    data(){
        return {
            mission: null
        }
    },

    methods: {
        loadMissionDetail(missionId){
            uni.request({
                url: `${app.globalData.apiUrl}/mission/getMissionById`,
                method: "GET",
                data: {
                    missionId: missionId
                },
                success: (res) => {
                    if(res.data.code !== 200){
                        uni.showToast({
                            title: res.data.msg,
                            icon: "fail",
                            mask: true,
                            position: "top"
                        });
                        return;
                    }
                    this.mission = res.data.data;
                }
            })
        },

        back(){
            uni.navigateBack();
        },

        finishMission(){
            uni.request({
                url: `${app.globalData.apiUrl}/mission/finishMission`,
                method: "GET",
                data: {
                    userId: app.globalData.userInfo.userId,
                    missionId: this.mission.id
                },
                success: (res) => {
                    if(res.data.code !== 200){
                        uni.showToast({
                            title: res.data.msg,
                            icon: "fail",
                            mask: true,
                            position: "top"
                        });
                        return;
                    }
                    uni.navigateBack();
                }
            })
        }
    }
}
</script>

<style>
.app-md{
    width: 100vw;
    height: 100vh;
    background-color: #FBFDFF;
    display: flex;
    flex-direction: column;
}

.app-md-header{
    width: 100vw;
    height: 346.15rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    box-shadow: 0rpx 8rpx 10rpx -12rpx rgba(0,0,0,0.25);
}

.app-md-header > text{
    font-size: 50rpx;
    height: 50rpx;
    line-height: 50rpx;
    font-weight: bold;
    margin-top: 173.08rpx;
    margin-left: 38.46rpx;
}

.app-md-header > view {
    display: flex;
    align-items: center;
    margin-left: 38.46rpx;
    margin-top: 76.92rpx;
}

.app-md-header > view > text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
    margin-left: 9.62rpx;
}

.app-md-header > view > uni-icons{
    font-weight: bold;
}

.app-md-img {
    width: 100vw;
    height: 384.62rpx;
}

.app-md-button{
    width: 153.85rpx;
    height: 153.85rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    align-self: center;
    background-color: #3EAFC8;
    margin-top: -96.16rpx;
    border-radius: 50%;
}

.app-md-button > text{
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: white;
    font-weight: bold;
}

.app-md-title {
    font-size: 34.62rpx;
    height: 34.62rpx;
    line-height: 34.62rpx;
    font-weight: bold;
    margin-top: 38.46rpx;
    align-self: center;
}

.app-md-desc {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 40rpx;
    color: #8E8E8E;
    margin-top: 28.85rpx;
    box-sizing: border-box;
    padding: 0 38.46rpx;
    align-self: center;
    text-align: center;
}
</style>