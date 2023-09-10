<template>
    <view class="app-mtl">
        <view class="app-mtl-header">
            <text>拉布拉卡</text>
            <view>
                <view @click="back">
                    <uni-icons type="back" size="26.92rpx" color="#8E8E8E"></uni-icons>
                    <text>返回</text>
                </view>
                <text>{{ listText }}</text>
            </view>
        </view>

        <view>
            <view class="app-mtl-item" v-for="item in missionData" @click="loadMissionCheck(item)">
                <img :src="item.img" />
                <view>
                    <view>
                        <text>{{ item.name }}</text>
                        <view>
                            <text>{{ item.type == 0 ? '每日' : '长期' }}</text>
                        </view>
                    </view>

                    <view>
                        <text v-if="item.type == 0">{{ getMissionText(item) }}</text>
                        <text v-else>已累计 {{ item.combo }} 天</text>
                        <text>{{ item.finishTime }}</text>
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
            queryType: {
                ALL: 0,
                ONLY_UNFINISH: 1,
                ONLY_FINISHED: 2,
                ONLY_UNFINISH_DAY: 3,
                ONLY_UNFINISH_MONTH: 4
            },
            listType: 0,
            listText: "每日打卡情况"
        }
    },

    computed: {
        missionData: function () {
            let data = this.originData.filter(item => item.type == this.listType);
            data.forEach(item => {
                let date = new Date(item.finishTime);
                let month = date.getMonth() + 1;
                month = month < 10 ? `0${month}` : month;
                let dates = date.getDate();
                dates = dates < 10 ? `0${dates}` : dates;
                let hour = date.getHours();
                hour = hour < 10 ? `0${hour}` : hour;
                let minute = date.getMinutes();
                minute = minute < 10 ? `0${minute}` : minute;
                item.finishTime = `${date.getFullYear()}/${month}/${dates} ${hour}:${minute}`;
            });
            return data;
        }
    },

    onLoad(options) {
        this.listType = options.type;
        this.listText = this.listType == 0 ? "每日打卡情况" : "每月打卡情况";
        this.loadMissionData();
    },

    methods: {
        back() {
            uni.navigateBack();
        },

        loadMissionData() {
            uni.request({
                url: `${app.globalData.apiUrl}/mission/getListByCreatorId`,
                method: "GET",
                data: {
                    creatorId: app.globalData.userInfo.userId,
                    type: this.queryType.ONLY_FINISHED
                },
                success: (res) => {
                    if (res.data.code !== 200) {
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
            })
        },

        loadMissionCheck(item) {
            if (item.type == 1 || item.checkType == 0 || (item.checkType == 1 && item.state == 1)) return;
            let missionId = item.id;
            uni.navigateTo({
                url: `/pages/mission_check/mission_check?missionId=${missionId}`
            });
        },

        getMissionText(item) {
            if (item.checkType == 0) return "已完成";
            return item.checkState == 0 ? "待审核" : "已审核";
        }
    }
}
</script>

<style>
.app-mtl {
    width: 100vw;
    height: 100vh;
    background-color: #FBFDFF;
}

.app-mtl-header {
    width: 100vw;
    height: 346.15rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    box-shadow: 0rpx 8rpx 10rpx -12rpx rgba(0, 0, 0, 0.25);
}

.app-mtl-header>text {
    font-size: 50rpx;
    height: 50rpx;
    line-height: 50rpx;
    font-weight: bold;
    margin-top: 173.08rpx;
    margin-left: 38.46rpx;
}

.app-mtl-header>view {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 76.92rpx;
    box-sizing: border-box;
    padding: 0 38.46rpx;
}

.app-mtl-header>view>text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
    margin-left: auto;
}

.app-mtl-header>view>view {
    display: flex;
    align-items: center;
}

.app-mtl-header>view>view>text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
    margin-left: 9.62rpx;
}

.app-mtl-header>view>view>uni-icons {
    font-weight: bold;
}

.app-mtl>view:last-child {
    width: 100vw;
    height: calc(100vh - 346.15rpx);
    display: flex;
    flex-direction: column;
    align-items: center;
    box-sizing: border-box;
    padding: 0 38.46rpx;
    overflow: scroll;
}

.app-mtl-item {
    width: 100%;
    height: 115.38rpx;
    display: flex;
    background-color: white;
    border-radius: 10rpx;
    box-shadow: 0rpx 0rpx 19rpx -8rpx rgba(0, 0, 0, 0.25);
    margin-top: 38.46rpx;
}

.app-mtl-item>img {
    width: 115.38rpx;
    height: 115.38rpx;
    border-top-left-radius: 10rpx;
    border-bottom-left-radius: 10rpx;
}

.app-mtl-item>view {
    width: 100%;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    padding: 19.23rpx;
}

.app-mtl-item>view>view {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.app-mtl-item>view>view:first-child>text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    font-weight: bold;
}

.app-mtl-item>view>view:first-child>view {
    width: 76.92rpx;
    height: 28.85rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 29rpx;
    background-color: #3EAFC8;
}

.app-mtl-item>view>view:first-child>view>text {
    font-size: 19.23rpx;
    height: 19.23rpx;
    line-height: 19.23rpx;
    color: white;
}

.app-mtl-item>view>view:last-child {
    margin-top: auto;
    margin-bottom: 0;
}

.app-mtl-item>view>view:last-child>text {
    font-size: 19.23rpx;
    height: 19.23rpx;
    line-height: 19.23rpx;
    color: #8E8E8E;
}

.app-mtl-item>view>view:last-child>text:first-child {
    color: #FC8800;
}

.app-mtl-item:last-child {
    margin-bottom: 38.46rpx;
}
</style>