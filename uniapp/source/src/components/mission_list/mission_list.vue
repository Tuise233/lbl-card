<template>
    <view class="com-ml">
        <view class="com-ml-header">
            <text class="com-ml-title">拉布拉卡</text>

            <view class="com-ml-options">
                <text :class="currentPage == 'mine' ? '.com-ml-options-active' : ''" @click="togglePage('mine')">我的</text>
                <text :class="currentPage == 'other' ? '.com-ml-options-active' : ''"
                    @click="togglePage('other')">TA的</text>
                <view @click="toggleTypes">
                    <uni-icons type="bottom" color="#8E8E8E" size="26.92rpx"></uni-icons>
                    <text>{{ types[typeIndex] }}</text>
                </view>
            </view>
        </view>
        <view class="com-ml-content" v-if="currentPage == 'mine'">
            <view class="com-ml-content-item" v-for="item in missionData" @click="loadMissionDetail(item.id)">
                <img :src="item.img" />
                <view>
                    <view>
                        <text>{{ item.name }}</text>
                        <view>
                            <text>{{ item.type == 0 ? '每日' : '长期' }}</text>
                        </view>
                    </view>

                    <view>
                        <text v-if="item.type == 0">{{ item.score }} 积分</text>
                        <text style="color: #FC8800;" v-else>已累计 {{ item.combo }} 天</text>
                        <text>{{ item.finishState }}</text>
                    </view>
                </view>
            </view>
        </view>

        <view class="com-ml-content com-ml-content-other" v-else>
            <view>
                <view class="com-ml-content-types" @click="loadMissionTypeList(0)">
                    <uni-icons type="medal" size="40" color="#575757"></uni-icons>
                    <text>每日打卡情况</text>
                </view>

                <view class="com-ml-content-types" @click="loadMissionTypeList(1)">
                    <uni-icons type="vip" size="40" color="#575757"></uni-icons>
                    <text>每月打卡情况</text>
                </view>
            </view>

            <view>
                <view class="com-ml-content-item com-ml-content-item-other" v-for="item in missionData" @click="editMission(item.id)">
                    <img :src="item.img" />
                    <view>
                        <view>
                            <text>{{ item.name }}</text>
                            <view>
                                <text>{{ item.type == 0 ? '每日' : '长期' }}</text>
                            </view>
                        </view>

                        <view>
                            <text v-if="item.type == 0">{{ item.score }} 积分</text>
                            <text style="color: #FC8800;" v-else>已累计 {{ item.combo }} 天</text>
                            <text>{{ item.finishState }}</text>
                        </view>
                    </view>
                </view>
                <view class="com-ml-content-create-button">
                    <uni-icons type="plusempty" size="25" color="white" @click="openCreateMission"></uni-icons>
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
            currentPage: "mine", //mine other
            typeIndex: 0,
            types: ["全部", "每日", "长期"],

            originData: [],
            checkInterval: null,
            isBack: false,
            queryType: {
                ALL: 0,
                ONLY_UNFINISH: 1,
                ONLY_FINISHED: 2,
                ONLY_UNFINISH_DAY: 3,
                ONLY_UNFINISH_MONTH: 4
            }
        }
    },

    computed: {
        missionData: function () {
            let data = this.originData;
            for (let i = 0; i < data.length; i++) {
                let finishState = "未完成";
                if (data[i].type == 0 && data[i].state == 1) {
                    finishState = "已完成";
                } else if (data[i].type == 1 && data[i].finishTime !== null) {
                    let curDate = new Date();
                    let thatDate = new Date(data[i].finishTime);
                    if (curDate.getFullYear() == thatDate.getFullYear() && curDate.getMonth() == thatDate.getMonth() && curDate.getDate() == thatDate.getDate()) {
                        finishState = "已完成";
                    }
                }
                data[i]["finishState"] = finishState;
            }
            data = this.typeIndex == 0 ? data : this.typeIndex == 1 ? data.filter(item => item.type == 0) : data.filter(item => item.type == 1);
            return data;
        }
    },

    created() {
        uni.showLoading({
            title: "加载中..."
        });
        const that = this;
        if (this.checkInterval === null) {
            this.checkInterval = setInterval(() => {
                if (app.globalData.userInfo !== null) {
                    let userId = that.currentPage == "mine" ? app.globalData.userInfo.userId : app.globalData.userInfo.targetId;
                    that.loadMissions(userId);
                    clearInterval(that.checkInterval);
                    that.checkInterval = null;
                    uni.hideLoading();
                }
            }, 100);
        }
    },

    methods: {
        togglePage(page) {
            this.currentPage = page;
            this.originData = [];
            if (this.currentPage == "mine") {
                this.loadMissions(app.globalData.userInfo.userId);
            } else {
                if (app.globalData.userInfo.targetId !== null) {
                    this.loadTargetMissions(app.globalData.userInfo.userId, this.queryType.ONLY_UNFINISH);
                }
            }
        },

        toggleTypes() {
            if (this.typeIndex >= this.types.length - 1) {
                this.typeIndex = 0;
            } else {
                this.typeIndex++;
            }
        },

        loadMissions(userId) {
            uni.request({
                url: `${app.globalData.apiUrl}/mission/getListByOwnerId`,
                method: "GET",
                data: {
                    ownerId: userId
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

        loadTargetMissions(userId, queryType) {
            uni.request({
                url: `${app.globalData.apiUrl}/mission/getListByCreatorId`,
                method: "GET",
                data: {
                    creatorId: userId,
                    type: queryType
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
            });
        },

        loadMissionDetail(missionId) {
            this.isBack = true;
            uni.navigateTo({
                url: `/pages/mission_detail/mission_detail?missionId=${missionId}`
            });
        },

        loadMissionTypeList(type) {
            this.isBack = true;
            uni.navigateTo({
                url: `/pages/mission_type_list/mission_type_list?type=${type}`
            });
        },

        editMission(missionId){
            this.isBack = true;
            uni.navigateTo({
                url: `/pages/create_mission/create_mission?missionId=${missionId}`
            });
        },

        onNavigateBack() {
            if (this.isBack == true) {
                this.isBack = false;
                switch(this.currentPage){
                    case "mine": {
                        this.loadMissions(app.globalData.userInfo.userId);
                        break;
                    }

                    case "other": {
                        this.loadTargetMissions(app.globalData.userInfo.userId, this.queryType.ONLY_UNFINISH);
                        break;
                    }
                }
            }
        },
        
        openCreateMission(){
            this.isBack = true;
            uni.navigateTo({
                url: "/pages/create_mission/create_mission"
            });
        }
    }
}
</script>

<style>
.com-ml {
    width: 100vw;
    height: 1430.77rpx;
    display: flex;
    flex-direction: column;
}

.com-ml-header {
    width: 100vw;
    height: 346.15rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    box-shadow: 0rpx 8rpx 10rpx -12rpx rgba(0, 0, 0, 0.25);
}

.com-ml-title {
    font-size: 50rpx;
    height: 50rpx;
    line-height: 50rpx;
    font-weight: bold;
    margin-top: 173.08rpx;
    margin-left: 38.46rpx;
}

.com-ml-options {
    width: 100vw;
    box-sizing: border-box;
    padding: 0 38.46rpx;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 69.23rpx;
    color: #8E8E8E;
}

.com-ml-options>text {
    font-size: 35rpx;
    height: 35rpx;
    line-height: 35rpx;
    font-weight: bold;
}

.com-ml-options>text:nth-child(2) {
    margin-left: 76.92rpx;
    margin-right: auto;
}

.com-ml-options>view {
    display: flex;
    align-items: center;
}

.com-ml-options>view>text {
    margin-left: 9.62rpx;
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
}

.com-ml-options-active {
    color: #3EAFC8;
}

.com-ml-content {
    width: 100vw;
    height: calc(100vh - 346.15rpx - 192.31rpx);
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    padding: 0 38.46rpx;
    overflow: scroll;
}

.com-ml-content-types:first-child {
    margin-left: 38.46rpx;
}

.com-ml-content-types:last-child {
    margin-right: 38.46rpx;
}

.com-ml-content-item {
    width: 100%;
    height: 115.38rpx;
    display: flex;
    background-color: white;
    border-radius: 10rpx;
    box-shadow: 0rpx 0rpx 19rpx -8rpx rgba(0, 0, 0, 0.25);
    margin-top: 38.46rpx;
}

.com-ml-content-item:last-child {
    margin-bottom: 38.46rpx;
}

.com-ml-content-item>img {
    width: 115.38rpx;
    height: 115.38rpx;
    border-top-left-radius: 10rpx;
    border-bottom-left-radius: 10rpx;
}

.com-ml-content-item>view {
    width: 100%;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    padding: 19.23rpx;
}

.com-ml-content-item>view>view {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-sizing: border-box;
}

.com-ml-content-item>view>view:first-child>text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    font-weight: bold;
}

.com-ml-content-item>view>view:first-child>view {
    width: 76.92rpx;
    height: 28.85rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 29rpx;
    background-color: #3EAFC8;
}

.com-ml-content-item>view>view:first-child>view>text {
    font-size: 19.23rpx;
    height: 19.23rpx;
    line-height: 19.23rpx;
    color: white;
}

.com-ml-content-item>view>view:last-child {
    margin-top: auto;
    margin-bottom: 0;
}

.com-ml-content-item>view>view:last-child>text {
    font-size: 19.23rpx;
    height: 19.23rpx;
    line-height: 19.23rpx;
    color: #8E8E8E;
}

.com-ml-content-other {
    padding: 0;
}

.com-ml-content-other>view:first-child {
    width: 100%;
    margin-top: 38.46rpx;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.com-ml-content-other>view:first-child>view {
    width: 326.92rpx;
    height: 115.38rpx;
    background-color: white;
    border-radius: 10rpx;
    box-shadow: 0rpx 0rpx 8rpx -2rpx rgba(0, 0, 0, 0.25);
    display: flex;
    align-items: center;
}

.com-ml-content-other>view:first-child>view>uni-icons {
    margin-left: 19.23rpx;
    font-weight: bold;
}

.com-ml-content-other>view:first-child>view>text {
    font-size: 30.77rpx;
    height: 30.77rpx;
    line-height: 30.77rpx;
    color: #575757;
    margin-left: 26.92rpx;
    font-weight: bold;
}

.com-ml-content-other>view:last-child {
    width: 100%;
    height: calc(100vh - 346.15rpx - 38.46rpx - 115.38rpx - 38.46rpx);
    margin-top: 38.46rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    overflow: scroll;
    box-sizing: border-box;
    padding: 0 38.46rpx;
}

.com-ml-content-item-other {
    margin-top: 0;
    margin-bottom: 38.46rpx;
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