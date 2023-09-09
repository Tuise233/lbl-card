<template>
    <view class="app-cm">
        <view class="app-cm-header">
            <text>拉布拉卡</text>
            <view>
                <view @click="back">
                    <uni-icons type="back" size="26.92rpx" color="#8E8E8E"></uni-icons>
                    <text>返回</text>
                </view>
                <text>创建新打卡</text>
            </view>
        </view>
        
        <InputBox class="app-cm-inputbox" label="打卡标题" type="text" v-model="mission.title"></InputBox>
        <InputBox class="app-cm-inputbox" label="打卡简述" type="textarea" v-model="mission.description"></InputBox>
        <InputBox class="app-cm-inputbox" label="打卡类型" type="select" v-model="mission.type" ol="每日打卡" or="长期打卡"></InputBox>
        <InputBox class="app-cm-inputbox" label="审批类型" type="select" v-model="mission.checkType" ol="需要审批" or="无需审批"></InputBox>
        <InputBox class="app-cm-inputbox" label="打卡积分奖励" type="number" v-model="mission.title"></InputBox>

        <view class="app-cm-button" @click="createMission">
            <span>创建新打卡</span>
        </view>
    </view>
</template>

<script>
import InputBox from "@/components/input_box/input_box.vue";

export default {
    components: {
        InputBox
    },

    data(){
        return {
            mission: {
                title: "",
                description: "",
                type: "left",
                checkType: "left",
                score: 0,
            }
        }
    },
    
    methods: {
        createMission(){

        },

        back(){
            uni.navigateBack();
        },
    }
}
</script>

<style>
.app-cm{
    width: 100vw;
    height: 100vh;
    background-color: #FBFDFF;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.app-cm-header{
    width: 100vw;
    height: 346.15rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    box-shadow: 0rpx 8rpx 10rpx -12rpx rgba(0,0,0,0.25);
}

.app-cm-header > text{
    font-size: 50rpx;
    height: 50rpx;
    line-height: 50rpx;
    font-weight: bold;
    margin-top: 173.08rpx;
    margin-left: 38.46rpx;
}

.app-cm-header > view {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 76.92rpx;
    box-sizing: border-box;
    padding: 0 38.46rpx;
}

.app-cm-header > view > text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
    margin-left: auto;
}

.app-cm-header > view > view {
    display: flex;
    align-items: center;
}

.app-cm-header > view > view > text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
    margin-left: 9.62rpx;
}

.app-cm-header > view > view > uni-icons{
    font-weight: bold;
}

.app-cm-inputbox { 
    margin-top: 36.54rpx;
}

.app-cm-button {
    width: 673.08rpx;
    height: 76.92rpx;
    border-radius: 10rpx;
    background-color: #3EAFC8;
    color: white;
    font-size: 33rpx;
    line-height: 33rpx;
    font-weight: bold;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 38.46rpx;
}
</style>