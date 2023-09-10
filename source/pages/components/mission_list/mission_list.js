// pages/components/mission_list/mission_list.js
import config from "../../../config";
const app = getApp();
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    // datas: {
    //   type: Array,
    //   value: []
    // }
  },

  /**
   * 组件的初始数据
   */
  data: {
    currentItem: "other",
    showTypes: ["全部", "每日", "长期"],
    showIndex: 0,
    datas: [],
    debugData: [{
      checkState: 0,
      checkType: 1,
      combo: 0,
      createTime: "2023-08-31T14:46:28.000+00:00",
      description: "这是一个测试的打卡任务",
      finishTime: "2023-08-31T14:46:29.000+00:00",
      id: 1,
      img: "https://cdn.discordapp.com/attachments/1061969744758054952/1146800226070433872/image.png",
      name: "打卡任务1",
      ownerId: "3O8IT1ql8h",
      score: 10,
      state: 0,
      targetId: "3O8IT1ql8h",
      type: 1
    }],
    originData: [],
    missionData: [],
    checkInterval: null
  },

  ready() {
    if (this.data.checkInterval === null) {
      let interval = setInterval(() => {
        if (app.globalData.userInfo != null) {
          clearInterval(this.data.checkInterval);
          this.loadMissionData();
        }
      }, 100);
      this.setData({
        checkInterval: interval
      });
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    toggleItem(e) {
      let item = e.currentTarget.dataset.item;
      this.setData({
        currentItem: item,
        showIndex: 0
      });

      if (item == "mine") {
        this.loadMissionData();
      } else {
        this.loadTargetMissionData();
      }
    },

    toggleShowType() {
      if (this.data.showIndex >= this.data.showTypes.length - 1) {
        this.setData({
          showIndex: 0
        });
      } else {
        this.setData({
          showIndex: this.data.showIndex + 1
        });
      }

      //切换展示方式
      switch (this.data.showIndex) {
        case 0: {
          this.setData({
            missionData: this.data.originData
          });
          break;
        }

        case 1: {
          this.setData({
            missionData: this.data.originData.filter(item => item.type == 0)
          });
          break;
        }

        case 2: {
          this.setData({
            missionData: this.data.originData.filter(item => item.type == 1)
          });
          break;
        }
      }
    },

    loadMissionData() {
      wx.request({
        url: `${config.apiUrl}/mission/getListByOwnerId`,
        method: "GET",
        data: {
          ownerId: app.globalData.userInfo.userId
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
          for (let i = 0; i < res.data.data.length; i++) {
            let finishState = "未完成";
            if (res.data.data[i].type == 0 && res.data.data[i].state == 1) {
              finishState = "已完成";
            } else if (res.data.data[i].type == 1) {
              let finishTime = new Date(res.data.data[i].finishTime);
              let currentTime = new Date();
              if (currentTime.getFullYear() === finishTime.getFullYear() && currentTime.getMonth() == finishTime.getMonth() && currentTime.getDate() == finishTime.getDate()) {
                finishState = "已完成";
              }
            }

            res.data.data[i]["finishState"] = finishState;
          }

          this.setData({
            originData: res.data.data,
            missionData: res.data.data
          });
        }
      })
    },

    loadTargetMissionData() {
      if (app.globalData.userInfo.targetId === "" || app.globalData.userInfo.targetId === undefined) {
        this.setData({
          missionData: [],
          originData: []
        });
        return;
      }
      wx.request({
        url: `${config.apiUrl}/mission/getListByTargetId`,
        method: "GET",
        data: {
          userId: app.globalData.userInfo.userId,
          targetId: app.globalData.userInfo.targetId
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
          this.setData({
            originData: res.data.data,
            missionData: res.data.data
          });
        }
      })
    },

    openDetail(e) {
      let item = e.currentTarget.dataset.item;
      wx.redirectTo({
        url: `/pages/mission_detail/mission_detail?id=${item.id}`,
      });
    }
  }
})
