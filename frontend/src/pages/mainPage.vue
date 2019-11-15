<template>
  <div class="main-body">
    <div class="main-head">
      <div class="title">Timeline</div>
      <div class="buttom">
        <el-button type="primary" plain @click="update()">{{updateNum}} 更新</el-button>
      </div>
    </div>
    <div class="box"></div>
    <div class="infinite-list-wrapper" style="overflow:auto">
      <div
        class="list"
        v-infinite-scroll="load"
        infinite-scroll-disabled="disabled"
        v-if="message"
        >
        <div v-for="i in totalNum" :key="i" class="list-item" >
          <div class="t1">{{message[i-1][0]}}</div>
          <div class="t2">{{message[i-1][1]}}</div>

          <div class="t3">{{message[i-1][2]}}</div>
        </div>
        
      </div>
      <div v-if="loading" class="text">加载中...</div>
      <div v-if="noMore" class="text">没有更多了</div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'; 


  const getMessage = () => axios.post('/app/getMessage');
  const getNewMessage = (id) => axios.post('/app/getNewMessage',{id:id});
  const getUpdateMessage = (num) => axios.post('/app/getUpdateMessage',{id: num})
  const getMessageNum = () => axios.post('/app/getNum')

  export default {
    name: "Timeline",
    data () {
      return {
        postNum: 0, //一开始数据库中所有消息的数量
        newNum: 0, //最新的所有消息数量
        updateNum: 0, //更新的消息数量
        totalNum: 3, //目前页面加载的消息数量
        newMessageId: 3,
        loading: false,
        touchBottom: true,        
        message:[
           ["KBShinya","2019-11-15T15:00:35.000+0000","互动抽奖 #A3!满开剧团# wow这个游戏真的好多帅哥！好多可爱的弟弟！！！本新人名为立花洗发水，激情参与游戏，又可以养帅哥了！大家快来和我一起玩耍！转发动态，抽3人送B站年度大会员，抽1人送Popscent波谱香水（50ml，气味随机）"],
           ["tisumi-阿心","2019-11-15T13:51:05.000+0000","Wraxu真是个带恶人啊，这个删了不发,以及OGE的录播又改回了仅限订阅者观看，"],
           ["小牛不是老牛","2019-11-15T13:50:46.000+0000","狗群主进攻源氏才是本素材视频正戏开始！"]
        ],
      }
    },
    computed: {
      noMore () {
        return this.newMessageId <= 1 ;
      },
      disabled () {
        const el = document.querySelector('.infinite-list-wrapper');
        const offsetHeight = el.offsetHeight;
        el.onscroll = () => {         
          const scrollTop = el.scrollTop;
          const scrollHeight = el.scrollHeight;
          if ((offsetHeight + scrollTop) - scrollHeight >= -1) {
            this.touchBottom = false;
          }       
        }//滚动的时候判断是否滚到底
        var temp = this.loading || this.noMore
        return temp || this.touchBottom       
      },
    },
    methods: {
      //加载最新的消息
      load () {
        console.log("load")        
        this.loading = true
        console.log(this.loading)
        setTimeout(() => {
          var id = this.newMessageId;
          getNewMessage(id).then(data => {
            var date1 = data.data.timestamp.substr(0,10)+" ";
            var date2 = data.data.timestamp.slice(11,16);
            this.message.push([data.data.author,date1+date2,data.data.content,data.data.img]);
            this.newMessageId = data.data.id; //更新新消息的ID
            this.totalNum += 1; //目前的页面显示的消息数量加1
          });
          this.loading = false
          this.touchBottom = true
        }, 2000)
      },
      //获取最新的消息
      update(){
        var num = this.updateNum;
        this.postNum = this.postNum +　this.updateNum;
        
        getUpdateMessage(num).then(data => {
          var l = num;
          for(var i = 0; i  < l ; i++){
            var date1 = data.data[i].timestamp.substr(0,10)+" ";
            var date2 = data.data[i].timestamp.slice(11,16);
            this.message.unshift([data.data[i].author,date1+date2,data.data[i].content,data.data[i].img]);
          }
          this.totalNum += num;
          //在message数组的头部插入最新的消息
        })
      },
      //获取更新的消息数量
      getNum(){
        getMessageNum().then(data => {
          this.newNum = data.data;
        })
        this.updateNum = this.newNum - this.postNum;
      },
    },
    created(){
      
      //获取三条消息
      getMessage().then(data => {
        var l = data.data.length;
        this.message.length = 0;
        for(var i = 0; i  < l ; i++){
          var date1 = data.data[i].timestamp.substr(0,10)+" ";
          var date2 = data.data[i].timestamp.slice(11,16);
          
          console.log(date2);
          this.message.push([data.data[i].author,date1+date2,data.data[i].content,data.data[i].img]);
        }
        this.totalNum = l;
        this.newMessageId = data.data[l-1].id;
      });
      //获得所有消息的数量
      getMessageNum().then(data => {
        this.postNum = data.data;
        this.newNum = data.data;
      })
    },
    mounted() {

      this.timer = setInterval(this.getNum, 1000);
    },
    beforeDestroy() {
      clearInterval(this.timer);
    }
  }
</script>

<style lang="less" scoped>
  .main-body {
    width: 400px;
    height: 500px;
    margin:0 auto;
    border:1px solid  #71C7C6;
    border-radius: 8px;

    .main-head{
      height: 60px;
      .title{
        margin-left: 20px;
        display: inline;
        line-height: 60px;
        font-family: PingFangSC-Regular;
        font-size: 25px;
        color: #2B3852;
        letter-spacing: 0;
      }
      .buttom{
        display: inline;
        margin-left: 170px;
      }
    }
    .box {
       width: 100%;
       height: 2px;
       border: 1px solid #EEF0F4;
       margin-top:10px;
       margin-bottom: 20px;
     }
    .infinite-list-wrapper{
      height: 330px;
      overflow-y: auto;
      text-align: center;
      .list {
        height: 330px;
        padding: 0;
        margin: 0;
        list-style: none;
        .list-item {
          display: flex;
          align-items: center;
          justify-content: center;
          height: auto;
          background: #e8f3fe;
          margin: 10px;
          color: #7dbcfc;
          flex-wrap: wrap;
          .t1{
            color:#2B3852;
            display: flex;
            flex-direction: row;
            font-size: 12px;
            width: 100px;
            margin-top: 10px;
          }
          .t2{
            color:#2B3852;
            display: flex;
            flex-direction: row-reverse;
            font-size: 12px;
            width: 100px;
            margin-left: 150px;
            margin-top: 10px;
          }   
          
          
          .t3{
            color:#2B3852;
            display: flex;
            float: left;
            flex-direction: column-reverse;
            text-align:left;
            font-size: 15px;
            margin-top: 20px;
            margin-bottom: 20px;
            margin-left: 6px;
          }
        }
      }
      .text{
        position: absolute;
        margin-left: 170px;
        //bottom: 0;
        //display: inline;
        font-size: 14px;
        color: #5e6d82;

      }
      
    }
    

  }

  
</style>


