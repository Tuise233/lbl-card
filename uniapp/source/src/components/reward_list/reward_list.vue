<template>
    <view class="cm-rl">
        <view class="com-rl-header">
            <text class="com-rl-title">拉布拉卡</text>

            <view class="com-rl-options">
                <text>积分商城</text>
                <view @click="toggleTypes">
                    <uni-icons type="bottom" color="#8E8E8E" size="26.92rpx"></uni-icons>
                    <text>{{ types[typeIndex] }}</text>
                </view>
            </view>
        </view>

        <view class="com-rl-content">
            <view class="com-rl-content-options">
                <view>
                    <uni-icons type="medal-filled" size="40" color="#575757"></uni-icons>
                    <view>
                        <text>我的积分</text>
                        <text>{{ userInfo.score }} 分</text>
                    </view>
                </view>

                <view @click="loadRewardRecord">
                    <uni-icons type="calendar-filled" size="40" color="#575757"></uni-icons>
                    <text>兑换记录</text>
                </view>
            </view>
            <view class="com-rl-content-list">
                <view v-for="item in datas" @click="buyReward(item)">
                    <img :src="item.img"/>
                    <text class="com-rl-content-list-item-title">{{ item.name }}</text>
                    <view>
                        <text>{{ item.score }} 积分</text>
                        <text>库存数: {{ item.amount }}</text>
                    </view>
                </view>
            </view>
        </view>
        <view class="com-ml-content-create-button" v-if="typeIndex == 1">
            <uni-icons type="plusempty" size="25" color="white"></uni-icons>
        </view>
    </view>    
</template>

<script>
const app = getApp();

export default {
    data(){
        return {
            checkInterval: null,
            typeIndex: 0,
            types: ["我的商城", "TA的商城"],
            datas: [],
            userInfo: null
        }
    },

    created(){
        uni.showLoading({
            title: "加载中..."
        });
        const that = this;
        if (this.checkInterval === null) {
            this.checkInterval = setInterval(() => {
                if (app.globalData.userInfo !== null) {
                    this.updateUserInfo();
                    if(app.globalData.openId !== null) {
                        setTimeout(() => {
                            this.loadRewards();
                        }, 500);
                    }
                    clearInterval(that.checkInterval);
                    that.checkInterval = null;
                    uni.hideLoading();
                }
            }, 100);
        }
    },

    methods: {
        toggleTypes() {
            if(this.typeIndex >= this.types.length - 1) {
                this.typeIndex = 0;
            } else {
                this.typeIndex++;
            }

            if(this.typeIndex == 0) { 
                this.loadRewards();
            } else {
                this.loadTargetRewards();
            }
        },

        updateUserInfo(){
            if(app.globalData.openId === null) {
                uni.showToast({
                    title: "无效的openId!",
                    icon: "fail",
                    mask: true,
                    position: "top"
                });
                return;
            }

            uni.request({
                url: `${app.globalData.apiUrl}/user/getUserInfo`,
                method: "GET",
                data: {
                    open_id: app.globalData.openId
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
                    app.globalData.userInfo = res.data.data;
                    this.userInfo = res.data.data;
                }
            })
        },

        loadRewards() {
            this.datas = [];
            uni.request({
                url: `${app.globalData.apiUrl}/reward/getRewardByOwnerId`,
                method: "GET",
                data: {
                    ownerId: app.globalData.userInfo.userId
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
                    this.datas = res.data.data;
                }
            });
        },

        loadTargetRewards() {
            this.datas = [];
            uni.request({
                url: `${app.globalData.apiUrl}/reward/getRewardByOwnerId`,
                method: "GET",
                data: {
                    ownerId: app.globalData.userInfo.targetId
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
                    this.datas = res.data.data;
                }
            });
        },

        buyReward(item){
            uni.showModal({
                title: "兑换奖励",
                content: `确定使用${item.score}积分兑换一个${item.name}吗?`,
                success: (res) => {
                    if(res.confirm) {
                        if(item.amount <= 0) {
                            uni.showToast({
                                title: "奖励库存不足!",
                                icon: "fail",
                                mask: true,
                                position: "top"
                            });
                            return;
                        }
                        uni.request({
                            url: `${app.globalData.apiUrl}/reward/buyReward`,
                            method: "GET",
                            data: {
                                userId: app.globalData.userInfo.userId,
                                rewardId: Number(item.id)
                            },
                            success: (res) => {
                                if(res.data.code !== 200) {
                                    uni.showToast({
                                        title: res.data.msg,
                                        icon: "fail",
                                        mask: true,
                                        position: "top"
                                    });
                                    return;
                                }
                                uni.showToast({
                                    title: "兑换成功!",
                                    icon: "success",
                                    mask: true,
                                    position: "top"
                                });
                                setTimeout(() => {
                                    this.loadRewards();
                                    this.updateUserInfo();
                                }, 1000);
                            }
                        });
                    }
                }
            });
        },

        loadRewardRecord() {
            uni.navigateTo({
                url: `/pages/reward_record/reward_record?userId=${app.globalData.userInfo.userId}`
            });
        }
    }
}
</script>

