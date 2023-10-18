<template>
    <view class="app-rr">
        <view class="app-rr-header">
            <text>拉布拉卡</text>
            <view @click="back">
                <uni-icons type="back" size="26.92rpx" color="#8E8E8E"></uni-icons>
                <text>返回</text>
            </view>
        </view>

        <view class="app-rr-content">
            <view class="app-rr-item" v-for="item in datas" @click="useItem(item)">
                <img :src="item.img" />
                <view>
                    <view>
                        <span class="app-rr-name">{{ item.name }}</span>
                        <view class="app-rr-score">
                            <span>{{ item.score }} 积分</span>
                        </view>
                    </view>

                    <view>
                        <span :class="item.state == 0 ? 'app-rr-unused' : 'app-rr-used'">{{ item.state == 0 ? '未使用' : '已使用' }}</span>
                        <span class="app-rr-time">{{ item.getTime }}</span>
                    </view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
const app = getApp();

export default {
    data() {
        return {
            originData: [],
            userId: null
        }
    },

    computed: {
        datas: function() {
            let data = this.originData;
            data.forEach(item => {
                let date = new Date(item.getTime);
                let month = date.getMonth() + 1;
                month = month < 10 ? `0${month}` : month;
                let dates = date.getDate();
                dates = dates < 10 ? `0${dates}` : dates;
                let hour = date.getHours();
                hour = hour < 10 ? `0${hour}` : hour;
                let minute = date.getMinutes();
                minute = minute < 10 ? `0${minute}` : minute;
                item.getTime = `${date.getFullYear()}/${month}/${dates} ${hour}:${minute}`;
            });
            return data;
        }
    },

    onLoad(options) {
        if(options.userId === undefined) return;
        this.userId = options.userId;
        this.loadUserInventory();
    },

    methods: {
        loadUserInventory() {
            this.originData = [];
            uni.request({
                url: `${app.globalData.apiUrl}/inventory/getUserInventoryItem`,
                method: "GET",
                data: {
                    userId: this.userId
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
                    this.originData = res.data.data;
                }
            });
        },

        back() {
            uni.navigateBack();
        },

        useItem(item) {
            if(item.state === 1 || this.userId !== app.globalData.userInfo.userId) return;
            uni.showModal({
                title: "使用奖励",
                content: `你确定要使用一个${item.name}?`,
                showCancel: true,
                cancelText: "再想想",
                confirmText: "确认",
                success: (res) => {
                    if(res.confirm) {
                        uni.request({
                            url: `${app.globalData.apiUrl}/inventory/useInventoryItem`,
                            method: "GET",
                            data: {
                                userId: app.globalData.userInfo.userId,
                                itemId: item.id
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
                                this.loadUserInventory();
                            }
                        });
                    }
                },
            })
        }
    },
}
</script>

<style>
.app-rr{
    width: 100vw;
    height: 1623rpx;
    display: flex;
    flex-direction: column;
}

.app-rr-header{
    width: 100vw;
    height: 346.15rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    box-shadow: 0rpx 8rpx 10rpx -12rpx rgba(0,0,0,0.25);
}

.app-rr-header > text{
    font-size: 50rpx;
    height: 50rpx;
    line-height: 50rpx;
    font-weight: bold;
    margin-top: 173.08rpx;
    margin-left: 38.46rpx;
}

.app-rr-header > view {
    display: flex;
    align-items: center;
    margin-left: 38.46rpx;
    margin-top: 76.92rpx;
}

.app-rr-header > view > text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
    margin-left: 9.62rpx;
}

.app-rr-header > view > uni-icons{
    font-weight: bold;
}

.app-rr-content {
    width: 100vw;
    height: calc(100vh - 346.15rpx);
    display: flex;
    flex-direction: column;
    align-items: center;
    box-sizing: border-box;
    padding: 38.46rpx 44.23rpx;
    overflow-y: scroll;
}

.app-rr-content::-webkit-scrollbar {
    width: 0;
}

.app-rr-item {
    width: 673.08rpx;
    height: 115.38rpx;
    display: flex;
    align-items: center;
    margin-bottom: 38.46rpx;
    box-shadow: 0rpx 0rpx 19rpx -8rpx rgba(0,0,0,0.25);
    background-color: white;
    border-radius: 10rpx;
}

.app-rr-item > img {
    width: 115.38rpx;
    height: 115.38rpx;
    border-top-left-radius: 10rpx;
    border-bottom-left-radius: 10rpx;
}

.app-rr-item > view {
    width: calc(100% - 115.38rpx);
    height: 100%;
    display: flex;
    flex-direction: column;
    border-top-right-radius: 10rpx;
    border-bottom-right-radius: 10rpx;
    box-sizing: border-box;
    padding: 19.23rpx 19.23rpx 25rpx 19.23rpx;
}

.app-rr-item > view > view {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.app-rr-item > view > view:last-child {
    margin-top: auto;
    margin-bottom: 0;
}

.app-rr-name {
    color: black;
    font-weight: bold;
    font-size: 26.92rpx;
}

.app-rr-score {
    display: flex;
    align-items: center;
    justify-content: center;
    box-sizing: border-box;
    padding: 3.85rpx 13.46rpx;
    background-color: #3EAFC8;
    border-radius: 28.85rpx;
    min-width: 76.92rpx;
}

.app-rr-score > span {
    color: white;
    font-size: 19.23rpx;
}

.app-rr-used {
    color: #FC8800;
    font-size: 19.23rpx;
}

.app-rr-unused {
    color: #8E8E8E;
    font-size: 19.23rpx;
}

.app-rr-time {
    color: #8E8E8E;
    font-size: 19.23rpx;
}
</style>
