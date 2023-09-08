// app.js
import config from "./config";

App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        let open_id = "";
        wx.request({
          url: `${config.apiUrl}/wx/getOriginData`,
          method: "GET",
          data: {
            code: res.code
          },
          success: (res) => {
            open_id = res.data.data;
            
            wx.request({
              url: `${config.apiUrl}/user/getUserInfo`,
              method: "GET",
              data: {
                open_id: open_id
              },
              success: (res) => {
                if(res.data.data === undefined){
                  this.createUser(open_id);
                } else {
                  this.globalData.userInfo = res.data.data;
                }
              }
            })
          }
        })
      }
    })
  },

  createUser(open_id){
    wx.getUserInfo({
      success: (res) => {
        wx.request({
          url: `${config.apiUrl}/user/createUser`,
          method: "GET",
          data: {
            open_id: open_id,
            name: res.userInfo.nickName
          },
          success: (res) => {
            getApp().globalData.userInfo = res.data.data;
          }
        })
      }
    })
  },

  globalData: {
    userInfo: null,
  }
})