<style>
.cm-rl{
    width: 100vw;
    height: 1430.77rpx;
    display: flex;
    flex-direction: column;
}

.com-rl-header {
    width: 100vw;
    height: 346.15rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    box-shadow: 0rpx 8rpx 10rpx -12rpx rgba(0, 0, 0, 0.25);
}

.com-rl-title {
    font-size: 50rpx;
    height: 50rpx;
    line-height: 50rpx;
    font-weight: bold;
    margin-top: 173.08rpx;
    margin-left: 38.46rpx;
}

.com-rl-options {
    width: 100vw;
    box-sizing: border-box;
    padding: 0 38.46rpx;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 69.23rpx;
    color: #8E8E8E;
}

.com-rl-options>text {
    font-size: 35rpx;
    height: 35rpx;
    line-height: 35rpx;
    font-weight: bold;
}

.com-rl-options>text:nth-child(2) {
    margin-left: 76.92rpx;
    margin-right: auto;
}

.com-rl-options>view {
    display: flex;
    align-items: center;
}

.com-rl-options>view>text {
    margin-left: 9.62rpx;
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
}

.com-rl-options-active {
    color: #3EAFC8;
}

.com-rl-content {
    width: 100vw;
    height: calc(100vh - 346.15rpx - 192.31rpx);
    display: flex;
    flex-direction: column;
    align-items: center;
}

.com-rl-content-options {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 38.46rpx;
    width: 100vw;
    height: 115.38rpx;
    box-sizing: border-box;
    padding: 0 38.46rpx;
}

.com-rl-content-options > view {
    width: 326.92rpx;
    height: 115.38rpx;
    display: flex;
    align-items: center;
    background-color: white;
    box-shadow: 0rpx 0rpx 8rpx -2rpx rgba(0,0,0,0.25);
    border-radius: 10rpx;
}

.com-rl-content-options > view > uni-icons {
    margin-left: 25rpx;
}

.com-rl-content-options > view > text {
    font-size: 30.77rpx;
    height: 30.77rpx;
    line-height: 30.77rpx;
    font-weight: bold;
    margin-left: auto;
    margin-right: 19.23rpx;
}

.com-rl-content-options > view > view {
    margin-left: auto;
    margin-right: 19.23rpx;
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    color: #575757;
}

.com-rl-content-options > view > view > text:first-child {
    font-size: 23.08rpx;
    height: 23.08rpx;
    line-height: 23.08rpx;
}

.com-rl-content-options > view > view > text:last-child {
    font-size: 30.77rpx;
    height: 30.77rpx;
    line-height: 30.77rpx;
    margin-top: 9.62rpx;
    font-weight: bold;
}

.com-rl-content-list {
    width: 680rpx;
    height: 892.31rpx;
    margin-top: 38.46rpx;
    display: flex;
    flex-wrap: wrap;
    overflow-y: scroll;
}

.com-rl-content-list::-webkit-scrollbar {
    width: 0;
}

.com-rl-content-list > view {
    width: 326.92rpx;
    height: 419.23rpx;
    background-color: white;
    border-radius: 10rpx;
    box-shadow: 0rpx 0rpx 8rpx -2rpx rgba(0,0,0,0.25);
    display: flex;
    flex-direction: column;
    align-items: center;
    box-sizing: border-box;
    padding: 19.23rpx;
    margin-bottom: 19.23rpx;
    margin-top: 5rpx;
    margin-left: 5rpx;
}

.com-rl-content-list > view:nth-child(2n + 1) {
    margin-right: 19.23rpx;
}

.com-rl-content-list > view > img {
    width: 288.46rpx;
    height: 288.46rpx;
    border-radius: 10rpx;
}

.com-rl-content-list-item-title {
    margin-top: 19.23rpx;
    font-size: 30.77rpx;
    height: 30.77rpx;
    line-height: 30.77rpx;
    align-self: flex-start;
}

.com-rl-content-list > view > view {
    width: 100%;
    margin-top: 19.23rpx;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.com-rl-content-list > view > view > text:first-child {
    color: #FC8800;
    font-size: 23.08rpx;
    height: 23.08rpx;
    line-height: 23.08rpx;
}

.com-rl-content-list > view > view > text:last-child {
    color: #575757;
    font-size: 23rpx;
    height: 23rpx;
    line-height: 23rpx;
}

.com-ml-content-create-button {
    width: 96.15rpx;
    height: 96.15rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    background-color: #3EAFC8;
    box-shadow: 0rpx 0rpx 2rpx 2rpx rgba(0, 0, 0, 0.25);
    border: 2rpx solid rgba(255, 255, 255, 0.25);

    position: absolute;
    right: 28.85rpx;
    bottom: 250rpx;
}
</style>